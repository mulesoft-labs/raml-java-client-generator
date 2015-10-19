
package form-parameters.api;

import form-parameters.resource.exec.Exec;


/**
 * Interact with the Data Weave Engine.
 * 
 */
public class DataWeaveAPIClient {

    private String _baseUrl;
    /**
     * Executes a Data Weave Script with the specified inputs.
     * 
     */
    public final Exec exec;

    public DataWeaveAPIClient(String baseUrl) {
        _baseUrl = baseUrl;
        exec = new Exec(getBaseUri());
    }

    public DataWeaveAPIClient() {
        this("http://dataweave-api.cloudhub.io/api");
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static DataWeaveAPIClient create(String baseUrl) {
        return new DataWeaveAPIClient(baseUrl);
    }

    public static DataWeaveAPIClient create() {
        return new DataWeaveAPIClient();
    }

}
