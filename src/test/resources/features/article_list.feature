Feature: Displaying and interacting with the article list on the Articles page

  Background:
    Given the 'Main' site is opened

  Scenario: Display at least 5 articles on page load
    Given the 'Articles' button is clicked
    Then I see at least 5 article previews

  Scenario: The Articles page loads successfully
    When I navigate to the 'Articles' page
    Then the page should load within 3 seconds
    And the page title should contain 'Articles'
    And the HTTP response status should be 200

  Scenario: Each article preview contains title, a publication date and an image
    Given the 'Articles' button is clicked
    Then each article preview contains a title
    And each article preview contains a publication date
    And each article preview contains an image
