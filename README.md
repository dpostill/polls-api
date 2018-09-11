# polls-api
A simple Spring Boot poll/survey application that provides a RESTful API interface to create questions and view poll results.

Also provided is a MVC interface to enable customers to answer the questions and view the results via a web page.

## REST Interfaces
### List All Questions [GET /questions]
### Create Question [POST /questions]
Content-Type: application/json
{
    "question": "Favourite programming language?",
    "choices": [
        "Swift",
        "Python",
        "Objective-C",
        "Ruby"
    ]
}

## Web Interfaces
### Answer Poll Questions [/poll.html]
### View Poll Results [/results.html]

## Database
A H2 database is used for holding the questions and number of votes

## Compile and start
mvn clean package
java -jar target/polls-api-1.0.0.jar