package org.mule.client.codegen.clientgenerator;

import com.sun.codemodel.*;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.mule.client.codegen.utils.MimeTypeHelper;
import org.mule.client.codegen.RestClientGenerator;
import org.mule.client.codegen.utils.NameHelper;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.MimeType;
import org.raml.model.ParamType;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.Header;
import org.raml.model.parameter.QueryParameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Jersey2RestClientGeneratorImpl implements RestClientGenerator {

    public static final String QUERY_PARAMETERS_PARAM_NAME = "queryParameters";
    public static final String BODY_PARAM_NAME = "body";
    public static final String HEADERS_PARAM_NAME = "headers";

    @Override
    public void callHttpMethod(@Nonnull JCodeModel cm, @Nonnull JDefinedClass resourceClass, @Nonnull JType returnType, @Nullable JType bodyType, @Nullable JType queryParameterType, @Nullable JType headerParameterType, @Nonnull Action action) {

        if (action.getType() == ActionType.PATCH) {
            System.out.println("Patch is not supported");
            return;
        }

        //Declare the method with the required inputs

        final JMethod actionMethod = resourceClass.method(JMod.PUBLIC, returnType, action.getType().name().toLowerCase());
        if (StringUtils.isNotBlank(action.getDescription())) {
            actionMethod.javadoc().add(action.getDescription());
        }
        final JVar bodyParam;
        if (bodyType != null) {
            bodyParam = actionMethod.param(bodyType, BODY_PARAM_NAME);
        } else {
            bodyParam = null;
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

        final JBlock body = actionMethod.body();
        final JVar targetVal = body.decl(cm.ref(WebTarget.class), "target", JExpr.invoke("getClient"));

        if (queryParameterParam != null && action.getQueryParameters() != null && !action.getQueryParameters().isEmpty()) {
            final Map<String, QueryParameter> queryParameters = action.getQueryParameters();
            for (Map.Entry<String, QueryParameter> stringQueryParameterEntry : queryParameters.entrySet()) {
                final String queryParameter = stringQueryParameterEntry.getKey();
                body._if(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter)).ne(JExpr._null()))._then()
                        .assign(targetVal,
                                targetVal.invoke("queryParam").arg(queryParameter).arg(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter))));
            }
        }


        final JVar invocationBuilder = body.decl(JMod.FINAL, cm.ref(Invocation.Builder.class), "invocationBuilder", targetVal.invoke("request").arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_JSON_TYPE")));

        if (headerParameterParam != null && action.getHeaders() != null && !action.getHeaders().isEmpty()) {
            final Map<String, Header> headers = action.getHeaders();
            for (Map.Entry<String, Header> headerEntry : headers.entrySet()) {
                final String headerParameter = headerEntry.getKey();
                body._if(headerParameterParam.invoke(NameHelper.getGetterName(headerParameter)).ne(JExpr._null()))._then()
                        .invoke(invocationBuilder, "header").arg(headerParameter).arg(headerParameterParam.invoke(NameHelper.getGetterName(headerParameter)));
            }
        }

        final JInvocation methodInvocation = JExpr.invoke(invocationBuilder, action.getType().name().toLowerCase());
        if (action.getType() != ActionType.GET && action.getType() != ActionType.OPTIONS && action.getType() != ActionType.DELETE) {
            if (bodyParam != null) {
                final Iterator<MimeType> iterator = action.getBody().values().iterator();
                if (iterator.hasNext()) {
                    final MimeType type = iterator.next();
                    if (MimeTypeHelper.isJsonType(type)) {
                        methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("json").arg(bodyParam));
                    } else if (MimeTypeHelper.isTextType(type)) {
                        methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("text").arg(bodyParam));
                    } else if (MimeTypeHelper.isBinaryType(type)) {
                        methodInvocation.arg(JExpr._new(cm._ref(Entity.class)).arg(bodyParam).arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_OCTET_STREAM_TYPE")));
                    } else if (MimeTypeHelper.isMultiPartType(type)) {
                        final JVar multiPartVar = body.decl(cm.ref(FormDataMultiPart.class), "multiPart", JExpr._new(cm.ref(FormDataMultiPart.class)));
                        final Map<String, List<FormParameter>> formParameters = type.getFormParameters();
                        for (Map.Entry<String, List<FormParameter>> param : formParameters.entrySet()) {
                            final FormParameter formParameter = param.getValue().get(0);
                            final String paramName = param.getKey();
                            final String paramGetterMethod = NameHelper.getGetterName(paramName);
                            final JBlock ifBlock = body._if(bodyParam.invoke(paramGetterMethod).ne(JExpr._null()))._then();
                            if (formParameter.getType() == ParamType.FILE) {
                                final JInvocation newFileDataBody = JExpr._new(cm._ref(FileDataBodyPart.class)).arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod));
                                ifBlock.invoke(multiPartVar, "bodyPart").arg(newFileDataBody);
                            } else {
                                ifBlock.invoke(multiPartVar, "field").arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod).invoke("toString"));
                            }
                        }
                        methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("entity").arg(multiPartVar).arg(multiPartVar.invoke("getMediaType")));
                    }
                }
            } else {
                methodInvocation.arg(JExpr._null());
            }
        }

        final JVar responseVal = body.decl(cm.ref(Response.class), "response", methodInvocation);

        body._if(responseVal.invoke("getStatusInfo").invoke("getFamily").ne(cm.directClass("javax.ws.rs.core.Response.Status.Family").staticRef("SUCCESSFUL")))._then()
                ._throw(JExpr._new(cm._ref(RuntimeException.class)).arg(responseVal.invoke("getStatusInfo").invoke("getReasonPhrase")));

        if (returnType != cm.VOID) {
            if (returnType.equals(cm.ref(Object.class))) {
                body._return(responseVal.invoke("getEntity"));
            } else {
                if (returnType instanceof JClass && !((JClass) returnType).getTypeParameters().isEmpty()) {
                    final JClass narrow = cm.anonymousClass(cm.ref(GenericType.class).narrow(returnType));
                    body._return(responseVal.invoke("readEntity").arg(JExpr._new(narrow)));
                } else {
                    body._return(responseVal.invoke("readEntity").arg(JExpr.dotclass(cm.ref(returnType.fullName()))));
                }
            }
        }
    }


    @Override
    public JMethod createClient(JCodeModel cm, JDefinedClass resourceClass, JMethod baseUrlMethod) {
        final JMethod clientMethod = resourceClass.method(JMod.PRIVATE, WebTarget.class, "getClient");
        final JBlock methodBody = clientMethod.body();
        final JVar client = methodBody.decl(JMod.FINAL, cm.ref(Client.class), "client", cm.directClass(ClientBuilder.class.getName()).staticInvoke("newClient"));
        final JVar target = methodBody.decl(JMod.FINAL, cm.ref(WebTarget.class), "target", client.invoke("target").arg(JExpr.invoke(baseUrlMethod)));
        methodBody._return(target);
        return clientMethod;
    }

    @Override
    public JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField, String uriParamName, JFieldVar uriParamField) {
        //UriBuilder.fromPath()
        final JBlock baseURIBody = baseUriMethod.body();
        final JVar parametersVar = baseURIBody.decl(JMod.FINAL, cm.ref(Map.class).narrow(String.class).narrow(String.class), "parameters", JExpr._new(cm.ref(HashMap.class).narrow(String.class).narrow(String.class)));
        baseURIBody.invoke(parametersVar, "put").arg(JExpr.lit(uriParamName)).arg(uriParamField);

        final JVar builderVar = baseURIBody.decl(JMod.FINAL, cm.ref(UriBuilder.class), "builder", cm.directClass(UriBuilder.class.getName()).staticInvoke("fromPath").arg(baseUrlField));
        final JVar uriVar = baseURIBody.decl(JMod.FINAL, cm.ref(URI.class), "uri", builderVar.invoke("build").arg(parametersVar));
        baseURIBody._return(uriVar.invoke("toString"));

        return baseUriMethod;
    }

    @Override
    public JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField) {
        baseUriMethod.body()._return(baseUrlField);
        return baseUriMethod;
    }

}
