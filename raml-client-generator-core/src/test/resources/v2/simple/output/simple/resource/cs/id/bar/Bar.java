
package simple.resource.cs.id.bar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.exceptions.FooException;
import simple.responses.FooResponse;

public class Bar {

    private String _baseUrl;
    private Client _client;

    public Bar() {
        _baseUrl = null;
        _client = null;
    }

    public Bar(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/bar");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public FooResponse<Void> post() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.method("POST", ((Entity) null));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        FooResponse<Void> apiResponse = new FooResponse<Void>(null, response.getStringHeaders(), response);
        return apiResponse;
    }

}
