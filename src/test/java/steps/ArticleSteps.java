package steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.*;

public class ArticleSteps {


    private WebDriver driver;
    private long pageLoadStart;
    private long pageLoadEnd;
    private final String BASE_URL = "https://wearecommunity.io";
    private final String ARTICLES_URL = String.join("/", BASE_URL, "articles");
   

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // nincs grafikus böngésző
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the {string} site is opened")
    public void thePageIsOpened(String pageName) {
        switch (pageName) {
            case "Main" -> driver.get(BASE_URL);
            case "Articles" -> driver.get(ARTICLES_URL);
        }
    }

    @Given("the 'Articles' button is clicked")
    public void clickArticlesButton() {
        WebElement articlesBtn = driver.findElement(By.linkText("Articles"));
        articlesBtn.click();
    }

    @Then("I see at least 5 article previews")
    public void seeAtLeastFiveArticles() {
        try {
            Thread.sleep(2000); // célszerű WebDriverWait-re cserélni később
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> articles = driver.findElements(By.cssSelector(".evnt-card-wrapper"));
        assertTrue("Expected at least 5 articles, but found: " + articles.size(), articles.size() >= 5);
    }

    @When("I navigate to the 'Articles' page")
    public void navigateToArticlesPage() {
        pageLoadStart = System.currentTimeMillis();
        driver.get(ARTICLES_URL);
        pageLoadEnd = System.currentTimeMillis();
    }

    @Then("the page should load within 3 seconds")
    public void checkPageLoadTime() {
        long duration = pageLoadEnd - pageLoadStart;
        System.out.println("Page load time: " + duration + " ms");
        assertTrue("Page load time exceeded 3 seconds", duration <= 3000);
    }

    @Then("the page title should contain 'Articles'")
    public void pageTitleShouldContainArticles() {
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        assertTrue("Title does not contain 'Articles'", title.contains("Articles"));
    }

    @Then("the HTTP response status should be 200")
    public void checkHttpStatus() {
        try {
            URL url = new URL(ARTICLES_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP response code: " + responseCode);
            assertEquals("Expected HTTP status 200", 200, responseCode);

        } catch (Exception e) {
            fail("HTTP status check failed: " + e.getMessage());
        }
    }


    @Then("each article preview contains a title")
    public void eachArticleHasTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".evnt-article-name h2")));

        List<WebElement> titles = driver.findElements(By.cssSelector(".evnt-article-name h2"));
        assertFalse("No article titles found", titles.isEmpty());

        for (WebElement title : titles) {
            String text = title.getText().trim();
            assertFalse("An article title is empty", text.isEmpty());
        }
    }


    @Then("each article preview contains a publication date")
    public void eachArticleHasDate() {
        List<WebElement> dateElements = driver.findElements(By.cssSelector(".evnt-article-date"));
        assertFalse("No publication dates found", dateElements.isEmpty());

        for (WebElement date : dateElements) {
            String dateText = date.getText().trim();
            assertFalse("A publication date is empty", dateText.isEmpty());
            System.out.println("Dátum: " + dateText);
        }
    }

    @Then("each article preview contains an image")
    public void eachArticleHasImage() {
        List<WebElement> imageDivs = driver.findElements(By.cssSelector(".evnt-article-image"));
        assertFalse("No image containers found", imageDivs.isEmpty());

        for (WebElement div : imageDivs) {
            String styleAttr = div.getAttribute("style");
            assertNotNull("Image style attribute is missing", styleAttr);
            assertTrue("Image style does not contain background-image", styleAttr.contains("background-image"));

            String url = styleAttr.replaceAll(".*url\\(\"?(.*?)\"?\\).*", "$1");
            assertFalse("Image URL is empty", url.trim().isEmpty());

            System.out.println("Kép URL: " + url);
        }
    }
}

