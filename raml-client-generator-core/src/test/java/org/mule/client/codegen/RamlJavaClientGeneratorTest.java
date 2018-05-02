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
        return Arrays.asList(new Object[][]{
                {"design_center"},
                {"empty_put"},
                {"form-parameters"},
                {"from-example"},
                {"global-type-body"},
                {"global-type-return"},
                {"library"},
                {"list"},
                {"multi_body"},
                {"oauth20"},
                {"oauth20-global"},
                {"simple"},
                {"type_decl"},
                {"x-www-form-urlencoded"}
        });
    }

    @Test
    public void runTest() throws IOException, JClassAlreadyExistsException, URISyntaxException {
        runGenerator(projectName);
    }

    private void runGenerator(String projectName) throws IOException, JClassAlreadyExistsException, URISyntaxException {

        final File actualTarget = new File(FileUtils.getTempDirectory(), "RamlJavaClientGeneratorTest" + File.separator + projectName + File.separator + "output");
        if (actualTarget.exists()) {
            FileUtils.cleanDirectory(actualTarget);
        }
        System.out.println("targetFolder = " + actualTarget);
        actualTarget.mkdirs();
        final URL resource = this.getClass().getClassLoader().getResource(projectName + "/" + "api.raml");
        new RamlJavaClientGenerator(projectName, actualTarget).generate(resource);
        assert resource != null;
        final File parentFile = new File(resource.toURI()).getParentFile();
        final File expected = new File(parentFile, "output");
        try {
            compareDir(actualTarget, expected);
        } finally {
            if (Boolean.getBoolean("update-result")) {
                System.out.println("-----------UPDATING RESULT -----------------");
                File expectedDirectory = new File(expected, "../../../../src/test/resources/" + projectName + "/output");
                FileUtils.deleteDirectory(expectedDirectory);
                System.out.println("expected = " + expected);
                System.out.println("actualTarget = " + actualTarget);
                FileUtils.copyDirectory(actualTarget, expectedDirectory);
            }
        }


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
                    String expectedLine;
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