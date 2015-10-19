
package list.resource.users;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import list.resource.users.model.UsersGETResponse;


/**
 * Users in the platform
 * 
 */
public class Users {

    private String _baseUrl;

    public Users(String baseUrl) {
        _baseUrl = (baseUrl +"/users");
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    /**
     * Returns the list of all users
     * 
     */
    public List<UsersGETResponse> get() {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
        return response.readEntity(new GenericType<List<UsersGETResponse>>() {


        }
        );
    }

}
