
package securedby_with_uses.resource.files;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import securedby_with_uses.exceptions.BuyosExperienceLayerException;
import securedby_with_uses.resource.files.model.FilesPOSTBody;
import securedby_with_uses.responses.BuyosExperienceLayerResponse;

public class Files {

    private String _baseUrl;
    private Client _client;

    public Files() {
        _baseUrl = null;
        _client = null;
    }

    public Files(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/files");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public BuyosExperienceLayerResponse<Void> post(FilesPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        FormDataMultiPart multiPart = new FormDataMultiPart();
        Response response = invocationBuilder.method("POST", Entity.entity(multiPart, multiPart.getMediaType()));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new BuyosExperienceLayerException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase(), response.getStringHeaders(), response);
        }
        BuyosExperienceLayerResponse<Void> apiResponse = new BuyosExperienceLayerResponse<Void>(null, response.getStringHeaders(), response);
        return apiResponse;
    }

}
