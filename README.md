# polling-service
Web service that allows user to add services with name and url, manipulate them and monitor their status

* Package name: 'com.kry.demo.pollingService'

# Getting Started

1) Setup a MySql database and create services table as defined in the schema at src/main/resources/scripts/services_schema.sql
2) Configure database properties in src/main/resources/application.yml with your local setup.
3) Run as a spring boot application with: *gradlew bootRun*
4) After starting the application, you can access the [api documentation](http://localhost:8088/swagger-ui.html)
5) To start [front end component](http://localhost:3000), navigate to src/web and follow instructions there. 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.0-SNAPSHOT/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.0-SNAPSHOT/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)