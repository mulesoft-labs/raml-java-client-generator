/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v08.model;

import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.Resource;
import org.mule.raml.model.parameter.Parameter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.raml.v2.api.model.v08.methods.Method;

public class ResourceImpl implements Resource
{

    private org.raml.v2.api.model.v08.resources.Resource resource;

    public ResourceImpl(org.raml.v2.api.model.v08.resources.Resource resource)
    {
        this.resource = resource;
    }

    @Override
    public String getRelativeUri()
    {
        return resource.relativeUri().value();
    }

    @Override public String getDescription()
    {
        return null;
    }

    @Override
    public String getUri()
    {
        return resource.resourcePath();
    }

    @Override
    public Action getAction(String name)
    {
        for (Method method : resource.methods())
        {
            if (method.method().equals(name))
            {
                return new ActionImpl(method);
            }
        }
        return null;
    }

    @Override
    public Map<ActionType, Action> getActions()
    {
        Map<ActionType, Action> map = new LinkedHashMap<>();
        for (Method method : resource.methods())
        {
            map.put(ActionType.valueOf(method.method().toUpperCase()), new ActionImpl(method));
        }
        return map;
    }

    @Override
    public Map<String, Resource> getResources()
    {
        Map<String, Resource> result = new HashMap<>();
        for (org.raml.v2.api.model.v08.resources.Resource item : resource.resources())
        {
            result.put(item.relativeUri().value(), new ResourceImpl(item));
        }
        return result;
    }

    @Override
    public String getDisplayName()
    {
        return resource.displayName();
    }

    @Override
    public Map<String, Parameter> getResolvedUriParameters()
    {
        Map<String, Parameter> result = new HashMap<>();
        org.raml.v2.api.model.v08.resources.Resource current = resource;
        while (current != null)
        {
            for (org.raml.v2.api.model.v08.parameters.Parameter parameter : current.uriParameters())
            {
                result.put(parameter.name(), new ParameterImpl(parameter));
            }
            current = current.parentResource();
        }
        return result;
    }

    @Override
    public Map<String, List<Parameter>> getBaseUriParameters()
    {
        throw new UnsupportedOperationException();
    }

}
