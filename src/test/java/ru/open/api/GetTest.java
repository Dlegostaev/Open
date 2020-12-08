package ru.open.api;

import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void testMethodGet(String configPath) throws TestException {
        RequestConfig requestConfig = APIJson.getConfigFromJson(configPath);
        Logger logger = LoggerFactory.getLogger("OpenApiLogger");

        logger.info("Get users list as response");
        ValidatableResponse response = getUsersAsResponse(requestConfig.getUrl(), requestConfig.getStatusCode());

        logger.info("Map response to java object");
        UsersPostResponse usersPostResponse = mapValidatableResponseToUsersPostResponse(response);

        logger.info("Check that all fields are not null");
        checkAllValuesOfUsersPostResponse(usersPostResponse);
    }
}