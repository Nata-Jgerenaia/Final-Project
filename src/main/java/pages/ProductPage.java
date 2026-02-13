package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locators
    private By productsLink = By.xpath("//a[@href='/products']");
    private By cartLink = By.xpath("//a[@href='/view_cart']");
    private By allProductsHeader = By.xpath("//h2[text()='All Products']");
    private By viewProductFirst = By.xpath("(//a[text()='View Product'])[1]");
    private By productDetailsHeader = By.xpath("//div[@class='product-information']/h2");
    private By searchInput = By.id("search_product");
    private By searchButton = By.id("submit_search");
    private By searchedProductsHeader = By.xpath("//h2[text()='Searched Products']");
    private By subscriptionEmailField = By.id("susbscribe_email");
    private By subscribeArrowButton = By.id("subscribe");
    private By successMessage = By.id("success-subscribe");

    // Methods
    public void navigateToAllProducts() {
        WebElement link = wait.until(d -> d.findElement(productsLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void navigateToCart() {
        wait.until(d -> d.findElement(cartLink)).click();
    }

    public void searchForProduct(String productName) {
        wait.until(d -> d.findElement(searchInput)).sendKeys(productName);
        driver.findElement(searchButton).click();
    }

    public boolean isSearchedProductsVisible() {
        // Fix: Use Lambda to find the element and then check if it's displayed
        return wait.until(d -> d.findElement(searchedProductsHeader)).isDisplayed();
    }

    public void subscribe(String email) {
        // Fix: Explicitly find the element within the wait
        WebElement emailField = wait.until(d -> d.findElement(subscriptionEmailField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailField);
        emailField.sendKeys(email);
        driver.findElement(subscribeArrowButton).click();
    }

    public String getSubscriptionSuccessMessage() {
        return wait.until(d -> d.findElement(successMessage)).getText();
    }

    public boolean isAllProductsVisible() {
        // Fix: Use Lambda instead of ExpectedConditions
        return wait.until(d -> d.findElement(allProductsHeader)).isDisplayed();
    }

    public void viewFirstProductDetails() {
        WebElement viewBtn = wait.until(d -> d.findElement(viewProductFirst));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewBtn);
    }

    public boolean isProductDetailsVisible() {
        return wait.until(d -> d.findElement(productDetailsHeader)).isDisplayed();
    }
}