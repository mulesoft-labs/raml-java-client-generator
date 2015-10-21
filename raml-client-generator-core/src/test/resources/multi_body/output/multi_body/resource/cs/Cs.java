
package multi_body.resource.cs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import multi_body.resource.cs.login.Login;

public class Cs {

    private String _baseUrl;
    public final Login login;

    public Cs(String baseUrl) {
        _baseUrl = (baseUrl +"/cs");
        login = new Login(getBaseUri());
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
