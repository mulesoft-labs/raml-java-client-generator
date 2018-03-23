
package simple.resource.cs;

import javax.ws.rs.client.Client;
import simple.resource.cs.data.Data;
import simple.resource.cs.id.Id;
import simple.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    private Client _client;
    public final Data data;
    public final Login login;
    
    public Cs() {
    		_baseUrl = null;
		_client = null;
		data = null;
		login = null;
    }

    public Cs(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/cs");
        this._client = _client;
        data = new Data(getBaseUri(), getClient());
        login = new Login(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public final Id id(String id) {
        return new Id(getBaseUri(), getClient(), id);
    }

}
