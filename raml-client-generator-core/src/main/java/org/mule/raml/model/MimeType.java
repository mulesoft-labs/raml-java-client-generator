/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.model;

import java.util.Map;

public interface MimeType
{

    String getSchema();

    String getSchemaPath();

    Map<String, TypeFieldDefinition> getFormParameters();

    /**
     * @return the mime type name (e.g: application/json)
     */
    String getType();

    String getExample();

}
