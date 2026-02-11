package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private By productsLink = By.xpath("//a[@href='/products']");
    private By allProductsHeader = By.xpath("//h2[text()='All Products']");
    private By viewProductFirst = By.xpath("(//a[text()='View Product'])[1]");
    private By productDetailsHeader = By.xpath("//div[@class='product-information']/h2");

    public void navigateToAllProducts() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(productsLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public boolean isAllProductsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(allProductsHeader)).isDisplayed();
    }

    public void viewFirstProductDetails() {
        WebElement viewBtn = wait.until(ExpectedConditions.presenceOfElementLocated(viewProductFirst));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewBtn);
    }

    public boolean isProductDetailsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailsHeader)).isDisplayed();
    }
}