
package xml_mimetype.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import xml_mimetype.resource.myapi.Myapi;

public class TClient {

    private String _baseUrl;
    /**
     * Some description
     * 
     */
    public final Myapi myapi;

    public TClient() {
        _baseUrl = null;
        myapi = null;
    }

    public TClient(String baseUrl) {
        _baseUrl = baseUrl;
        myapi = new Myapi(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static TClient create(String baseUrl) {
        return new TClient(baseUrl);
    }

}
