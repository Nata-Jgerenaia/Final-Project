package UI_tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class UserWorkFlowTests extends BaseTest {

    // Helper to bypass ads and force clicks
    private void safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        // Remove Google Ads that block the UI
        ((JavascriptExecutor) getDriver()).executeScript(
                "const ads = document.getElementsByClassName('adsbygoogle'); for (let ad of ads) { ad.remove(); }"
        );

        // JS Click to avoid "ElementClickInterceptedException"
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    @Test(description = "Test 1: Search Product and Verify")
    public void testSearchProduct() {
        getDriver().get("https://automationexercise.com/products");
        getDriver().findElement(By.id("search_product")).sendKeys("Blue Top");
        safeClick(By.id("submit_search"));

        WebElement productTitle = getDriver().findElement(By.xpath("//p[text()='Blue Top']"));
        Assert.assertTrue(productTitle.isDisplayed());
    }

    @Test(description = "Test 2: Contact Support Form")
    public void testContactSupportForm() {
        getDriver().get("https://automationexercise.com/contact_us");
        getDriver().findElement(By.name("name")).sendKeys("Nata Test");
        getDriver().findElement(By.name("email")).sendKeys("nata.test@mail.com");
        getDriver().findElement(By.name("subject")).sendKeys("Support");
        getDriver().findElement(By.id("message")).sendKeys("Hello, this is a test.");

        safeClick(By.name("submit")); // Fixed the click interception here
        getDriver().switchTo().alert().accept();

        WebElement successMsg = getDriver().findElement(By.xpath("//div[contains(@class, 'status alert-success')]"));
        Assert.assertTrue(successMsg.isDisplayed());
    }

    @Test(description = "Test 3: Existing Email Registration Error")
    public void testExistingEmailRegistration() {
        getDriver().get("https://automationexercise.com/login");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Nata");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("nata.test@mail.com");

        safeClick(By.xpath("//button[@data-qa='signup-button']")); // Fixed the click interception here

        WebElement errorMsg = getDriver().findElement(By.xpath("//p[contains(text(), 'Email Address already exist')]"));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @Test(description = "Test 4: Verify Subscription in Home Page")
    public void testSubscriptionHomePage() {
        getDriver().get("https://automationexercise.com");
        // Scroll to bottom where subscription is
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        getDriver().findElement(By.id("susbscribe_email")).sendKeys("test@mail.com");
        safeClick(By.id("subscribe"));

        WebElement successMsg = getDriver().findElement(By.id("success-subscribe"));
        Assert.assertTrue(successMsg.isDisplayed());
    }
}