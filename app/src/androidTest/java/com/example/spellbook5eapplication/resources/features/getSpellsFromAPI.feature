Feature: Fetching spell list from API

  Scenario: Successfully retrieving a list of spells
    Given I have a network connection
    When I request the list of spells from the API
    Then I should receive a list of spells

  Scenario: Handling failure in retrieving the list of spells
    Given I have a network connection
    When I request the list of spells from the API
    And the API is down
    Then I should receive an error message