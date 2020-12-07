package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RequestConfig {
    @Getter
    @Setter
    String url;

    @Getter
    @Setter
    String contentType;

    @Getter
    @Setter
    int statusCode;
}
