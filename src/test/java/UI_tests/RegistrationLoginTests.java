package UI_tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class RegistrationLoginTests extends BaseTest {

    @Test
    public void testFullUserLifecycle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String email = "nata." + System.currentTimeMillis() + "@mail.com";
        String password = "Pass123!";

        // --- STEP 1: REGISTER ---
        driver.get("https://automationexercise.com/login");
        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys("Nata");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        driver.findElement(By.id("id_gender2")).click();
        driver.findElement(By.id("password")).sendKeys(password);

        new Select(driver.findElement(By.id("days"))).selectByVisibleText("10");
        new Select(driver.findElement(By.id("months"))).selectByVisibleText("May");
        new Select(driver.findElement(By.id("years"))).selectByVisibleText("1995");

        driver.findElement(By.id("first_name")).sendKeys("Nata");
        driver.findElement(By.id("last_name")).sendKeys("Test");
        driver.findElement(By.id("address1")).sendKeys("123 Automation St");
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("United States");
        driver.findElement(By.id("state")).sendKeys("Georgia");
        driver.findElement(By.id("city")).sendKeys("Tbilisi");
        driver.findElement(By.id("zipcode")).sendKeys("0101");
        driver.findElement(By.id("mobile_number")).sendKeys("555123456");

        WebElement createBtn = driver.findElement(By.xpath("//button[@data-qa='create-account']"));
        js.executeScript("arguments[0].click();", createBtn);

        // Verify Registration
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']")));

        // --- STEP 2: LOGOUT (Via Home Page to avoid ads) ---
        driver.get("https://automationexercise.com/");
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        js.executeScript("arguments[0].click();", logoutBtn);
        Assert.assertTrue(driver.findElement(By.linkText("Signup / Login")).isDisplayed(), "Logout failed!");

        // --- STEP 3: INCORRECT LOGIN ---
        driver.findElement(By.linkText("Signup / Login")).click();
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys("WrongPassword123");
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@style='color: red;']")));
        Assert.assertTrue(errorMsg.getText().contains("incorrect"), "Error message not displayed for wrong login!");

        // --- STEP 4: CORRECT LOGIN ---
        // Clear fields and try again
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).clear();
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).clear();
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        // Final Verification
        WebElement loggedInAs = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Logged in as')]")));
        Assert.assertTrue(loggedInAs.isDisplayed(), "Final login failed!");

        System.out.println("Success: Registered, Logged Out, Failed Login, and Correct Login completed in one session!");
    }
}