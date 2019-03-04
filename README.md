# Requirements
- Java JDK 8
- Payara Micro 184 Jar (Download: https://www.payara.fish/software/downloads/)

# Build
``
mvn clean package 
``

# Run
``
 java -jar /path/to/payara/micro.jar --deploy target/interview.war
``

Go to the following URL inside a web browser:

``
http://localhost:8080/interview/
``

# H2 Web Console

Embedded H2 web console is started in this address:

``
http://localhost:8082
``

```
Driver: org.h2.Driver
URL JDBC: jdbc:h2:mem:test
User: <leave empty>
Password: <leave empty>
```