
package x-www-form-urlencoded.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import x-www-form-urlencoded.resource.sendFormData.SendFormData;


/**
 * Some content
 * 
 */
public class TestsendformdataClient {

    private String _baseUrl;
    public final SendFormData sendFormData;

    public TestsendformdataClient(String baseUrl) {
        _baseUrl = baseUrl;
        sendFormData = new SendFormData(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static TestsendformdataClient create(String baseUrl) {
        return new TestsendformdataClient(baseUrl);
    }

}
