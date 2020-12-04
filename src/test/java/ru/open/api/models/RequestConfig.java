package ru.open.api.models;

public class RequestConfig {
    public String url;
    //public String userFilePath;
    public String contentType;
    public int statusCode;

    //public RequestConfig(String url, String userFilePath, String contentType, int statusCode) {
    public RequestConfig(String url, String contentType, int statusCode) {
        this.url = url;
        //this.userFilePath = userFilePath;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }
}
