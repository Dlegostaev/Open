package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RegisteredUser {
    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    String email;

    @Getter
    @Setter
    String first_name;

    @Getter
    @Setter
    String last_name;

    @Getter
    @Setter
    String avatar;
}
