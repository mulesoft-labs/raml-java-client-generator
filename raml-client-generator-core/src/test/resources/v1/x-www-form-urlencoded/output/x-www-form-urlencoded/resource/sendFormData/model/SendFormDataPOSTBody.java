
package x-www-form-urlencoded.resource.sendFormData.model;


public class SendFormDataPOSTBody {

    /**
     * parameter number 1.
     * 
     * 
     */
    private String _param1;
    /**
     * parameter number 2.
     * 
     * 
     */
    private Integer _param2;

    /**
     * 
     * @param param1
     *     parameter number 1.
     *     
     */
    public SendFormDataPOSTBody(String param1) {
        _param1 = param1;
    }

    public void setParam1(String param1) {
        _param1 = param1;
    }

    /**
     * 
     * @return
     *     parameter number 1.
     *     
     */
    public String getParam1() {
        return _param1;
    }

    /**
     * 
     * @param param2
     *     parameter number 2.
     *     
     */
    public SendFormDataPOSTBody withParam2(Integer param2) {
        _param2 = param2;
        return this;
    }

    public void setParam2(Integer param2) {
        _param2 = param2;
    }

    /**
     * 
     * @return
     *     parameter number 2.
     *     
     */
    public Integer getParam2() {
        return _param2;
    }

}
