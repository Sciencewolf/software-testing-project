package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AboutUsSteps {
    private static WebDriver driver;
    private final String BASE_URL = "https://wearecommunity.io";
    private final String ABOUT_US_URL = String.join("/", BASE_URL, "about-us");

    @BeforeAll()
    public static void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterAll()
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("Open {string} page")
    public void openAboutUsPage(String pageName) {
        driver.get(ABOUT_US_URL);
    }

    @Then("Find {string} email")
    public void findEmailOnPage(String givenEmailText) {
        WebElement emailHtmlTag = driver.findElement(By.cssSelector("a[href='mailto:ask@wearecommunity.io']"));
        String emailText = emailHtmlTag.getText();
        Assert.assertTrue(givenEmailText + " email is on page", emailText.contains("ask@wearecommunity.io"));
    }

    @Given("Press 'Go to the FAQ page' button")
    public void pressGoToTheFaqPageButton() throws InterruptedException {
        WebElement faqBtn = driver.findElement(By.linkText("Go to the FAQ page"));

        // Scroll into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", faqBtn);
        Thread.sleep(500);
        faqBtn.click();
    }


    @Then("Load {string} page")
    public void loadFaqPage(String url) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("FAQ page is loaded " + url, currentUrl.contains("https://wearecommunity.io/communities/we_are_community/faq"));
    }

    @And("Title should be 'FAQ | Community platform'")
    public void checkTitle() {
        String title = driver.getTitle();
        Assert.assertTrue("Title is 'FAQ | Community platform'", title.contains("FAQ | Community platform"));
    }
}
