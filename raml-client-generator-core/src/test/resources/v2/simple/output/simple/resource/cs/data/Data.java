
package simple.resource.cs.data;

import javax.ws.rs.client.Client;
import simple.resource.cs.data.foo.Foo;

public class Data {

    private String _baseUrl;
    private Client _client;
    public final Foo foo;

    public Data() {
        _baseUrl = null;
        _client = null;
        foo = null;
    }

    public Data(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/data");
        this._client = _client;
        foo = new Foo(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
