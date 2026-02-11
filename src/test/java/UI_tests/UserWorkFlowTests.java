package UI_tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class UserWorkFlowTests extends BaseTest {

    /* The browser opens ONCE via @BeforeClass in BaseTest.
       Tests will now run sequentially in the same window.
    */

    @Test(priority = 1, description = "Verify that user cannot register with an existing email")
    public void testExistingEmailRegistration() {
        driver.get("https://automationexercise.com"); // Start at home

        WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginLink);

        driver.findElement(By.name("name")).sendKeys("Tester");
        driver.findElement(By.xpath("//div[@class='signup-form']//input[@name='email']")).sendKeys("tester123@test.com");

        driver.findElement(By.xpath("//button[text()='Signup']")).click();

        WebElement error = driver.findElement(By.xpath("//p[contains(text(),'Email Address already exist')]"));
        Assert.assertTrue(error.getText().contains("Email Address already exist"));
    }

    @Test(priority = 2, description = "Verify that the Contact Us form works correctly")
    public void testContactSupportForm() {
        // No driver.get() here; it continues from the previous page
        ContactUsPage contactPage = new ContactUsPage(driver);
        contactPage.fillContactForm("Tester", "test@test.com", "Support Request", "Message content.");
        contactPage.handleAlert();

        Assert.assertTrue(contactPage.getSuccessText().contains("successfully"));
    }

    @Test(priority = 3, description = "Verify navigation to the Test Cases page")
    public void testTestCasesNavigation() {
        TestCasesPage tcPage = new TestCasesPage(driver);
        tcPage.clickTestCases();
        Assert.assertTrue(tcPage.isHeaderVisible());
    }

    @Test(priority = 4, description = "Verify product catalog visibility")
    public void testProductCatalogVisibility() {
        ProductPage productPage = new ProductPage(driver);
        productPage.navigateToAllProducts();
        Assert.assertTrue(productPage.isAllProductsVisible());

        productPage.viewFirstProductDetails();
        Assert.assertTrue(productPage.isProductDetailsVisible());
    }
}