
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
    "name",
    "url",
    "client_id",
    "client_secret"
})
public class Oauth2TokenValidationProvider {

    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Oauth2TokenValidationProvider() {
    }

    /**
     * 
     * @param clientId
     * @param name
     * @param clientSecret
     * @param url
     */
    public Oauth2TokenValidationProvider(String name, String url, String clientId, String clientSecret) {
        super();
        this.name = name;
        this.url = url;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Oauth2TokenValidationProvider withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public Oauth2TokenValidationProvider withUrl(String url) {
        this.url = url;
        return this;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Oauth2TokenValidationProvider withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Oauth2TokenValidationProvider withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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

    public Oauth2TokenValidationProvider withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("url", url).append("clientId", clientId).append("clientSecret", clientSecret).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(clientSecret).append(clientId).append(additionalProperties).append(url).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Oauth2TokenValidationProvider) == false) {
            return false;
        }
        Oauth2TokenValidationProvider rhs = ((Oauth2TokenValidationProvider) other);
        return new EqualsBuilder().append(name, rhs.name).append(clientSecret, rhs.clientSecret).append(clientId, rhs.clientId).append(additionalProperties, rhs.additionalProperties).append(url, rhs.url).isEquals();
    }

}
