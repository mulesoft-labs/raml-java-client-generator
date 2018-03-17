
package simple.resource.cs.data.foo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.exceptions.FooException;
import simple.resource.cs.data.foo.model.FooGETHeader;
import simple.resource.cs.data.foo.model.FooGETQueryParam;

public class Foo {

    private String _baseUrl;
    private Client _client;

    public Foo(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/foo");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public void get(FooGETQueryParam queryParameters, FooGETHeader headers) {
        WebTarget target = this._client.target(getBaseUri());
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
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
    }

}
