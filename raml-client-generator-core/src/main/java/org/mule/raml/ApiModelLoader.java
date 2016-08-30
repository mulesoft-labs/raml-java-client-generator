/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.mule.raml.impl.v08.model.ApiModelImpl;
import org.mule.raml.model.ApiModel;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.DefaultResourceLoader;
import org.raml.v2.api.loader.ResourceLoader;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.system.types.AnnotableSimpleType;

import javax.annotation.Nullable;
import java.io.File;
import java.io.Reader;

import static com.google.common.collect.Iterables.transform;

public class ApiModelLoader
{

    public static ApiModel build(File ramlFile)
    {
        return build(new DefaultResourceLoader(), ramlFile);
    }

    public static ApiModel build(ResourceLoader resourceLoader, File ramlFile)
    {
        RamlModelResult ramlModelResult = new RamlModelBuilder(resourceLoader).buildApi(ramlFile);
        return wrapApiModel(ramlModelResult);
    }


    private static ApiModel wrapApiModel(RamlModelResult ramlModelResult) throws RuntimeException
    {
        if (ramlModelResult.hasErrors())
        {
            throw new RuntimeException("Invalid RAML descriptor " + Iterables.toString(transform(ramlModelResult.getValidationResults(), new Function<ValidationResult, String>()
            {
                @Nullable
                @Override
                public String apply(ValidationResult input)
                {
                    return input.getMessage();
                }
            })));
        }
        if (ramlModelResult.isVersion08())
        {
            return new ApiModelImpl(ramlModelResult.getApiV08());
        }
        else
        {
            return new org.mule.raml.impl.v10.model.ApiModelImpl(ramlModelResult.getApiV10());
        }
    }


    public static String nullSafe(AnnotableSimpleType<?> simpleType)
    {
        return simpleType != null ? String.valueOf(simpleType.value()) : null;
    }

    public static ApiModel build(Reader reader, String location)
    {
        RamlModelResult ramlModelResult = new RamlModelBuilder(new DefaultResourceLoader()).buildApi(reader, location);
        return wrapApiModel(ramlModelResult);
    }
}
