Feature: Can retrieve soells
  The app should be able to retrieve a spell

  Scenario: At least one spell is retrieved
    Given a spell list
    When I open it
    Then I should have at least one spell