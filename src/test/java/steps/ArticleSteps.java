package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


import static org.junit.Assert.*;

public class ArticleSteps {

    WebDriver driver;
    long pageLoadStart;
    long pageLoadEnd;

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
            case "Articles" -> driver.get("https://wearecommunity.io/articles");
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

    @When("I navigate to the 'Articles' page")
    public void navigateToArticlesPage() {
        initializeDriver();
        pageLoadStart = System.currentTimeMillis();
        driver.get("https://wearecommunity.io/articles");
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
            URL url = new URL("https://wearecommunity.io/articles");
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


    @Then("the article previews are ordered by publication date descending")
    public void articlePreviewsAreInDescendingDateOrder() {
        // Kikeressük a dátumokat az article cardokból
        List<WebElement> dateElements = driver.findElements(By.cssSelector(".evnt-article-date"));

        List<LocalDate> dates = new ArrayList<>();

        // A weboldalon használt formátum: "April 17, 2025"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

        for (WebElement dateElement : dateElements) {
            String dateText = dateElement.getText().trim();

            try {
                LocalDate parsedDate = LocalDate.parse(dateText, formatter);
                dates.add(parsedDate);
            } catch (Exception e) {
                fail("Nem sikerült a dátumot értelmezni: " + dateText);
            }
        }

        // Kiírjuk a dátumokat ellenőrzésként
        System.out.println("Talált dátumok:");
        for (LocalDate d : dates) {
            System.out.println(d);
        }

        // Ellenőrizzük, hogy csökkenő sorrendben vannak-e
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate current = dates.get(i);
            LocalDate next = dates.get(i + 1);

            assertTrue(
                    "A dátumok nincsenek csökkenő sorrendben: " + current + " után " + next,
                    !current.isBefore(next)
            );
        }
    }


    @Then("each article preview contains a title")
    public void eachArticleHasTitle() {
        List<WebElement> titleElements = driver.findElements(By.cssSelector(".evnt-article-name h2"));
        assertFalse("No article titles found", titleElements.isEmpty());

        for (WebElement title : titleElements) {
            String text = title.getText().trim();
            assertFalse("An article title is empty", text.isEmpty());
        }
    }








}
