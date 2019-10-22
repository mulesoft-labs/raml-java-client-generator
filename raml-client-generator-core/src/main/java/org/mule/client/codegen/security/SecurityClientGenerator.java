package org.mule.client.codegen.security;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;

public interface SecurityClientGenerator {

    JMethod createClient(JDefinedClass containerClass);

    JMethod createClientWithMultipart(JDefinedClass containerClass);
}
