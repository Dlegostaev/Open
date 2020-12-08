package ru.open.api;

import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UnregisteredUser;
import ru.open.util.TestException;

import java.io.File;

import static ru.open.api.steps.Steps.*;
import static ru.open.api.util.APIJson.getConfigFromJson;
import static ru.open.api.util.APIJson.getUserFromJson;

public class PostTest {
    @Parameters({"configPath", "userFilePath"})
    @Test
    public void testMethodPost(String configPath, String userFilePath) throws TestException {
        RequestConfig requestConfig = getConfigFromJson(configPath);
        UnregisteredUser userReference = getUserFromJson(userFilePath);
        Logger logger = LoggerFactory.getLogger("OpenApiLogger");

        logger.info("Prepare body of request");
        File testUser = prepareRequestBody(userFilePath);

        logger.info("Send request with prepared body");
        ValidatableResponse response = postUsersWithBody(requestConfig.getContentType(), testUser,
                requestConfig.getUrl(), requestConfig.getStatusCode());

        logger.info("Map response to java object");
        UnregisteredUser userResult = mapValidatableResponseToUnregisteredUser(response);

        logger.info("Check that response user fields match reference user fields");
        checkValuesBetweenResponseAndReference(userResult, userReference);
    }
}
