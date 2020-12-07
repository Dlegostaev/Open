package ru.open.api;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UnregisteredUser;
import ru.open.util.TestException;

import java.io.File;

import static ru.open.api.steps.Steps.*;
import static ru.open.api.util.APIJson.GetConfigFromJson;
import static ru.open.api.util.APIJson.GetUserFromJson;

public class PostTest {
    @Parameters({"configPath", "userFilePath"})
    @Test
    public void TestMethodPost(String configPath, String userFilePath) throws TestException {
        RequestConfig requestConfig = GetConfigFromJson(configPath);
        UnregisteredUser userReference = GetUserFromJson(userFilePath);

        // 1) подготовить тело запроса
        File testUser = PrepareRequestBody(userFilePath);

        // 2) отправить запрос с телом
        ValidatableResponse response = PostUsersWithBody(requestConfig.getContentType(), testUser,
                requestConfig.getUrl(), requestConfig.getStatusCode());

        // 3) замапить ответ на объект
        UnregisteredUser userResult = MapValidatableResponseToUnregisteredUser(response);

        // 4) проверить, что в ответе те же самые значения из запроса
        CheckValuesBetweenResponseAndReference(userResult, userReference);
    }
}
