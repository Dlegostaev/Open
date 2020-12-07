package ru.open.api;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UnregisteredUser;
import ru.open.util.TestException;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.open.api.util.APIJson.GetConfigFromJson;
import static ru.open.api.util.APIJson.GetUserFromJson;

public class PostTest {
    @Parameters({"configPath", "userFilePath"})
    @Test
    public void TestMethodPost(String configPath, String userFilePath) throws TestException {
        RequestConfig requestConfig = GetConfigFromJson(configPath);

        UnregisteredUser userReference = GetUserFromJson(userFilePath);

        // 1) подготовить тело запроса
        File testUser = new File(userFilePath);
        // 2) отправить запрос с телом
        UnregisteredUser userResult = given()
                .contentType(requestConfig.getContentType())
                .body(testUser)
                .when()
                .post(requestConfig.getUrl())
                .then()
                .statusCode(requestConfig.getStatusCode())
                // 3) замапить ответ на объект
                .extract()
                .as(UnregisteredUser.class);

        // 4) проверить, что в ответе те же самые значения из запроса
        assert userResult.name.equals(userReference.name) : "User name doesn't match the reference";
        assert userResult.job.equals(userReference.job) : "User job doesn't match the reference";
    }
}
