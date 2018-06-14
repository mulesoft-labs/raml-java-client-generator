
package design_center.exceptions;


public class DesignCenterProjectsServiceException
    extends RuntimeException
{

    private int statusCode;
    private String reason;

    public DesignCenterProjectsServiceException(int statusCode, String reason) {
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
