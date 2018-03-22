
package empty_put.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import empty_put.resource.fileName.FileName;

public class SimpleApiClient {

    private String _baseUrl;

    public SimpleApiClient(String baseUrl) {
        _baseUrl = baseUrl;
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static SimpleApiClient create(String baseUrl) {
        return new SimpleApiClient(baseUrl);
    }

    public FileName fileName(String fileName) {
        return new FileName(getBaseUri(), getClient(), fileName);
    }

}
