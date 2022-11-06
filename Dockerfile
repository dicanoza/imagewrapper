FROM openjdk:17
CMD ./mvnw clean install
COPY target/imagewrapper-0.0.1-SNAPSHOT.jar imagewrapper-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/imagewrapper-0.0.1-SNAPSHOT.jar"]