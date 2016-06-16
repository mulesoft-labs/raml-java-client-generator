package org.mule.client.codegen.utils;


import org.raml.model.MimeType;

public class MimeTypeHelper {
    public static final String APPLICATION_JSON_MIME_TYPE = "application/json";
    public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
    public static final String BINARY_OCTET_STREAM_MIME_TYPE = "/octet-stream";
    public static final String MULTIPART_FORM_DATA_MIME_TYPE = "multipart/form-data";
    public static final String X_WWWW_FORM_URL_ENCODED_MIME_TYPE = "application/x-www-form-urlencoded";


    public static boolean isJsonType(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(APPLICATION_JSON_MIME_TYPE);
    }

    public static boolean isMultiPartType(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(MULTIPART_FORM_DATA_MIME_TYPE);
    }

    public static boolean isFormUrlEncoded(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(X_WWWW_FORM_URL_ENCODED_MIME_TYPE);
    }

    public static boolean isTextType(MimeType mimeType) {
        return mimeType.getType().equalsIgnoreCase(TEXT_PLAIN_MIME_TYPE);
    }

    public static boolean isBinaryType(MimeType mimeType) {
        return mimeType.getType().endsWith(BINARY_OCTET_STREAM_MIME_TYPE);
    }
}
