@skip
Feature: About Us and FAQ Page

  Scenario: Search for email on About Us page
    Given Open 'https://wearecommunity.io/about-us' page
    Then Find 'ask@wearecommunity.io' email

  Scenario: Find and click 'Go to the FAQ page' button
    Given Press 'Go to the FAQ page' button
    Then Load 'https://wearecommunity.io/communities/we_are_community/faq' page
    And Title should be 'FAQ | Community platform'
