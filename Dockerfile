FROM openjdk:11
ADD target/flowrspot-0.0.1-SNAPSHOT.jar flowrspot-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar" , "flowrspot-0.0.1-SNAPSHOT.jar"]