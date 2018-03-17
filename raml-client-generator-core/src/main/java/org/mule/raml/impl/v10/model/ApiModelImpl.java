/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import static com.google.common.collect.Lists.transform;
import static org.mule.raml.ApiModelLoader.nullSafe;

import com.google.common.base.Function;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.DocumentationItem;
import org.mule.raml.model.Resource;
import org.mule.raml.model.SecurityScheme;
import org.mule.raml.model.TypeFieldDefinition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.datamodel.AnyTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.ExternalTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.security.SecuritySchemeRef;
import org.raml.v2.api.model.v10.system.types.AnnotableSimpleType;

import javax.annotation.Nullable;

public class ApiModelImpl implements ApiModel
{

    private Api api;

    public ApiModelImpl(Api api)
    {
        this.api = api;
    }

    @Override
    public Map<String, Resource> getResources()
    {
        Map<String, Resource> map = new LinkedHashMap<>();
        List<org.raml.v2.api.model.v10.resources.Resource> resources = api.resources();
        for (org.raml.v2.api.model.v10.resources.Resource resource : resources)
        {
            map.put(resource.relativeUri().value(), new ResourceImpl(resource));
        }
        return map;
    }

    @Override
    public String getBaseUri()
    {
        return nullSafe(api.baseUri());
    }

    @Override
    public String getVersion()
    {
        return nullSafe(api.version());
    }

    @Override
    public List<Map<String, String>> getSchemas()
    {
        Map<String, String> map = new LinkedHashMap<>();
        List<TypeDeclaration> types = api.types();
        if (types.isEmpty())
        {
            types = api.schemas();
        }
        for (TypeDeclaration typeDeclaration : types)
        {
            map.put(typeDeclaration.name(), getTypeAsString(typeDeclaration));
        }
        List<Map<String, String>> result = new ArrayList<>();
        result.add(map);
        return result;
    }

    @Override
    public List<DocumentationItem> getDocumentation()
    {
        return transform(api.documentation(), new Function<org.raml.v2.api.model.v10.api.DocumentationItem, DocumentationItem>()
        {
            @Nullable @Override public DocumentationItem apply(@Nullable org.raml.v2.api.model.v10.api.DocumentationItem input)
            {
                return new DocumentationItemImpl(input);
            }
        });
    }

    static String getTypeAsString(TypeDeclaration typeDeclaration)
    {
        if (typeDeclaration instanceof ExternalTypeDeclaration)
        {
            return ((ExternalTypeDeclaration) typeDeclaration).schemaContent();
        }
        if (typeDeclaration instanceof AnyTypeDeclaration)
        {
            return null;
        }
        //return non-null value in order to detect that a schema was defined
        return "[yaml-type-flag]";
    }

    @Override
    public Map<String, TypeFieldDefinition> getBaseUriParameters()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SecurityScheme> getSecuritySchemes()
    {
        return transform(api.securitySchemes(), new Function<org.raml.v2.api.model.v10.security.SecurityScheme, SecurityScheme>()
        {
            @Nullable @Override public SecurityScheme apply(@Nullable org.raml.v2.api.model.v10.security.SecurityScheme input)
            {
                return new SecuritySchemeImpl(input);
            }
        });

    }

    @Override
    public String getTitle()
    {
        final AnnotableSimpleType<String> title = api.title();
        return title != null ? title.value() : null;
    }

    @Override
    public List<SecurityScheme> getSecuredBy() {
        List<SecuritySchemeRef> securitySchemeRefs = api.securedBy();
        List<SecurityScheme> result = new ArrayList<>();
        for (SecuritySchemeRef securitySchemeRef : securitySchemeRefs) {
            result.add(new SecuritySchemeImpl(securitySchemeRef.securityScheme()));
        }
        return result;
    }

}
