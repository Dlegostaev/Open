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
        System.out.println("APITestPost");

        //String configPath = "src/test/java/ru/open/api/data/postData.json";
        RequestConfig requestConfig = GetConfigFromJson(configPath);

        UnregisteredUser userReference = GetUserFromJson(userFilePath);
        System.out.println(userReference.name);
        System.out.println(userReference.job);
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
