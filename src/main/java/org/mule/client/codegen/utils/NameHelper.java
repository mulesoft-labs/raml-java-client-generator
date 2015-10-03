package org.mule.client.codegen.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.remove;

public class NameHelper {

    private static List<String> keywords = Arrays.asList(
            "abstract", "continue", "for", "new", "switch",
            "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this",
            "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws",
            "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try",
            "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile",
            "const", "float", "native", "super", "while");


    public static String toValidClassName(String name) {
        return toValidJavaIdentifier(toCamelCase(toValidJavaIdentifier(name), false));
    }

    public static String toValidFieldName(String name) {
        return toValidJavaIdentifier(toCamelCase(name, true));
    }

    public static String getSetterName(String name) {
        return toValidJavaIdentifier("set" + toCamelCase(name, false));
    }

    public static String getGetterName(String name) {
        return toValidJavaIdentifier("get" + toCamelCase(name, false));
    }

    public static String toResourcePathToPackageName(String resourcePath) {
        String packagePath = "";
        final String[] elements = resourcePath.split("/");
        if (elements.length > 1) {
            for (int i = 1; i < elements.length; i++) {
                String element = elements[i];
                if (packagePath.isEmpty()) {
                    packagePath = packagePath + toValidJavaIdentifier(element);
                } else {
                    packagePath = packagePath + "." + toValidJavaIdentifier(element);
                }
            }
        }
        return packagePath;
    }

    private static String toValidJavaIdentifier(String name) {
        StringBuilder sb = new StringBuilder();
        if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            name = name.substring(1);
        }
        for (char c : name.toCharArray()) {
            if (!Character.isJavaIdentifierPart(c)) {
                sb.append("");
            } else {
                sb.append(c);
            }
        }
        if (keywords.contains(sb.toString())) {
            sb.append("_");
        }
        return sb.toString();
    }

    public static String toCamelCase(String name, boolean startWithLowerCase) {
        char[] wordDelimiters = new char[]{'_', ' ', '-'};

        if (containsAny(name, wordDelimiters)) {
            String capitalizedNodeName = WordUtils.capitalize(name, wordDelimiters);
            name = name.charAt(0) + capitalizedNodeName.substring(1);

            for (char c : wordDelimiters) {
                name = remove(name, c);
            }
        }

        return startWithLowerCase ? StringUtils.uncapitalize(name) : StringUtils.capitalize(name);
    }
}
