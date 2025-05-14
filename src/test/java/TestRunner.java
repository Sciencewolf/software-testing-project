import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",   // ide kerültek a .feature fájlok
        glue = {"steps"},                             // itt vannak a StepDefinition osztályok
        plugin = {"pretty", "html:target/cucumber-report.html"}, // jelentés generálás
        stepNotifications = true,
        monochrome = true

)
public class TestRunner {
}