
package library.resource.provider.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class ProviderGETResponseBody {

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
    public ProviderGETResponseBody() {
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
    public ProviderGETResponseBody(Type type, Oauth2AuthorizationProvider oauth2AuthorizationProvider, Oauth2ClientProvider oauth2ClientProvider, Oauth2TokenProvider oauth2TokenProvider, Oauth2TokenValidationProvider oauth2TokenValidationProvider, Saml saml, ServiceProvider serviceProvider) {
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

    public ProviderGETResponseBody withType(Type type) {
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

    public ProviderGETResponseBody withOauth2AuthorizationProvider(Oauth2AuthorizationProvider oauth2AuthorizationProvider) {
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

    public ProviderGETResponseBody withOauth2ClientProvider(Oauth2ClientProvider oauth2ClientProvider) {
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

    public ProviderGETResponseBody withOauth2TokenProvider(Oauth2TokenProvider oauth2TokenProvider) {
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

    public ProviderGETResponseBody withOauth2TokenValidationProvider(Oauth2TokenValidationProvider oauth2TokenValidationProvider) {
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

    public ProviderGETResponseBody withSaml(Saml saml) {
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

    public ProviderGETResponseBody withServiceProvider(ServiceProvider serviceProvider) {
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

    public ProviderGETResponseBody withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProviderGETResponseBody.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("oauth2AuthorizationProvider");
        sb.append('=');
        sb.append(((this.oauth2AuthorizationProvider == null)?"<null>":this.oauth2AuthorizationProvider));
        sb.append(',');
        sb.append("oauth2ClientProvider");
        sb.append('=');
        sb.append(((this.oauth2ClientProvider == null)?"<null>":this.oauth2ClientProvider));
        sb.append(',');
        sb.append("oauth2TokenProvider");
        sb.append('=');
        sb.append(((this.oauth2TokenProvider == null)?"<null>":this.oauth2TokenProvider));
        sb.append(',');
        sb.append("oauth2TokenValidationProvider");
        sb.append('=');
        sb.append(((this.oauth2TokenValidationProvider == null)?"<null>":this.oauth2TokenValidationProvider));
        sb.append(',');
        sb.append("saml");
        sb.append('=');
        sb.append(((this.saml == null)?"<null>":this.saml));
        sb.append(',');
        sb.append("serviceProvider");
        sb.append('=');
        sb.append(((this.serviceProvider == null)?"<null>":this.serviceProvider));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.oauth2ClientProvider == null)? 0 :this.oauth2ClientProvider.hashCode()));
        result = ((result* 31)+((this.oauth2AuthorizationProvider == null)? 0 :this.oauth2AuthorizationProvider.hashCode()));
        result = ((result* 31)+((this.saml == null)? 0 :this.saml.hashCode()));
        result = ((result* 31)+((this.serviceProvider == null)? 0 :this.serviceProvider.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.oauth2TokenValidationProvider == null)? 0 :this.oauth2TokenValidationProvider.hashCode()));
        result = ((result* 31)+((this.oauth2TokenProvider == null)? 0 :this.oauth2TokenProvider.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProviderGETResponseBody) == false) {
            return false;
        }
        ProviderGETResponseBody rhs = ((ProviderGETResponseBody) other);
        return (((((((((this.oauth2ClientProvider == rhs.oauth2ClientProvider)||((this.oauth2ClientProvider!= null)&&this.oauth2ClientProvider.equals(rhs.oauth2ClientProvider)))&&((this.oauth2AuthorizationProvider == rhs.oauth2AuthorizationProvider)||((this.oauth2AuthorizationProvider!= null)&&this.oauth2AuthorizationProvider.equals(rhs.oauth2AuthorizationProvider))))&&((this.saml == rhs.saml)||((this.saml!= null)&&this.saml.equals(rhs.saml))))&&((this.serviceProvider == rhs.serviceProvider)||((this.serviceProvider!= null)&&this.serviceProvider.equals(rhs.serviceProvider))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.oauth2TokenValidationProvider == rhs.oauth2TokenValidationProvider)||((this.oauth2TokenValidationProvider!= null)&&this.oauth2TokenValidationProvider.equals(rhs.oauth2TokenValidationProvider))))&&((this.oauth2TokenProvider == rhs.oauth2TokenProvider)||((this.oauth2TokenProvider!= null)&&this.oauth2TokenProvider.equals(rhs.oauth2TokenProvider))));
    }

}
