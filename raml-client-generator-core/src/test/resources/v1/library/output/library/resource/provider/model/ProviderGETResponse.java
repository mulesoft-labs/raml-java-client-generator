
package library.resource.provider.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "oauth2_authorization_provider",
    "oauth2_client_provider",
    "oauth2_token_provider",
    "oauth2_token_validation_provider",
    "saml",
    "service_provider"
})
public class ProviderGETResponse {

    @JsonProperty("type")
    private Type type;
    @JsonProperty("oauth2_authorization_provider")
    private Oauth2AuthorizationProvider oauth2AuthorizationProvider;
    @JsonProperty("oauth2_client_provider")
    private Oauth2ClientProvider oauth2ClientProvider;
    @JsonProperty("oauth2_token_provider")
    private Oauth2TokenProvider oauth2TokenProvider;
    @JsonProperty("oauth2_token_validation_provider")
    private Oauth2TokenValidationProvider oauth2TokenValidationProvider;
    @JsonProperty("saml")
    private Saml saml;
    @JsonProperty("service_provider")
    private ServiceProvider serviceProvider;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProviderGETResponse() {
    }

    /**
     * 
     * @param oauth2ClientProvider
     * @param oauth2AuthorizationProvider
     * @param saml
     * @param serviceProvider
     * @param type
     * @param oauth2TokenValidationProvider
     * @param oauth2TokenProvider
     */
    public ProviderGETResponse(Type type, Oauth2AuthorizationProvider oauth2AuthorizationProvider, Oauth2ClientProvider oauth2ClientProvider, Oauth2TokenProvider oauth2TokenProvider, Oauth2TokenValidationProvider oauth2TokenValidationProvider, Saml saml, ServiceProvider serviceProvider) {
        super();
        this.type = type;
        this.oauth2AuthorizationProvider = oauth2AuthorizationProvider;
        this.oauth2ClientProvider = oauth2ClientProvider;
        this.oauth2TokenProvider = oauth2TokenProvider;
        this.oauth2TokenValidationProvider = oauth2TokenValidationProvider;
        this.saml = saml;
        this.serviceProvider = serviceProvider;
    }

    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    public ProviderGETResponse withType(Type type) {
        this.type = type;
        return this;
    }

    @JsonProperty("oauth2_authorization_provider")
    public Oauth2AuthorizationProvider getOauth2AuthorizationProvider() {
        return oauth2AuthorizationProvider;
    }

    @JsonProperty("oauth2_authorization_provider")
    public void setOauth2AuthorizationProvider(Oauth2AuthorizationProvider oauth2AuthorizationProvider) {
        this.oauth2AuthorizationProvider = oauth2AuthorizationProvider;
    }

    public ProviderGETResponse withOauth2AuthorizationProvider(Oauth2AuthorizationProvider oauth2AuthorizationProvider) {
        this.oauth2AuthorizationProvider = oauth2AuthorizationProvider;
        return this;
    }

    @JsonProperty("oauth2_client_provider")
    public Oauth2ClientProvider getOauth2ClientProvider() {
        return oauth2ClientProvider;
    }

    @JsonProperty("oauth2_client_provider")
    public void setOauth2ClientProvider(Oauth2ClientProvider oauth2ClientProvider) {
        this.oauth2ClientProvider = oauth2ClientProvider;
    }

    public ProviderGETResponse withOauth2ClientProvider(Oauth2ClientProvider oauth2ClientProvider) {
        this.oauth2ClientProvider = oauth2ClientProvider;
        return this;
    }

    @JsonProperty("oauth2_token_provider")
    public Oauth2TokenProvider getOauth2TokenProvider() {
        return oauth2TokenProvider;
    }

    @JsonProperty("oauth2_token_provider")
    public void setOauth2TokenProvider(Oauth2TokenProvider oauth2TokenProvider) {
        this.oauth2TokenProvider = oauth2TokenProvider;
    }

    public ProviderGETResponse withOauth2TokenProvider(Oauth2TokenProvider oauth2TokenProvider) {
        this.oauth2TokenProvider = oauth2TokenProvider;
        return this;
    }

    @JsonProperty("oauth2_token_validation_provider")
    public Oauth2TokenValidationProvider getOauth2TokenValidationProvider() {
        return oauth2TokenValidationProvider;
    }

    @JsonProperty("oauth2_token_validation_provider")
    public void setOauth2TokenValidationProvider(Oauth2TokenValidationProvider oauth2TokenValidationProvider) {
        this.oauth2TokenValidationProvider = oauth2TokenValidationProvider;
    }

    public ProviderGETResponse withOauth2TokenValidationProvider(Oauth2TokenValidationProvider oauth2TokenValidationProvider) {
        this.oauth2TokenValidationProvider = oauth2TokenValidationProvider;
        return this;
    }

    @JsonProperty("saml")
    public Saml getSaml() {
        return saml;
    }

    @JsonProperty("saml")
    public void setSaml(Saml saml) {
        this.saml = saml;
    }

    public ProviderGETResponse withSaml(Saml saml) {
        this.saml = saml;
        return this;
    }

    @JsonProperty("service_provider")
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @JsonProperty("service_provider")
    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public ProviderGETResponse withServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ProviderGETResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("oauth2AuthorizationProvider", oauth2AuthorizationProvider).append("oauth2ClientProvider", oauth2ClientProvider).append("oauth2TokenProvider", oauth2TokenProvider).append("oauth2TokenValidationProvider", oauth2TokenValidationProvider).append("saml", saml).append("serviceProvider", serviceProvider).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(oauth2ClientProvider).append(oauth2AuthorizationProvider).append(saml).append(serviceProvider).append(additionalProperties).append(type).append(oauth2TokenValidationProvider).append(oauth2TokenProvider).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProviderGETResponse) == false) {
            return false;
        }
        ProviderGETResponse rhs = ((ProviderGETResponse) other);
        return new EqualsBuilder().append(oauth2ClientProvider, rhs.oauth2ClientProvider).append(oauth2AuthorizationProvider, rhs.oauth2AuthorizationProvider).append(saml, rhs.saml).append(serviceProvider, rhs.serviceProvider).append(additionalProperties, rhs.additionalProperties).append(type, rhs.type).append(oauth2TokenValidationProvider, rhs.oauth2TokenValidationProvider).append(oauth2TokenProvider, rhs.oauth2TokenProvider).isEquals();
    }

}
