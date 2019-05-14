package org.mule.client.codegen;

public class CodeGenConfig {

    private boolean includeAdditionalProperties = true;

    private boolean useJava8Dates = false;

    private boolean useJava8Optional = false;

    private String targetVersion = "1.6";

    public CodeGenConfig() {
    }

    public Boolean getIncludeAdditionalProperties() {
        return includeAdditionalProperties;
    }

    public CodeGenConfig setIncludeAdditionalProperties(boolean includeAdditionalProperties) {
        this.includeAdditionalProperties = includeAdditionalProperties;
        return this;
    }

    public boolean getUseJava8Dates() {
        return useJava8Dates;

    }

    public boolean getUseJava8Optional() {
        return useJava8Optional;
    }

    public CodeGenConfig setUseJava8Optional(boolean useJava8Optional) {
        this.useJava8Optional = useJava8Optional;
        return this;
    }

    public CodeGenConfig setUseJava8Dates(boolean useJava8Dates) {
        this.useJava8Dates = useJava8Dates;
        return this;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public CodeGenConfig setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
        return this;
    }
}
