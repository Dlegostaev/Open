package ru.open.api;

import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UsersPostResponse;

import static io.restassured.RestAssured.*;
import static ru.open.api.util.JsonHelper.GetConfigFromJson;

public class GetTest {
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
}
