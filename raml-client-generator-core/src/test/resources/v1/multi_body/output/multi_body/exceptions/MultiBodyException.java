
package multi_body.exceptions;


public class MultiBodyException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public MultiBodyException(int statusCode, String reason) {
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
