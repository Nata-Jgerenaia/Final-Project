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

public class RegistrationLoginTests extends BaseTest {

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    @Test(description = "1. Register User")
    public void testUserRegistration() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        sharedEmail = "nata." + System.currentTimeMillis() + "@mail.com";
        getDriver().get("https://automationexercise.com/login");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Nata");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(sharedEmail);
        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='signup-button']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(sharedPassword);
        getDriver().findElement(By.id("first_name")).sendKeys("Nata");
        getDriver().findElement(By.id("last_name")).sendKeys("Test");
        getDriver().findElement(By.id("address1")).sendKeys("123 St");
        getDriver().findElement(By.id("state")).sendKeys("Georgia");
        getDriver().findElement(By.id("city")).sendKeys("Tbilisi");
        getDriver().findElement(By.id("zipcode")).sendKeys("0101");
        getDriver().findElement(By.id("mobile_number")).sendKeys("555123");
        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='create-account']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']")));
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "testUserRegistration", description = "2. Parallel Correct Login/Logout")
    public void testLoginAndLogoutFlow() {
        performLogin(sharedEmail, sharedPassword);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Logged in as')]")));
        jsClick(getDriver().findElement(By.linkText("Logout")));
    }

    @Test(dependsOnMethods = "testUserRegistration", description = "3. Parallel Incorrect Login")
    public void testLoginIncorrect() {
        performLogin(sharedEmail, "WrongPass!");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@style='color: red;']")));
        Assert.assertTrue(error.getText().contains("incorrect"));
    }

    private void performLogin(String email, String password) {
        getDriver().get("https://automationexercise.com/login");
        ((JavascriptExecutor) getDriver()).executeScript("const ads = document.getElementsByClassName('adsbygoogle'); for (let ad of ads) { ad.remove(); }");
        getDriver().findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='login-button']")));
    }
}