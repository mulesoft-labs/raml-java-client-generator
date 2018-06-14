
package sub_resource_on_same_line.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import sub_resource_on_same_line.resource.projects.Projects;

public class DesignCenterProjectsServicewithsubresourceonsamelineClient {

    private String _baseUrl;
    public final Projects projects;

    public DesignCenterProjectsServicewithsubresourceonsamelineClient(String baseUrl) {
        _baseUrl = baseUrl;
        projects = new Projects(getBaseUri(), getClient());
    }

    public DesignCenterProjectsServicewithsubresourceonsamelineClient() {
        this("http://{host}:{port}/repository/api/v1");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static DesignCenterProjectsServicewithsubresourceonsamelineClient create(String baseUrl) {
        return new DesignCenterProjectsServicewithsubresourceonsamelineClient(baseUrl);
    }

    public static DesignCenterProjectsServicewithsubresourceonsamelineClient create() {
        return new DesignCenterProjectsServicewithsubresourceonsamelineClient();
    }

}
