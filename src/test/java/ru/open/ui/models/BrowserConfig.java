package ru.open.ui.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BrowserConfig {
    @Getter
    @Setter
    String browserName;

    @Getter
    @Setter
    int timeout;
}
