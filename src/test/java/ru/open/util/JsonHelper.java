package ru.open.util;

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
}
