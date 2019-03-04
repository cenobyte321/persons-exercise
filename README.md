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
