
package securedby_with_uses.responses;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class BuyosExperienceLayerResponse<T >{

    private T body;
    private MultivaluedMap<String, String> headers;
    private Response response;

    public BuyosExperienceLayerResponse(T body, MultivaluedMap<String, String> headers, Response response) {
        this.body = body;
        this.headers = headers;
        this.response = response;
    }

    public T getBody() {
        return this.body;
    }

    public MultivaluedMap<String, String> getHeaders() {
        return this.headers;
    }

    public Response getResponse() {
        return this.response;
    }

}
