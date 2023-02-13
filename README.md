# java-challenge
Java challenge for AXA LI

### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Experience in Java
I have 2 years experience in Java and I have been using Spring Boot for 2 years as well.

### What was done
- Made project working as expected (employee saving was not working properly)
- Modified project structure (packages related)
- Dramatically changed employee controller
  - Changed request mappings, endpoints names
  - Added request and response models instead of DAO manipulation at controller
  - Added models fields validation
  - Changed endpoints signatures to have returned type
  - Added HTTP statuses according to REST architectural style
  - Replaced property DI with constructor DI
- Improved documentation and comments
  - Added doc comments for interfaces and models
  - Updated swagger version
  - Added detailed swagger documentation of employee controller 
- Added in-memory caching logic for database calls
- Implemented tests for controller, service and repository (pretty high coverage now)

### What can be improved next
If I had more time, I would:
- Replace in-memory caching logic for database calls with Redis or any other non in-memory cache
- Continue refactoring of project architecture according to team/company code style and development approach (e.g. Domain Driven Development)
- Improve models validation
- Add detailed logging
- Add integration tests
- Improve existing tests 
  - Current tests rely on injected in-memory H2 database, so using separate test bean or mock would be much better
- Protect controller endpoints
  - Add auth controller for authentication and obtaining JWT token
  - Secure existing controller to make request available only for authorized users
- Probably add Docker support for project, make maven deploy project as a ready docker image