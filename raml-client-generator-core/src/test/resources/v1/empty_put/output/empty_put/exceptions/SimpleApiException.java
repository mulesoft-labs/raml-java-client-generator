
package empty_put.exceptions;


public class SimpleApiException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public SimpleApiException(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReason() {
        return this.reason;
    }

}
