package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleSteps {

    WebDriver driver;

    // WebDriver inicializálása
    private void initializeDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
    }

    @Given("the {string} site is opened")
    public void thePageIsOpened(String pageName) {
        initializeDriver();
        switch (pageName) {
            case "Main" -> driver.get("https://wearecommunity.io/");
        }
    }

    @Given("the 'Articles' button is clicked")
    public void clickArticlesButton() {
        // Az "Articles" gombra kattintás
        WebElement articlesBtn = driver.findElement(By.linkText("Articles"));
        articlesBtn.click();
    }

    @Then("I see at least 5 article previews")
    public void seeAtLeastFiveArticles() {
        // Várakozás az oldalelemek betöltésére (ha szükséges)
        try {
            Thread.sleep(2000); // később érdemes WebDriverWait-re cserélni
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cikkek keresése
        List<WebElement> articles = driver.findElements(By.cssSelector(".evnt-card-wrapper")); // pl. .card, .item stb.

        // Minimum 5 elem jelenléte
        assertTrue("Expected at least 5 articles, but found: " + articles.size(), articles.size() >= 5);
    }




}
