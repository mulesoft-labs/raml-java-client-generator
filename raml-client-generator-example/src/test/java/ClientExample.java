import org.mule.example.api.ClientAPIClient;
import org.mule.example.resource.users.model.UsersGETResponse;

import java.util.List;


public class ClientExample {
    public static void main(String[] args) {
        final List<UsersGETResponse> result = ClientAPIClient.create().users.get();
    }
}
