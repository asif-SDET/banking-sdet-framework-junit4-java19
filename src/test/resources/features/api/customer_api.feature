@api @smoke
Feature: Customer banking API

  Scenario: Retrieve a customer using REST Assured
    When I send a GET request for customer 1001
    Then the API status code should be 200
    And the customer POJO first name should be "Asif"
