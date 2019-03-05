# Requirements
- Java JDK 8
- Payara Micro 184 Jar (Download: https://www.payara.fish/software/downloads/)

# Build
``
mvn clean package 
``

# Run

Backend:

UK Server: 

``
java -jar /path/to/payara/micro.jar --deploy service/target/service.war --port 8080
``

US Server:

``
java -jar /path/to/payara/micro.jar --deploy service/target/service.war --port 8081
``


Web App:

``
java -jar /path/to/payara/micro.jar --deploy webapp/target/webapp.war --port 8084
``

Go to the following URL inside a web browser:

``
http://localhost:8084/webapp/
``

# H2 Web Console

Embedded H2 web console is started in these addresses:

UK Server

``
http://localhost:8082
``

US Server

``
http://localhost:8083
``

```
Driver: org.h2.Driver
URL JDBC: jdbc:h2:mem:test
User: <leave empty>
Password: <leave empty>
```