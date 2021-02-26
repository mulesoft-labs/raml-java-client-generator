package org.mule.client.codegen.clientgenerator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
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
import com.sun.codemodel.JTypeVar;
import com.sun.codemodel.JVar;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.mule.client.codegen.OutputVersion;
import org.mule.client.codegen.RamlJavaClientGenerator;
import org.mule.client.codegen.RestClientGenerator;
import org.mule.client.codegen.model.JTypeWithMimeType;
import org.mule.client.codegen.utils.MimeTypeHelper;
import org.mule.client.codegen.utils.NameHelper;
import org.mule.client.codegen.utils.TypeConstants;
import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.TypeFieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.mule.client.codegen.utils.SecuritySchemesHelper.isOauth20SecuredBy;


public class Jersey2RestClientGeneratorImpl implements RestClientGenerator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BODY_PARAM_NAME = "body";
    private static final String HEADERS_PARAM_NAME = "headers";
    private static final String TOKEN_PARAM_NAME = "authorizationToken";
    private static final String MIME_TYPE_NAME = "mimeType";
    private static final String QUERY_PARAMETERS_PARAM_NAME = "queryParameters";

    private static JClass exceptionClass;
    private static JClass responseClass;


    @Override
    public void callHttpMethod(@Nonnull JCodeModel cm, @Nonnull JDefinedClass resourceClass, @Nonnull JTypeWithMimeType returnType, @Nonnull OutputVersion outputVersion, @Nullable JTypeWithMimeType bodyType, @Nullable JType queryParameterType, @Nullable JType headerParameterType, @Nonnull Action action, ApiModel apiModel) {
        // Declare the method with the required inputs
        JMethod actionMethod;
        if (outputVersion.ordinal() >= OutputVersion.v2.ordinal()) {
            actionMethod = resourceClass.method(JMod.PUBLIC, responseClass.narrow(returnType.getType()), action.getType().name().toLowerCase());
        } else {
            actionMethod = resourceClass.method(JMod.PUBLIC, returnType.getType(), action.getType().name().toLowerCase());
        }
        if (StringUtils.isNotBlank(action.getDescription())) {
            actionMethod.javadoc().add(action.getDescription());
        }
        final JVar bodyParam;
        if (bodyType != null) {
            bodyParam = actionMethod.param(bodyType.getType(), BODY_PARAM_NAME);
        } else {
            bodyParam = null;
        }

        JVar mimeTypeParam = null;
        if (bodyType != null) {
            final MimeType type = bodyType.getMimeType();
            if (MimeTypeHelper.isAnyType(type)) {
                mimeTypeParam = actionMethod.param(String.class, MIME_TYPE_NAME);
            }
        }

        final JVar queryParameterParam;
        if (queryParameterType != null) {
            queryParameterParam = actionMethod.param(queryParameterType, QUERY_PARAMETERS_PARAM_NAME);
        } else {
            queryParameterParam = null;
        }

        final JVar headerParameterParam;
        if (headerParameterType != null) {
            headerParameterParam = actionMethod.param(headerParameterType, HEADERS_PARAM_NAME);
        } else {
            headerParameterParam = null;
        }

        final JVar authenticationParam;
        //If global but not locally specified
        if (isOauth20SecuredBy(action.getResource()) || (isOauth20SecuredBy(apiModel) && action.getResource().getSecuredBy().isEmpty())) {
            authenticationParam = actionMethod.param(String.class, TOKEN_PARAM_NAME);
        } else {
            authenticationParam = null;
        }

        final JBlock body = actionMethod.body();
        final JVar targetVal = body.decl(cm.ref(WebTarget.class), "target", JExpr._this().ref(RamlJavaClientGenerator.CLIENT_FIELD_NAME).invoke("target").arg(JExpr.invoke("getBaseUri")));

        if (queryParameterParam != null && action.getQueryParameters() != null && !action.getQueryParameters().isEmpty()) {
            final Map<String, TypeFieldDefinition> queryParameters = action.getQueryParameters();
            for (Map.Entry<String, TypeFieldDefinition> stringQueryParameterEntry : queryParameters.entrySet()) {
                final String queryParameter = stringQueryParameterEntry.getKey();
                body._if(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter)).ne(JExpr._null()))._then()
                        .assign(targetVal,
                                targetVal.invoke("queryParam").arg(queryParameter).arg(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter))));
            }
        }

        final JVar invocationBuilder = body.decl(JMod.FINAL, cm.ref(Invocation.Builder.class), "invocationBuilder", targetVal.invoke("request").arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_JSON_TYPE")));

        if (headerParameterParam != null && action.getHeaders() != null && !action.getHeaders().isEmpty()) {
            final Map<String, TypeFieldDefinition> headers = action.getHeaders();
            for (Map.Entry<String, TypeFieldDefinition> headerEntry : headers.entrySet()) {
                final String headerParameter = headerEntry.getKey();
                body._if(headerParameterParam.invoke(NameHelper.getGetterName(headerParameter)).ne(JExpr._null()))._then()
                        .invoke(invocationBuilder, "header").arg(headerParameter).arg(headerParameterParam.invoke(NameHelper.getGetterName(headerParameter)));
            }
        }

        if (authenticationParam != null) {
            body.add(invocationBuilder.invoke("header").arg("Authorization").arg(JExpr.lit("bearer ").plus(authenticationParam)));
        }

        JInvocation methodInvocation = JExpr.invoke(invocationBuilder, "method");
        methodInvocation.arg(action.getType().name());
        if (action.getType() != ActionType.GET && action.getType() != ActionType.OPTIONS && action.getType() != ActionType.DELETE) {
            if (bodyParam != null) {
                final MimeType type = bodyType.getMimeType();
                if (MimeTypeHelper.isJsonType(type)) {
                    methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("json").arg(bodyParam));
                } else if (MimeTypeHelper.isTextType(type)) {
                    methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("text").arg(bodyParam));
                } else if (MimeTypeHelper.isBinaryType(type)) {
                    methodInvocation.arg((cm.ref(Entity.class).staticInvoke("entity").arg(bodyParam).arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_OCTET_STREAM_TYPE"))));
                } else if (MimeTypeHelper.isMultiPartType(type)) {
                    final JVar multiPartVar = body.decl(cm.ref(FormDataMultiPart.class), "multiPart", JExpr._new(cm.ref(FormDataMultiPart.class)));
                    final Map<String, TypeFieldDefinition> formParameters = type.getFormParameters();
                    for (Map.Entry<String, TypeFieldDefinition> param : formParameters.entrySet()) {
                        final TypeFieldDefinition formParameter = param.getValue();
                        final String paramName = param.getKey();
                        final String paramGetterMethod = NameHelper.getGetterName(paramName);
                        final JBlock ifBlock = body._if(bodyParam.invoke(paramGetterMethod).ne(JExpr._null()))._then();
                        if (TypeConstants.FILE.equals(formParameter.getType())) {
                            final JInvocation newFileDataBody = JExpr._new(cm._ref(FileDataBodyPart.class)).arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod));
                            ifBlock.invoke(multiPartVar, "bodyPart").arg(newFileDataBody);
                        } else {
                            ifBlock.invoke(multiPartVar, "field").arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod).invoke("toString"));
                        }
                    }
                    methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("entity").arg(multiPartVar).arg(multiPartVar.invoke("getMediaType")));
                } else if (MimeTypeHelper.isFormUrlEncodedType(type)) {
                    final JVar multiValuedMapVar = body.decl(cm.ref(MultivaluedMap.class), "multiValuedMap", JExpr._new(cm.ref(MultivaluedHashMap.class)));
                    final Map<String, TypeFieldDefinition> formParameters = type.getFormParameters();
                    for (Map.Entry<String, TypeFieldDefinition> param : formParameters.entrySet()) {
                        final String paramName = param.getKey();
                        final String paramGetterMethod = NameHelper.getGetterName(paramName);
                        final JBlock ifBlock = body._if(bodyParam.invoke(paramGetterMethod).ne(JExpr._null()))._then();
                        ifBlock.invoke(multiValuedMapVar, "add").arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod).invoke("toString"));
                    }
                    methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("entity").arg(multiValuedMapVar).arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_FORM_URLENCODED_TYPE")));
                } else if (MimeTypeHelper.isAnyType(type)) {
                    assert mimeTypeParam != null;
                    methodInvocation.arg((cm.ref(Entity.class).staticInvoke("entity").arg(bodyParam).arg(mimeTypeParam)));
                } else {
                    methodInvocation.arg((cm.ref(Entity.class).staticInvoke("entity").arg(bodyParam).arg(type.getType())));
                }

            } else {
                methodInvocation.arg(JExpr.cast(cm.directClass(Entity.class.getName()), JExpr._null()));
            }
        }

        final JVar responseVal = body.decl(cm.ref(Response.class), "response", methodInvocation);

        final JBlock ifBlock = body._if(responseVal.invoke("getStatusInfo").invoke("getFamily").ne(cm.directClass("javax.ws.rs.core.Response.Status.Family").staticRef("SUCCESSFUL")))._then();
        final JVar statusInfo = ifBlock.decl(cm.ref(Response.StatusType.class), "statusInfo", responseVal.invoke("getStatusInfo"));

        ifBlock._throw(
                JExpr._new(exceptionClass)
                        .arg(statusInfo.invoke("getStatusCode"))
                        .arg(statusInfo.invoke("getReasonPhrase"))
                        .arg(responseVal.invoke("getStringHeaders"))
                        .arg(responseVal)
        );

        if (returnType.getType() != cm.VOID) {
            final MimeType type = returnType.getMimeType();

            JExpression jInvocation;
            if (MimeTypeHelper.isJsonType(type)) {
                if (returnType.getType().equals(cm.ref(String.class))) {
                    //Read entity as Object and then cast to String
                    jInvocation = JExpr.cast(returnType.getType(), responseVal.invoke("readEntity").arg(JExpr.dotclass(cm.ref(Object.class))));
                } else {
                    if (returnType.getType() instanceof JClass && !((JClass) returnType.getType()).getTypeParameters().isEmpty()) {
                        jInvocation = responseVal.invoke("readEntity").arg(JExpr.direct("\n" +
                                "new " + GenericType.class.getName() + "<" + returnType.getType().fullName() + ">() {}"));
                    } else {
                        jInvocation = responseVal.invoke("readEntity").arg(JExpr.dotclass(cm.ref(returnType.getType().fullName())));
                    }
                }
            } else {
                jInvocation = responseVal.invoke("readEntity").arg(JExpr.dotclass(cm.ref(returnType.getType().fullName())));
            }

            if (outputVersion.ordinal() >= OutputVersion.v2.ordinal()) {
                JInvocation apiResponseInvocation = JExpr._new(responseClass.narrow(returnType.getType()));
                apiResponseInvocation.arg(jInvocation);
                apiResponseInvocation.arg(responseVal.invoke("getStringHeaders"));
                apiResponseInvocation.arg(responseVal);
                final JVar apiResponseVal = body.decl(responseClass.narrow(returnType.getType()), "apiResponse", apiResponseInvocation);
                body._return(apiResponseVal);
            } else {
                body._return(jInvocation);
            }
        } else if (outputVersion.ordinal() >= OutputVersion.v2.ordinal()) {
            JInvocation apiResponseInvocation = JExpr._new(responseClass.narrow(Void.class));
            apiResponseInvocation.arg(JExpr._null());
            apiResponseInvocation.arg(responseVal.invoke("getStringHeaders"));
            apiResponseInvocation.arg(responseVal);
            final JVar apiResponseVal = body.decl(responseClass.narrow(returnType.getType()), "apiResponse", apiResponseInvocation);
            body._return(apiResponseVal);
        }
    }


    @Override
    public JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField) {
        baseUriMethod.body()._return(baseUrlField);
        return baseUriMethod;
    }

    @Override
    public void buildCustomException(JCodeModel cm, String basePackage, String apiName) {
        try {
            final JDefinedClass customExceptionClass = cm._class(basePackage + "." + "exceptions" + "." + NameHelper.toValidClassName(apiName) + "Exception");
            customExceptionClass._extends(RuntimeException.class);

            final JFieldVar statusCodeField = customExceptionClass.field(JMod.PRIVATE, Integer.TYPE, "statusCode");
            final JFieldVar reasonField = customExceptionClass.field(JMod.PRIVATE, String.class, "reason");
            final JClass rawHeadersClass = cm.ref(MultivaluedMap.class).narrow(cm.ref(String.class), cm.ref(String.class));
            final JFieldVar headersField = customExceptionClass.field(JMod.PRIVATE, rawHeadersClass, "headers");
            final JFieldVar responseField = customExceptionClass.field(JMod.PRIVATE, Response.class, "response");

            // Constructor with only statusCode, reason, headers and response
            final JMethod exceptionConstructor = customExceptionClass.constructor(JMod.PUBLIC);
            //Start Constructor Params
            JVar statusCodeParameter = exceptionConstructor.param(Integer.TYPE, "statusCode");
            JVar reasonParameter = exceptionConstructor.param(String.class, "reason");
            JVar headersParameter = exceptionConstructor.param(rawHeadersClass, "headers");
            JVar responseParameter = exceptionConstructor.param(Response.class, "response");
            //End Constructor Params
            //Constructor Body
            exceptionConstructor.body().invoke("super").arg(reasonParameter);
            exceptionConstructor.body().assign(JExpr._this().ref(statusCodeField), statusCodeParameter);
            exceptionConstructor.body().assign(JExpr._this().ref(reasonField), reasonParameter);
            exceptionConstructor.body().assign(JExpr._this().ref(headersField), headersParameter);
            exceptionConstructor.body().assign(JExpr._this().ref(responseField), responseParameter);
            //Constructor Body

            // Constructor with only statusCode and reason
            JMethod containerConstructorWithoutHeadersAndResponse = customExceptionClass.constructor(JMod.PUBLIC);
            statusCodeParameter = containerConstructorWithoutHeadersAndResponse.param(Integer.TYPE, "statusCode");
            reasonParameter = containerConstructorWithoutHeadersAndResponse.param(String.class, "reason");
            JInvocation thisInvocation = JExpr.invoke("this")
                    .arg(statusCodeParameter).arg(reasonParameter).arg(JExpr._null()).arg(JExpr._null());
            containerConstructorWithoutHeadersAndResponse.body().add(thisInvocation);

            JMethod statusCodeGetterMethod = customExceptionClass.method(JMod.PUBLIC, Integer.TYPE, "getStatusCode");
            JMethod reasonGetterMethod = customExceptionClass.method(JMod.PUBLIC, String.class, "getReason");

            statusCodeGetterMethod.body()._return(JExpr._this().ref(statusCodeField));
            reasonGetterMethod.body()._return(JExpr._this().ref(reasonField));

            JMethod headersGetterMethod = customExceptionClass.method(JMod.PUBLIC, rawHeadersClass, "getHeaders");
            headersGetterMethod.body()._return(JExpr._this().ref(headersField));

            JMethod responseGetterMethod = customExceptionClass.method(JMod.PUBLIC, Response.class, "getResponse");
            responseGetterMethod.body()._return(JExpr._this().ref(responseField));

            exceptionClass = customExceptionClass;
        } catch (JClassAlreadyExistsException e) {
            exceptionClass = cm.ref(RuntimeException.class);
        }
    }

    @Override
    public void buildCustomResponse(JCodeModel cm, String basePackage, ApiModel apiModel) throws JClassAlreadyExistsException {

        String apiName = apiModel.getTitle();

        JDefinedClass customResponseClass = cm._class(basePackage + "." + "responses" + "." + NameHelper.toValidClassName(apiName) + "Response");
        JTypeVar genericType = customResponseClass.generify("T");

        JFieldVar bodyField = customResponseClass.field(JMod.PRIVATE, genericType, "body");
        JClass rawHeadersClass = cm.ref(MultivaluedMap.class)
                .narrow(cm.ref(String.class), cm.ref(String.class));
        JFieldVar headersField = customResponseClass.field(JMod.PRIVATE, rawHeadersClass, "headers");
        JFieldVar responseField = customResponseClass.field(JMod.PRIVATE, Response.class, "response");


        JMethod containerConstructor = customResponseClass.constructor(JMod.PUBLIC);

        JVar bodyParameter = containerConstructor.param(genericType, "body");
        containerConstructor.body().assign(JExpr._this().ref(bodyField), bodyParameter);

        JVar headersParameter = containerConstructor.param(rawHeadersClass, "headers");
        containerConstructor.body().assign(JExpr._this().ref(headersField), headersParameter);

        JVar responseParameter = containerConstructor.param(Response.class, "response");
        containerConstructor.body().assign(JExpr._this().ref(responseField), responseParameter);

        JMethod bodyGetterMethod = customResponseClass.method(JMod.PUBLIC, genericType, "getBody");
        bodyGetterMethod.body()._return(JExpr._this().ref(bodyField));

        JMethod headersGetterMethod = customResponseClass.method(JMod.PUBLIC, rawHeadersClass, "getHeaders");
        headersGetterMethod.body()._return(JExpr._this().ref(headersField));

        JMethod responseGetterMethod = customResponseClass.method(JMod.PUBLIC, Response.class, "getResponse");
        responseGetterMethod.body()._return(JExpr._this().ref(responseField));

        responseClass = customResponseClass;
    }
}