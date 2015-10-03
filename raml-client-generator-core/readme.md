# RAML Client Generator

This tool generates a java rest client for a raml based api using a resource api approach.

```
 new RamlJavaClientGenerator(
                "com.acme",
                targetFolder).generate(this.getClass().getClassLoader().getResource("simple/basic.raml"));
```