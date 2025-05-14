package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class AboutUsSteps {
    private static WebDriver driver = CustomDriverManager.getDriver();

    @Given("Open {string} page")
    public void openPage(String pageUrl) {
        driver.get(pageUrl);
        System.out.println("Page opened: " + pageUrl + "");
    }

    @Then("Find {string} email")
    public void findEmailOnPage(String givenEmailText) {
        dismissCookieBannerIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By emailSelector = By.cssSelector("a[href='mailto:" + givenEmailText + "']");

        WebElement emailTag = wait.until(ExpectedConditions.presenceOfElementLocated(emailSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailTag);
        wait.until(ExpectedConditions.visibilityOf(emailTag));

        String emailText = emailTag.getText().trim();
        Assert.assertTrue("Expected email not found", emailText.contains(givenEmailText));

        System.out.println("Email found: " + emailText);
    }

    @Given("Press 'Go to the FAQ page' button")
    public void pressGoToTheFaqPageButton() {
        dismissCookieBannerIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement faqBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Go to the FAQ page")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqBtn);

        String originalWindow = driver.getWindowHandle();
        faqBtn.click();

        // Switch to the new tab if it opened one
        Set<String> windows = driver.getWindowHandles();
        for (String handle : windows) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("FAQ page opened");
    }

    @Then("Load {string} page")
    public void loadFaqPage(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains(expectedUrl));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Current URL did not match", currentUrl.contains(expectedUrl));
        System.out.println("Current URL: " + currentUrl);
    }

    @And("Title should be {string}")
    public void checkTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertTrue("Title mismatch", actualTitle.contains(expectedTitle));
        System.out.println("Title: " + actualTitle);
    }

    private void dismissCookieBannerIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            acceptBtn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-banner-sdk")));
        } catch (TimeoutException | NoSuchElementException ignored) {
            // Banner already gone or not present
        }
    }
}
