package org.mule.client.codegen.security;

import com.sun.codemodel.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class NoSecuredClientGenerator implements SecurityClientGenerator {

    @Override
    public JMethod createClient(JDefinedClass containerClass) {
        JCodeModel cm = new JCodeModel();
        JMethod getClient = containerClass.method(JMod.PROTECTED, Client.class, "getClient");
        getClient.body()._return(cm.anonymousClass(ClientBuilder.class).staticInvoke("newClient"));
        return getClient;
    }

    @Override
    public JMethod createClientWithMultipart(JDefinedClass containerClass) {
        JCodeModel cm = new JCodeModel();
        JMethod getClient = containerClass.method(JMod.PROTECTED, Client.class, "getClientWithMultipart");
        JBlock body = getClient.body();

        JClass ccRef = cm.ref(ClientConfig.class);
        final JVar ccVal = body.decl(cm.ref(ClientConfig.class), "cc", JExpr._new(ccRef));
        body.add(ccVal.invoke("register").arg(cm.ref(MultiPartFeature.class).dotclass()));

        JClass cbRef = cm.ref(ClientBuilder.class);
        JInvocation init = cbRef.staticInvoke("newBuilder");
        final JVar cbVal = body.decl(cbRef, "clientBuilder", init);
        body._return(cbVal.invoke("withConfig").arg(ccVal).invoke("build"));
        return getClient;
    }
}
