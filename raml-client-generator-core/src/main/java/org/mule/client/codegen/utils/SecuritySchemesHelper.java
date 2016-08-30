package org.mule.client.codegen.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.SecurityScheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecuritySchemesHelper
{

    public static final String BASIC_AUTHENTICATION = "Basic Authentication";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static List<Pair<String, SecurityScheme>> getSupportedSecuritySchemes(ApiModel raml)
    {
        List<Pair<String, SecurityScheme>> supportedSecuritySchemes = new ArrayList<>();
        for (SecurityScheme schemeMap : raml.getSecuritySchemes())
        {
            if (schemeMap.getType().equals("Basic Authentication"))
            {
                supportedSecuritySchemes.add(new ImmutablePair<>(schemeMap.getType(), schemeMap));
            }
        }

        return supportedSecuritySchemes;
    }

    public static Collection<? extends JFieldVar> createBasicAuthFields(JDefinedClass containerClass)
    {
        List<JFieldVar> fieldVarList = new ArrayList<>();
        JCodeModel jCodeModel = new JCodeModel();
        fieldVarList.add(containerClass.field(JMod.PRIVATE | JMod.STATIC, jCodeModel._ref(String.class), USERNAME));
        fieldVarList.add(containerClass.field(JMod.PRIVATE | JMod.STATIC, jCodeModel._ref(String.class), PASSWORD));
        return fieldVarList;
    }
}
