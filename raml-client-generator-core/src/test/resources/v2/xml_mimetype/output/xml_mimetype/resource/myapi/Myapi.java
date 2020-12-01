
package xml_mimetype.resource.myapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import xml_mimetype.exceptions.TException;
import xml_mimetype.resource.myapi.model.MyapiPUTQueryParam;
import xml_mimetype.responses.TResponse;


/**
 * Some description
 * 
 */
public class Myapi {

    private String _baseUrl;
    private Client _client;

    public Myapi() {
        _baseUrl = null;
        _client = null;
    }

    public Myapi(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/myapi");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    /**
     * some description.
     * 
     */
    public TResponse<Object> put(Object body, MyapiPUTQueryParam queryParameters) {
        WebTarget target = this._client.target(getBaseUri());
        if (queryParameters.getSomeName()!= null) {
            target = target.queryParam("someName", queryParameters.getSomeName());
        }
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.method("PUT", Entity.entity(body, "application/xml"));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new TException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        TResponse<Object> apiResponse = new TResponse<Object>(response.readEntity(Object.class), response.getStringHeaders(), response);
        return apiResponse;
    }

}
