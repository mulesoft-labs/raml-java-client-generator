package org.mule.client.codegen;


import com.sun.codemodel.JClassAlreadyExistsException;
import org.apache.commons.io.FileUtils;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

@RunWith(value = Parameterized.class)
public class RamlJavaClientGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String projectName;

    public RamlJavaClientGeneratorTest(String projectName) {
        this.projectName = projectName;
    }

    @Parameterized.Parameters(name = "{index}: {0}/api.raml")
    public static Iterable<Object[]> folders() {
        return Arrays.asList(new Object[][]{
                {"avoid_additional_properties"},
                {"design_center"},
                {"empty_put"},
                {"form-parameters"},
                {"from-example"},
                {"global-type-body"},
                {"global-type-return"},
                {"include_schema"},
                {"java_8_dates"},
                {"library"},
                {"list"},
                {"multi_body"},
                {"oauth20"},
                {"oauth20-global"},
                {"oauth_override"},
                {"recursive_type"},
                {"simple"},
                {"sub_resource_on_same_line"},
                {"same_path_multiple_times"},
                {"type_decl"},
                {"use_optional"},
                {"x-www-form-urlencoded"}
        });
    }

    @Test
    public void runTestV1() throws IOException, JClassAlreadyExistsException, URISyntaxException {
        runGenerator(projectName, OutputVersion.v1);
    }

    @Test
    public void runTestV2() throws IOException, JClassAlreadyExistsException, URISyntaxException {
        runGenerator(projectName, OutputVersion.v2);
    }

    private void runGenerator(String projectName, OutputVersion outputVersion) throws IOException, JClassAlreadyExistsException, URISyntaxException {
        final URL resource = this.getClass().getClassLoader().getResource(outputVersion.toString() + "/" + projectName + "/" + "api.raml");
        if (resource == null) {
            System.out.println("Ingoring " + projectName + " for " + outputVersion + " as it doesn't exists");
            return;
        }
        final File actualTarget = new File(FileUtils.getTempDirectory(), "RamlJavaClientGeneratorTest" + File.separator + outputVersion.toString() + File.separator + projectName + File.separator + "output");
        if (actualTarget.exists()) {
            FileUtils.cleanDirectory(actualTarget);
        }
        logger.info("targetFolder = " + actualTarget);
        actualTarget.mkdirs();

        CodeGenConfig codeGenConfig = new CodeGenConfig();
        final URL config = this.getClass().getClassLoader().getResource(outputVersion.toString() + "/" + projectName + "/" + "api.properties");
        if (config != null) {
            Properties properties = new Properties();
            properties.load(config.openStream());
            codeGenConfig.setUseJava8Dates(Boolean.parseBoolean(properties.getProperty("java8Dates", "false")));
            codeGenConfig.setUseJava8Optional(Boolean.parseBoolean(properties.getProperty("optionals", "false")));
            codeGenConfig.setIncludeAdditionalProperties(Boolean.parseBoolean(properties.getProperty("additionalProperties", "false")));
            codeGenConfig.setUseBigDecimals(Boolean.parseBoolean(properties.getProperty("useBigDecimals", "false")));
        }
        new RamlJavaClientGenerator(projectName, actualTarget, outputVersion, codeGenConfig).generate(resource);
        assert resource != null;
        final File parentFile = new File(resource.toURI()).getParentFile();
        final File expected = new File(parentFile, "output");
        try {
            compareDir(actualTarget, expected);
        } finally {
            try {
                if (Boolean.getBoolean("update-result")) {
                    logger.info("-----------UPDATING RESULT -----------------");
                    File expectedDirectory = new File(expected, "../../../../../src/test/resources/" + outputVersion.toString() + "/" + projectName + "/output").getAbsoluteFile();
                    System.out.println("expectedDirectory = " + resource);
                    FileUtils.deleteDirectory(expectedDirectory);
                    expectedDirectory.mkdirs();
                    logger.info("expected = " + expectedDirectory);
                    logger.info("actualTarget = " + actualTarget);
                    FileUtils.copyDirectory(actualTarget, expectedDirectory);
                }
            } catch (Exception e) {
                e.printStackTrace();
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