Feature: Speaker page functionality

  Background:
    Given I open the speakers page

  Scenario: Speakers page loads successfully
    Then The page should load successfully
    And The URL should end with "/speakers"

  Scenario: First speaker tile is displayed
    Then The first speaker tile should be visible
    And The tile should contain a name

  Scenario: The in-app help button should open the help interface
    When I click on the in-app help button
    Then the help interface should be visible