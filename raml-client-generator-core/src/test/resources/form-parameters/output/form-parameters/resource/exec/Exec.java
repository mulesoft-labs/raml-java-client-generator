
package form-parameters.resource.exec;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import form-parameters.resource.exec.model.ExecPOSTBody;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;


/**
 * Executes a Data Weave Script with the specified inputs.
 * 
 */
public class Exec {

    private String _baseUrl;

    public Exec(String baseUrl) {
        _baseUrl = (baseUrl +"/exec");
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    public void post(ExecPOSTBody body) {
        WebTarget target = getClient();
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
            throw new RuntimeException(((((("("+ statusInfo.getFamily())+") ")+ statusInfo.getStatusCode())+" ")+ statusInfo.getReasonPhrase()));
        }
    }

}
