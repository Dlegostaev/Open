package ru.open.api;

import org.apache.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UsersPostResponse;
import ru.open.api.models.UnregisteredUser;

import java.io.File;

import static io.restassured.RestAssured.*;
import static ru.open.api.util.JsonHelper.GetConfigFromJson;
import static ru.open.api.util.JsonHelper.GetUserFromJson;

public class APITest {
    @Test
    public void TestMethodGet() {
        System.out.println("APITestGet");

        String configPath = "src/test/java/ru/open/api/data/getData.json";
        RequestConfig requestConfig = GetConfigFromJson(configPath);

        UsersPostResponse usersPostResponse = get(requestConfig.url)    // 1) получить список пользователей
                .then()
                .assertThat()
                .statusCode(requestConfig.statusCode)
                .extract()  // 2) замапить на объект
                .as(UsersPostResponse.class);

        // 3) проверить, что все поля пришли (достаточно на notNull)
        // TODO if "int" field is empty in response, this field will be set to 0
        //  (in theory, correct data can also be 0, so this asserts can be wrong)
        assert usersPostResponse.page != 0: "response page is 0";
        assert usersPostResponse.per_page != 0: "response page is 0";
        assert usersPostResponse.total != 0: "response page is 0";
        assert usersPostResponse.total_pages != 0: "response page is 0";
        assert usersPostResponse.data != null: "response data is null";

        //TODO optional asserts (remove?)
//        assert response.per_page == response.data.length: "response users number doesn't match with per_page nubmer";
//        for (int i = 0; i < response.data.length; i++) {
//            RegisteredUser user = response.data[i];
//            assert user.id != 0: "user " + i + " id is 0";
//            assert user.email != null: "user " + i + " email is null";
//            assert user.first_name != null: "user " + i + " first_name is null";
//            assert user.last_name != null: "user " + i + " last_name is null";
//            assert user.avatar != null: "user " + i + " avatar is null";
//        }
    }

    // Ability to run as DDT
    //TODO add dataprovider: number of users configs = number of test runs
    @DataProvider(name = "data-provider")
    public Object[][] dataProviderMethod() {
        return new Object[][] {
                { "src/test/java/ru/open/api/data/users/testUser.json" },
                { "src/test/java/ru/open/api/data/users/testUser2.json" }
        };
    }

    @Test(dataProvider = "data-provider")
    public void TestMethodPost(String userFilePath) {
        System.out.println("APITestPost");

        String configPath = "src/test/java/ru/open/api/data/postData.json";
        RequestConfig requestConfig = GetConfigFromJson(configPath);

        UnregisteredUser userReference = GetUserFromJson(userFilePath);
        // 1) подготовить тело запроса
        File testUser = new File(userFilePath);
        // 2) отправить запрос с телом
        UnregisteredUser userResult = given()
                .contentType(requestConfig.contentType)
                .body(testUser)
                .when()
                .post(requestConfig.url)
                .then()
                .statusCode(requestConfig.statusCode)
                // 3) замапить ответ на объект
                .extract()
                .as(UnregisteredUser.class);

        // 4) проверить, что в ответе те же самые значения из запроса
        assert userResult.name.equals(userReference.name): "user name doesn't match the reference";
        assert userResult.job.equals(userReference.job): "user job doesn't match the reference";

        //TODO remove
        //assert userResult.id.equals(userReference.id): "user id doesn't match the reference";
        //assert userResult.id == userReference.id: "user id doesn't match the reference";
        //assert userResult.createdAt.equals(userReference.createdAt): "createdAt job doesn't match the reference";
    }



// TODO remove

//    public <T> T GetObjectFromJson(String json) {
//        Gson gson = new Gson();
//        T object = gson.fromJson(json, T.class);
//        System.out.println(json);
//        System.out.println(object.toString());
//        return object;
//    }

//    public static Response GetResponseFromJson(String json) {
//        Gson gson = new Gson();
//        Response response = gson.fromJson(json, Response.class);
//        System.out.println(json);
//        System.out.println(response.toString());
//        return response;
//    }
}
