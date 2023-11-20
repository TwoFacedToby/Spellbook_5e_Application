Feature: Load spells into the application from the API

  Scenario: Testing the functionality of the API to fetch spell data
    Given I am ready to make an API request
    When I make the API request to fetch spells
    Then I should receive a valid response with spell data

  Scenario: Verifying content of loaded spells
    Given I have successfully loaded spells from the API
    When I inspect the loaded spell data
    Then I should see details of the spells including names and descriptions