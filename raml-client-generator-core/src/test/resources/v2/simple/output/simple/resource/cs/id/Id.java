
package simple.resource.cs.id;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import simple.exceptions.FooException;
import simple.resource.cs.id.bar.Bar;
import simple.responses.FooResponse;

public class Id {

    private String _baseUrl;
    private Client _client;
    public final Bar bar;

    public Id() {
        _baseUrl = null;
        _client = null;
        bar = null;
    }

    public Id(String baseUrl, Client _client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ uriParam));
        this._client = _client;
        bar = new Bar(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public FooResponse<Void> get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        FooResponse<Void> apiResponse = new FooResponse<Void>(null, response.getStringHeaders(), response);
        return apiResponse;
    }

}
