
package x-www-form-urlencoded.api;

import javax.ws.rs.client.Client;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import x-www-form-urlencoded.resource.sendFormData.SendFormData;


/**
 * Some content
 * 
 */
public class TestsendformdataClient {

    private String _baseUrl;
    public final SendFormData sendFormData;

    public TestsendformdataClient() {
        _baseUrl = null;
        sendFormData = null;
    }

    public TestsendformdataClient(String baseUrl) {
        _baseUrl = baseUrl;
        sendFormData = new SendFormData(getBaseUri(), getClientWithMultipart());
    }

    protected Client getClient() {
        return javax.ws.rs.client.ClientBuilder.newClient();
    }

    protected Client getClientWithMultipart() {
        ClientConfig cc = new ClientConfig();
        cc.register(MultiPartFeature.class);
        javax.ws.rs.client.ClientBuilder clientBuilder = javax.ws.rs.client.ClientBuilder.newBuilder();
        return clientBuilder.withConfig(cc).build();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static TestsendformdataClient create(String baseUrl) {
        return new TestsendformdataClient(baseUrl);
    }

}
