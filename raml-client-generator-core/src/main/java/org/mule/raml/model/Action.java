/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.model;

import java.util.Map;

public interface Action
{
    ActionType getType();

    Resource getResource();

    Map<String, MimeType> getBody();

    Map<String, TypeFieldDefinition> getQueryParameters();

    Map<String, Response> getResponses();

    Map<String, TypeFieldDefinition> getHeaders();

    String getDescription();
}
