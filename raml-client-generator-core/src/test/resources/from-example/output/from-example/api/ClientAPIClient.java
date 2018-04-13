
package from-example.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import from-example.resource.users.Users;


/**
 * This api describes how to access to the users platform
 * 
 */
public class ClientAPIClient {

    private String _baseUrl;
    public final Users users;
    
    public ClientAPIClient() {
    		_baseUrl = null;
		users = null;
    }

    public ClientAPIClient(String baseUrl) {
        _baseUrl = baseUrl;
        users = new Users(getBaseUri(), getClient());
    }

    public ClientAPIClient() {
        this("http://mycompany.com/clientservice/api");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static ClientAPIClient create(String baseUrl) {
        return new ClientAPIClient(baseUrl);
    }

    public static ClientAPIClient create() {
        return new ClientAPIClient();
    }

}
