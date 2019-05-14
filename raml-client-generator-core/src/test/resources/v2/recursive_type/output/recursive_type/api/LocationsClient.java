
package recursive_type.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import recursive_type.resource.bar.Bar;

public class LocationsClient {

    private String _baseUrl;
    public final Bar bar;

    public LocationsClient(String baseUrl) {
        _baseUrl = baseUrl;
        bar = new Bar(getBaseUri(), getClient());
    }

    public LocationsClient() {
        this("/bar/locations");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static LocationsClient create(String baseUrl) {
        return new LocationsClient(baseUrl);
    }

    public static LocationsClient create() {
        return new LocationsClient();
    }

}
