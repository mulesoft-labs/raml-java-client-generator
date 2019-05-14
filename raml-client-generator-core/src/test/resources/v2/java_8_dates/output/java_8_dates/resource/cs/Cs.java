
package java_8_dates.resource.cs;

import java_8_dates.resource.cs.login.Login;
import javax.ws.rs.client.Client;

public class Cs {

    private String _baseUrl;
    private Client _client;
    public final Login login;

    public Cs() {
        _baseUrl = null;
        _client = null;
        login = null;
    }

    public Cs(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/cs");
        this._client = _client;
        login = new Login(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
