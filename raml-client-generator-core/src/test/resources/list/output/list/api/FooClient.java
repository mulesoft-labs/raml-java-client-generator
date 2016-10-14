
package list.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import list.resource.users.Users;


/**
 * This api describes how to access to the users platform
 * 
 */
public class FooClient {

    private String _baseUrl;
    public final Users users;

    public FooClient(String baseUrl) {
        _baseUrl = baseUrl;
        users = new Users(getBaseUri(), getClient());
    }

    private Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static FooClient create(String baseUrl) {
        return new FooClient(baseUrl);
    }

}
