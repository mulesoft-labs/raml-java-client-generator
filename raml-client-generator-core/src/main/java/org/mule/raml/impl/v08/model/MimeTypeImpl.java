/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v08.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.TypeFieldDefinition;

import java.util.Map;

import org.raml.v2.api.model.v08.bodies.BodyLike;

import javax.annotation.Nullable;

public class MimeTypeImpl implements MimeType
{

    private BodyLike bodyLike;

    public MimeTypeImpl(BodyLike bodyLike)
    {
        this.bodyLike = bodyLike;
    }

    @Override
    public String getType()
    {
        return bodyLike.name();
    }

    @Override
    public String getExample()
    {
        return bodyLike.example() != null ? bodyLike.example().value() : null;
    }

    @Override
    public String getSchema()
    {
        return bodyLike.schema() != null ? bodyLike.schema().value() : null;
    }

    @Override
    public Map<String, TypeFieldDefinition> getFormParameters()
    {

        return Maps.uniqueIndex(Lists.transform(bodyLike.formParameters(),
                new Function<org.raml.v2.api.model.v08.parameters.Parameter, TypeFieldDefinition>()
                {
                    @Nullable
                    @Override
                    public TypeFieldDefinition apply(@Nullable org.raml.v2.api.model.v08.parameters.Parameter input)
                    {
                        return new ParameterImpl(input);
                    }
                }), new Function<TypeFieldDefinition, String>()
        {
            @Nullable @Override public String apply(@Nullable TypeFieldDefinition input)
            {
                return input.getName();
            }
        });
    }

}
