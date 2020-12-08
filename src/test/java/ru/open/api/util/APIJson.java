package ru.open.api.util;

import com.google.gson.Gson;
import ru.open.api.models.RequestConfig;
import ru.open.api.models.UnregisteredUser;
import ru.open.util.TestException;

import static ru.open.util.JsonHelper.readFileAsString;

public class APIJson {
    public static UnregisteredUser getUserFromJson(String filepath) throws TestException {
        String json = readFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, UnregisteredUser.class);
    }

    public static RequestConfig getConfigFromJson(String filepath) throws TestException {
        String json = readFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, RequestConfig.class);
    }
}
