
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
    "issuer",
    "public_key",
    "audience",
    "bypass_expiration"
})
public class Saml {

    @JsonProperty("name")
    private String name;
    @JsonProperty("issuer")
    private String issuer;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("audience")
    private String audience;
    @JsonProperty("bypass_expiration")
    private Boolean bypassExpiration;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Saml() {
    }

    /**
     * 
     * @param audience
     * @param name
     * @param publicKey
     * @param bypassExpiration
     * @param issuer
     */
    public Saml(String name, String issuer, String publicKey, String audience, Boolean bypassExpiration) {
        super();
        this.name = name;
        this.issuer = issuer;
        this.publicKey = publicKey;
        this.audience = audience;
        this.bypassExpiration = bypassExpiration;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Saml withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("issuer")
    public String getIssuer() {
        return issuer;
    }

    @JsonProperty("issuer")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Saml withIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    @JsonProperty("public_key")
    public String getPublicKey() {
        return publicKey;
    }

    @JsonProperty("public_key")
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Saml withPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    @JsonProperty("audience")
    public String getAudience() {
        return audience;
    }

    @JsonProperty("audience")
    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Saml withAudience(String audience) {
        this.audience = audience;
        return this;
    }

    @JsonProperty("bypass_expiration")
    public Boolean getBypassExpiration() {
        return bypassExpiration;
    }

    @JsonProperty("bypass_expiration")
    public void setBypassExpiration(Boolean bypassExpiration) {
        this.bypassExpiration = bypassExpiration;
    }

    public Saml withBypassExpiration(Boolean bypassExpiration) {
        this.bypassExpiration = bypassExpiration;
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

    public Saml withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("issuer", issuer).append("publicKey", publicKey).append("audience", audience).append("bypassExpiration", bypassExpiration).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(audience).append(name).append(publicKey).append(additionalProperties).append(bypassExpiration).append(issuer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Saml) == false) {
            return false;
        }
        Saml rhs = ((Saml) other);
        return new EqualsBuilder().append(audience, rhs.audience).append(name, rhs.name).append(publicKey, rhs.publicKey).append(additionalProperties, rhs.additionalProperties).append(bypassExpiration, rhs.bypassExpiration).append(issuer, rhs.issuer).isEquals();
    }

}
