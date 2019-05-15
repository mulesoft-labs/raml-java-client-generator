
package oauth_override.resource.api;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import oauth_override.exceptions.CoreServicesAPIReferenceException;
import oauth_override.resource.api.login.Login;
import oauth_override.resource.api.model.ApiGETResponseBody;
import oauth_override.responses.CoreServicesAPIReferenceResponse;

public class Api {

    private String _baseUrl;
    private Client _client;
    public final Login login;

    public Api() {
        _baseUrl = null;
        _client = null;
        login = null;
    }

    public Api(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/api");
        this._client = _client;
        login = new Login(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * Returns the list of all users
     * 
     */
    public CoreServicesAPIReferenceResponse<List<ApiGETResponseBody>> get(String authorizationToken) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        invocationBuilder.header("Authorization", ("Bearer "+ authorizationToken));
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new CoreServicesAPIReferenceException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        CoreServicesAPIReferenceResponse<List<ApiGETResponseBody>> apiResponse = new CoreServicesAPIReferenceResponse<List<ApiGETResponseBody>>(response.readEntity((
new javax.ws.rs.core.GenericType<java.util.List<oauth_override.resource.api.model.ApiGETResponseBody>>() {})), response.getStringHeaders(), response);
        return apiResponse;
    }

}
