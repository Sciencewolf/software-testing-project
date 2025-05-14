Feature: Events page displays upcoming and past events

  Background:
    Given the Main site is opened
    And the Events button is clicked

  Scenario: Events page loads successfully
    Then the page should return HTTP 200 status

  Scenario: Upcoming Events section shows at least one event
    When Click on the Upcoming Events tab
    Then Should see at least one event card in the Upcoming Events section

  Scenario: Past Events section shows at least one event
    When Click on the Past Events tab
    Then Should see at least one event card in the Past Events section