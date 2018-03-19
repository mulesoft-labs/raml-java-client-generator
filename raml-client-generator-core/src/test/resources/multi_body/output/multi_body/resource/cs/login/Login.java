
package multi_body.resource.cs.login;

import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import multi_body.exceptions.MultiBodyException;
import multi_body.resource.cs.login.model.LoginPOSTBody;

public class Login {

    private String _baseUrl;
    private Client _client;

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

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(LoginPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
    }

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(InputStream body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.entity(body, javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
    }

    public Object get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MultiBodyException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.getEntity();
    }

}
