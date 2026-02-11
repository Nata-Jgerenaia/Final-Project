package UI_tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class ExampleTest extends BaseTest {

    @Test
    public void testHomePageVisibility() {
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible!");

        homePage.clickSignupLogin();
    }
}