
package multi_body.api;

import multi_body.resource.cs.Cs;


/**
 * A String
 * 
 */
public class MultiBodyClient {

    private String _baseUrl;
    public final Cs cs;

    public MultiBodyClient(String baseUrl) {
        _baseUrl = baseUrl;
        cs = new Cs(getBaseUri());
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static MultiBodyClient create(String baseUrl) {
        return new MultiBodyClient(baseUrl);
    }

}
