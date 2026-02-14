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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        // Wait for element to be present first
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        // Remove Ads multiple times to be safe
        ((JavascriptExecutor) getDriver()).executeScript(
                "const ads = document.querySelectorAll('.adsbygoogle, #aswift_0_host, #aswift_1_host'); " +
                        "for (let ad of ads) { ad.remove(); }"
        );

        // Use JS click as it is more reliable against overlays
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    @Test(description = "7. Contact Us Form")
    public void testContactUs() {
        getDriver().get("https://automationexercise.com/contact_us");

        // Ensure the form is ready
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("name"))).sendKeys("Nata");

        getDriver().findElement(By.name("email")).sendKeys("mari@mail.com");
        getDriver().findElement(By.name("subject")).sendKeys("Test Support");
        getDriver().findElement(By.id("message")).sendKeys("Hello, this is a final test for the lecturer.");

        // We use a regular click here first because Contact Us needs to trigger the Alert
        try {
            getDriver().findElement(By.name("submit")).click();
        } catch (Exception e) {
            // If blocked by ad, fall back to safeClick
            safeClick(By.name("submit"));
        }

        // Forcefully wait and accept the browser alert
        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();

        // Check for success message with a fresh wait
        WebElement success = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".status.alert-success")));
        Assert.assertTrue(success.isDisplayed(), "Success message was not found after alert acceptance!");
    }

    @Test(description = "10. Test Case 13: Verify Product quantity in Cart")
    public void testProductQuantityInCart() {
        // 1. Go to a specific product detail page directly
        getDriver().get("https://automationexercise.com/product_details/2");

        // 2. Increase quantity to 4
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("quantity")));
        quantityInput.clear();
        quantityInput.sendKeys("4");

        // 3. Add to cart
        safeClick(By.xpath("//button[contains(@class, 'cart')]"));

        // 4. Click 'View Cart' (Wait for it to be visible in the modal)
        WebElement viewCartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//u[text()='View Cart']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", viewCartLink);

        // 5. Verify quantity is 4 using a precise XPath
        // We look for the button inside the cart_quantity cell
        WebElement quantityResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='cart_quantity']/button")));

        Assert.assertEquals(quantityResult.getText(), "4", "The quantity in the cart does not match!");
    }
    @Test(description = "9. View Product Details")
    public void testViewProductDetails() {
        getDriver().get("https://automationexercise.com/products");
        safeClick(By.xpath("(//a[text()='View Product'])[1]"));

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("product_details"));
        Assert.assertTrue(getDriver().findElement(By.className("product-information")).isDisplayed());
    }

    @Test(description = "10. Add to Cart from Product Page")
    public void testAddToCart() {
        getDriver().get("https://automationexercise.com/product_details/1");
        safeClick(By.xpath("//button[contains(@class, 'cart')]"));

        WebElement modal = new WebDriverWait(getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        Assert.assertTrue(modal.isDisplayed(), "Add to cart modal did not appear!");
    }
}