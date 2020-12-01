
package x-www-form-urlencoded.resource.sendFormData;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import x-www-form-urlencoded.exceptions.TestsendformdataException;
import x-www-form-urlencoded.resource.sendFormData.model.SendFormDataPOSTBody;
import x-www-form-urlencoded.responses.TestsendformdataResponse;

public class SendFormData {

    private String _baseUrl;
    private Client _client;

    public SendFormData() {
        _baseUrl = null;
        _client = null;
    }

    public SendFormData(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/sendFormData");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public TestsendformdataResponse<Void> post(SendFormDataPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE);
        MultivaluedMap multiValuedMap = new MultivaluedHashMap();
        if (body.getParam1()!= null) {
            multiValuedMap.add("param1", body.getParam1().toString());
        }
        if (body.getParam2()!= null) {
            multiValuedMap.add("param2", body.getParam2().toString());
        }
        Response response = invocationBuilder.method("POST", Entity.entity(multiValuedMap, javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new TestsendformdataException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        TestsendformdataResponse<Void> apiResponse = new TestsendformdataResponse<Void>(null, response.getStringHeaders(), response);
        return apiResponse;
    }

}
