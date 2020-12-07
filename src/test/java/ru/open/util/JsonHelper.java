package ru.open.util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHelper {
    public static String ReadFileAsString(String filepath) throws TestException {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (Exception e) {
            throw new TestException("Can't read file from path: " + filepath);
        }
    }
}
