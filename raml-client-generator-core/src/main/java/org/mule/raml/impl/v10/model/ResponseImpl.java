/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import org.mule.raml.model.MimeType;
import org.mule.raml.model.Response;
import org.mule.raml.model.parameter.Parameter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

public class ResponseImpl implements Response
{

    private org.raml.v2.api.model.v10.bodies.Response response;

    public ResponseImpl(org.raml.v2.api.model.v10.bodies.Response response)
    {
        this.response = response;
    }

    @Override
    public boolean hasBody()
    {
        return !response.body().isEmpty();
    }

    @Override
    public Map<String, MimeType> getBody()
    {
        Map<String, MimeType> result = new LinkedHashMap<>();
        for (TypeDeclaration typeDeclaration : response.body())
        {
            result.put(typeDeclaration.name(), new MimeTypeImpl(typeDeclaration));
        }
        return result;
    }

    @Override
    public Map<String, Parameter> getHeaders()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBody(Map<String, MimeType> body)
    {
        throw new UnsupportedOperationException();
    }

}
