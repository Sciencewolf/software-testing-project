package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class EventsSteps {

    private static WebDriver driver = CustomDriverManager.getDriver();

    private static final String BASE_URL = "https://wearecommunity.io";
    private static final String EVENTS_URL = BASE_URL + "/events";

    @Given("the Main site is opened")
    public void openMainSite() {
        driver.get(BASE_URL);
    }

    @And("the Events button is clicked")
    public void clickEventsButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement eventsBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Events")));
        eventsBtn.click();
    }

    @And("in the top middle side of the page 'Events' subtitle should be appear")
    public void verifyEventsSubtitleIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String expectedText = "Events";

        try {
            WebElement subtitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.tagName("h1")
            ));
            String actualText = subtitle.getText();
            Assert.assertEquals("The subtitle is not correct.", expectedText, actualText);
        } catch (TimeoutException e) {
            Assert.fail("The subtitle 'Events' was not found on the page.");
        }
    }

    @Then("the page should return HTTP 200 status")
    public void verifyHttpStatus200() {
        try {
            URL url = new URL(EVENTS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP response code: " + responseCode);
            assertEquals("Expected HTTP 200", 200, responseCode);
        } catch (Exception e) {
            fail("Failed to check HTTP status: " + e.getMessage());
        }
    }

    @When("Click on the Upcoming Events tab")
    public void clickUpcomingEventsTab() {
        WebElement upcomingEventsBtn = driver.findElement(By.xpath("//*[@id='app']/div/main/section[3]/div/div/div[1]/ul/li[1]/a"));
        upcomingEventsBtn.click();
    }

    @When("Click on the Past Events tab")
    public void clickPastEventsTab() {
        WebElement pastEventsBtn = driver.findElement(By.xpath("//*[@id='app']/div/main/section[3]/div/div/div[1]/ul/li[2]/a"));
        pastEventsBtn.click();
    }

    @Then("Should see at least one event card in the Upcoming Events section")
    public void verifyUpcomingEventsVisible() {
        try {
            Thread.sleep(2000); // célszerű WebDriverWait-re cserélni később
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> events = driver.findElements(By.cssSelector(".evnt-card-wrapper"));
        assertTrue("Expected at least 1 articles, but found: " + events.size(), events.size() >= 1);
    }

    @Then("Should see at least one event card in the Past Events section")
    public void verifyPastEventsVisible() {
        try {
            Thread.sleep(2000); // célszerű WebDriverWait-re cserélni később
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> events = driver.findElements(By.cssSelector(".evnt-card-wrapper"));
        assertTrue("Expected at least 1 articles, but found: " + events.size(), events.size() >= 1);
    }


}
