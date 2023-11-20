Feature: Load spells into the application from the API

  Scenario: Verifying content of loaded spells
    Given I have successfully loaded spells from the API
    When I inspect the loaded spell data
    Then I should see details of the spells including names and descriptions