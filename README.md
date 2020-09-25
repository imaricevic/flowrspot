# FlowrSpot

A simple REST API dockerised application built with *Java Spring Boot* and *mySQL*.

## Running the project

From the project root build the application .jar with
```
./mvnw package
```

Download and install Docker for windows/Linux/mac from the official website https://hub.docker.com/.    
After installation check whether Docker is properly installed and if it's running.  
Start the Docker mySQL and spring app containers with
```
docker-compose -f docker-compose.yml up
```

## Links

- MySql Adminer exposed at  http://localhost:8080/  with credentials
```
root/root
```

- FlowrSpot RSEST API endpoint
http://localhost:8081/

- REST call examples
https://documenter.getpostman.com/view/12844819/TVKFzvfT
