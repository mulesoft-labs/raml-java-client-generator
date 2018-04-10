import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.mule.example.api.ClientAPIClient;


public class ClientExampleWithCustomClient {
    public static void main(final String[] args) {

        final ClientAPIClient client = new ClientAPIClient() {
            @Override
            protected Client getClient() {
                final Client client = ClientBuilder.newClient();
                client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
                client.property(ClientProperties.READ_TIMEOUT, 1000);
                return client;
            }
        };
        client.users.userId("luis").get();

    }
}
