
package type_decl.exceptions;


public class MyapiException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public MyapiException(int statusCode, String reason) {
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
