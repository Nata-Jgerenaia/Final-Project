package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TestCasesPage {
    WebDriver driver;
    WebDriverWait wait;

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private By testCasesLink = By.xpath("//a[contains(text(), 'Test Cases')]");
    private By testCasesHeader = By.xpath("//b[text()='Test Cases']");

    public void clickTestCases() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(testCasesLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public boolean isHeaderVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(testCasesHeader)).isDisplayed();
    }
}