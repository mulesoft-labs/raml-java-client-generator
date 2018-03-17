/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v08.model;

import com.google.common.base.Function;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.DocumentationItem;
import org.mule.raml.model.Resource;
import org.mule.raml.model.SecurityScheme;
import org.mule.raml.model.TypeFieldDefinition;
import org.raml.v2.api.model.v08.api.Api;
import org.raml.v2.api.model.v08.api.GlobalSchema;
import org.raml.v2.api.model.v08.security.SecuritySchemeRef;
import org.raml.v2.api.model.v08.system.types.FullUriTemplateString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.transform;

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
        List<org.raml.v2.api.model.v08.resources.Resource> resources = api.resources();
        for (org.raml.v2.api.model.v08.resources.Resource resource : resources)
        {
            map.put(resource.relativeUri().value(), new ResourceImpl(resource));
        }
        return map;
    }

    @Override
    public String getVersion()
    {
        return api.version();
    }

    @Override
    public String getBaseUri()
    {
        final FullUriTemplateString baseUri = api.baseUri();
        return baseUri != null ? baseUri.value() : null;
    }

    @Override
    public Map<String, TypeFieldDefinition> getBaseUriParameters()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SecurityScheme> getSecuritySchemes()
    {
        return transform(api.securitySchemes(), new Function<org.raml.v2.api.model.v08.security.SecurityScheme, SecurityScheme>()
        {
            @Nullable @Override public SecurityScheme apply(@Nullable org.raml.v2.api.model.v08.security.SecurityScheme input)
            {
                return new SecuritySchemeImpl(input);
            }
        });

    }

    @Override
    public List<Map<String, String>> getSchemas()
    {
        Map<String, String> map = new LinkedHashMap<>();
        List<GlobalSchema> schemas = api.schemas();
        for (GlobalSchema schema : schemas)
        {
            map.put(schema.key(), schema.value() != null ? schema.value().value() : null);
        }
        List<Map<String, String>> result = new ArrayList<>();
        result.add(map);
        return result;
    }

    @Override
    public List<DocumentationItem> getDocumentation()
    {
        return transform(api.documentation(), new Function<org.raml.v2.api.model.v08.api.DocumentationItem, DocumentationItem>()
        {
            @Nullable @Override public DocumentationItem apply(@Nullable org.raml.v2.api.model.v08.api.DocumentationItem input)
            {
                return new DocumentationItemImpl(input);
            }
        });
    }

    @Override
    public String getTitle()
    {
        return api.title();
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
