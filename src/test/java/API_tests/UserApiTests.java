package API_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.restassured.AllureRestAssured; // Import for the filter
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test(description = "API 12: DELETE User Account")
    public void testDeleteAccount_API12() {
        Response response = given()
                .filter(new AllureRestAssured()) // This adds the Request/Response to Allure
                .formParam("email", "testuser_gemini@example.com")
                .formParam("password", "password123")
                .delete("/deleteAccount");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 13: PUT Update User Account")
    public void testUpdateAccount_API13() {
        Response response = given()
                .filter(new AllureRestAssured()) // Requirement fulfilled here too
                .formParam("name", "Updated Name")
                .formParam("email", "testuser_gemini@example.com")
                .formParam("password", "password123")
                .put("/updateAccount");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "API 14: GET User Detail by Email")
    public void testGetUserDetail_API14() {
        Response response = given()
                .filter(new AllureRestAssured()) // Requirement fulfilled here too
                .queryParam("email", "testuser_gemini@example.com")
                .get("/getUserDetailByEmail");

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}