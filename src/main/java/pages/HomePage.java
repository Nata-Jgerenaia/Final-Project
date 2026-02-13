package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    private final By signupLoginBtn = By.xpath("//a[contains(text(), 'Signup / Login')]");
    private final By homeVisibleElement = By.xpath("//i[@class='fa fa-home']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public boolean isHomePageVisible() {
        return driver.findElement(homeVisibleElement).isDisplayed();
    }

    public void clickSignupLogin() {
        driver.findElement(signupLoginBtn).click();
    }
}
