Feature: Simple Feature
  As a user, I want to test a simple feature

Scenario: Search Functionality
  Given the user is on the spell list screen
  When the user enters a spell name or part of a spell name into the search field
  Then the search results should display all spells where the name contains the entered text, sorted by relevance