
package simple.resource.cs.id.bar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.exceptions.FooException;

public class Bar {

    private String _baseUrl;
    private Client client;

    public Bar(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/bar");
        this.client = client;
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public String post() {
        WebTarget target = this.client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.post(null);
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(String.class);
    }

}
