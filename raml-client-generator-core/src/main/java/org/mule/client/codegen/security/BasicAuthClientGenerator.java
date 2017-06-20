package org.mule.client.codegen.security;

import com.sun.codemodel.*;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

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

        JVar decl = body.decl(JMod.FINAL, cm._ref(Client.class), "client", cm.anonymousClass(ClientBuilder.class).staticInvoke("newClient"));
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
}
