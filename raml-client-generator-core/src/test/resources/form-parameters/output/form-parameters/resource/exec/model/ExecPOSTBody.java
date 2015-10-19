
package form-parameters.resource.exec.model;

import java.io.File;

public class ExecPOSTBody {

    /**
     * The file to be uploaded
     * 
     */
    private File _file;
    /**
     * The phone number or client identifier to use as the caller id. If
     * using a phone number, it must be a Twilio number or a Verified
     * outgoing caller id for your account.
     * 
     * 
     */
    private String _from;

    /**
     * 
     * @param file
     *     The file to be uploaded
     * @param from
     *     The phone number or client identifier to use as the caller id. If
     *     using a phone number, it must be a Twilio number or a Verified
     *     outgoing caller id for your account.
     *     
     */
    public ExecPOSTBody(File file, String from) {
        _file = file;
        _from = from;
    }

    public void setFile(File file) {
        _file = file;
    }

    /**
     * 
     * @return
     *     The file to be uploaded
     */
    public File getFile() {
        return _file;
    }

    public void setFrom(String from) {
        _from = from;
    }

    /**
     * 
     * @return
     *     The phone number or client identifier to use as the caller id. If
     *     using a phone number, it must be a Twilio number or a Verified
     *     outgoing caller id for your account.
     *     
     */
    public String getFrom() {
        return _from;
    }

}
