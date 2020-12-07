package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UsersPostResponse {
    @Getter
    @Setter
    int page;

    @Getter
    @Setter
    int per_page;

    @Getter
    @Setter
    int total;

    @Getter
    @Setter
    int total_pages;

    @Getter
    @Setter
    RegisteredUser[] data;
}
