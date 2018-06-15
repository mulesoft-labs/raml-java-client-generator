
package sub_resource_on_same_line.exceptions;


public class DesignCenterProjectsServicewithsubresourceonsamelineException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public DesignCenterProjectsServicewithsubresourceonsamelineException(int statusCode, String reason) {
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
