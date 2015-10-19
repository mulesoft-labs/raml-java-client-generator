import org.mule.example.api.ClientAPIClient;
import org.mule.example.resource.users.userId.model.UserIdGETResponse;


public class ClientExample {
    public static void main(String[] args) {
       ClientAPIClient.create().users.userId("luis").get();

    }
}
