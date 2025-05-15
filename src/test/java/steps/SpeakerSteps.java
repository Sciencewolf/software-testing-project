package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.*;

public class SpeakerSteps {

    private static WebDriver driver = CustomDriverManager.getDriver();

    private final String BASE_URL = "https://wearecommunity.io";
    private final String ARTICLES_URL = String.join("/", BASE_URL, "speakers");

    //Background
    @Given("I open the speakers page")
    public void i_open_the_speakers_page() {
        driver.get(ARTICLES_URL);
    }

    //Scenario - Page loading
    @Then("The page should load successfully")
    public void the_page_should_load_successfully() {
        String title = driver.getTitle();
        assertNotNull("Page title is null!", title);
        assertFalse("Page title is empty!", title.trim().isEmpty());
    }

    @Then("The URL should end with {string}")
    public void the_url_should_end_with(String expectedEnding) {
        String currentUrl = driver.getCurrentUrl();
        assertTrue("URL does not end with " + expectedEnding + "!", currentUrl.contains(expectedEnding));
    }

    //Scenario - Speaker title
    @Then("The first speaker tile should be visible")
    public void the_first_speaker_tile_should_be_visible() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".evnt-card-wrapper")));
        List<WebElement> cards = driver.findElements(By.cssSelector(".evnt-card-wrapper"));
        assertFalse("No speaker cards found on the page", cards.isEmpty());
    }

    @Then("The tile should contain a name")
    public void the_tile_should_contain_a_name() {
        By nameLocator = By.className("evnt-user-name");
        WebElement nameElement = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        String nameText = nameElement.getText();
        assertNotNull("The name element is null!", nameText);
        assertFalse("The name text is empty!", nameText.trim().isEmpty());
    }

    //Scenario - help button
    @When("I click on the in-app help button")
    public void i_click_on_the_in_app_help_button() {
        WebElement helpButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class*='inAppHelpButton']")));
        helpButton.click();
    }

    @Then("the help interface should be visible")
    public void the_help_interface_should_be_visible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement helpModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-sidebar-frame-container")));
        assertTrue("The help interface is not visible", helpModal.isDisplayed());
    }
}
