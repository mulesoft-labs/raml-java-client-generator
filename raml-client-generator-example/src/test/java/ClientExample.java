import org.mule.example.api.ClientAPIClient;
import org.mule.example.exceptions.ClientAPIException;
import org.mule.example.resource.users.userId.model.UserIdGETResponseBody;
import org.mule.example.responses.ClientAPIResponse;


public class ClientExample {
    public static void main(String[] args) {
        try {
            final ClientAPIResponse<UserIdGETResponseBody> result = ClientAPIClient.create().users.userId("luis").get();
            System.out.println (result.getBody().getUser());
        } catch ( ClientAPIException e ) {
            System.err.println("ERROR " + e.getStatusCode() + " " + e.getReason());
        }
    }
}
