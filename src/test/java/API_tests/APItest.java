package API_tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class APItest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test
    public void testGetAllProductsList_API1() {
        Response response = get("/productsList");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("products"), "Body should contain products");

    }

    @Test
    public void testPostToAllProductsList_API2() {
        Response response = post("/productsList");
        JsonPath json = response.jsonPath();

        Assert.assertEquals(json.get("responseCode"), (Integer) 405);
        Assert.assertEquals(json.get("message"), "This request method is not supported.");
    }

    @Test
    public void testGetAllBrandsList_API3() {
        Response response = get("/brandsList");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("brands"), "Body should contain brands");
    }

    @Test
    public void testPutToAllBrandsList_API4() {
        Response response = put("/brandsList");
        JsonPath json = response.jsonPath();

        Assert.assertEquals(json.get("responseCode"), (Integer) 405);
        // FIXED: Added a dot at the end of the string
        Assert.assertEquals(json.get("message"), "This request method is not supported.");
    }
}