package ru.open.ui.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TestConfig {
    @Getter
    @Setter
    String startPage;

    @Getter
    @Setter
    String searchString;

    @Getter
    @Setter
    String goalPage;

    @Getter
    @Setter
    String usdName;

    @Getter
    @Setter
    String eurName;
}
