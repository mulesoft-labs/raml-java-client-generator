package org.mule.client.codegen;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.mule.client.codegen.utils.SecuritySchemesHelper.BASIC_AUTHENTICATION;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.mule.client.codegen.clientgenerator.Jersey2RestClientGeneratorImpl;
import org.mule.client.codegen.model.JTypeWithMimeType;
import org.mule.client.codegen.security.BasicAuthClientGenerator;
import org.mule.client.codegen.security.NoSecuredClientGenerator;
import org.mule.client.codegen.security.SecurityClientGenerator;
import org.mule.client.codegen.utils.MimeTypeHelper;
import org.mule.client.codegen.utils.NameHelper;
import org.mule.client.codegen.utils.SecuritySchemesHelper;
import org.mule.client.codegen.utils.TypeConstants;
import org.mule.raml.ApiModelLoader;
import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.Resource;
import org.mule.raml.model.Response;
import org.mule.raml.model.SecurityScheme;
import org.mule.raml.model.TypeFieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class RamlJavaClientGenerator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String OK_RESPONSE = "200";
    public static final String CREATED_RESPONSE = "201";
    public static final String ACCEPTED_RESPONSE = "202";

    // Code generation constants
    public static final String RESPONSE_CLASS_SUFFIX = "Response";
    public static final String HEADER_CLASS_SUFFIX = "Header";
    public static final String QUERY_PARAM_CLASS_SUFFIX = "QueryParam";
    public static final String BODY_CLASS_SUFFIX = "Body";
    public static final String CLIENT_CLASS_SUFFIX = "Client";

    public static final String PRIVATE_FIELD_PREFIX = "_";

    public static final String PACKAGE_SEPARATOR = ".";
    public static final String MODEL_PACKAGE_NAME = "model";
    public static final String BASE_URL_FIELD_NAME = "baseUrl";
    public static final String URI_PARAM_FIELD_NAME = "uriParam";
    public static final String GET_BASE_URI_METHOD_NAME = "getBaseUri";
    public static final String CLIENT_FIELD_NAME = "_client";

    // Types
    private static Map<String, Class<?>> CLASS_BY_TYPE = new HashMap<>();

    {
        CLASS_BY_TYPE.put(TypeConstants.STRING, String.class);
        CLASS_BY_TYPE.put(TypeConstants.NUMBER, Double.class);
        CLASS_BY_TYPE.put(TypeConstants.INTEGER, Integer.class);
        CLASS_BY_TYPE.put(TypeConstants.DATE, Date.class);
        CLASS_BY_TYPE.put(TypeConstants.FILE, File.class);
        CLASS_BY_TYPE.put(TypeConstants.BOOLEAN, Boolean.class);
    }

    private String basePackage;
    private File targetFolder;
    private CodeGenConfig codeGenConfig;
    private RestClientGenerator clientGenerator;
    private OutputVersion outputVersion;

    // This two properties hold state so maybe should be local and pass through
    private Map<String, JType> globalTypes;
    private Map<String, Pair<JDefinedClass, JMethod>> resourceClasses;


    public RamlJavaClientGenerator(String basePackage, File targetFolder) {
        this(basePackage, targetFolder, OutputVersion.v1, new CodeGenConfig());
    }

    public RamlJavaClientGenerator(String basePackage, File targetFolder, OutputVersion outputVersion, CodeGenConfig codeGenConfig) {
        this.basePackage = basePackage;
        this.targetFolder = targetFolder;
        this.codeGenConfig = codeGenConfig;
        this.clientGenerator = new Jersey2RestClientGeneratorImpl();
        this.resourceClasses = new HashMap<>();
        this.globalTypes = new HashMap<>();
        this.outputVersion = outputVersion;
    }

    public void generate(URL ramlFile) throws JClassAlreadyExistsException, IOException {
        if (ramlFile == null) {
            throw new IllegalArgumentException("Url can not be null ");
        }
        globalTypes.clear();
        resourceClasses.clear();
        logger.info("Start generating for " + ramlFile);
        try (final InputStreamReader inputStreamReader = new InputStreamReader(ramlFile.openStream())) {
            final ApiModel raml = ApiModelLoader.build(inputStreamReader, ramlFile.toExternalForm());
            logger.info("Parsed successfully " + ramlFile);
            generate(raml);
        }

    }

    private void generate(ApiModel raml) throws JClassAlreadyExistsException, IOException {
        final Map<String, Resource> resources = raml.getResources();
        final JCodeModel cm = new JCodeModel();
        final List<Pair<String, SecurityScheme>> supportedSecuritySchemes = SecuritySchemesHelper.getSupportedSecuritySchemes(raml);
        final List<Map<String, String>> schemas = raml.getSchemas();
        final List<JFieldVar> generatedRequiredField = new ArrayList<>();
        SecurityClientGenerator clientGenerator = new NoSecuredClientGenerator();

        for (Map<String, String> schema : schemas) {
            for (Map.Entry<String, String> schemaEntry : schema.entrySet()) {
                String packageName = basePackage + PACKAGE_SEPARATOR + MODEL_PACKAGE_NAME;
                String value = schemaEntry.getValue();
                final JType schemaType = generatePojoFromSchema(cm, NameHelper.toValidClassName(schemaEntry.getKey()), packageName, value, SourceType.JSONSCHEMA);
                globalTypes.put(value, schemaType);
            }
        }

        this.clientGenerator.buildCustomException(cm, basePackage, raml.getTitle());
        if (outputVersion.ordinal() >= OutputVersion.v2.ordinal()) {
            this.clientGenerator.buildCustomResponse(cm, basePackage, raml);
        }

        final JDefinedClass containerClientClass = cm
                ._class(basePackage + PACKAGE_SEPARATOR + "api" + PACKAGE_SEPARATOR + NameHelper.toValidClassName(raml.getTitle()) + CLIENT_CLASS_SUFFIX);

        //Check if exist a supported security exist
        if (!supportedSecuritySchemes.isEmpty()) {
            Pair<String, SecurityScheme> stringSecuritySchemePair = supportedSecuritySchemes.get(0);
            switch (stringSecuritySchemePair.getValue().getType()) {
                case BASIC_AUTHENTICATION: {
                    generatedRequiredField.addAll(SecuritySchemesHelper.createBasicAuthFields(containerClientClass));
                    clientGenerator = new BasicAuthClientGenerator(generatedRequiredField);
                    break;
                }
            }
        }

        final JFieldVar baseUriField = containerClientClass.field(JMod.PRIVATE, String.class, "_" + BASE_URL_FIELD_NAME);

        final JMethod getClientMethod = clientGenerator.createClient(containerClientClass);
        JMethod getClientWithMultipart = null;
        if (hasMultipart(resources)) {
            getClientWithMultipart = clientGenerator.createClientWithMultipart(containerClientClass);
        }

        JMethod defaultConstructor = null;
        if (isBlank(raml.getBaseUri())) {
            //Only if it is blank we need an empty constructor if not it will be provided
            defaultConstructor = containerClientClass.constructor(JMod.PUBLIC);
            defaultConstructor.body().assign(baseUriField, JExpr._null());
            for (JFieldVar jFieldVar : generatedRequiredField) {
                defaultConstructor.body().assign(jFieldVar, JExpr._null());
            }
        }

        final JMethod containerConstructor = containerClientClass.constructor(JMod.PUBLIC);
        final JVar baseUriParam = containerConstructor.param(String.class, BASE_URL_FIELD_NAME);

        for (JFieldVar jFieldVar : generatedRequiredField) {
            JVar param = containerConstructor.param(jFieldVar.type(), jFieldVar.name());
            containerConstructor.body().assign(JExpr._this().ref(jFieldVar), param);
        }

        containerConstructor.body().assign(baseUriField, baseUriParam);

        containerClientClass.method(JMod.PROTECTED, String.class, GET_BASE_URI_METHOD_NAME).body()._return(baseUriField);

        final JMethod factoryMethod = containerClientClass.method(JMod.PUBLIC | JMod.STATIC, containerClientClass, "create");
        final JVar baseUrlFactoryParam = factoryMethod.param(cm.ref(String.class), BASE_URL_FIELD_NAME);

        for (JFieldVar field : generatedRequiredField) {
            factoryMethod.param(field.type(), field.name());
        }

        JInvocation invocation = JExpr._new(containerClientClass).arg(baseUrlFactoryParam);

        for (JVar generatedMethodRequiredVar : generatedRequiredField) {
            invocation.arg(generatedMethodRequiredVar);
        }

        factoryMethod.body()._return(invocation);

        if (raml.getDocumentation() != null && !raml.getDocumentation().isEmpty()) {
            containerClientClass.javadoc().add(raml.getDocumentation().get(0).getContent());
        }

        if (StringUtils.isNotBlank(raml.getBaseUri())) {
            JMethod constructor = containerClientClass.constructor(JMod.PUBLIC);
            JInvocation thisInvocation = JExpr.invoke("this");
            thisInvocation.arg(JExpr.lit(raml.getBaseUri().replaceAll("/$", "")));
            for (JVar var : generatedRequiredField) {
                JVar param = constructor.param(var.type(), var.name());
                thisInvocation.arg(param);
            }
            constructor.body().add(thisInvocation);

            JMethod create = containerClientClass.method(JMod.PUBLIC | JMod.STATIC, containerClientClass, "create");
            JInvocation newInvocation = JExpr._new(containerClientClass);

            for (JFieldVar var : generatedRequiredField) {
                JVar param = create.param(var.type(), var.name());
                newInvocation.arg(param);
            }
            create.body()._return(newInvocation);

        }

        buildResourceClass(cm, containerClientClass, defaultConstructor, containerConstructor, resources, "", getClientMethod, getClientWithMultipart, raml);


        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        // TODO add a output or disable output
        cm.build(targetFolder, targetFolder, null);
        logger.info("Finished Generation");
    }

    private void buildResourceClass(JCodeModel cm, JDefinedClass containerClass, JMethod defaultContainerConstructor, JMethod containerConstructor, Map<String, Resource> resources, String containerResource,
                                    JMethod getClientMethod, JMethod getClientWithMultipart, ApiModel apiModel) throws JClassAlreadyExistsException, IOException {

        for (Map.Entry<String, Resource> stringResourceEntry : resources.entrySet()) {
            JDefinedClass parentClass = containerClass;
            JMethod parentConstructor = containerConstructor;
            JMethod parentDefaultConstructor = defaultContainerConstructor;
            String parentResource = containerResource;
            final String resourcesName = stringResourceEntry.getKey();
            final String[] resourceParts = resourcesName.split("/");
            if (resourceParts.length > 1) {
                for (int i = 1; i < resourceParts.length; i++) {
                    final String resourceName = resourceParts[i];
                    final Resource resource = stringResourceEntry.getValue();
                    final String resourcePath = parentResource + "/" + resourceName;
                    logger.info("-" + resourcePath);
                    final JDefinedClass resourceClass;
                    JMethod defaultConstructor = null;
                    final JMethod resourceConstructor;
                    //Check if it is not already defined

                    if (!resourceClasses.containsKey(resourcePath)) {
                        final String resourceDescription = resource.getDescription();
                        //Generate resource class
                        resourceClass = cm._class(getResourcePackage(resourcePath) + PACKAGE_SEPARATOR + NameHelper.toValidClassName(resourceName));
                        if (StringUtils.isNotEmpty(resourceDescription)) {
                            resourceClass.javadoc().add(resourceDescription);
                        }

                        final JMethod getClient = resourceClass.method(JMod.PROTECTED, Client.class, "getClient");
                        getClient.body()._return(JExpr._this().ref(CLIENT_FIELD_NAME));

                        final JFieldVar baseUrlField = resourceClass.field(JMod.PRIVATE, String.class, PRIVATE_FIELD_PREFIX + BASE_URL_FIELD_NAME);
                        final JFieldVar clientField = resourceClass.field(JMod.PRIVATE, Client.class, CLIENT_FIELD_NAME);

                        defaultConstructor = resourceClass.constructor(JMod.PUBLIC);
                        defaultConstructor.body().assign(baseUrlField, JExpr._null());
                        defaultConstructor.body().assign(clientField, JExpr._null());

                        resourceConstructor = resourceClass.constructor(JMod.PUBLIC);
                        final JVar baseUrlParam = resourceConstructor.param(String.class, BASE_URL_FIELD_NAME);
                        final JVar clientParam = resourceConstructor.param(Client.class, CLIENT_FIELD_NAME);
                        final JMethod getResourceMethod = resourceClass.method(JMod.PRIVATE, String.class, GET_BASE_URI_METHOD_NAME);
                        if (isURIParameter(resourceName)) {
                            //Add constructor additional parameter for uriParam
                            final JVar uriParamConstructorParam = resourceConstructor.param(String.class, URI_PARAM_FIELD_NAME);

                            //Link with parent as method
                            final String uriParameterName = resourceName.substring(1, resourceName.length() - 1);
                            final JMethod resourceFactoryMethod = parentClass.method(JMod.PUBLIC, resourceClass, NameHelper.toValidFieldName(uriParameterName));
                            if (StringUtils.isNotEmpty(resourceDescription)) {
                                resourceFactoryMethod.javadoc().add(resourceDescription);
                            }
                            final JVar uriParam = resourceFactoryMethod.param(String.class, NameHelper.toValidFieldName(uriParameterName));
                            resourceFactoryMethod.body()._return(JExpr._new(resourceClass).arg(JExpr.invoke(GET_BASE_URI_METHOD_NAME)).arg(JExpr.invoke(getClient)).arg(uriParam));

                            final JInvocation encodedUriParam = cm.ref(URLEncoder.class).staticInvoke("encode").arg(uriParamConstructorParam);
                            resourceConstructor.body().assign(baseUrlField, baseUrlParam.plus(JExpr.lit("/").plus(encodedUriParam)));
                            resourceConstructor.body().assign(JExpr._this().ref(clientField), clientParam);

                        } else {
                            resourceConstructor.body().assign(baseUrlField, baseUrlParam.plus(JExpr.lit("/" + resourceName)));
                            resourceConstructor.body().assign(JExpr._this().ref(clientField), clientParam);
                            //Link with parent as field
                            final JFieldVar resourceField = parentClass.field(JMod.PUBLIC | JMod.FINAL, resourceClass, NameHelper.toValidFieldName(resourceName));
                            if (StringUtils.isNotEmpty(resourceDescription)) {
                                resourceField.javadoc().add(resourceDescription);
                            }

                            if (!hasMultipart(resource)) {
                                parentConstructor.body()
                                        .assign(resourceField, JExpr._new(resourceClass).arg(JExpr.invoke(GET_BASE_URI_METHOD_NAME)).arg(JExpr.invoke(getClientMethod)));
                            } else {
                                parentConstructor.body()
                                        .assign(resourceField, JExpr._new(resourceClass).arg(JExpr.invoke(GET_BASE_URI_METHOD_NAME)).arg(JExpr.invoke(getClientWithMultipart)));
                            }

                            if (parentDefaultConstructor != null) {
                                parentDefaultConstructor.body().assign(resourceField, JExpr._null());
                            }
                        }

                        this.clientGenerator.resolveBaseURI(cm, getResourceMethod, baseUrlField);
                        this.resourceClasses.put(resourcePath, new ImmutablePair<>(resourceClass, resourceConstructor));
                    } else {
                        final Pair<JDefinedClass, JMethod> resourcePair = this.resourceClasses.get(resourcePath);
                        resourceClass = resourcePair.getLeft();
                        resourceConstructor = resourcePair.getRight();
                        defaultConstructor = resourceClass.getConstructor(new JType[]{});
                    }

                    parentClass = resourceClass;
                    parentConstructor = resourceConstructor;
                    parentDefaultConstructor = defaultConstructor;
                    parentResource = resourcePath;

                    //Only last resource should trigger children and actions
                    if (i == resourceParts.length - 1) {
                        buildActionMethods(cm, resourceClass, resource, resourcePath, resourceName, apiModel);
                        buildResourceClass(cm, resourceClass, parentDefaultConstructor, resourceConstructor, resource.getResources(), resourcePath, getClientMethod, getClientWithMultipart, apiModel);
                    }
                }
            }
        }
    }

    /**
     * @param resource starting point ;]
     * @return true if any of the resource's action, or any of it's resources' actions childs is multipart
     */
    private boolean hasMultipart(Resource resource) {
        final Map<ActionType, Action> actions = resource.getActions();
        for (Map.Entry<ActionType, Action> actionTypeActionEntry : actions.entrySet()) {
            final Action action = actionTypeActionEntry.getValue();
            boolean multipart = hasMultipart(action);
            if (multipart) {
                return true;
            }
        }
        return hasMultipart(resource.getResources());
    }

    private boolean hasMultipart(Map<String, Resource> resources) {
        for (Map.Entry<String, Resource> stringResourceEntry : resources.entrySet()) {
            boolean multipart = hasMultipart(stringResourceEntry.getValue());
            if (multipart) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMultipart(Action action) {
        if (action.getBody() != null) {
            for (MimeType mimeType : action.getBody().values()) {
                final MimeType body = mimeType;
                if (MimeTypeHelper.isMultiPartType(body) || MimeTypeHelper.isFormUrlEncodedType(body)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getResourcePackage(String resourcePath) {
        return basePackage + PACKAGE_SEPARATOR + "resource" + PACKAGE_SEPARATOR + NameHelper.toResourcePathToPackageName(resourcePath);
    }

    private boolean isURIParameter(String resourceName) {
        return resourceName.startsWith("{") && resourceName.endsWith("}");
    }

    private void buildActionMethods(JCodeModel cm, JDefinedClass resourceClass, Resource resource, String resourcePath, String resourceName, ApiModel apiModel)
            throws IOException, JClassAlreadyExistsException {
        final Map<ActionType, Action> actions = resource.getActions();
        for (Map.Entry<ActionType, Action> actionTypeActionEntry : actions.entrySet()) {
            final ActionType actionType = actionTypeActionEntry.getKey();
            final Action action = actionTypeActionEntry.getValue();
            logger.info("  " + action.getType() + "");

            final Response response = getActionResponse(action);
            final List<JTypeWithMimeType> bodiesType = buildBodyType(cm, actionType, action, resourcePath, resourceName);
            final JTypeWithMimeType returnType = buildReturnType(cm, actionType, response, resourcePath, resourceName);
            final JType queryParameterType = buildQueryParametersType(cm, actionType, action, resourcePath, resourceName);
            final JType headerParameterType = buildHeaderType(cm, resourcePath, resourceName, actionType, action);
            if (bodiesType.isEmpty()) {
                clientGenerator.callHttpMethod(cm, resourceClass, returnType, outputVersion, null, queryParameterType, headerParameterType, action, apiModel);
            } else {
                for (JTypeWithMimeType bodyType : bodiesType) {
//                    final MimeType type = bodyType.getMimeType();
//                    if (MimeTypeHelper.isMultiPartType(type)) {
//                        //TODO lautaro do something.
//                        asdasd
//                    }
                    clientGenerator.callHttpMethod(cm, resourceClass, returnType, outputVersion, bodyType, queryParameterType, headerParameterType, action, apiModel);
                }
            }
        }
    }

    private Response getActionResponse(Action action) {
        Response response = action.getResponses().get(OK_RESPONSE);
        if (response == null) {
            response = action.getResponses().get(CREATED_RESPONSE);
        }
        if (response == null) {
            response = action.getResponses().get(ACCEPTED_RESPONSE);
        }
        return response;
    }

    private JTypeWithMimeType buildReturnType(JCodeModel cm, ActionType actionType, Response response, String resourcePath, String resourceName) throws IOException {
        JTypeWithMimeType returnType = new JTypeWithMimeType(cm.VOID, null);
        if (response != null) {

            if (response.getBody() != null) {
                final Iterator<Map.Entry<String, MimeType>> bodies = response.getBody().entrySet().iterator();
                if (bodies.hasNext()) {
                    final Map.Entry<String, MimeType> bodyEntry = bodies.next();
                    final MimeType mimeType = bodyEntry.getValue();
                    if (MimeTypeHelper.isJsonType(mimeType)) {
                        String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + RESPONSE_CLASS_SUFFIX;
                        if (outputVersion.ordinal() >= OutputVersion.v2.ordinal()) {
                            className = className + BODY_CLASS_SUFFIX;
                        }
                        returnType = new JTypeWithMimeType(getOrGeneratePojoFromJsonSchema(cm, resourcePath, mimeType, className), mimeType);

                    } else if (MimeTypeHelper.isTextType(mimeType)) {
                        returnType = new JTypeWithMimeType(cm.ref(String.class), mimeType);
                    } else if (MimeTypeHelper.isBinaryType(mimeType)) {
                        returnType = new JTypeWithMimeType(cm.ref(InputStream.class), mimeType);
                    } else {
                        returnType = new JTypeWithMimeType(cm.ref(Object.class), mimeType);
                    }
                }
            }
        }
        return returnType;
    }

    private JType buildHeaderType(JCodeModel cm, String resourcePath, String resourceName, ActionType actionType, Action action) throws JClassAlreadyExistsException {
        final Map<String, TypeFieldDefinition> headers = action.getHeaders();
        return buildMap(cm, actionType, resourcePath, resourceName, headers, HEADER_CLASS_SUFFIX);
    }

    private JType buildQueryParametersType(JCodeModel cm, ActionType actionType, Action action, String resourcePath, String resourceName) throws JClassAlreadyExistsException {
        final Map<String, TypeFieldDefinition> queryParameters = action.getQueryParameters();
        return buildMap(cm, actionType, resourcePath, resourceName, queryParameters, QUERY_PARAM_CLASS_SUFFIX);
    }

    private JType buildMap(JCodeModel cm, ActionType actionType, String resourcePath, String resourceName, Map<String, TypeFieldDefinition> queryParameters, String queryParamClassSuffix) throws JClassAlreadyExistsException {
        JType queryParameterType = null;
        if (queryParameters != null && !queryParameters.isEmpty()) {
            final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + queryParamClassSuffix;
            queryParameterType = toParametersJavaBean(cm, className, queryParameters, resourcePath);
        }
        return queryParameterType;
    }

    private List<JTypeWithMimeType> buildBodyType(JCodeModel cm, ActionType actionType, Action action, String resourcePath, String resourceName)
            throws IOException, JClassAlreadyExistsException {
        final List<JTypeWithMimeType> result = new ArrayList<>();

        if (action.getBody() != null) {
            for (MimeType mimeType : action.getBody().values()) {
                final JType bodyType;
                final MimeType body = mimeType;
                final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + BODY_CLASS_SUFFIX;
                if (MimeTypeHelper.isJsonType(body)) {
                    bodyType = getOrGeneratePojoFromJsonSchema(cm, resourcePath, body, className);
                } else if (MimeTypeHelper.isTextType(body)) {
                    bodyType = cm.ref(String.class);
                } else if (MimeTypeHelper.isBinaryType(body)) {
                    bodyType = cm.ref(InputStream.class);
                } else if (MimeTypeHelper.isMultiPartType(body) || MimeTypeHelper.isFormUrlEncodedType(body)) {
                    final Map<String, TypeFieldDefinition> formParameters = body.getFormParameters();
                    bodyType = toParametersJavaBean(cm, className, formParameters, resourcePath);
                } else {
                    bodyType = cm.ref(Object.class);
                }
                if (bodyType != null) {
                    result.add(new JTypeWithMimeType(bodyType, mimeType));
                } else {
                    logger.info("No type was inferred for body type at " + resourcePath + " on method " + action.getType());
                }
            }
        }
        return result;
    }

    private JType getOrGeneratePojoFromJsonSchema(JCodeModel cm, String resourcePath, MimeType mimeType, String className) throws IOException {
        JType type = null;

        if (StringUtils.isNotBlank(mimeType.getSchema())) {
            if (globalTypes.containsKey(mimeType.getSchema())) {
                type = globalTypes.get(mimeType.getSchema());
            } else {
                type = generatePojoFromSchema(cm, className, getModelPackage(resourcePath), mimeType.getSchema(), mimeType.getSchemaPath(), SourceType.JSONSCHEMA);
            }
        } else if (StringUtils.isNotBlank(mimeType.getExample())) {
            type = generatePojoFromSchema(cm, className, getModelPackage(resourcePath), mimeType.getExample(), SourceType.JSON);
        }

        if (type != null && type.fullName().equals("java.lang.Object") && StringUtils.isNotBlank(mimeType.getExample())) {
            type = generatePojoFromSchema(cm, className, getModelPackage(resourcePath), mimeType.getExample(), SourceType.JSON);
        }

        if (type == null) {
            type = cm.ref(Object.class);
        }

        return type;
    }

    private String getModelPackage(String resourcePath) {
        return getResourcePackage(resourcePath) + PACKAGE_SEPARATOR + MODEL_PACKAGE_NAME;
    }

    public JType toParametersJavaBean(JCodeModel cm, String className, Map<String, TypeFieldDefinition> paramMap, String resourcePath) throws JClassAlreadyExistsException {
        final JDefinedClass paramsClass = cm._class(getModelPackage(resourcePath) + PACKAGE_SEPARATOR + className);
        final JMethod paramsConstructor = paramsClass.constructor(JMod.PUBLIC);

        for (Map.Entry<String, TypeFieldDefinition> parameterEntry : paramMap.entrySet()) {
            String parameterName = parameterEntry.getKey();
            TypeFieldDefinition typeFieldDefinition = parameterEntry.getValue();
            String defaultValueDefinition = typeFieldDefinition.getDefaultValue();
            String descriptionDefinition = typeFieldDefinition.getDescription();

            Class<?> fieldType = CLASS_BY_TYPE.get(typeFieldDefinition.getType());
            fieldType = fieldType != null ? fieldType : String.class;

            final JFieldVar paramField;

            if (StringUtils.isNotBlank(defaultValueDefinition)) {
                final JExpression defaultValue;
                switch (typeFieldDefinition.getType()) {
                    case TypeConstants.STRING:
                        defaultValue = JExpr.lit(defaultValueDefinition);
                        break;
                    case TypeConstants.NUMBER:
                        defaultValue = JExpr.lit(Double.parseDouble(defaultValueDefinition));
                        break;
                    case TypeConstants.INTEGER:
                        defaultValue = JExpr.lit(Integer.parseInt(defaultValueDefinition));
                        break;
                    case TypeConstants.DATE:
                        // TODO What to do here?
                        defaultValue = JExpr._null();
                        break;
                    case TypeConstants.FILE:
                        // TODO What to do here?
                        defaultValue = JExpr._null();
                        break;
                    case TypeConstants.BOOLEAN:
                        // TODO What to do here?
                        defaultValue = JExpr.lit(Boolean.parseBoolean(defaultValueDefinition));
                        break;
                    default:
                        defaultValue = JExpr._null();
                        break;
                }
                paramField = paramsClass.field(JMod.PRIVATE, fieldType, PRIVATE_FIELD_PREFIX + NameHelper.toValidFieldName(parameterName), defaultValue);
            } else {
                paramField = paramsClass.field(JMod.PRIVATE, fieldType, PRIVATE_FIELD_PREFIX + NameHelper.toValidFieldName(parameterName));
            }

            if (StringUtils.isNotBlank(descriptionDefinition)) {
                paramField.javadoc().add(descriptionDefinition);
            }
            if (typeFieldDefinition.isRequired()) {
                // In constructor
                final JVar paramParam = paramsConstructor.param(fieldType, NameHelper.toValidFieldName(parameterName));
                paramsConstructor.body().assign(paramField, paramParam);
                if (StringUtils.isNotBlank(descriptionDefinition)) {
                    paramsConstructor.javadoc().addParam(paramParam).append(descriptionDefinition);
                }
            } else {
                // Setter
                final JMethod builderMethod = paramsClass.method(JMod.PUBLIC, paramsClass, NameHelper.getWithName(parameterName));
                final JVar paramParam = builderMethod.param(fieldType, NameHelper.toValidFieldName(parameterName));
                if (StringUtils.isNotBlank(descriptionDefinition)) {
                    builderMethod.javadoc().addParam(paramParam).append(descriptionDefinition);
                }
                builderMethod.body().assign(paramField, paramParam);
                builderMethod.body()._return(JExpr._this());
            }

            // Setter
            final JMethod setterMethod = paramsClass.method(JMod.PUBLIC, cm.VOID, NameHelper.getSetterName(parameterName));
            final JVar paramParam = setterMethod.param(fieldType, NameHelper.toValidFieldName(parameterName));
            setterMethod.body().assign(paramField, paramParam);

            // Getter
            final JMethod getterMethod = paramsClass.method(JMod.PUBLIC, fieldType, NameHelper.getGetterName(parameterName));
            getterMethod.body()._return(paramField);
            if (StringUtils.isNotBlank(descriptionDefinition)) {
                getterMethod.javadoc().addReturn().append(descriptionDefinition);
            }
        }

        return paramsClass;
    }

    public JType generatePojoFromSchema(JCodeModel codeModel, String className, String packageName, String json, SourceType sourceType) throws IOException {
        return generatePojoFromSchema(codeModel, className, packageName, json, null, sourceType);
    }

    public JType generatePojoFromSchema(JCodeModel codeModel, String className, String packageName, String json, String url, SourceType sourceType) throws IOException {
        try {
            SchemaMapper schemaMapper = new SchemaMapper(getRuleFactory(sourceType, codeGenConfig), new SchemaGenerator());
            if (SourceType.JSON == sourceType) {
                return schemaMapper.generate(codeModel, className, packageName, json);
            } else {
                URI uri;
                if (url == null) {
                    File tmpFile = File.createTempFile("tmp", "json");
                    try (FileWriter writer = new FileWriter(tmpFile)) {
                        writer.write(json);
                    }
                    uri = tmpFile.toURI();
                } else {
                    uri = URI.create(url);
                }

                return schemaMapper.generate(codeModel, className, packageName, json, uri);
            }
        } catch (JsonParseException e) {
            logger.info("Can not generate  " + className + " from schema since : " + e.getMessage());
            return codeModel.ref(String.class);
        }
    }


    private RuleFactory getRuleFactory(final SourceType sourceType, CodeGenConfig codeGenConfig) {
        final DefaultGenerationConfig generationConfig = new JsonSchemaGeneratorConfiguration(sourceType, codeGenConfig);

        return new RuleFactory(generationConfig, new Jackson2Annotator(generationConfig), new SchemaStore());
    }

    private static class JsonSchemaGeneratorConfiguration extends DefaultGenerationConfig {
        private SourceType sourceType;
        private CodeGenConfig codeGenConfig;

        public JsonSchemaGeneratorConfiguration(SourceType sourceType, CodeGenConfig codeGenConfig) {
            this.sourceType = sourceType;
            this.codeGenConfig = codeGenConfig;
        }

        @Override
        public SourceType getSourceType() {
            return this.sourceType;
        }

        @Override
        public boolean isUseLongIntegers() {
            return true;
        }

        @Override
        public boolean isIncludeConstructors() {
            return true;
        }

        @Override
        public boolean isConstructorsRequiredPropertiesOnly() {
            return false;
        }

        @Override
        public boolean isGenerateBuilders() {
            return true;
        }

        @Override
        public Language getTargetLanguage() {
            return super.getTargetLanguage();
        }

        @Override
        public boolean isIncludeAdditionalProperties() {
            return codeGenConfig.getIncludeAdditionalProperties();
        }

        @Override
        public boolean isUseOptionalForGetters() {
            return codeGenConfig.getUseJava8Optional();
        }

        @Override
        public String getDateTimeType() {
            return codeGenConfig.getUseJava8Dates() ? "java.time.LocalDateTime" : null;
        }

        @Override
        public String getDateType() {
            return codeGenConfig.getUseJava8Dates() ? "java.time.LocalDate" : null;
        }

        @Override
        public String getTimeType() {
            return codeGenConfig.getUseJava8Dates() ? "java.time.LocalTime" : null;
        }

        @Override
        public boolean isUseBigDecimals() {
            return codeGenConfig.getUseBigDecimals();
        }

        @Override
        public String getTargetVersion() {
            return codeGenConfig.getTargetVersion();
        }
    }
}
