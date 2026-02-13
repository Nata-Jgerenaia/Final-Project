package UI_tests;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class SearchAndSubscriptionTests extends BaseTest {

    private void safeClick(By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) getDriver()).executeScript("const ads = document.getElementsByClassName('adsbygoogle'); for (let ad of ads) { ad.remove(); }");
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    @Test(description = "4. Search Product TC9")
    public void testSearchProduct_TC9() {
        getDriver().get("https://automationexercise.com/products");
        getDriver().findElement(By.id("search_product")).sendKeys("Blue Top");
        safeClick(By.id("submit_search"));
        WebElement header = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Searched Products']")));
        Assert.assertTrue(header.isDisplayed());
    }

    @Test(description = "5. Verify Subscription in Home Page")
    public void testSubscriptionHome() {
        getDriver().get("https://automationexercise.com");
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        getDriver().findElement(By.id("susbscribe_email")).sendKeys("test@test.com");
        safeClick(By.id("subscribe"));
        Assert.assertTrue(getDriver().findElement(By.id("success-subscribe")).isDisplayed());
    }

    @Test(description = "6. Verify Subscription in Cart Page")
    public void testSubscriptionCart() {
        getDriver().get("https://automationexercise.com/view_cart");
        getDriver().findElement(By.id("susbscribe_email")).sendKeys("test@test.com");
        safeClick(By.id("subscribe"));
        Assert.assertTrue(getDriver().findElement(By.id("success-subscribe")).isDisplayed());
    }
}