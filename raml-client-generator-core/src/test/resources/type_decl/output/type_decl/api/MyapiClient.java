
package type_decl.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import type_decl.resource.test.Test;

public class MyapiClient {

    private String _baseUrl;
    public final Test test;

    public MyapiClient(String baseUrl) {
        _baseUrl = baseUrl;
        test = new Test(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static MyapiClient create(String baseUrl) {
        return new MyapiClient(baseUrl);
    }

}
