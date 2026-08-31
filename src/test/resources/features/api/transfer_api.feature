@api
Feature: Transfer banking API

  Scenario: Transfer money and verify database balances
    Given account "CHK-1001" has a starting database balance of 1500 dollars
    And account "SAV-1001" has a starting database balance of 3000 dollars
    When I POST a transfer of 250 dollars from "CHK-1001" to "SAV-1001"
    Then the API status code should be 201
    And the transfer response status should be "COMPLETED"
    And account "CHK-1001" should have a database balance of 1250 dollars
    And account "SAV-1001" should have a database balance of 3250 dollars
