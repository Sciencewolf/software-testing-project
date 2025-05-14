Feature: About Us and FAQ Page

  Scenario: Find ABOUT US text on About us page
    Given Open 'https://wearecommunity.io/about-us' page
    Then Find 'ABOUT US' text

  Scenario: Check year in footer
    Given Scroll to footer
    Then Year in footer should be equal to current year

  Scenario: Find and click 'Go to the FAQ page' button
    Given Press 'Go to the FAQ page' button
    Then Load 'https://wearecommunity.io/communities/we_are_community/faq' page
    And Title should be 'FAQ | Community platform'