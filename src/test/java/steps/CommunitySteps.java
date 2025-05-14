package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class CommunitySteps {

    private static WebDriver driver = CustomDriverManager.getDriver();


    // --- Background step ---
    @Given("I open the {string} page")
    public void iOpenThePage(String url) {
        driver.get(url);
    }

    // --- Scenario: Page loads successfully ---
    @Then("the page should load without errors")
    public void pageShouldLoadWithoutErrors() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isComplete = wait.until(driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")
        );
        assertTrue("Page did not load completely", isComplete);
    }

    @Then("the URL should contain {string}")
    public void urlShouldContain(String expected) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue("URL does not contain expected string: " + expected, currentUrl.contains(expected));
    }

    // --- Scenario: Join button presence and accessibility ---
    @Given("the communities are displayed")
    public void communitiesAreDisplayed() {
        List<WebElement> cards = driver.findElements(By.cssSelector(".evnt-card-wrapper"));
        assertFalse("No community cards found on the page", cards.isEmpty());
    }

    @Then("each community card should contain a visible \"Join\" button")
    public void eachCardShouldHaveJoinButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> cards = driver.findElements(By.cssSelector(".evnt-reg-wrapper"));
        assertFalse("No community cards found on the page", cards.isEmpty());

        for (WebElement card : cards) {
            List<WebElement> buttons = card.findElements(By.cssSelector("button.subscribe.reg-button"));
            assertFalse("Missing 'Join' button in a community card", buttons.isEmpty());

            WebElement joinButton = buttons.get(0);

            wait.until(ExpectedConditions.visibilityOf(joinButton));
            assertTrue("'Join' button is not visible", joinButton.isDisplayed());
            assertEquals("'Join' button text mismatch", "Join", joinButton.getText().trim());
        }
    }

    @When("I click on a \"Join\" button")
    public void iClickOnAJoinButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Kiválasztjuk az első Join gombot
        WebElement joinButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.subscribe.reg-button")
        ));
        joinButton.click();
    }

    @Then("a login modal should appear")
    public void aLoginModalShouldAppear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ellenőrizzük, hogy megjelenik-e a login modál szövege
        WebElement modalText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'You must be logged in to continue')]")
        ));

        assertTrue("Login modal did not appear", modalText.isDisplayed());
    }
}
