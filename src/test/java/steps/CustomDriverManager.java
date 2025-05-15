package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CustomDriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        String headless = System.getProperty("headless", "true");

        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

            if(Boolean.parseBoolean(headless)) {
                options.addArguments("--headless");
            }

            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
