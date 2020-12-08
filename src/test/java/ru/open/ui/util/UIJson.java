package ru.open.ui.util;

import com.google.gson.Gson;
import ru.open.ui.models.BrowserConfig;
import ru.open.ui.models.TestConfig;
import ru.open.ui.models.WebsiteData;
import ru.open.util.TestException;

import static ru.open.util.JsonHelper.readFileAsString;

public class UIJson {
    public static BrowserConfig getBrowserConfigFromJson(String filepath) throws TestException {
        String json = readFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, BrowserConfig.class);
    }

    public static TestConfig getTestConfigFromJson(String filepath) throws TestException {
        String json = readFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, TestConfig.class);
    }

    public static WebsiteData getWebsiteDataFromJson(String filepath) throws TestException {
        String json = readFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, WebsiteData.class);
    }
}
