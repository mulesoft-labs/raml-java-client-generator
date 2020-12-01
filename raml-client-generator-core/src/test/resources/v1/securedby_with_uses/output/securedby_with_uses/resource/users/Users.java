
package securedby_with_uses.resource.users;

import javax.ws.rs.client.Client;
import securedby_with_uses.resource.users.id.Id;

public class Users {

    private String _baseUrl;
    private Client _client;

    public Users() {
        _baseUrl = null;
        _client = null;
    }

    public Users(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/users");
        this._client = _client;
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public Id id(String id) {
        return new Id(getBaseUri(), getClient(), id);
    }

}
