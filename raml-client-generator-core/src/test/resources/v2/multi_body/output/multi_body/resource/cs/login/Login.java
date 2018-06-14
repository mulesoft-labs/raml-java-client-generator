
package multi_body.resource.cs.login;

import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import multi_body.exceptions.MultiBodyException;
import multi_body.resource.cs.login.model.LoginPOSTBody;
import multi_body.responses.MultiBodyResponse;

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

    public MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody> post(LoginPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody> apiResponse = new MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody>(response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

    public MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody> post(InputStream body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.entity(body, javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody> apiResponse = new MultiBodyResponse<multi_body.resource.cs.login.model.LoginPOSTResponseBody>(response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

    public MultiBodyResponse<multi_body.resource.cs.login.model.LoginGETResponseBody> get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        MultiBodyResponse<multi_body.resource.cs.login.model.LoginGETResponseBody> apiResponse = new MultiBodyResponse<multi_body.resource.cs.login.model.LoginGETResponseBody>(response.readEntity(multi_body.resource.cs.login.model.LoginGETResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

}
