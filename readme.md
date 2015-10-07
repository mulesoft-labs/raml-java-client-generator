# RAML Client Generator 

This tool generates a java rest client for a raml based api using a resource api approach.



## Using it from java

It can easily be embedded into your code just add de dependency

```
  <dependency>
            <groupId>org.mule.raml.codegen</groupId>
            <artifactId>raml-client-generator-core</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
```

And then call the RamlJavaClientGenerator

```
 new RamlJavaClientGenerator(
                "com.acme",
                targetFolder).generate(this.getClass().getClassLoader().getResource("simple/basic.raml"));
```

## Using it from maven

For maven just add this plugin 


```
    <build>
        <plugins>
            <plugin>
                <groupId>org.mule.raml.codegen</groupId>
                <artifactId>raml-client-generator-maven-plugin</artifactId>
                <version>0.1-SNAPSHOT</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate-client</goal>
                        </goals>
                        <configuration>
                            <basePackage>org.mule.example</basePackage>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```    