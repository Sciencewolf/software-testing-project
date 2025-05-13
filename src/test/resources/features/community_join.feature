Feature: Join button visibility on the Communities page

  Background:
    Given I open the "https://wearecommunity.io/communities" page

  Scenario: Communities page loads successfully
    Then the page should load without errors
    And the URL should contain "/communities"

  Scenario: Each community card has a visible and accessible "Join" button
    Given the communities are displayed
    Then each community card should contain a visible "Join" button

  Scenario: "Join" button is clickable and leads to appropriate destination
    When I click on a "Join" button
    Then a login modal should appear