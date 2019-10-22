package org.mule.client.codegen.security;

import com.sun.codemodel.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.mule.client.codegen.RamlJavaClientGenerator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;

public class BasicAuthClientGenerator implements SecurityClientGenerator {

    private List<JFieldVar> generatedRequiredField;

    public BasicAuthClientGenerator(List<JFieldVar> generatedRequiredField) {
        this.generatedRequiredField = generatedRequiredField;
    }

    @Override
    public JMethod createClient(JDefinedClass containerClass) {
        JCodeModel cm = new JCodeModel();
        JMethod getClient = containerClass.method(JMod.PROTECTED, Client.class, "getClient");
        JBlock body = getClient.body();

        JVar decl = body.decl(JMod.FINAL, cm._ref(Client.class), RamlJavaClientGenerator.CLIENT_FIELD_NAME, cm.anonymousClass(ClientBuilder.class).staticInvoke("newClient"));
        JInvocation invoke = cm.ref(HttpAuthenticationFeature.class).staticInvoke("basic");

        for (JFieldVar var : generatedRequiredField) {
            invoke.arg(var);
        }

        invoke.invoke("build");

        JInvocation jInvocation1 = decl.invoke("register").arg(invoke);
        body.add(jInvocation1);
        body._return(decl);
        return getClient;
    }

    @Override
    public JMethod createClientWithMultipart(JDefinedClass containerClass) {
        JCodeModel cm = new JCodeModel();
        JMethod getClient = containerClass.method(JMod.PROTECTED, Client.class, "getClientWithMultipart");
        JBlock body = getClient.body();

        JVar decl = body.decl(JMod.FINAL, cm._ref(Client.class), RamlJavaClientGenerator.CLIENT_FIELD_NAME, cm.anonymousClass(ClientBuilder.class).staticInvoke("newClient"));
        JInvocation invoke = cm.ref(HttpAuthenticationFeature.class).staticInvoke("basic");

        for (JFieldVar var : generatedRequiredField) {
            invoke.arg(var);
        }

        invoke.invoke("build");

        JInvocation jInvocation1 = decl.invoke("register").arg(invoke);
        body.add(jInvocation1);

        JInvocation jInvocation2 = decl.invoke("register").arg(cm.ref(MultiPartFeature.class).dotclass());
        body.add(jInvocation2);
        body._return(decl);
        return getClient;
    }
}
