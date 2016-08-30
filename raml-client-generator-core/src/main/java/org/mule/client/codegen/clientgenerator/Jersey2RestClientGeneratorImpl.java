package org.mule.client.codegen.clientgenerator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.mule.client.codegen.RestClientGenerator;
import org.mule.client.codegen.utils.MimeTypeHelper;
import org.mule.client.codegen.utils.NameHelper;
import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.parameter.Parameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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
        final JVar targetVal = body.decl(cm.ref(WebTarget.class), "target", JExpr._this().ref("client").invoke("target").arg(JExpr.invoke("getBaseUri")));

        if (queryParameterParam != null && action.getQueryParameters() != null && !action.getQueryParameters().isEmpty()) {
            final Map<String, Parameter> queryParameters = action.getQueryParameters();
            for (Map.Entry<String, Parameter> stringQueryParameterEntry : queryParameters.entrySet()) {
                final String queryParameter = stringQueryParameterEntry.getKey();
                body._if(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter)).ne(JExpr._null()))._then()
                        .assign(targetVal,
                                targetVal.invoke("queryParam").arg(queryParameter).arg(queryParameterParam.invoke(NameHelper.getGetterName(queryParameter))));
            }
        }


        final JVar invocationBuilder = body.decl(JMod.FINAL, cm.ref(Invocation.Builder.class), "invocationBuilder", targetVal.invoke("request").arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_JSON_TYPE")));

        if (headerParameterParam != null && action.getHeaders() != null && !action.getHeaders().isEmpty()) {
            final Map<String, Parameter> headers = action.getHeaders();
            for (Map.Entry<String, Parameter> headerEntry : headers.entrySet()) {
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
                        methodInvocation.arg((cm.ref(Entity.class).staticInvoke("entity").arg(bodyParam).arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_OCTET_STREAM_TYPE"))));
                    } else if (MimeTypeHelper.isMultiPartType(type)) {
                        final JVar multiPartVar = body.decl(cm.ref(FormDataMultiPart.class), "multiPart", JExpr._new(cm.ref(FormDataMultiPart.class)));
                        final Map<String, Parameter> formParameters = type.getFormParameters();
                        for (Map.Entry<String, Parameter> param : formParameters.entrySet()) {
                            final Parameter formParameter = param.getValue();
                            final String paramName = param.getKey();
                            final String paramGetterMethod = NameHelper.getGetterName(paramName);
                            final JBlock ifBlock = body._if(bodyParam.invoke(paramGetterMethod).ne(JExpr._null()))._then();
//                            if (formParameter.getType() == ParamType.FILE) {
//                                final JInvocation newFileDataBody = JExpr._new(cm._ref(FileDataBodyPart.class)).arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod));
//                                ifBlock.invoke(multiPartVar, "bodyPart").arg(newFileDataBody);
//                            } else {
                                ifBlock.invoke(multiPartVar, "field").arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod).invoke("toString"));
//                            }
                        }
                        methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("entity").arg(multiPartVar).arg(multiPartVar.invoke("getMediaType")));
                    } else if (MimeTypeHelper.isFormUrlEncodedType(type)) {
                        final JVar multiValuedMapVar = body.decl(cm.ref(MultivaluedMap.class), "multiValuedMap", JExpr._new(cm.ref(MultivaluedHashMap.class)));
                        final Map<String, Parameter> formParameters = type.getFormParameters();
                        for (Map.Entry<String, Parameter> param : formParameters.entrySet()) {
                            final String paramName = param.getKey();
                            final String paramGetterMethod = NameHelper.getGetterName(paramName);
                            final JBlock ifBlock = body._if(bodyParam.invoke(paramGetterMethod).ne(JExpr._null()))._then();
                            ifBlock.invoke(multiValuedMapVar, "add").arg(JExpr.lit(paramName)).arg(bodyParam.invoke(paramGetterMethod).invoke("toString"));
                        }
                        methodInvocation.arg(cm.directClass(Entity.class.getName()).staticInvoke("entity").arg(multiValuedMapVar).arg(cm.directClass(MediaType.class.getName()).staticRef("APPLICATION_FORM_URLENCODED_TYPE")));
                    }
                }
            } else {
                methodInvocation.arg(JExpr._null());
            }
        }

        final JVar responseVal = body.decl(cm.ref(Response.class), "response", methodInvocation);

        final JBlock ifBlock = body._if(responseVal.invoke("getStatusInfo").invoke("getFamily").ne(cm.directClass("javax.ws.rs.core.Response.Status.Family").staticRef("SUCCESSFUL")))._then();
        final JVar statusInfo = ifBlock.decl(cm.ref(Response.StatusType.class), "statusInfo", responseVal.invoke("getStatusInfo"));
        ifBlock._throw(JExpr._new(cm._ref(RuntimeException.class)).arg(
                JExpr.lit("(").plus(statusInfo.invoke("getFamily")).plus(JExpr.lit(") ")).plus(statusInfo.invoke("getStatusCode")).plus(JExpr.lit(" ")).plus(statusInfo.invoke("getReasonPhrase"))));

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
    public JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField) {
        baseUriMethod.body()._return(baseUrlField);
        return baseUriMethod;
    }

}
