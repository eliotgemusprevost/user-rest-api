## user-rest-api

### A few notes

By default there is 2 users added when running the application. The first username is eliotprevost and the password is admin123 
the correct credential will be needed when using localhost:8080/authenticate in order to get a valid token.

Then the token can be used for every endpoint with OAuth2 authentication.

Authentication endpoint to retrieve token -
POST http://localhost:8080/authenticate

needed in body 
{
"username":"eliotprevost",
"password":"admin123"
}

####To retrieve specific user
GET http://localhost:8080/user/{id}

####To retrieve all users
GET http://localhost:8080/user/all

####To create a new user
POST http://localhost:8080/users

####To update a user
PUT http://localhost:8080/users

####To delete a user
DELETE http://localhost:8080/users


## Database

I could probably have added JPA as a database for the users but to save a little bit of time I only used a persistent HashMap

## Tests

I did added unit tests but ran out of time a little bit for integration tests.
