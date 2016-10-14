
package simple.resource.cs.login.model;

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
    "username",
    "password"
})
public class LoginPOSTResponse {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("username")
    private String username;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("password")
    private String password;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public LoginPOSTResponse() {
    }

    /**
     * 
     * @param password
     * @param username
     */
    public LoginPOSTResponse(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The username
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * 
     * (Required)
     * 
     * @param username
     *     The username
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public LoginPOSTResponse withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * 
     * (Required)
     * 
     * @param password
     *     The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public LoginPOSTResponse withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public LoginPOSTResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(username).append(password).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LoginPOSTResponse) == false) {
            return false;
        }
        LoginPOSTResponse rhs = ((LoginPOSTResponse) other);
        return new EqualsBuilder().append(username, rhs.username).append(password, rhs.password).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
