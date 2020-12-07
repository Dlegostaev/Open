package ru.open.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UnregisteredUser {
    @Getter
    @Setter
    public String name;

    @Getter
    @Setter
    public String job;

    @Getter
    @Setter
    public int id;

    @Getter
    @Setter
    public String createdAt;
}
