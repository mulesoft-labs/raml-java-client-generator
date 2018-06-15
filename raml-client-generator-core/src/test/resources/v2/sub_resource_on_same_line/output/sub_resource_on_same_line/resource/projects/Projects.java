
package sub_resource_on_same_line.resource.projects;

import javax.ws.rs.client.Client;
import sub_resource_on_same_line.resource.projects.rename.Rename;

public class Projects {

    private String _baseUrl;
    private Client _client;
    public final Rename rename;

    public Projects() {
        _baseUrl = null;
        _client = null;
        rename = null;
    }

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
