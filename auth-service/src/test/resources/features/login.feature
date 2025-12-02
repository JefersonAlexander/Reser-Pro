#Language: en

Feature: User login
    As I user I want to login to the platform to access the services that my role grants me

    Scenario: Successful login with correct credentials
        Given I am an active user with email "existinguser@test.com" and password "userexist123"
        When I enter the email "existinguser@test.com" and the password "userexist123"
        Then login is successful
        And the JWT token is generated

    Scenario: Login with an incorrect password
        Given I am an active user with email "existinguser@test.com" and password "userexist123"
        When I enter the email "existinguser@test.com" and the password "wrongpassword123"
        Then login fails
        And I can see a message that says "Bad credentials"

    Scenario: Login with a not existing email
        Given I am a not existing user
        When I enter the email "notexistinguser@test.com" and the password "notexistpassword123"
        Then login fails
        And I can see a message that says "User not found"