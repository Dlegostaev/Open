package ru.open.api.util;

import com.google.gson.Gson;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UnregisteredUser;
import ru.open.util.TestException;

import static ru.open.util.JsonHelper.ReadFileAsString;

public class APIJson {
    public static UnregisteredUser GetUserFromJson(String filepath) throws TestException {
        String json = ReadFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, UnregisteredUser.class);
    }

    public static RequestConfig GetConfigFromJson(String filepath) throws TestException {
        String json = ReadFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, RequestConfig.class);
    }
}
