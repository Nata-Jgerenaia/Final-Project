package API_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductApiTests {
    private final String BASE_URL = "https://automationexercise.com/api";

    @Test(description = "API 1: Get All Products List")
    public void testGetAllProducts() {
        Response response = RestAssured.get(BASE_URL + "/productsList");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 3: Get All Brands List")
    public void testGetAllBrands() {
        Response response = RestAssured.get(BASE_URL + "/brandsList");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 5: POST To Search Product")
    public void testSearchProductAPI() {
        Response response = RestAssured.given()
                .formParam("search_product", "tshirt")
                .post(BASE_URL + "/searchProduct");
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}