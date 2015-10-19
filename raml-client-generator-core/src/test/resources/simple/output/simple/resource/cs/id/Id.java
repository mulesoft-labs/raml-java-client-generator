
package simple.resource.cs.id;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.resource.cs.id.bar.Bar;

public class Id {

    private String _baseUrl;
    public final Bar bar;

    public Id(String baseUrl, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        bar = new Bar(getBaseUri());
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    public void get() {
        WebTarget target = getClient();
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
    }

}
