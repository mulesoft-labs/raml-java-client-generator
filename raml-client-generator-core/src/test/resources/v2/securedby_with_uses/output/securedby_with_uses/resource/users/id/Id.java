
package securedby_with_uses.resource.users.id;

import java.net.URLEncoder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import securedby_with_uses.exceptions.BuyosExperienceLayerException;
import securedby_with_uses.responses.BuyosExperienceLayerResponse;

public class Id {

    private String _baseUrl;
    private Client _client;

    public Id() {
        _baseUrl = null;
        _client = null;
    }

    public Id(String baseUrl, Client _client, String uriParam) {
        _baseUrl = (baseUrl +("/"+ URLEncoder.encode(uriParam)));
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public BuyosExperienceLayerResponse<securedby_with_uses.resource.users.id.model.IdGETResponseBody> get() {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.method("GET");
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new BuyosExperienceLayerException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        BuyosExperienceLayerResponse<securedby_with_uses.resource.users.id.model.IdGETResponseBody> apiResponse = new BuyosExperienceLayerResponse<securedby_with_uses.resource.users.id.model.IdGETResponseBody>(response.readEntity(securedby_with_uses.resource.users.id.model.IdGETResponseBody.class), response.getStringHeaders(), response);
        return apiResponse;
    }

}
