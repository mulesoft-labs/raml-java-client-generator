
package oauth20-global.resource.api;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import oauth20-global.exceptions.CoreServicesAPIReferenceException;
import oauth20-global.resource.api.model.ApiGETResponse;

public class Api {

    private String _baseUrl;
    private Client _client;

    public Api() {
        _baseUrl = null;
        _client = null;
    }

    public Api(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/api");
        this._client = _client;
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
    public List<ApiGETResponse> get(String authorizationToken) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        invocationBuilder.header("Authorization", ("Bearer "+ authorizationToken));
        Response response = invocationBuilder.method("GET");
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new CoreServicesAPIReferenceException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        return response.readEntity((
new javax.ws.rs.core.GenericType<java.util.List<oauth20-global.resource.api.model.ApiGETResponse>>() {}));
    }

}
