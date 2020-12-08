package ru.open.ui.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class WebsiteData {
    @Getter
    @Setter
    String buy;

    @Getter
    @Setter
    String sell;
}
