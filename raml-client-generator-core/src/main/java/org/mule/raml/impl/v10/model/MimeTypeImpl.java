/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import static org.mule.raml.impl.v10.model.ApiModelImpl.getTypeAsString;

import org.mule.client.codegen.utils.MimeTypeHelper;
import org.mule.raml.model.MimeType;
import org.mule.raml.model.TypeFieldDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.raml.v2.api.model.v10.datamodel.ExampleSpec;
import org.raml.v2.api.model.v10.datamodel.ExternalTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

public class MimeTypeImpl implements MimeType {

    private TypeDeclaration typeDeclaration;

    public MimeTypeImpl(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    @Override
    public String getType() {
        return typeDeclaration.name();
    }

    @Override
    public String getExample() {
        ExampleSpec example = typeDeclaration.example();
        if (example != null && example.value() != null) {
            return example.value();
        }
        List<ExampleSpec> examples = typeDeclaration.examples();
        if (examples != null && !examples.isEmpty()) {
            if (examples.get(0).value() != null) {
                return examples.get(0).value();
            }
        }
        return null;
    }

    @Override
    public String getSchema() {
        if (typeDeclaration instanceof ExternalTypeDeclaration) {
            return ((ExternalTypeDeclaration) typeDeclaration).schemaContent();
        }
        if (MimeTypeHelper.isJsonType(this)) {
            return typeDeclaration.toJsonSchema();
        }
        return null;

    }

    @Override
    public Map<String, TypeFieldDefinition> getFormParameters() {
        // no longer supported in RAML 1.0
        return new HashMap<>();
    }

}
