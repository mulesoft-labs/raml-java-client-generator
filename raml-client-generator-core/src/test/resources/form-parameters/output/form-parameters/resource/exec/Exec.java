
package form-parameters.resource.exec;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import form-parameters.exceptions.DataWeaveAPIException;
import form-parameters.resource.exec.model.ExecPOSTBody;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

public class Exec {

    private String _baseUrl;
    private Client _client;
    
    public Exec() {
    }

    public Exec(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/exec");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public void post(ExecPOSTBody body) {
        WebTarget target = this._client.target(getBaseUri());
        final javax.ws.rs.client.Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON_TYPE);
        FormDataMultiPart multiPart = new FormDataMultiPart();
        if (body.getFile()!= null) {
            multiPart.bodyPart(new FileDataBodyPart("file", body.getFile()));
        }
        if (body.getFrom()!= null) {
            multiPart.field("From", body.getFrom().toString());
        }
        Response response = invocationBuilder.post(Entity.entity(multiPart, multiPart.getMediaType()));
        if (response.getStatusInfo().getFamily()!= Family.SUCCESSFUL) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new DataWeaveAPIException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
        }
    }

}
