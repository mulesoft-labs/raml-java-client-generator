package org.mule.client.codegen;


import com.sun.codemodel.*;
import org.mule.client.codegen.model.JBodyType;
import org.mule.raml.model.Action;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface RestClientGenerator {

    void callHttpMethod(@Nonnull JCodeModel cm, @Nonnull JDefinedClass resourceClass, @Nonnull JType returnType, @Nullable JBodyType bodyType, @Nullable JType queryParameterType, @Nullable JType headerParameterType, @Nonnull Action action);

    JMethod createClient(JCodeModel cm, JDefinedClass resourceClass, JMethod baseUrlField);

    JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField);

    JClass buildCustomException(JCodeModel cm, String basePackage, String apiName);
}
