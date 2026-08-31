@domain @smoke
Feature: Banking domain transfer

  Scenario: Transfer money between checking and savings accounts
    Given a checking account has a balance of 1500 dollars
    And a savings account has a balance of 3000 dollars
    When I transfer 200 dollars from checking to savings in the domain model
    Then the checking domain balance should be 1300 dollars
    And the savings domain balance should be 3200 dollars
