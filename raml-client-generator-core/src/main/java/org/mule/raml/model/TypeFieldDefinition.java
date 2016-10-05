/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.model;

import java.util.Map;

public interface TypeFieldDefinition
{
    boolean isRequired();

    String getDefaultValue();

    boolean isRepeat();

    boolean isArray();

    String getDisplayName();

    String getDescription();

    String getExample();

    Map<String, String> getExamples();

    String getName();
}
