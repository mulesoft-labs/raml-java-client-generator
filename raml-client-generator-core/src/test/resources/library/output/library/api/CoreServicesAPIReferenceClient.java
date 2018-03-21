
package library.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import library.resource.provider.Provider;

public class CoreServicesAPIReferenceClient {

    private String _baseUrl;
    /**
     * External auth* provider information for the organization
     * 
     */
    public final Provider provider;
    
    public CoreServicesAPIReferenceClient() {
    }

    public CoreServicesAPIReferenceClient(String baseUrl) {
        _baseUrl = baseUrl;
        provider = new Provider(getBaseUri(), getClient());
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
