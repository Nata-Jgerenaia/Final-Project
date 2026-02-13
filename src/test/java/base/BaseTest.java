package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {
    // ThreadLocal ensures each parallel browser has its own driver
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    // Static email so all tests use the SAME user created in Step 1
    protected static String sharedEmail;
    protected static String sharedPassword = "Pass123!";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driverThread.set(driver);
        getDriver().get("https://automationexercise.com");
    }

    public WebDriver getDriver() {
        return driverThread.get();
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
        }
    }
}