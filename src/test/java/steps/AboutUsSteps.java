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
    }

    @Then("Find {string} text")
    public void findTextOnPage(String givenText) {
        dismissCookieBannerIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement h1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h1.HeroHeader-module__pageHeading__3mENp")));

        String headingText = h1.getText().trim();
        Assert.assertEquals(givenText, headingText);
    }

    @Given("Scroll to footer")
    public void scrollToFooter() {
        dismissCookieBannerIfPresent();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Then("Year in footer should be equal to current year")
    public void checkYearInFooter() {
        dismissCookieBannerIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement year = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".evnt-copyright")));

        String currentYear = String.valueOf(java.time.LocalDate.now().getYear());
        String footerYear = year.getText().trim();

        String extractedYearFromFooter = footerYear.replaceAll("\\D+", "");

        Assert.assertEquals(currentYear, extractedYearFromFooter);
    }

    @Given("Press {string} button")
    public void pressGoToTheFaqPageButton(String givenText) {
        dismissCookieBannerIfPresent();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement faqBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(givenText)));
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
    }

    @Then("Load {string} page")
    public void loadFaqPage(String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains(expectedUrl));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Current URL did not match", currentUrl.contains(expectedUrl));
    }

    @And("Title should be {string}")
    public void checkTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertTrue("Title mismatch", actualTitle.contains(expectedTitle));
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
