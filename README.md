# spring-boot-userregistration-authentication
Spring boot application for user registration and authentication

User Registration --> http://localhost:8080/userservice/register

{
"firstName": "FirstName",
"lastName": "LastName",
"userName": "UserName",
"password": "PASsword123"
}

User Authentication -->http://127.0.0.1:8080/userservice/login

Request
{
    "userName": "UserName",
    "password": "PASsword123"
}

Response : Will authenticate user and generate token and send this back on the response header.

Please write a Spring REST controller that handles user registration. The REST resource should be accessible via "/userservice/register" and
accepts following JSON data:
{
"firstName": "Some first name",
"lastName": "The last name",
"userName": "The user name",
"password": "The password in plain text"
}
In case of success it should return the user object without the password:
{
"id": "Id generated by the back-end",
"firstName": "Some first name",
"lastName": "The last name",
"userName": "The user name"
}
In case the username is already existing, an error response should be returned with HTTP error-code 409:
{
"code": "USER_ALREADY_EXISTS",
"description": "A user with the given username already exists"
}


