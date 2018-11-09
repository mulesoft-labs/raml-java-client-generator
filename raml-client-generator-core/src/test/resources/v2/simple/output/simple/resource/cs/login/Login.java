
package simple.resource.cs.login;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import simple.exceptions.FooException;
import simple.resource.cs.login.model.LoginPOSTBody;
import simple.responses.FooResponse;

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

    public FooResponse<simple.resource.cs.login.model.LoginPOSTResponseBody> post(LoginPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        FooResponse<simple.resource.cs.login.model.LoginPOSTResponseBody> apiResponse = new FooResponse<simple.resource.cs.login.model.LoginPOSTResponseBody>(response.readEntity(simple.resource.cs.login.model.LoginPOSTResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

    public FooResponse<simple.resource.cs.login.model.LoginGETResponseBody> get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        FooResponse<simple.resource.cs.login.model.LoginGETResponseBody> apiResponse = new FooResponse<simple.resource.cs.login.model.LoginGETResponseBody>(response.readEntity(simple.resource.cs.login.model.LoginGETResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

}
