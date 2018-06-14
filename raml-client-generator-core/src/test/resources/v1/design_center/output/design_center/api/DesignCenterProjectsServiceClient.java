
package design_center.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import design_center.resource.projects.Projects;

public class DesignCenterProjectsServiceClient {

    private String _baseUrl;
    public final Projects projects;

    public DesignCenterProjectsServiceClient(String baseUrl) {
        _baseUrl = baseUrl;
        projects = new Projects(getBaseUri(), getClient());
    }

    public DesignCenterProjectsServiceClient() {
        this("http://{host}:{port}/repository/api/v1");
    }

    protected Client getClient() {
        return ClientBuilder.newClient();
    }

    protected String getBaseUri() {
        return _baseUrl;
    }

    public static DesignCenterProjectsServiceClient create(String baseUrl) {
        return new DesignCenterProjectsServiceClient(baseUrl);
    }

    public static DesignCenterProjectsServiceClient create() {
        return new DesignCenterProjectsServiceClient();
    }

}
