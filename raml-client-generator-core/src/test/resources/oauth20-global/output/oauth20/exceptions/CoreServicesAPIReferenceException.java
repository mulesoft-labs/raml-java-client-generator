
package oauth20.exceptions;


public class CoreServicesAPIReferenceException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public CoreServicesAPIReferenceException(int statusCode, String reason) {
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
