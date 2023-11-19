Feature: Open a spell
  getting a spell from the main page when opening the app

  @openSpell-feature
  Scenario: Press a spell
    Given I fetch all spells
    When I press a spell
    Then I get a overview of the spell