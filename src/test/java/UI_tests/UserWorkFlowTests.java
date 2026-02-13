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

    private void safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) getDriver()).executeScript("const ads = document.getElementsByClassName('adsbygoogle'); for (let ad of ads) { ad.remove(); }");
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    @Test(description = "7. Contact Us Form")
    public void testContactUs() {
        getDriver().get("https://automationexercise.com/contact_us");
        getDriver().findElement(By.name("name")).sendKeys("Nata");
        getDriver().findElement(By.name("email")).sendKeys("nata@mail.com");
        getDriver().findElement(By.name("subject")).sendKeys("Test");
        getDriver().findElement(By.id("message")).sendKeys("Hello");
        safeClick(By.name("submit"));
        getDriver().switchTo().alert().accept();
        WebElement success = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'status alert-success')]")));
        Assert.assertTrue(success.isDisplayed());
    }

    @Test(description = "8. Existing Email Error")
    public void testExistingEmail() {
        getDriver().get("https://automationexercise.com/login");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Nata");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys("nata.test@mail.com");
        safeClick(By.xpath("//button[@data-qa='signup-button']"));
        WebElement error = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'already exist')]")));
        Assert.assertTrue(error.isDisplayed());
    }

    @Test(description = "9. View Product Details")
    public void testViewProductDetails() {
        getDriver().get("https://automationexercise.com/products");
        safeClick(By.xpath("(//a[text()='View Product'])[1]"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("product_details"));
        Assert.assertTrue(getDriver().findElement(By.className("product-information")).isDisplayed());
    }

    @Test(description = "10. Add to Cart from Product Page")
    public void testAddToCart() {
        getDriver().get("https://automationexercise.com/product_details/1");
        safeClick(By.xpath("//button[contains(@class, 'cart')]"));
        WebElement modal = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        Assert.assertTrue(modal.isDisplayed());
    }
}