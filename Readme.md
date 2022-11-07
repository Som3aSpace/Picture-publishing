### Intro
I used sql server, there is little space on my machine, and I thought it will be easy to change later

#### versions
- spring boot APP with JAVA 11 and MAVEN 3.6.3

###How to start?
-   create empty scheme in sql server with name "yeshtery"
- Don't forget to change datasource credentials username/password
- there are DDL scripts active so once the app runs all entities will be created and also init data will be inserted using prepared script you can find it in "HOME/src/resources/data.sql"
- there is a swagger ui could help you test the endpoints on the following url "http://localhost:8080/swagger-ui/"
however I used postman on testing, so I exported the collection as .json that you could import and test also, you can find the file on the following path "HOME/src/resources/Yeshtery.postman_collection.json"
  
