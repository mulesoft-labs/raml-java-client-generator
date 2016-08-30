/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.model;

import org.mule.raml.model.parameter.Parameter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ApiModel extends Serializable
{
    Resource getResource(String path);

    String getBaseUri();

    Map<String, Resource> getResources();

    String getVersion();

    Map<String, Parameter> getBaseUriParameters();

    List<SecurityScheme> getSecuritySchemes();

    String getUri();

    List<Map<String, String>> getSchemas();

    List<DocumentationItem> getDocumentation();

    String getTitle();
}
