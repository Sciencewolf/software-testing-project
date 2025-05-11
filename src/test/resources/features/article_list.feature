Feature: Displaying and interacting with the article list on the Articles page

  Background:
    Given the 'Main' site is opened

  Scenario: Display at least 5 articles on page load
    Given the 'Articles' button is clicked
    Then I see at least 5 article previews

 # Scenario: Each article preview contains title, description and image
 #   Given the 'Articles' button is clicked
 #   Then each article preview contains a title
 #   And each article preview contains a short description
 #   And each article preview contains a thumbnail image

#  Scenario: Articles are sorted from newest to oldest
#    Given the 'Articles' button is clicked
#    Then the article previews are ordered by publication date descending

#  Scenario: The Articles page loads successfully
#    When I navigate to the 'Articles' page
#    Then the page should load within 3 seconds
#    And the page title should contain 'Articles'
#    And the HTTP response status should be 200

#  Scenario: No errors are shown during standard navigation
#    When I navigate to the 'Articles' page
#    Then the browser console should contain no JavaScript errors
#    And the network panel should show no failed requests