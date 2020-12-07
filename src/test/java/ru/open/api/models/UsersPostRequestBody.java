package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UsersPostRequestBody {
    @Getter
    @Setter
    String postBody;

    @Getter
    @Setter
    String contentType;

    @Getter
    @Setter
    String url;

    @Getter
    @Setter
    int statusCode;
}
