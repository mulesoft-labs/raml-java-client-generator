
package simple.resource.cs.data.foo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.resource.cs.data.foo.model.FooGETHeader;
import simple.resource.cs.data.foo.model.FooGETQueryParam;

public class Foo {

    private String _baseUrl;

    public Foo(String baseUrl) {
        _baseUrl = (baseUrl +"/foo");
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    public void get(FooGETQueryParam queryParameters, FooGETHeader headers) {
        WebTarget target = getClient();
        if (queryParameters.getQ()!= null) {
            target = target.queryParam("q", queryParameters.getQ());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        if (headers.getId()!= null) {
            invocationBuilder.header("id", headers.getId());
        }
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
    }

}
