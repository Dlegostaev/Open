package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UnregisteredUser {
    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String job;

    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    String createdAt;
}
