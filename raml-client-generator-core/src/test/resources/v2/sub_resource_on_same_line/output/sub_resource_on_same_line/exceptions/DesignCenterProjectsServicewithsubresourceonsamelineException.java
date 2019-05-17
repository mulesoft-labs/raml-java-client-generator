
package sub_resource_on_same_line.exceptions;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class DesignCenterProjectsServicewithsubresourceonsamelineException
    extends RuntimeException
{

    private int statusCode;
    private String reason;
    private MultivaluedMap<String, String> headers;
    private Response response;

    public DesignCenterProjectsServicewithsubresourceonsamelineException(int statusCode, String reason, MultivaluedMap<String, String> headers, Response response) {
        super(reason);
        this.statusCode = statusCode;
        this.reason = reason;
        this.headers = headers;
        this.response = response;
    }

    public DesignCenterProjectsServicewithsubresourceonsamelineException(int statusCode, String reason) {
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
