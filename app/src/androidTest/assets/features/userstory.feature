Feature: Simple Feature
  As a user, I want to test a simple feature

Scenario: Cucumber Test 1: Search Functionality acceptance test
  Given the user is on the spell list screen
  When the user enters a spell name or part of a spell name into the search field
  Then the search results should display all spells where the name contains the entered text, sorted by relevance

Scenario: Cucumber Test 2: Filter functionality acceptance test
  Given the user is viewing a list of spells
  When the user applies a filter based on specific attributes (e.g., level, school, class)
  Then the list should update to show only spells that match the selected attributes

Scenario: Cucumber Test 3: Choose specific spellbook
  Given a list of user-created spellbooks
  When the user selects a specific spellbook
  Then the content of that spellbook should be displayed

Scenario: Cucumber Test 4: Add spell to Favourites
  Given the user is viewing a list of spells not favourited
  When the user marks a spell as a favorite by tapping the heart icon
  And the tapped spell is already favourited, the spell should be removed from the favourites list
  Then that spell should be saved to a dedicated Favorites section

  Scenario: Cucumber Test 5: Creating Homebrew Spells
    Given the user has an idea for a new homebrew spell
    When the user writes all the spell information down
    And create the spell
    Then that spell should exist localy