
package oauth20-global.exceptions;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class CoreServicesAPIReferenceException
    extends RuntimeException
{

    private int statusCode;
    private String reason;
    private MultivaluedMap<String, String> headers;
    private Response response;

    public CoreServicesAPIReferenceException(int statusCode, String reason, MultivaluedMap<String, String> headers, Response response) {
        super(reason);
        this.statusCode = statusCode;
        this.reason = reason;
        this.headers = headers;
        this.response = response;
    }

    public CoreServicesAPIReferenceException(int statusCode, String reason) {
        this(statusCode, reason, null, null);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReason() {
        return this.reason;
    }

    public MultivaluedMap<String, String> getHeaders() {
        return this.headers;
    }

    public Response getResponse() {
        return this.response;
    }

}
