
package design_center.resource.projects;

import javax.ws.rs.client.Client;
import design_center.resource.projects.rename.Rename;

public class Projects {

    private String _baseUrl;
    private Client _client;
    public final Rename rename;

    public Projects(String baseUrl, Client _client) {
        _baseUrl = (baseUrl +"/projects");
        this._client = _client;
        rename = new Rename(getBaseUri(), getClient());
    }

    protected Client getClient() {
        return this._client;
    }

    private String getBaseUri() {
        return _baseUrl;
    }

}
