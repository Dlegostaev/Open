package ru.open.api.util;


import com.google.gson.Gson;
import ru.open.api.models.UnregisteredUser;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelper {
    public static String ReadFileAsString(String file)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (Exception e) {
            System.out.println("Exception"); //TODO handle exception
        }
        return null;    //TODO null?
    }

    public static UnregisteredUser GetUserFromJson(String filepath) {
        String json = ReadFileAsString(filepath);
        Gson gson = new Gson();
        return gson.fromJson(json, UnregisteredUser.class);
    }
}
