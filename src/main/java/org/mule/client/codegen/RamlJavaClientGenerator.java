package org.mule.client.codegen;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.codemodel.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.mule.client.codegen.clientgenerator.Jersey2RestClientGeneratorImpl;
import org.mule.client.codegen.utils.NameHelper;
import org.raml.model.*;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.Header;
import org.raml.model.parameter.QueryParameter;
import org.raml.parser.visitor.RamlDocumentBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RamlJavaClientGenerator {


    public static final String PACKAGE_SEPARATOR = ".";
    public static final String APPLICATION_JSON_MIME_TYPE = "application/json";
    public static final String OK_RESPONSE = "200";

    public static final String BASE_URL_FIELD_NAME = "baseUrl";
    public static final String URI_PARAM_FIELD_NAME = "uriParam";

    public static final String PRIVATE_FIELD_PREFIX = "_";

    public static final String GET_BASE_URI_METHOD_NAME = "getBaseUri";


    private String basePackage;
    private File targetFolder;
    private RestClientGenerator clientGenerator;
    private Map<String, Pair<JDefinedClass, JMethod>> resourceClasses;

    public RamlJavaClientGenerator(String basePackage, File targetFolder) {
        this.basePackage = basePackage;
        this.targetFolder = targetFolder;
        this.clientGenerator = new Jersey2RestClientGeneratorImpl();
        this.resourceClasses = new HashMap<>();
    }

    public void generate(URL ramlFile) throws JClassAlreadyExistsException, IOException {
        System.out.println("Start generating for " + ramlFile);
        final Raml raml = new RamlDocumentBuilder().build(ramlFile.openStream(), ramlFile.toExternalForm());
        System.out.println("Parsed successfully " + ramlFile);
        generate(raml);

    }

    private void generate(Raml raml) throws JClassAlreadyExistsException, IOException {
        final Map<String, Resource> resources = raml.getResources();
        final JCodeModel cm = new JCodeModel();
        final JDefinedClass containerClass = cm._class(basePackage + PACKAGE_SEPARATOR + "api" + PACKAGE_SEPARATOR + NameHelper.toValidClassName(raml.getTitle()) + "Client");
        final JFieldVar baseUriField = containerClass.field(JMod.PRIVATE, String.class, "_" + BASE_URL_FIELD_NAME);
        final JMethod containerConstructor = containerClass.constructor(JMod.PUBLIC);
        final JVar baseUriParam = containerConstructor.param(String.class, BASE_URL_FIELD_NAME);
        containerConstructor.body().assign(baseUriField, baseUriParam);
        containerClass.method(JMod.PROTECTED, String.class, GET_BASE_URI_METHOD_NAME).body()._return(baseUriField);

        if (raml.getDocumentation() != null && !raml.getDocumentation().isEmpty()) {
            containerClass.javadoc().add(raml.getDocumentation().get(0).getContent());
        }

        if (StringUtils.isNotBlank(raml.getBaseUri())) {
            containerClass.constructor(JMod.PUBLIC).body().invoke("this").arg(JExpr.lit(raml.getBaseUri()));
        }

        buildResourceClass(cm, containerClass, containerConstructor, resources, "");
        cm.build(targetFolder);
        System.out.println("Finished Generation");
    }

    private void buildResourceClass(JCodeModel cm, JDefinedClass containerClass, JMethod containerConstructor, Map<String, Resource> resources, String containerResource) throws JClassAlreadyExistsException, IOException {

        for (Map.Entry<String, Resource> stringResourceEntry : resources.entrySet()) {
            JDefinedClass parentClass = containerClass;
            JMethod parentConstructor = containerConstructor;
            String parentResource = containerResource;
            final String resourcesName = stringResourceEntry.getKey();
            final String[] resourceParts = resourcesName.split("/");
            if (resourceParts.length > 1) {
                for (int i = 1; i < resourceParts.length; i++) {
                    final String resourceName = resourceParts[i];
                    final Resource resource = stringResourceEntry.getValue();
                    final String resourcePath = parentResource + "/" + resourceName;
                    System.out.println("Resource -> " + resourcePath);
                    final JDefinedClass resourceClass;
                    final JMethod resourceConstructor;
                    //Check if it is not already defined

                    if (!resourceClasses.containsKey(resourcePath)) {
                        final String resourceDescription = resource.getDescription();
                        //Generate resource class
                        resourceClass = cm._class(getResourcePackage(resourcePath) + PACKAGE_SEPARATOR + NameHelper.toValidClassName(resourceName));
                        if (StringUtils.isNotEmpty(resourceDescription)) {
                            resourceClass.javadoc().add(resourceDescription);
                        }
                        final JFieldVar baseUrlField = resourceClass.field(JMod.PRIVATE, String.class, PRIVATE_FIELD_PREFIX + BASE_URL_FIELD_NAME);
                        resourceConstructor = resourceClass.constructor(JMod.PUBLIC);
                        final JVar baseUrlParam = resourceConstructor.param(String.class, BASE_URL_FIELD_NAME);
                        resourceConstructor.body().assign(baseUrlField, baseUrlParam.plus(JExpr.lit("/" + resourceName)));

                        final JMethod getResourceMethod = resourceClass.method(JMod.PRIVATE, String.class, GET_BASE_URI_METHOD_NAME);
                        final JMethod baseURIMethod;
                        if (isURIParameter(resourceName)) {
                            //Add constructor additional parameter for uriParam
                            final JVar uriParamConstructorParam = resourceConstructor.param(String.class, URI_PARAM_FIELD_NAME);
                            final JFieldVar uriParamField = resourceClass.field(JMod.PRIVATE, String.class, PRIVATE_FIELD_PREFIX + URI_PARAM_FIELD_NAME);
                            resourceConstructor.body().assign(uriParamField, uriParamConstructorParam);
                            //Link with parent as method
                            final String uriParameterName = resourceName.substring(1, resourceName.length() - 1);
                            final JMethod resourceFactoryMethod = parentClass.method(JMod.PUBLIC | JMod.FINAL, resourceClass, NameHelper.toValidFieldName(uriParameterName));
                            if (StringUtils.isNotEmpty(resourceDescription)) {
                                resourceFactoryMethod.javadoc().add(resourceDescription);
                            }
                            final JVar uriParam = resourceFactoryMethod.param(String.class, NameHelper.toValidFieldName(uriParameterName));
                            resourceFactoryMethod.body()._return(JExpr._new(resourceClass).arg(JExpr.invoke(GET_BASE_URI_METHOD_NAME)).arg(uriParam));
                            baseURIMethod = clientGenerator.resolveBaseURI(cm, getResourceMethod, baseUrlField, NameHelper.toValidFieldName(uriParameterName), uriParamField);
                        } else {
                            //Link with parent as field
                            final JFieldVar resourceField = parentClass.field(JMod.PUBLIC | JMod.FINAL, resourceClass, NameHelper.toValidFieldName(resourceName));
                            if (StringUtils.isNotEmpty(resourceDescription)) {
                                resourceField.javadoc().add(resourceDescription);
                            }
                            baseURIMethod = clientGenerator.resolveBaseURI(cm, getResourceMethod, baseUrlField);
                            parentConstructor.body().assign(resourceField, JExpr._new(resourceClass).arg(JExpr.invoke(GET_BASE_URI_METHOD_NAME)));
                        }
                        this.clientGenerator.createClient(cm, resourceClass, baseURIMethod);
                        this.resourceClasses.put(resourcePath, new ImmutablePair<>(resourceClass, resourceConstructor));
                    } else {
                        final Pair<JDefinedClass, JMethod> resourcePair = this.resourceClasses.get(resourcePath);
                        resourceClass = resourcePair.getLeft();
                        resourceConstructor = resourcePair.getRight();
                    }

                    parentClass = resourceClass;
                    parentConstructor = resourceConstructor;
                    parentResource = resourcePath;

                    //Only last resource should trigger children and actions
                    if (i == resourceParts.length - 1) {
                        buildActionMethods(cm, resourceClass, resource, resourcePath, resourceName);
                        buildResourceClass(cm, resourceClass, resourceConstructor, resource.getResources(), resourcePath);
                    }
                }
            }
        }
    }

    private String getResourcePackage(String resourcePath) {
        return basePackage + PACKAGE_SEPARATOR + "resource" + PACKAGE_SEPARATOR + NameHelper.toResourcePathToPackageName(resourcePath);
    }

    private boolean isURIParameter(String resourceName) {
        return resourceName.startsWith("{") && resourceName.endsWith("}");
    }

    private void buildActionMethods(JCodeModel cm, JDefinedClass resourceClass, Resource resource, String resourcePath, String resourceName) throws IOException, JClassAlreadyExistsException {
        final Map<ActionType, Action> actions = resource.getActions();
        for (Map.Entry<ActionType, Action> actionTypeActionEntry : actions.entrySet()) {
            final ActionType actionType = actionTypeActionEntry.getKey();
            final Action action = actionTypeActionEntry.getValue();
            final Response response = action.getResponses().get(OK_RESPONSE);

            final JType returnType = buildReturnType(cm, actionType, response, resourcePath, resourceName);

            final JType bodyType = buildBodyType(cm, actionType, action, resourcePath, resourceName);

            final JType queryParameterType = buildQueryParametersType(cm, actionType, action, resourcePath, resourceName);

            final JType headerParameterType = buildHeaderType(cm, resourcePath, resourceName, actionType, action);

            clientGenerator.callHttpMethod(cm, resourceClass, returnType, bodyType, queryParameterType, headerParameterType, action);

        }
    }

    private JType buildReturnType(JCodeModel cm, ActionType actionType, Response response, String resourcePath, String resourceName) throws IOException {
        JType returnType = cm.VOID;
        if (response != null && response.getBody() != null && response.getBody().get(APPLICATION_JSON_MIME_TYPE) != null) {
            final MimeType mimeType = response.getBody().get(APPLICATION_JSON_MIME_TYPE);
            final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + "Response";
            if (StringUtils.isNotBlank(mimeType.getSchema())) {
                returnType = generatePojoFromSchema(cm, className, getModelPackage(resourcePath), mimeType.getSchema());
            } else if (StringUtils.isNotBlank(mimeType.getExample())) {
                returnType = generatePojoFromExample(cm, className, getModelPackage(resourcePath), mimeType.getExample());
            } else {
                returnType = cm.ref(Object.class);
            }
        }
        return returnType;
    }

    private JType buildHeaderType(JCodeModel cm, String resourcePath, String resourceName, ActionType actionType, Action action) throws JClassAlreadyExistsException {
        final Map<String, Header> headers = action.getHeaders();
        JType headerParameterType = null;
        if (headers != null && !headers.isEmpty()) {
            final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + "Header";
            headerParameterType = toParametersJavaBean(cm, className, headers, resourcePath);
        }
        return headerParameterType;
    }

    private JType buildQueryParametersType(JCodeModel cm, ActionType actionType, Action action, String resourcePath, String resourceName) throws JClassAlreadyExistsException {
        final Map<String, QueryParameter> queryParameters = action.getQueryParameters();
        JType queryParameterType = null;
        if (queryParameters != null && !queryParameters.isEmpty()) {
            final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + "QueryParam";
            queryParameterType = toParametersJavaBean(cm, className, queryParameters, resourcePath);
        }
        return queryParameterType;
    }

    private JType buildBodyType(JCodeModel cm, ActionType actionType, Action action, String resourcePath, String resourceName) throws IOException {
        JType bodyType = null;
        if (action.getBody() != null && action.getBody().get(APPLICATION_JSON_MIME_TYPE) != null) {
            final MimeType jsonMimeType = action.getBody().get(APPLICATION_JSON_MIME_TYPE);
            final String className = NameHelper.toValidClassName(resourceName) + NameHelper.toCamelCase(actionType.name(), false) + "Body";
            if (StringUtils.isNotBlank(jsonMimeType.getSchema())) {
                bodyType = generatePojoFromSchema(cm, className, getModelPackage(resourcePath), jsonMimeType.getSchema());
            } else if (StringUtils.isNotBlank(jsonMimeType.getExample())) {
                bodyType = generatePojoFromExample(cm, className, getModelPackage(resourcePath), jsonMimeType.getExample());
            }
        }
        return bodyType;
    }

    private String getModelPackage(String resourcePath) {
        return getResourcePackage(resourcePath) + PACKAGE_SEPARATOR + "model";
    }

    public JType toParametersJavaBean(JCodeModel cm, String className, Map<String, ? extends AbstractParam> paramMap, String resourcePath) throws JClassAlreadyExistsException {
        final JDefinedClass paramsClass = cm._class(getModelPackage(resourcePath) + PACKAGE_SEPARATOR + className);
        final JMethod paramsConstructor = paramsClass.constructor(JMod.PUBLIC);
        for (Map.Entry<String, ? extends AbstractParam> paramEntries : paramMap.entrySet()) {
            final JFieldVar paramField = paramsClass.field(JMod.PRIVATE, String.class, PRIVATE_FIELD_PREFIX + NameHelper.toValidFieldName(paramEntries.getKey()));
            if (paramEntries.getValue().isRequired()) {
                //In constructor
                final JVar paramParam = paramsConstructor.param(String.class, NameHelper.toValidFieldName(paramEntries.getKey()));
                paramsConstructor.body().assign(paramField, paramParam);
            } else {
                //setter
                final JMethod setterMethod = paramsClass.method(JMod.PUBLIC, cm.VOID, NameHelper.getSetterName(paramEntries.getKey()));
                final JVar paramParam = setterMethod.param(String.class, NameHelper.toValidFieldName(paramEntries.getKey()));
                setterMethod.body().assign(paramField, paramParam);
            }
            //Getter
            final JMethod getterMethod = paramsClass.method(JMod.PUBLIC, String.class, NameHelper.getGetterName(paramEntries.getKey()));
            getterMethod.body()._return(paramField);
        }


        return paramsClass;
    }


    public JType generatePojoFromSchema(JCodeModel codeModel, String className, String packageName, String json) throws IOException {
        try {
            final JPackage jpackage = codeModel._package(packageName);
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode schemaNode = mapper.readTree(json);
            return getRuleFactory().getSchemaRule().apply(className, schemaNode, jpackage, new Schema((URI) null, schemaNode));
        } catch (JsonParseException e) {
            System.out.println("Can not generate  " + className + "since json is not well formatted:\n" + json);
            e.printStackTrace();
            return null;
        }
    }

    public JType generatePojoFromExample(JCodeModel codeModel, String className, String packageName, String json) throws IOException {
        try {
            final JPackage jpackage = codeModel._package(packageName);
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode schemaNode = new SchemaGenerator().schemaFromExample(mapper.readTree(json));
            return getRuleFactory().getSchemaRule().apply(className, schemaNode, jpackage, new Schema((URI) null, schemaNode));
        } catch (JsonParseException e) {
            System.out.println("Can not generate  " + className + "since :" + e.getMessage() + " :\n" + json);

            return null;
        }
    }


    private RuleFactory getRuleFactory() {
        return new RuleFactory(new JsonSchemaGeneratorConfiguration(), new Jackson2Annotator(), new SchemaStore());
    }


    private class JsonSchemaGeneratorConfiguration extends DefaultGenerationConfig {

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
    }
}
