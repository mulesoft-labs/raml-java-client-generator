
package java_8_dates.api;

import java_8_dates.resource.cs.Cs;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;


/**
 * A String
 * 
 */
public class FooClient {

    private String _baseUrl;
    public final Cs cs;

    public FooClient() {
        _baseUrl = null;
        cs = null;
    }

    public FooClient(String baseUrl) {
        _baseUrl = baseUrl;
        cs = new Cs(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static FooClient create(String baseUrl) {
        return new FooClient(baseUrl);
    }

}
