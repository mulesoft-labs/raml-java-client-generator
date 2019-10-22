
package securedby_with_uses.api;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import securedby_with_uses.resource.files.Files;
import securedby_with_uses.resource.users.Users;

public class BuyosExperienceLayerClient {

    private static java.lang.String username;
    private static java.lang.String password;
    private java.lang.String _baseUrl;
    public final Users users;
    public final Files files;

    public BuyosExperienceLayerClient(java.lang.String baseUrl, java.lang.String username, java.lang.String password) {
        this.username = username;
        this.password = password;
        _baseUrl = baseUrl;
        users = new Users(getBaseUri(), getClient());
        files = new Files(getBaseUri(), getClientWithMultipart());
    }

    public BuyosExperienceLayerClient(java.lang.String username, java.lang.String password) {
        this("http://localhost:24045/api", username, password);
    }

    protected javax.ws.rs.client.Client getClient() {
        final javax.ws.rs.client.Client _client = javax.ws.rs.client.ClientBuilder.newClient();
        _client.register(org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.basic(username, password));
        return _client;
    }

    protected javax.ws.rs.client.Client getClientWithMultipart() {
        final javax.ws.rs.client.Client _client = javax.ws.rs.client.ClientBuilder.newClient();
        _client.register(org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.basic(username, password));
        _client.register(MultiPartFeature.class);
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