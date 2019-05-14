
package java_8_dates.resource.cs.login.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created_at",
    "updated_at",
    "last_used",
    "user_id",
    "client_id",
    "domain",
    "scopes"
})
public class AuthorizationJson {

    /**
     * The time the authorization was created
     * (Required)
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("The time the authorization was created")
    private LocalDateTime createdAt;
    /**
     * The time the authorization was last modified
     * (Required)
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("The time the authorization was last modified")
    private LocalDateTime updatedAt;
    /**
     * The time the authorization was last used in an OAuth2 flow
     * 
     */
    @JsonProperty("last_used")
    @JsonPropertyDescription("The time the authorization was last used in an OAuth2 flow")
    private LocalDateTime lastUsed;
    /**
     * The ID of the user who granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    @JsonPropertyDescription("The ID of the user who granted the authorization")
    private String userId;
    /**
     * The ID of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    @JsonPropertyDescription("The ID of the client who is granted the authorization")
    private String clientId;
    /**
     * The domain of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("domain")
    @JsonPropertyDescription("The domain of the client who is granted the authorization")
    private String domain;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scopes")
    private List<String> scopes = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthorizationJson() {
    }

    /**
     * 
     * @param lastUsed
     * @param createdAt
     * @param clientId
     * @param domain
     * @param scopes
     * @param userId
     * @param updatedAt
     */
    public AuthorizationJson(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastUsed, String userId, String clientId, String domain, List<String> scopes) {
        super();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastUsed = lastUsed;
        this.userId = userId;
        this.clientId = clientId;
        this.domain = domain;
        this.scopes = scopes;
    }

    /**
     * The time the authorization was created
     * (Required)
     * 
     */
    @JsonProperty("created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * The time the authorization was created
     * (Required)
     * 
     */
    @JsonProperty("created_at")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AuthorizationJson withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * The time the authorization was last modified
     * (Required)
     * 
     */
    @JsonProperty("updated_at")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * The time the authorization was last modified
     * (Required)
     * 
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AuthorizationJson withUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    /**
     * The time the authorization was last used in an OAuth2 flow
     * 
     */
    @JsonProperty("last_used")
    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    /**
     * The time the authorization was last used in an OAuth2 flow
     * 
     */
    @JsonProperty("last_used")
    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    public AuthorizationJson withLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    /**
     * The ID of the user who granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * The ID of the user who granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AuthorizationJson withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * The ID of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * The ID of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public AuthorizationJson withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * The domain of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    /**
     * The domain of the client who is granted the authorization
     * (Required)
     * 
     */
    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public AuthorizationJson withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public AuthorizationJson withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AuthorizationJson.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
        sb.append(',');
        sb.append("lastUsed");
        sb.append('=');
        sb.append(((this.lastUsed == null)?"<null>":this.lastUsed));
        sb.append(',');
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("clientId");
        sb.append('=');
        sb.append(((this.clientId == null)?"<null>":this.clientId));
        sb.append(',');
        sb.append("domain");
        sb.append('=');
        sb.append(((this.domain == null)?"<null>":this.domain));
        sb.append(',');
        sb.append("scopes");
        sb.append('=');
        sb.append(((this.scopes == null)?"<null>":this.scopes));
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
        result = ((result* 31)+((this.lastUsed == null)? 0 :this.lastUsed.hashCode()));
        result = ((result* 31)+((this.createdAt == null)? 0 :this.createdAt.hashCode()));
        result = ((result* 31)+((this.clientId == null)? 0 :this.clientId.hashCode()));
        result = ((result* 31)+((this.domain == null)? 0 :this.domain.hashCode()));
        result = ((result* 31)+((this.scopes == null)? 0 :this.scopes.hashCode()));
        result = ((result* 31)+((this.userId == null)? 0 :this.userId.hashCode()));
        result = ((result* 31)+((this.updatedAt == null)? 0 :this.updatedAt.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AuthorizationJson) == false) {
            return false;
        }
        AuthorizationJson rhs = ((AuthorizationJson) other);
        return ((((((((this.lastUsed == rhs.lastUsed)||((this.lastUsed!= null)&&this.lastUsed.equals(rhs.lastUsed)))&&((this.createdAt == rhs.createdAt)||((this.createdAt!= null)&&this.createdAt.equals(rhs.createdAt))))&&((this.clientId == rhs.clientId)||((this.clientId!= null)&&this.clientId.equals(rhs.clientId))))&&((this.domain == rhs.domain)||((this.domain!= null)&&this.domain.equals(rhs.domain))))&&((this.scopes == rhs.scopes)||((this.scopes!= null)&&this.scopes.equals(rhs.scopes))))&&((this.userId == rhs.userId)||((this.userId!= null)&&this.userId.equals(rhs.userId))))&&((this.updatedAt == rhs.updatedAt)||((this.updatedAt!= null)&&this.updatedAt.equals(rhs.updatedAt))));
    }

}
