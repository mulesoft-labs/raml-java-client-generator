
package form-parameters.api;

import javax.ws.rs.client.Client;
import form-parameters.resource.exec.Exec;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;


/**
 * Interact with the Data Weave Engine.
 *
 */
public class DataWeaveAPIClient {

    private String _baseUrl;
    public final Exec exec;

    public DataWeaveAPIClient(String baseUrl) {
        _baseUrl = baseUrl;
        exec = new Exec(getBaseUri(), getClientWithMultipart());
    }

    public DataWeaveAPIClient() {
        this("http://dataweave-api.cloudhub.io/api");
    }

    protected Client getClient() {
        return javax.ws.rs.client.ClientBuilder.newClient();
    }

    protected Client getClientWithMultipart() {
        ClientConfig cc = new ClientConfig();
        cc.register(MultiPartFeature.class);
        javax.ws.rs.client.ClientBuilder clientBuilder = javax.ws.rs.client.ClientBuilder.newBuilder();
        return clientBuilder.wichConfig(cc).build();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static DataWeaveAPIClient create(String baseUrl) {
        return new DataWeaveAPIClient(baseUrl);
    }

    public static DataWeaveAPIClient create() {
        return new DataWeaveAPIClient();
    }

}