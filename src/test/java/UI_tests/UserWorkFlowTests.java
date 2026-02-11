package UI_tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class UserWorkFlowTests extends BaseTest {

    @Test(priority = 1)
    public void testExistingEmailRegistration() {
        WebElement loginLink = driver.findElement(By.xpath("//a[contains(text(),'Signup / Login')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginLink);

        driver.findElement(By.name("name")).sendKeys("Tester");
        driver.findElement(By.xpath("//div[@class='signup-form']//input[@name='email']")).sendKeys("tester123@test.com");
        driver.findElement(By.xpath("//button[text()='Signup']")).click();

        WebElement error = driver.findElement(By.xpath("//p[contains(text(),'Email Address already exist')]"));
        Assert.assertTrue(error.getText().contains("Email Address already exist"));
    }

    @Test(priority = 2)
    public void testContactSupportForm() {
        ContactUsPage contactPage = new ContactUsPage(driver);
        contactPage.fillContactForm("Tester", "test@test.com", "Support", "Message content.");
        contactPage.handleAlert();
        Assert.assertTrue(contactPage.getSuccessText().contains("successfully"));
    }

    @Test(priority = 3)
    public void testTestCasesNavigation() {
        TestCasesPage tcPage = new TestCasesPage(driver);
        tcPage.clickTestCases();
        Assert.assertTrue(tcPage.isHeaderVisible());
    }

    @Test(priority = 4)
    public void testProductCatalogVisibility() {
        ProductPage productPage = new ProductPage(driver);
        productPage.navigateToAllProducts();
        Assert.assertTrue(productPage.isAllProductsVisible());
        productPage.viewFirstProductDetails();
        Assert.assertTrue(productPage.isProductDetailsVisible());
    }
}