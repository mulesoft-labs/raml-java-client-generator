
package simple.resource.cs.login;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import simple.exceptions.FooException;
import simple.resource.cs.login.model.LoginPOSTBody;

public class Login {

    private String _baseUrl;
    private Client client;

    public Login(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/login");
        this.client = client;
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public simple.resource.cs.login.model.LoginPOSTResponse post(LoginPOSTBody body) {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(simple.resource.cs.login.model.LoginPOSTResponse.class);
    }

    public simple.resource.cs.login.model.LoginGETResponse get() {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(simple.resource.cs.login.model.LoginGETResponse.class);
    }

}
