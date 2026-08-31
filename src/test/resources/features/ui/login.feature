@ui
Feature: Business banking UI validation

  Scenario: Valid employee logs in and sees accounts
    Given I open the local business banking login page
    When I login with username "asif" and password "Bank123!"
    Then I should see the welcome message "Welcome Asif"
    And I should see 2 banking accounts
    And the page title should be "Business Banking Portal"

  @ui
  Scenario: Valid employee logs in using Excel test data
    Given I open the local business banking login page
    When I login using Excel test data
    Then I should see the welcome message "Welcome Asif"
    And I should see 2 banking accounts
    And the page title should be "Business Banking Portal"