
package securedby_with_uses.api;

import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import securedby_with_uses.resource.users.Users;

public class BuyosExperienceLayerClient {

    private static java.lang.String username;
    private static java.lang.String password;
    private java.lang.String _baseUrl;
    public final Users users;

    public BuyosExperienceLayerClient(java.lang.String baseUrl, java.lang.String username, java.lang.String password) {
        this.username = username;
        this.password = password;
        _baseUrl = baseUrl;
        users = new Users(getBaseUri(), getClient());
    }

    public BuyosExperienceLayerClient(java.lang.String username, java.lang.String password) {
        this("http://localhost:24045/api", username, password);
    }

    protected javax.ws.rs.client.Client getClient() {
        final javax.ws.rs.client.Client _client = ClientBuilder.newClient();
        _client.register(HttpAuthenticationFeature.basic(username, password));
        return _client;
    }

    protected java.lang.String getBaseUri() {
        return _baseUrl;
    }

    public static BuyosExperienceLayerClient create(java.lang.String baseUrl, java.lang.String username, java.lang.String password) {
        return new BuyosExperienceLayerClient(baseUrl, username, password);
    }

    public static BuyosExperienceLayerClient create(java.lang.String username, java.lang.String password) {
        return new BuyosExperienceLayerClient(username, password);
    }

}
