package UI_tests;

import base.BaseTest; // This fixes "Cannot resolve symbol BaseTest"
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchAndSubscriptionTests extends BaseTest {

    @Test(description = "Test Case 9: Search Product")
    public void testSearchProduct_TC9() {
        // We use 'driver' directly because it is 'protected' in BaseTest

        Allure.step("Click on 'Products' button", () -> {
            driver.findElement(By.xpath("//a[@href='/products']")).click();
        });

        Allure.step("Enter product name 'Blue Top' and search", () -> {
            driver.findElement(By.id("search_product")).sendKeys("Blue Top");
            driver.findElement(By.id("submit_search")).click();
        });

        Allure.step("Verify 'SEARCHED PRODUCTS' is visible", () -> {
            boolean isVisible = driver.findElement(By.xpath("//h2[text()='Searched Products']")).isDisplayed();
            Assert.assertTrue(isVisible);
        });
    }
}