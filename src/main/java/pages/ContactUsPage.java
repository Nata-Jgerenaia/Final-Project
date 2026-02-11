package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ContactUsPage {
    WebDriver driver;
    WebDriverWait wait;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private By contactUsLink = By.xpath("//a[contains(text(), 'Contact us')]");
    private By nameField = By.name("name");
    private By emailField = By.name("email");
    private By subjectField = By.name("subject");
    private By messageField = By.id("message");
    private By submitButton = By.name("submit");
    private By successMessage = By.cssSelector(".status.alert.alert-success");

    public void fillContactForm(String name, String email, String subject, String message) {
        // Navigate to Contact Us
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(contactUsLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        // Fill fields
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(subjectField).sendKeys(subject);
        driver.findElement(messageField).sendKeys(message);

        // Scroll to the button to make sure it's in view before clicking
        WebElement submitBtn = driver.findElement(submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        // Click the submit button
        submitBtn.click();
    }

    public void handleAlert() {
        try {
            // Wait for the native JS alert
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            // If the standard click failed to trigger it, try JS click as backup
            WebElement submitBtn = driver.findElement(submitButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }
    }

    public String getSuccessText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}