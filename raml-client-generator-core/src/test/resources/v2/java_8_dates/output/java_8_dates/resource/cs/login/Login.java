
package java_8_dates.resource.cs.login;

import java.util.List;
import java_8_dates.exceptions.FooException;
import java_8_dates.resource.cs.login.model.AuthorizationJson;
import java_8_dates.responses.FooResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

public class Login {

    private String _baseUrl;
    private Client _client;

    public Login() {
        _baseUrl = null;
        _client = null;
    }

    public Login(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/login");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public FooResponse<List<AuthorizationJson>> post() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        FooResponse<List<AuthorizationJson>> apiResponse = new FooResponse<List<AuthorizationJson>>(response.readEntity((
new javax.ws.rs.core.GenericType<java.util.List<java_8_dates.resource.cs.login.model.AuthorizationJson>>() {})), response.getStringHeaders(), response);
        return apiResponse;
    }

}
