
package simple.exceptions;


public class FooException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public FooException(int statusCode, String reason) {
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
