
package from-example.exceptions;


public class ClientAPIException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public ClientAPIException(int statusCode, String reason) {
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
