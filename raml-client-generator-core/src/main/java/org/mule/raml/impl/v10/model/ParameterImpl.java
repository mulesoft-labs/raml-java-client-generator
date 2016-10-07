/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import org.mule.raml.model.TypeFieldDefinition;
import org.raml.v2.api.model.v10.datamodel.ArrayTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.ExampleSpec;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.system.types.AnnotableSimpleType;
import org.raml.v2.api.model.v10.system.types.MarkdownString;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParameterImpl implements TypeFieldDefinition
{

    private TypeDeclaration typeDeclaration;

    public ParameterImpl(TypeDeclaration typeDeclaration)
    {

        this.typeDeclaration = typeDeclaration;
    }

    @Override
    public boolean isRequired()
    {
        return typeDeclaration.required();
    }

    @Override
    public String getDefaultValue()
    {
        return typeDeclaration.defaultValue();
    }

    @Override
    public boolean isRepeat()
    {
        // only available in RAML 0.8
        return false;
    }

    @Override
    public boolean isArray()
    {
        return typeDeclaration instanceof ArrayTypeDeclaration;
    }

    @Override
    public String getDisplayName()
    {
        return getValue(typeDeclaration.displayName());
    }

    @Override
    public String getDescription()
    {
        final MarkdownString description = typeDeclaration.description();
        return getValue(description);
    }

    public static String getValue(AnnotableSimpleType<String> description)
    {
        return description != null ? description.value() : null;
    }

    @Override
    public String getExample()
    {
        if (typeDeclaration.example() == null)
        {
            return null;
        }
        return typeDeclaration.example().value();
    }

    @Override
    public Map<String, String> getExamples()
    {
        Map<String, String> examples = new LinkedHashMap<>();
        for (ExampleSpec example : typeDeclaration.examples())
        {
            examples.put(example.name(), example.value());
        }
        return examples;
    }

    @Override public String getName()
    {
        return null;
    }

    @Override
    public String getType() {
        return typeDeclaration.type();
    }
}
