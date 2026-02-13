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

    // STEP 1: REGISTRATION (This runs alone first)
    @Test
    public void testUserRegistration() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        sharedEmail = "nata." + System.currentTimeMillis() + "@mail.com";

        getDriver().get("https://automationexercise.com/login");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Nata");
        getDriver().findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(sharedEmail);
        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='signup-button']")));

        // Fill data...
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(sharedPassword);
        getDriver().findElement(By.id("first_name")).sendKeys("Nata");
        getDriver().findElement(By.id("last_name")).sendKeys("Test");
        getDriver().findElement(By.id("address1")).sendKeys("123 Automation St");
        getDriver().findElement(By.id("state")).sendKeys("Georgia");
        getDriver().findElement(By.id("city")).sendKeys("Tbilisi");
        getDriver().findElement(By.id("zipcode")).sendKeys("0101");
        getDriver().findElement(By.id("mobile_number")).sendKeys("555123456");

        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='create-account']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']")));

        System.out.println("Registration finished. Triggering parallel logins now...");
        Thread.sleep(2000); // Small pause for DB sync
    }

    // BROWSER 1: Login & Logout
    @Test(dependsOnMethods = "testUserRegistration")
    public void testLoginAndLogoutFlow() {
        System.out.println("LOG: Browser 1 (Login/Logout) starting on Thread: " + Thread.currentThread().getId());
        performLogin(sharedEmail, sharedPassword);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Logged in as')]")));

        jsClick(getDriver().findElement(By.linkText("Logout")));
        Assert.assertTrue(getDriver().findElement(By.linkText("Signup / Login")).isDisplayed());
    }

    // BROWSER 2: Incorrect Login
    @Test(dependsOnMethods = "testUserRegistration")
    public void testLoginIncorrect() {
        System.out.println("LOG: Browser 2 (Incorrect Login) starting on Thread: " + Thread.currentThread().getId());
        performLogin(sharedEmail, "WrongPassword123!");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@style='color: red;']")));
        Assert.assertTrue(errorMsg.getText().contains("incorrect"));
    }

    private void performLogin(String email, String password) {
        getDriver().get("https://automationexercise.com/login");
        ((JavascriptExecutor) getDriver()).executeScript(
                "const ads = document.getElementsByClassName('adsbygoogle'); for (let ad of ads) { ad.remove(); }"
        );
        getDriver().findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        getDriver().findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        jsClick(getDriver().findElement(By.xpath("//button[@data-qa='login-button']")));
    }
}