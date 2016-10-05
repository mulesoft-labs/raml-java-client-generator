/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v08.model;

import org.mule.raml.model.MimeType;
import org.mule.raml.model.Response;
import org.mule.raml.model.TypeFieldDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.v2.api.model.v08.bodies.BodyLike;

public class ResponseImpl implements Response
{

    private org.raml.v2.api.model.v08.bodies.Response response;

    public ResponseImpl(org.raml.v2.api.model.v08.bodies.Response response)
    {
        this.response = response;
    }

    @Override
    public Map<String, MimeType> getBody()
    {
        Map<String, MimeType> result = new LinkedHashMap<>();
        for (BodyLike bodyLike : response.body())
        {
            result.put(bodyLike.name(), new MimeTypeImpl(bodyLike));
        }
        return result;
    }

    @Override
    public Map<String, TypeFieldDefinition> getHeaders()
    {
        throw new UnsupportedOperationException();
    }

}
