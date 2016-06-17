
package simple.resource.cs;

import javax.ws.rs.client.Client;
import simple.resource.cs.data.Data;
import simple.resource.cs.id.Id;
import simple.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    private Client client;
    public final Login login;
    public final Data data;

    public Cs(String baseUrl, Client client) {
        _baseUrl = (baseUrl +"/cs");
        this.client = client;
        login = new Login(getBaseUri(), getClient());
        data = new Data(getBaseUri(), getClient());
    }

    private Client getClient() {
        return this.client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    public final Id id(String id) {
        return new Id(getBaseUri(), getClient(), id);
    }

}
