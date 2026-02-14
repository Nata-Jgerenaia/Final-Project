package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationLoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public RegistrationLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void goToLoginPage() {
        driver.get("https://automationexercise.com/login");
    }

    public void register(String name, String email, String password) {
        driver.get("https://automationexercise.com/login");

        WebElement signupName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='signup-name']")));
        signupName.sendKeys(name);

        WebElement signupEmail = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
        signupEmail.sendKeys(email);

        WebElement signupBtn = driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signupBtn);

    }

    public void login(String email, String password) {
        goToLoginPage();

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-qa='login-email']")));
        emailInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@data-qa='login-password']"));
        passwordInput.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//button[@data-qa='login-button']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
    }

    public void logout() {
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-qa='logout-button']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutBtn);
    }

    public boolean isLoggedIn(String username) {
        WebElement loggedInAs = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Logged in as')]")));
        return loggedInAs.getText().contains(username);
    }
}
