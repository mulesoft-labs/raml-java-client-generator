
package global-type.resource.cs.login;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import global-type.model.Auth;

public class Login {

    private String _baseUrl;

    public Login(String baseUrl) {
        _baseUrl = (baseUrl +"/login");
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    public String post(Auth body) {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(Entity.json(body));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
        return response.readEntity(String.class);
    }

}
