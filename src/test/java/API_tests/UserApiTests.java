package API_tests; // This must be the very first line

// --- 1. THE IMPORTS GO HERE (This fixes the red errors) ---
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class UserApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test(description = "API 12: DELETE User Account")
    public void testDeleteAccount_API12() {
        // This is where you write the Map logic
        Map<String, String> formData = new HashMap<>();
        formData.put("email", "testuser_gemini@example.com");
        formData.put("password", "password123");

        Response response = given()
                .formParams(formData)
                .when()
                .delete("/deleteAccount");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 13: PUT Update User Account")
    public void testUpdateAccount_API13() {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "Updated Student Name");
        formData.put("email", "testuser_gemini@example.com");
        formData.put("password", "password123");

        Response response = given()
                .formParams(formData)
                .when()
                .put("/updateAccount");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 14: GET User Detail by Email")
    public void testGetUserDetail_API14() {
        // queryParam is used directly here
        Response response = given()
                .queryParam("email", "testuser_gemini@example.com")
                .when()
                .get("/getUserDetailByEmail");

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}