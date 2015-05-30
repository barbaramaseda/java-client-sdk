cortical.io
===========
Welcome to the cortical.io Retina java client source code page.

Release Version: 2.2.0

This page contains
<UL>
<LI><B>Introduction</B></LI>
<LI><B>Dependencies</B></LI>
<LI><B>Maven Repository</B></LI>
<LI><B>How to use</B></LI>
<LI><B>Change Log</B></LI>
</UL>


### Introduction
cortical.io's java client - a simple java http client which simplifies communication between any java application and the Retina server using the Retina's REST API.
The source code is split into 4 Maven projects:

* The parent build (retina-service-client-build)
* The client (retina-service-java-api-client)
* The REST model (retina-service-rest-model)
* An example project (retina-service-java-client-example)


### Dependencies
cortical.io's Retina java client is compatible with Java version: 1.7. See the respective pom files for each project for details regarding dependencies.

Compatible with all 2.x.x versions of <a href="http://api.cortical.io">cortical.io's api</a>.

### Maven Repository
If you just need the JAR file, this can be downloaded directly from the <a href="http://https://search.maven.org/#browse|-882754531">Sonatype Central Repository</a> directly or by adding the dependency to a Maven pom:

```
<dependency>
    <groupId>io.cortical</groupId>
    <artifactId>retina-service-java-api-client</artifactId>
    <version>2.2.0</version>
</dependency>
```

### How to use/build
* You will need to have Maven 3.x installed.
* Clone all the sources from our Github repository.
* Navigate to the parent directory (retina-service-client-build) and build the project by typing on the command line: ```mvn clean install```
   * The jar file produced by the client api project (found in the target directory) can then be imported into any project (e.g. the example project).
   * To import the JAR into an existing Maven project, see the below code example:

```
<dependency>
    <groupId>io.cortical</groupId>
    <artifactId>retina-service-java-api-client</artifactId>
    <version>2.2.0</version>
</dependency>
```

   * For non-Maven projects you need to use the ```retina-service-java-api-client-jar-with-dependencies.jar``` (e.g. when directly calling the java client).

* For examples of usage please see:
   * The unit tests included with the client project
   * The example project
    
For further documentation about the Retina-API and information on cortical.io's 'Retina' technology please see: 
http://www.cortical.io/resources_tutorials.html

If you have any questions or problems please visit our forum:
http://www.cortical.io/resources_forum.html

### Change Log
<B>v 2.2.0</B>
* new Classfiy api with /classify/create_category_filter
* new /text/detect_language endpoint
* new /compare/bulk endpoint

<B>v 2.1.0</B>
* initial release
