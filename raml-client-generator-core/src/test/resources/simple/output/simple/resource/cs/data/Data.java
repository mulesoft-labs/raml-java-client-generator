
package simple.resource.cs.data;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import simple.resource.cs.data.foo.Foo;

public class Data {

    private String _baseUrl;
    public final Foo foo;

    public Data(String baseUrl) {
        _baseUrl = (baseUrl +"/data");
        foo = new Foo(getBaseUri());
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

}
