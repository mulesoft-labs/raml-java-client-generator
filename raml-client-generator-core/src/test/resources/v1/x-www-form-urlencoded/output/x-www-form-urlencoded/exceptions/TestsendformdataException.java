
package x-www-form-urlencoded.exceptions;


public class TestsendformdataException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public TestsendformdataException(int statusCode, String reason) {
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
