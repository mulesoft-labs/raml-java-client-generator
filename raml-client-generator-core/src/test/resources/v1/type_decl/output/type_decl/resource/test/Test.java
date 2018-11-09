
package type_decl.resource.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import type_decl.exceptions.MyapiException;

public class Test {

    private String _baseUrl;
    private Client _client;

    public Test() {
        _baseUrl = null;
        _client = null;
    }

    public Test(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/test");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public type_decl.model.MyType get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new MyapiException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        return response.readEntity(type_decl.model.MyType.class);
    }

}
