/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.Resource;
import org.mule.raml.model.Response;
import org.mule.raml.model.parameter.Parameter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.system.types.MarkdownString;

public class ActionImpl implements Action
{

    private Method method;

    public ActionImpl(Method method)
    {
        this.method = method;
    }

    @Override
    public ActionType getType()
    {
        return ActionType.valueOf(method.method().toUpperCase());
    }

    @Override
    public boolean hasBody()
    {
        return !method.body().isEmpty();
    }

    @Override
    public Map<String, Response> getResponses()
    {
        Map<String, Response> result = new LinkedHashMap<>();
        for (org.raml.v2.api.model.v10.bodies.Response response : method.responses())
        {
            result.put(response.code().value(), new ResponseImpl(response));
        }
        return result;
    }

    @Override
    public Resource getResource()
    {
        return new ResourceImpl(method.resource());
    }

    @Override
    public Map<String, MimeType> getBody()
    {
        Map<String, MimeType> result = new LinkedHashMap<>();
        for (TypeDeclaration typeDeclaration : method.body())
        {
            result.put(typeDeclaration.name(),  new MimeTypeImpl(typeDeclaration));
        }
        return result;
    }

    @Override
    public Map<String, Parameter> getQueryParameters()
    {
        Map<String, Parameter> result = new HashMap<>();
        for (TypeDeclaration typeDeclaration : method.queryParameters())
        {
            result.put(typeDeclaration.name(), new ParameterImpl(typeDeclaration));
        }
        return result;
    }

    @Override
    public Map<String, List<Parameter>> getBaseUriParameters()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Parameter> getHeaders()
    {
        Map<String, Parameter> result = new HashMap<>();
        for (TypeDeclaration typeDeclaration : method.headers())
        {
            result.put(typeDeclaration.name(), new ParameterImpl(typeDeclaration));
        }
        return result;
    }

    @Override public String getDescription()
    {
        final MarkdownString description = method.description();
        return description != null ? description.value() : null;
    }

}
