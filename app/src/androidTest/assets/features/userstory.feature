Feature: Simple Feature
  As a user, I want to test a simple feature

Scenario: Simple Test Scenario
  Given I have a simple test environment
  When I perform a simple action
  Then I should get a simple result

Scenario: Search Functionality
  Given the user is on the spell list screen
  When the user enters a spell name or part of a spell name into the search field
  Then the search results should display all spells where the name contains the entered text, sorted by relevance
  And the search should be performed within a few seconds to ensure responsiveness, If no spells match the criteria, a message should be displayed indicating "No spells found."