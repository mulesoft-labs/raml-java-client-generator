package org.mule.client.codegen.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mule.client.codegen.RamlJavaClientGenerator;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;

@Mojo(name = "generate-client", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "generate-client")
public class RamlJavaClientGeneratorMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project.build.resources[0].directory}/api.raml")
    private String ramlFile;

    @Parameter()
    private String ramlURL;

    @Parameter(required = true)
    private String basePackage;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources")
    private String outputDir;


    @Parameter(required = true,readonly = true, defaultValue = "${project}")
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            final URL ramlURL;
            if (this.ramlURL != null && !this.ramlURL.isEmpty()) {
                ramlURL = new URL(this.ramlURL);
            } else {
                final File ramlFile = new File(this.ramlFile);
                if (!ramlFile.exists()) {
                    getLog().error("Raml file not found " + ramlFile);
                }
                ramlURL = ramlFile.toURI().toURL();
            }
            final RamlJavaClientGenerator ramlJavaClientGenerator = new RamlJavaClientGenerator(basePackage, new File(outputDir));
            ramlJavaClientGenerator.generate(ramlURL);

            project.addCompileSourceRoot(outputDir);

        } catch (Exception e) {
            throw new MojoExecutionException("Exception while generating client.", e);
        }
    }
}
