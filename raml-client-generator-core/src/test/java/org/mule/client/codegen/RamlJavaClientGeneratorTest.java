package org.mule.client.codegen;


import com.sun.codemodel.JClassAlreadyExistsException;
import org.apache.commons.io.FileUtils;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

@RunWith(value = Parameterized.class)
public class RamlJavaClientGeneratorTest {

    private String projectName;

    public RamlJavaClientGeneratorTest(String projectName) {
        this.projectName = projectName;
    }

    @Parameterized.Parameters(name = "{index}: {0}/api.raml")
    public static Iterable<Object[]> folders() {
        return Arrays.asList(new Object[][]{{"simple"}, {"form-parameters"}, {"list"}, {"global-type-body"}, {"multi_body"}, {"x-www-form-urlencoded"}});
    }

    @Test
    public void runTest() throws IOException, JClassAlreadyExistsException, URISyntaxException {
        runGenerator(projectName);
    }

    private void runGenerator(String projectName) throws IOException, JClassAlreadyExistsException, URISyntaxException {
        final File targetFolder = new File(FileUtils.getTempDirectory(), projectName);
        if (targetFolder.exists()) {
            FileUtils.cleanDirectory(targetFolder);
        }
        System.out.println("targetFolder = " + targetFolder);
        targetFolder.mkdirs();
        final URL resource = this.getClass().getClassLoader().getResource(projectName + "/" + "api.raml");
        new RamlJavaClientGenerator(projectName, targetFolder).generate(resource);
        assert resource != null;
        final File parentFile = new File(resource.toURI()).getParentFile();
        final File outputFolder = new File(parentFile, "output");
        compareDir(targetFolder, outputFolder);
    }

    public void compareDir(File actual, File expected) throws IOException {
        final File[] files = expected.listFiles();
        if (files == null) {
            Assert.fail("No expected files specified");
        } else {
            for (File expectedInnerFile : files) {
                final File actualInnerFile = new File(actual, expectedInnerFile.getName());
                Assert.assertTrue("File was not created " + actualInnerFile.getAbsolutePath(), actualInnerFile.exists());
                if (expectedInnerFile.isDirectory() && actualInnerFile.isDirectory()) {
                    compareDir(expectedInnerFile, actualInnerFile);
                } else if (expectedInnerFile.isFile() && actualInnerFile.isFile()) {
                    final String expectedContent = FileUtils.readFileToString(expectedInnerFile);
                    final String actualContent = FileUtils.readFileToString(actualInnerFile);
                    BufferedReader expectedBuffer = new BufferedReader(new StringReader(expectedContent));
                    BufferedReader actualBuffer = new BufferedReader(new StringReader(actualContent));
                    int i = 0;
                    String expectedLine = null;
                    while ((expectedLine = expectedBuffer.readLine()) != null) {
                        Assert.assertThat("Line " + i + " did not match " + actualInnerFile.getPath(), expectedLine, new IsEqualIgnoringWhiteSpace(actualBuffer.readLine()));
                        i++;
                    }
                    //                    Assert.assertThat("Files " + expectedInnerFile.getPath() + " did not match " + actualInnerFile.getPath(), expectedContent, new IsEqualIgnoringWhiteSpace(actualContent));
                } else {
                    Assert.fail("Expected isDirectory " + expectedInnerFile.isDirectory() + " but actual isDirectory " + actualInnerFile.isDirectory());
                }
            }
        }
    }
}