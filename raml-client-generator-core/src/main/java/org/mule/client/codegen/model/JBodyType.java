package org.mule.client.codegen.model;

import com.sun.codemodel.JType;
import org.mule.raml.model.MimeType;

public class JBodyType {

    private JType type;

    private MimeType mimeType;

    public JBodyType(JType type, MimeType mimeType) {
        this.type = type;
        this.mimeType = mimeType;
    }

    public JType getType() {
        return type;
    }

    public MimeType getMimeType() {
        return mimeType;
    }
}