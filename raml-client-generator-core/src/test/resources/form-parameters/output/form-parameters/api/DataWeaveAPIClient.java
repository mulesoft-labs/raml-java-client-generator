
package form-parameters.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import form-parameters.resource.exec.Exec;


/**
 * Interact with the Data Weave Engine.
 * 
 */
public class DataWeaveAPIClient {

    private String _baseUrl;
    public final Exec exec;

    public DataWeaveAPIClient(String baseUrl) {
        _baseUrl = baseUrl;
        exec = new Exec(getBaseUri(), getClient());
    }

    public DataWeaveAPIClient() {
        this("http://dataweave-api.cloudhub.io/api");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
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
