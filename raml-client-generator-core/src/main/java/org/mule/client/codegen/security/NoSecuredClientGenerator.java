package org.mule.client.codegen.security;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class NoSecuredClientGenerator implements SecurityClientGenerator {

    @Override
    public JMethod createClient(JDefinedClass containerClass) {
        JCodeModel cm = new JCodeModel();
        JMethod getClient = containerClass.method(JMod.PRIVATE, Client.class, "getClient");
        getClient.body()._return(cm.anonymousClass(ClientBuilder.class).staticInvoke("newClient"));
        return getClient;
    }
}
