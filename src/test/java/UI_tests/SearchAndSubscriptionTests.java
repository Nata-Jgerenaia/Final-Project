package UI_tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductPage;

public class SearchAndSubscriptionTests extends BaseTest {

    @Test(priority = 1)
    public void testSearchProduct_TC9() {
        ProductPage productPage = new ProductPage(driver);
        productPage.navigateToAllProducts();
        productPage.searchForProduct("blue top");
        Assert.assertTrue(productPage.isSearchedProductsVisible());
    }

    @Test(priority = 2)
    public void testSubscriptionHome_TC10() {
        ProductPage productPage = new ProductPage(driver);
        productPage.subscribe("test@email.com");
        Assert.assertEquals(productPage.getSubscriptionSuccessMessage(), "You have been successfully subscribed!");
    }

    @Test(priority = 3)
    public void testSubscriptionCart_TC11() {
        ProductPage productPage = new ProductPage(driver);
        productPage.navigateToCart();
        productPage.subscribe("test@email.com");
        Assert.assertEquals(productPage.getSubscriptionSuccessMessage(), "You have been successfully subscribed!");
    }
}