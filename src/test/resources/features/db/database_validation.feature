@db @smoke
Feature: Banking database validation

  Scenario: Validate seeded business banking customer and account data
    When I query the database for customer 1001
    Then the database customer first name should be "Asif"
    And account "CHK-1001" should have a database balance of 1500 dollars
