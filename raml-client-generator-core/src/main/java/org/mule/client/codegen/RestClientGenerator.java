package org.mule.client.codegen;


import com.sun.codemodel.*;
import org.raml.model.Action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface RestClientGenerator {

    void callHttpMethod(@Nonnull JCodeModel cm, @Nonnull JDefinedClass resourceClass, @Nonnull JType returnType, @Nullable JType bodyType, @Nullable JType queryParameterType, @Nullable JType headerParameterType, @Nonnull Action action);

    JMethod createClient(JCodeModel cm, JDefinedClass resourceClass, JMethod baseUrlField);

    JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField, String uriParamName, JFieldVar uriParamField);

    JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField);
}
