
package multi_body.resource.cs.login;

import java.io.InputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import multi_body.resource.cs.login.model.LoginPOSTBody;

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

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(LoginPOSTBody body) {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
        return response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
    }

    public multi_body.resource.cs.login.model.LoginPOSTResponse post(InputStream body) {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(javax.ws.rs.client.Entity.json(body));
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
        return response.readEntity(multi_body.resource.cs.login.model.LoginPOSTResponse.class);
    }

    public multi_body.resource.cs.login.model.LoginGETResponse get() {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= javax.ws.rs.core.Response.Status.Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
        return response.readEntity(multi_body.resource.cs.login.model.LoginGETResponse.class);
    }

}
