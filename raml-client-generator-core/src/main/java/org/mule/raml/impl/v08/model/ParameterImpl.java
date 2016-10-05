/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v08.model;

import org.mule.raml.model.TypeFieldDefinition;
import org.raml.v2.api.model.v08.system.types.MarkdownString;

import java.util.HashMap;
import java.util.Map;

public class ParameterImpl implements TypeFieldDefinition
{

    private org.raml.v2.api.model.v08.parameters.Parameter parameter;

    public ParameterImpl(org.raml.v2.api.model.v08.parameters.Parameter parameter)
    {
        this.parameter = parameter;
    }

    @Override
    public boolean isRequired()
    {
        return parameter.required();
    }

    @Override
    public String getDefaultValue()
    {
        if (parameter.defaultValue() == null)
        {
            return null;
        }
        return parameter.defaultValue().toString();
    }

    @Override
    public boolean isRepeat()
    {
        return parameter.repeat();
    }

    @Override
    public boolean isArray()
    {
        // only available in RAML 1.0+
        return false;
    }


    public String getName()
    {
        return parameter.name();
    }

    @Override
    public String getDisplayName()
    {
        return parameter.displayName();
    }

    @Override
    public String getDescription()
    {
        final MarkdownString description = parameter.description();
        return description != null ? description.value() : null;
    }

    @Override
    public String getExample()
    {
        return parameter.example();
    }

    @Override
    public Map<String, String> getExamples()
    {
        // only available in RAML 1.0+
        return new HashMap<>();
    }

}
