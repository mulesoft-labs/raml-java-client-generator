package org.mule.client.codegen.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.raml.model.Raml;
import org.raml.model.SecurityScheme;

public class SecuritySchemesHelper
{

    public static final String BASIC_AUTHENTICATION = "Basic Authentication";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static List<Pair<String, SecurityScheme>> getSupportedSecuritySchemes(Raml raml)
    {
        List<Pair<String, SecurityScheme>> supportedSecuritySchemes = new ArrayList<>();
        for (Map<String, SecurityScheme> schemeMap : raml.getSecuritySchemes())
        {
            for (Map.Entry<String, SecurityScheme> schemeEntry : schemeMap.entrySet())
            {
                if (schemeEntry.getValue().getType().equals("Basic Authentication"))
                {
                    supportedSecuritySchemes.add(new ImmutablePair<>(schemeEntry.getKey(), schemeEntry.getValue()));
                }
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
