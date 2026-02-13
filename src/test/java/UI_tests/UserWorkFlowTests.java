package UI_tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class UserWorkFlowTests extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
    }

    @AfterMethod
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test(priority = 1, description = "Verify existing email registration")
    public void testExistingEmailRegistration() {
        WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginLink);

        driver.findElement(By.name("name")).sendKeys("Tester");
        driver.findElement(By.xpath("//div[@class='signup-form']//input[@name='email']")).sendKeys("tester123@test.com");
        driver.findElement(By.xpath("//button[text()='Signup']")).click();

        WebElement error = driver.findElement(By.xpath("//p[contains(text(),'Email Address already exist')]"));
        Assert.assertTrue(error.getText().contains("Email Address already exist"));
    }

    @Test(priority = 2, description = "Verify contact support form")
    public void testContactSupportForm() {
        ContactUsPage contactPage = new ContactUsPage(driver);
        contactPage.fillContactForm("Tester", "test@test.com", "Support Request", "Message content.");
        contactPage.handleAlert();
        Assert.assertTrue(contactPage.getSuccessText().contains("successfully"));
    }

    @Test(priority = 3, description = "Verify navigation to test cases")
    public void testTestCasesNavigation() {
        TestCasesPage tcPage = new TestCasesPage(driver);
        tcPage.clickTestCases();
        Assert.assertTrue(tcPage.isHeaderVisible());
    }

    @Test(priority = 4, description = "Verify product catalog")
    public void testProductCatalogVisibility() {
        ProductPage productPage = new ProductPage(driver);
        productPage.navigateToAllProducts();
        Assert.assertTrue(productPage.isAllProductsVisible());
        productPage.viewFirstProductDetails();
        Assert.assertTrue(productPage.isProductDetailsVisible());
    }
}