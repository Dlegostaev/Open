package ru.open.api.steps;

import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import ru.open.api.models.RegisteredUser;
import ru.open.api.models.UnregisteredUser;
import ru.open.api.models.UsersPostResponse;

import java.io.File;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Steps {
    public static ValidatableResponse getUsersAsResponse(String url, int statusCode) {
        return get(url)
                .then()
                .statusCode(statusCode);
    }

    public static UsersPostResponse mapValidatableResponseToUsersPostResponse(ValidatableResponse response) {
        return response.extract().as(UsersPostResponse.class);
    }

    public static void checkAllValuesOfUsersPostResponse(UsersPostResponse usersPostResponse) {
        // if "int" field is empty in response, extract() to UsersPostResponse model will set this field to 0
        // so, this asserts are correct only if 0 can't return as acceptable value
        Assert.assertNotEquals(usersPostResponse.getPage(), 0, "Response page is 0");
        Assert.assertNotEquals(usersPostResponse.getPer_page(), 0, "Response per_page is 0");
        Assert.assertNotEquals(usersPostResponse.getTotal(), 0, "Response total is 0");
        Assert.assertNotEquals(usersPostResponse.getTotal_pages(), 0, "Response total_pages is 0");
        Assert.assertNotNull(usersPostResponse.getData(), "Response data is null");

        Assert.assertEquals(usersPostResponse.getPer_page(), usersPostResponse.getData().length,
                "Response users number doesn't match with per_page number");
        for (int i = 0; i < usersPostResponse.getData().length; i++) {
            RegisteredUser user = usersPostResponse.getData()[i];
            Assert.assertNotEquals(user.getId(), 0, "User \" + i + \" id is 0");
            Assert.assertNotNull(user.getEmail(), "User \" + i + \" email is null");
            Assert.assertNotNull(user.getFirst_name(), "User \" + i + \" first_name is null");
            Assert.assertNotNull(user.getLast_name(), "User \" + i + \" last_name is null");
            Assert.assertNotNull(user.getAvatar(), "User \" + i + \" avatar is null");
        }
    }

    public static File prepareRequestBody(String userFilePath) {
        return new File(userFilePath);
    }

    public static ValidatableResponse postUsersWithBody(String contentType, File testUser, String url, int statusCode) {
        return given()
                .contentType(contentType)
                .body(testUser)
                .when()
                .post(url)
                .then()
                .statusCode(statusCode);
    }

    public static UnregisteredUser mapValidatableResponseToUnregisteredUser(ValidatableResponse response) {
        return response.extract().as(UnregisteredUser.class);
    }

    public static void checkValuesBetweenResponseAndReference(UnregisteredUser userResult, UnregisteredUser userReference) {
        Assert.assertEquals(userResult.getName(), userReference.getName(), "User name doesn't match the reference");
        Assert.assertEquals(userResult.getJob(), userReference.getJob(), "User job doesn't match the reference");
    }
}
