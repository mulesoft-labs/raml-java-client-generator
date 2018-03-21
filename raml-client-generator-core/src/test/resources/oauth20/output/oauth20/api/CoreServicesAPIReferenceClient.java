
package oauth20.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import oauth20.resource.api.Api;

public class CoreServicesAPIReferenceClient {

    private String _baseUrl;
    public final Api api;
    
    public CoreServicesAPIReferenceClient() {
    }

    public CoreServicesAPIReferenceClient(String baseUrl) {
        _baseUrl = baseUrl;
        api = new Api(getBaseUri(), getClient());
    }

    public CoreServicesAPIReferenceClient() {
        this("https://anypoint.mulesoft.com/accounts");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static CoreServicesAPIReferenceClient create(String baseUrl) {
        return new CoreServicesAPIReferenceClient(baseUrl);
    }

    public static CoreServicesAPIReferenceClient create() {
        return new CoreServicesAPIReferenceClient();
    }

}
