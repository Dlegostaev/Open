package ru.open.api.steps;

import io.restassured.response.ValidatableResponse;
import ru.open.api.models.RegisteredUser;
import ru.open.api.models.UsersPostResponse;

import static io.restassured.RestAssured.get;

public class Steps {
    public static ValidatableResponse GetUsersAsResponse(String url, int statusCode) {
        return get(url)
                .then()
                .assertThat()
                .statusCode(statusCode);
    }

    public static UsersPostResponse MapValidatableResponseToUsersPostResponse(ValidatableResponse response) {
        return response.extract().as(UsersPostResponse.class);
    }

    public static void CheckAllValuesOfUsersPostResponse(UsersPostResponse usersPostResponse) {
        // if "int" field is empty in response, extract() to UsersPostResponse model will set this field to 0
        // so, this asserts are correct only if 0 can't return as acceptable value
        assert usersPostResponse.getPage() != 0 : "Response page is 0";
        assert usersPostResponse.getPer_page() != 0 : "Response page is 0";
        assert usersPostResponse.getTotal() != 0 : "Response page is 0";
        assert usersPostResponse.getTotal_pages() != 0 : "Response page is 0";
        assert usersPostResponse.getData() != null : "Response data is null";

        assert usersPostResponse.getPer_page() == usersPostResponse.getData().length : "Response users number doesn't match with per_page nubmer";
        for (int i = 0; i < usersPostResponse.getData().length; i++) {
            RegisteredUser user = usersPostResponse.getData()[i];
            assert user.getId() != 0 : "User " + i + " id is 0";
            assert user.getEmail() != null : "User " + i + " email is null";
            assert user.getFirst_name() != null : "User " + i + " first_name is null";
            assert user.getLast_name() != null : "User " + i + " last_name is null";
            assert user.getAvatar() != null : "User " + i + " avatar is null";
        }
    }
}
