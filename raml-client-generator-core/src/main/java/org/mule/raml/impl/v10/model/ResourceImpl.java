/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.raml.impl.v10.model;

import static org.mule.raml.ApiModelLoader.nullSafe;

import org.mule.raml.model.Action;
import org.mule.raml.model.ActionType;
import org.mule.raml.model.Resource;

import java.util.*;

import org.mule.raml.model.SecurityScheme;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.security.SecuritySchemeRef;
import org.raml.v2.api.model.v10.system.types.MarkdownString;

public class ResourceImpl implements Resource {

    private org.raml.v2.api.model.v10.resources.Resource resource;

    public ResourceImpl(org.raml.v2.api.model.v10.resources.Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getDescription() {
        final MarkdownString description = resource.description();
        return description != null ? description.value() : null;
    }

    @Override
    public String getUri() {
        return resource.resourcePath();
    }


    public List<SecurityScheme> getSecuredBy() {
        List<SecuritySchemeRef> securitySchemeRefs = resource.securedBy();
        List<SecurityScheme> result = new ArrayList<>();
        for (SecuritySchemeRef securitySchemeRef : securitySchemeRefs) {
            result.add(new SecuritySchemeImpl(securitySchemeRef.securityScheme()));
        }
        return result;
    }

    @Override
    public Action getAction(String name) {
        for (Method method : resource.methods()) {
            if (method.method().equals(name)) {
                return new ActionImpl(method);
            }
        }
        return null;
    }

    @Override
    public Map<ActionType, Action> getActions() {
        Map<ActionType, Action> map = new LinkedHashMap<>();
        for (Method method : resource.methods()) {
            map.put(ActionType.valueOf(method.method().toUpperCase()), new ActionImpl(method));
        }
        return map;
    }

    @Override
    public Map<String, Resource> getResources() {
        Map<String, Resource> result = new HashMap<>();
        for (org.raml.v2.api.model.v10.resources.Resource item : resource.resources()) {
            result.put(item.relativeUri().value(), new ResourceImpl(item));
        }
        return result;
    }

    @Override
    public String getDisplayName() {
        return nullSafe(resource.displayName());
    }

}
