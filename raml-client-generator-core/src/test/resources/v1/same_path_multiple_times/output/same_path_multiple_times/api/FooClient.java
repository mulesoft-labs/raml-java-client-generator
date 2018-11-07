
package same_path_multiple_times.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import same_path_multiple_times.resource.foo.Foo;


/**
 * A String
 * 
 */
public class FooClient {

    private String _baseUrl;
    public final Foo foo;

    public FooClient() {
        _baseUrl = null;
        foo = null;
    }

    public FooClient(String baseUrl) {
        _baseUrl = baseUrl;
        foo = new Foo(getBaseUri(), getClient());
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
