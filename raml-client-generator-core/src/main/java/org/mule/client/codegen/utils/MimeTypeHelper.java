package org.mule.client.codegen.utils;


import org.raml.model.MimeType;

public class MimeTypeHelper {
    public static final String APPLICATION_JSON_MIME_TYPE = "application/json";
    public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
    public static final String BINARY_OCTET_STREAM_MIME_TYPE = "/octet-stream";

    public static boolean isJsonType(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(APPLICATION_JSON_MIME_TYPE);
    }

    public static boolean isTextType(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(TEXT_PLAIN_MIME_TYPE);
    }

    public static boolean isBinaryType(MimeType mimeType) {
        return mimeType.getType().endsWith(BINARY_OCTET_STREAM_MIME_TYPE);
    }
}
