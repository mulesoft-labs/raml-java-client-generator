package org.mule.raml.impl.v10.model;

import org.mule.raml.model.SecurityScheme;

public class SecuritySchemeImpl implements SecurityScheme
{
    private org.raml.v2.api.model.v10.security.SecurityScheme securityScheme;

    public SecuritySchemeImpl(org.raml.v2.api.model.v10.security.SecurityScheme securityScheme)
    {
        this.securityScheme = securityScheme;
    }

    @Override
    public String getType()
    {
        return securityScheme.type();
    }
}
