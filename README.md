# polls-api
A simple Spring Boot poll/survey application that provides a RESTful API interface to create a multiple choice questionnaire.

Also provided is a MVC interface to enable customers to answer the questions and view the results via a web page.

### REST Interfaces
- List All Questions [GET http://localhost:8080/questions]
- Create Question [POST http://localhost:8080/questions] [Content-Type: application/json]
```
{
    "question": "Favourite programming language?",
    "choices": [
        "Swift",
        "Python",
        "Objective-C",
        "Ruby"
    ]
}
```

### Web Interfaces
- Answer Poll Questions [http://localhost:8080/poll.html]
- View Poll Results [http://localhost:8080/results.html]

### Database
A H2 database is used for holding the questions and number of votes

### Compile and start
```
mvn clean package
java -jar target/polls-api-1.0.0.jar
```

### Swagger page
```
http://localhost:8080/swagger-ui.html
```