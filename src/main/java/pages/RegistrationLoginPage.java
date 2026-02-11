package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationLoginPage {
    WebDriver driver;

    public RegistrationLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for the registration/login page
    By loginEmail = By.xpath("//input[@data-qa='login-email']");
    By loginPassword = By.xpath("//input[@data-qa='login-password']");
    By loginBtn = By.xpath("//button[@data-qa='login-button']");

    public void login(String email, String password) {
        driver.findElement(loginEmail).sendKeys(email);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
}