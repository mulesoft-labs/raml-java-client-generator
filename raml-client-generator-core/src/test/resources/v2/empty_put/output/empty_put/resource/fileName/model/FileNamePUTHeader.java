
package empty_put.resource.fileName.model;


public class FileNamePUTHeader {

    /**
     * If this header is given, the system will check in the file log if this commit id equals the last commit where the file has been modified.
     * If someone else made a modification before, and error will be returned.
     * 
     * 
     */
    private String _xBaseCommitId;
    /**
     * Message of the commit that will be generated when upserting the file.
     * 
     */
    private String _xCommitMessage;

    /**
     * 
     * @param xCommitMessage
     *     Message of the commit that will be generated when upserting the file.
     * @param xBaseCommitId
     *     If this header is given, the system will check in the file log if this commit id equals the last commit where the file has been modified.
     *     If someone else made a modification before, and error will be returned.
     *     
     */
    public FileNamePUTHeader(String xBaseCommitId, String xCommitMessage) {
        _xBaseCommitId = xBaseCommitId;
        _xCommitMessage = xCommitMessage;
    }

    public void setXBaseCommitId(String xBaseCommitId) {
        _xBaseCommitId = xBaseCommitId;
    }

    /**
     * 
     * @return
     *     If this header is given, the system will check in the file log if this commit id equals the last commit where the file has been modified.
     *     If someone else made a modification before, and error will be returned.
     *     
     */
    public String getXBaseCommitId() {
        return _xBaseCommitId;
    }

    public void setXCommitMessage(String xCommitMessage) {
        _xCommitMessage = xCommitMessage;
    }

    /**
     * 
     * @return
     *     Message of the commit that will be generated when upserting the file.
     */
    public String getXCommitMessage() {
        return _xCommitMessage;
    }

}
