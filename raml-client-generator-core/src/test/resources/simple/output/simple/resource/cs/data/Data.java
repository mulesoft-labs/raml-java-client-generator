
package simple.resource.cs.data;

import javax.ws.rs.client.Client;
import simple.resource.cs.data.foo.Foo;

public class Data {

    private String _baseUrl;
    private Client client;
    public final Foo foo;

    public Data(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/data");
        this.client = client;
        foo = new Foo(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
