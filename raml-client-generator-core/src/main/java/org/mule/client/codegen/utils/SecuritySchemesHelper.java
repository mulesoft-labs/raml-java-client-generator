package org.mule.client.codegen.utils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.mule.raml.model.ApiModel;
import org.mule.raml.model.Resource;
import org.mule.raml.model.SecurityScheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SecuritySchemesHelper {

    public static final String BASIC_AUTHENTICATION = "Basic Authentication";
    public static final String OAUTH_20 = "OAuth 2.0";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static List<String> SUPPORTED_SECURITY_SCHEMES = Arrays.asList(OAUTH_20, BASIC_AUTHENTICATION);

    public static List<Pair<String, SecurityScheme>> getSupportedSecuritySchemes(ApiModel raml) {
        List<Pair<String, SecurityScheme>> supportedSecuritySchemes = new ArrayList<>();
        for (SecurityScheme schemeMap : raml.getSecuritySchemes()) {
            if (SUPPORTED_SECURITY_SCHEMES.contains(schemeMap.getType())) {
                supportedSecuritySchemes.add(new ImmutablePair<>(schemeMap.getType(), schemeMap));
            }
        }
        List<SecurityScheme> securedBy = raml.getSecuredBy();
        for (SecurityScheme securedBySecurityScheme : securedBy) {
            if (SUPPORTED_SECURITY_SCHEMES.contains(securedBySecurityScheme.getType())) {
                supportedSecuritySchemes.add(new ImmutablePair<>(securedBySecurityScheme.getType(), securedBySecurityScheme));
            }
		}
        return supportedSecuritySchemes;
    }

    public static boolean isOauth20SecuredBy(Resource resource) {
        List<SecurityScheme> securedBy = resource.getSecuredBy();
        return isOauth20(securedBy);
    }

    public static boolean isOauth20SecuredBy(ApiModel apiModel) {
        List<SecurityScheme> securedBy = apiModel.getSecuredBy();
        return isOauth20(securedBy);
    }

    private static boolean isOauth20(List<SecurityScheme> securedBy) {
        boolean containsOauth = false;
        for (SecurityScheme securityScheme : securedBy) {
            containsOauth = containsOauth || (securityScheme != null && securityScheme.getType().equals(SecuritySchemesHelper.OAUTH_20));
        }

        return containsOauth;
    }

    public static Collection<? extends JFieldVar> createBasicAuthFields(JDefinedClass containerClass) {
        List<JFieldVar> fieldVarList = new ArrayList<>();
        JCodeModel jCodeModel = new JCodeModel();
        fieldVarList.add(containerClass.field(JMod.PRIVATE | JMod.STATIC, jCodeModel._ref(String.class), USERNAME));
        fieldVarList.add(containerClass.field(JMod.PRIVATE | JMod.STATIC, jCodeModel._ref(String.class), PASSWORD));
        return fieldVarList;
    }
}
