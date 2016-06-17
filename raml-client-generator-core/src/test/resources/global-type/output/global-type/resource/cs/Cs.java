
package global-type.resource.cs;

import javax.ws.rs.client.Client;
import global-type.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    private Client client;
    public final Login login;

    public Cs(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/cs");
        this.client = client;
        login = new Login(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
