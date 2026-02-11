package API_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductApiTests {
    private final String BASE_URL = "https://automationexercise.com/api";

    @Test(priority = 1, description = "API 5: POST To Search Product")
    public void testSearchProductWithParameter() {
        Response response = RestAssured.given()
                .formParam("search_product", "tshirt")
                .post(BASE_URL + "/searchProduct");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("products"));

    }

    @Test(priority = 2, description = "API 6: POST To Search Product without parameter")
    public void testSearchProductWithoutParameter() {
        Response response = RestAssured.given()
                .post(BASE_URL + "/searchProduct");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("Bad request"));

    }

    @Test(priority = 3, description = "API 9: DELETE To Verify Login")
    public void testDeleteVerifyLogin() {
        // We use .delete() instead of .post()
        Response response = RestAssured.given()
                .delete(BASE_URL + "/verifyLogin");

        // Print the response so you can see the message
        System.out.println("API 9 Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        // The API should tell you that DELETE is not supported for this URL
        Assert.assertTrue(response.getBody().asString().contains("This request method is not supported."),
                "Response message should indicate method not supported");

    }
}