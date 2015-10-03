package org.mule.client.codegen.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mule.client.codegen.RamlJavaClientGenerator;

import java.io.File;
import java.net.URL;

@Mojo(name = "generate-client", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class RamlJavaClientGeneratorMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project.build.sourceDirectory}/main/resources/api.raml")
    private String ramlFile;

    @Parameter()
    private String ramlURL;

    @Parameter(required = true)
    private String basePackage;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources")
    private String outputDir;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final URL ramlURL;
            if (this.ramlURL != null && !this.ramlURL.isEmpty()) {
                ramlURL = new URL(this.ramlURL);
            } else {
                ramlURL = new File(this.ramlFile).toURI().toURL();
            }
            final RamlJavaClientGenerator ramlJavaClientGenerator = new RamlJavaClientGenerator(basePackage, new File(outputDir));
            ramlJavaClientGenerator.generate(ramlURL);
        } catch (Exception e) {
            throw new MojoExecutionException("Exception while running push check valid parameters.", e);
        }
    }
}
