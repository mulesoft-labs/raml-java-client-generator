
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
        StringBuilder sb = new StringBuilder();
        sb.append(Saml.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("issuer");
        sb.append('=');
        sb.append(((this.issuer == null)?"<null>":this.issuer));
        sb.append(',');
        sb.append("publicKey");
        sb.append('=');
        sb.append(((this.publicKey == null)?"<null>":this.publicKey));
        sb.append(',');
        sb.append("audience");
        sb.append('=');
        sb.append(((this.audience == null)?"<null>":this.audience));
        sb.append(',');
        sb.append("bypassExpiration");
        sb.append('=');
        sb.append(((this.bypassExpiration == null)?"<null>":this.bypassExpiration));
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
        result = ((result* 31)+((this.audience == null)? 0 :this.audience.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.publicKey == null)? 0 :this.publicKey.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.bypassExpiration == null)? 0 :this.bypassExpiration.hashCode()));
        result = ((result* 31)+((this.issuer == null)? 0 :this.issuer.hashCode()));
        return result;
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
        return (((((((this.audience == rhs.audience)||((this.audience!= null)&&this.audience.equals(rhs.audience)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.publicKey == rhs.publicKey)||((this.publicKey!= null)&&this.publicKey.equals(rhs.publicKey))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.bypassExpiration == rhs.bypassExpiration)||((this.bypassExpiration!= null)&&this.bypassExpiration.equals(rhs.bypassExpiration))))&&((this.issuer == rhs.issuer)||((this.issuer!= null)&&this.issuer.equals(rhs.issuer))));
    }

}
