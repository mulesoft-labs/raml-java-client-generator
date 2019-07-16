# RAML Client Generator

![alt text](https://travis-ci.org/mulesoft-labs/raml-java-client-generator.svg?branch=master "Logo Title Text 1")
  
This tool generates a java rest client for a raml based api using a resource api approach. 
Supports both 0.8 and 1.0 versions of Raml

## Example

For this api

```raml
#%RAML 0.8
title: Client API
version: 0.1
baseUri: http://mycompany.com/clientservice/api
documentation:
  - title : Users Platform
    content : This api describes how to access to the users platform
mediaType: application/json
/users:
  description: "Users in the platform"
  get:
    description: "Returns the list of all users"
    responses:
      200:
        body:
          application/json:
            example: |
             [{"user" : "Mariano"}]
```

Using the generated api 

```java
final ClientAPIResponse<List<UsersGETResponseBody>> result = ClientAPIClient.create().users.get();
```

Customizing the client 

```java
final ClientAPIClient client = new ClientAPIClient() {
    @Override
    protected Client getClient() {
        final Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        client.property(ClientProperties.READ_TIMEOUT, 1000);
        return client;
    }
};
client.users.userId("luis").get();
```

## Calling the code generator from Java

It can easily be embedded the code generation into your code just.
Add the dependency to `pom.xml`

```xml
  <dependency>
        <groupId>org.mule.raml.codegen</groupId>
        <artifactId>raml-client-generator-core</artifactId>
        <version>0.11</version>
  </dependency>
```

**Note:** Since the RAML Java Client Generator artifacts are not published to Maven Central you will also have to add the following repository either to your `pom.xml` or an active profile in your maven settings.

```xml
  <repositories>
    <repository>
      <id>mulesoft-releases</id>
      <name>Mule Release Repository</name>
      <url>https://repository-master.mulesoft.org/nexus/content/repositories/releases</url>
    </repository>
  </repositories>
```

And then call the RamlJavaClientGenerator

```java
 new RamlJavaClientGenerator(
                "com.acme",
                targetFolder).generate(this.getClass().getClassLoader().getResource("simple/basic.raml"));
```

## Generate client code from RAML using the maven plugin

There is also a maven plugin that allows you to generate the client code during your build process.


```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mule.raml.codegen</groupId>
                <artifactId>raml-client-generator-maven-plugin</artifactId>
                <version>0.11</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate-client</goal>
                        </goals>
                        <configuration>
                            <basePackage>org.mule.example</basePackage>
                            <useJava8Dates>false</useJava8Dates>                            
                            <!--True by default                            -->
                            <includeAdditionalProperties>true</includeAdditionalProperties>
                            <!--False by default                            -->
                            <useOptionalForGetters>false</useOptionalForGetters>
                            <!--False by default                            -->
                            <useBigDecimals>false</useBigDecimals>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

**Note:** Since the RAML Java Client Generator artifacts are not published to Maven Central you will also have to add the following plugin repository either to your `pom.xml` or an active profile in your maven settings.

```xml
  <pluginRepositories>
    <pluginRepository>
      <id>mulesoft-releases</id>
      <name>Mule Release Repository</name>
      <url>https://repository-master.mulesoft.org/nexus/content/repositories/releases</url>
    </pluginRepository>
  </pluginRepositories>
```

### Disclaimer

This is an incubator project (so expect bugs) so no mulesoft oficial support. If any issue is detected please report an issue and we will try to fix it. Also PR are wellcome. 
