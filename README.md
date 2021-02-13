## Book Your Room API Documentations

### Get Started

##### Configuring SQL

- Open ```src/main/resources/application.yml```

- In **application.yml**, configure the datasource: 

  1. Change the username and password

  2. Make database with name *bookyourroom*

     **OR**

     Change the database name to your desired database name in the url

  ```
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/bookyourrooms?serverTimesone=UTC
      username: root
      password: 12345678
  ```

  

##### Running Spring Boot

Run spring boot application by using command:

```
mvn spring-boot:run
```

Voila! Your server is now active on port 8080



##### API Documentations

After the server run, go to url path ```localhost:8080/swagger-ui.html``` for accessing the full documented API



#### About The Project

The project is about hotel booking services which will be used for 

- Self booking by guest
- Administration by the hotel manager the hotel and room info which will be used for Room Booking