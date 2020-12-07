package ru.open.api;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UsersPostResponse;
import ru.open.api.util.APIJson;
import ru.open.util.TestException;

import static ru.open.api.steps.Steps.*;

public class GetTest {
    @Parameters("configPath")
    @Test
    public void TestMethodGet(String configPath) throws TestException {
        RequestConfig requestConfig = APIJson.GetConfigFromJson(configPath);
        // 1) получить список пользователей
        ValidatableResponse response = GetUsersAsResponse(requestConfig.getUrl(), requestConfig.getStatusCode());

        // 2) замапить на объект
        UsersPostResponse usersPostResponse = MapValidatableResponseToUsersPostResponse(response);

        // 3) проверить, что все поля пришли (достаточно на notNull)
        CheckAllValuesOfUsersPostResponse(usersPostResponse);
    }
}