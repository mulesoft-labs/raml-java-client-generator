package org.mule.client.codegen;


import com.sun.codemodel.JClassAlreadyExistsException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class RamlJavaClientGeneratorTest {

    @Test
    public void simple() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "simple");
        if(targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        System.out.println("targetFolder = " + targetFolder);
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "simple",
                targetFolder).generate(this.getClass().getClassLoader().getResource("simple/basic.raml"));
    }


    @Test
    public void list() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "list");
        if(targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        System.out.println("targetFolder = " + targetFolder);
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "list",
                targetFolder).generate(this.getClass().getClassLoader().getResource("list/api.raml"));
    }

    @Test
    public void formParameters() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "formParameters");
        if(targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        System.out.println("targetFolder = " + targetFolder);
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "formParameters",
                targetFolder).generate(this.getClass().getClassLoader().getResource("form-parameters/api.raml"));
    }

    @Test
    public void dataweave() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "dataweave");
        if(targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        System.out.println("targetFolder = " + targetFolder);
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "dataweave",
                targetFolder).generate(this.getClass().getClassLoader().getResource("dataweave/api.raml"));
    }

    @Test
    public void apiplatform() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "apiplatform");
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "apiplatform",
                targetFolder).generate(this.getClass().getClassLoader().getResource("apiplatform/api.raml"));
    }


    @Test
    public void dataGateway() throws IOException, JClassAlreadyExistsException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), "dataGateway");
        if(targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        targetFolder.mkdirs();
        new RamlJavaClientGenerator(
                "dataGateway",
                targetFolder).generate(this.getClass().getClassLoader().getResource("datagateway/api.raml"));
    }


}
