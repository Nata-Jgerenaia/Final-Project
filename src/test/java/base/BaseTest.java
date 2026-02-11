package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.time.Duration;

public class BaseTest {

    // protected allows UserWorkFlowTests to use this driver
    protected WebDriver driver;

    @BeforeClass // Changed from Method to Class
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        // Keep the implicit wait - it's important for stability!
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initial navigation
        driver.get("https://automationexercise.com");
    }

    @AfterClass // Changed from Method to Class
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}