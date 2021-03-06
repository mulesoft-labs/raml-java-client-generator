package org.mule.client.codegen;


import com.sun.codemodel.*;
import org.mule.client.codegen.model.JTypeWithMimeType;
import org.mule.raml.model.Action;
import org.mule.raml.model.ApiModel;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface RestClientGenerator {


    void callHttpMethod(@Nonnull JCodeModel cm, @Nonnull JDefinedClass resourceClass, @Nonnull JTypeWithMimeType returnType, @Nonnull OutputVersion outputVersion, @Nullable JTypeWithMimeType bodyType, @Nullable JType queryParameterType, @Nullable JType headerParameterType, @Nonnull Action action, ApiModel apiModel);

    JMethod resolveBaseURI(JCodeModel cm, JMethod baseUriMethod, JFieldVar baseUrlField);

    void buildCustomException(JCodeModel cm, String basePackage, String apiName);

	void buildCustomResponse(JCodeModel cm, String basePackage, ApiModel apiModel)
			throws JClassAlreadyExistsException;
}
