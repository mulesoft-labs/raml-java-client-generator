
package same_path_multiple_times.resource.foo.b;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import same_path_multiple_times.exceptions.FooException;

public class B {

    private String _baseUrl;
    private Client _client;

    public B() {
        _baseUrl = null;
        _client = null;
    }

    public B(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/b");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public same_path_multiple_times.resource.foo.b.model.BGETResponse get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new FooException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
        return response.readEntity(same_path_multiple_times.resource.foo.b.model.BGETResponse.class);
    }

}
