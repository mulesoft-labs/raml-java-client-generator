
package simple.resource.cs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import simple.resource.cs.data.Data;
import simple.resource.cs.id.Id;
import simple.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    public final Login login;
    public final Data data;

    public Cs(String baseUrl) {
        _baseUrl = (baseUrl +"/cs");
        login = new Login(getBaseUri());
        data = new Data(getBaseUri());
    }

    private String getBaseUri() {
        return _baseUrl;
    }

    private WebTarget getClient() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(getBaseUri());
        return target;
    }

    public final Id id(String id) {
        return new Id(getBaseUri(), id);
    }

}
