# miro-technical-assignment

A working Spring Boot + Spring Data application implementing a hypermedia API 
    
## Usage
### Running the Spring Boot app
Navigate to the directory into which you cloned the repo and execute this:
```
$ mvn spring-boot:run -Dspring-boot.run.profiles=(sql/nosql)
```
You may run the application with an embedded H2 database by selecting the the profile `sql`  or alternatively use a embedded Mongo db by selecting the profile `nosql` running in server mode on the same machine  

Once started you can access the APIs on port 8080, e.g.
```
$ curl http://localhost:8080/widgets
``` 

### Running the executable JAR

`mvn package` creates an executable jar that may be launched directly

```
$ java -jar -Dspring.profiles.active=(sql/nosql) target/widget-api-1.0-SNAPSHOT.jar
```

## REST APIs Endpoints
### Create a Widget resource
```
POST /widget
Accept: application/json
Content-Type: application/json

{
  "x": 50,
  "y": 50,
  "width": 100,
  "height": 100,
  "zindex": 10
}
```

### Find a Widget Resource
```
Get /widget/{widgetId}
Accept: application/json
Content-Type: application/json
```

### Update a Widget resource
```
PATCH /widget/{widgetId}
Accept: application/json
Content-Type: application/json

{
  "x": 50,
  "y": 50,
  "width": 100,
  "height": 100,
  "zindex": 20
}
```

### Delete a Widget Resource
```
DELETE /widget/{widgetId}
Accept: application/json
Content-Type: application/json
```


### Retrieve a list of Widgets
```
Get /widgets?page=1&size=10&lowerX=0&lowerY=0&upperX=100&upperY=150
Accept: application/json
Content-Type: application/json
```

### Refresh application config or get information about system health, configurations, etc.
```
http://localhost:8081/actuator/refresh
http://localhost:8081/actuator/health
http://localhost:8081/actuator/env
http://localhost:8081/actuator/info
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/logfile
```
### To view Swagger 3 API docs
Run the server and browse to 
```
localhost:8080/swagger-ui.html
```
![Swagger UI](img/swagger.jpg "Swagger UI")