
package form-parameters.exceptions;


public class DataWeaveAPIException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public DataWeaveAPIException(int statusCode, String reason) {
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
