package ru.open.api.models;

public class UsersPostRequestBody {
    String postBody;
    String contentType;
    String url;
    int statusCode;

    public UsersPostRequestBody(String postBody, String contentType, String url, int statusCode) {
        this.postBody = postBody;
        this.contentType = contentType;
        this.url = url;
        this.statusCode = statusCode;
    }
}
