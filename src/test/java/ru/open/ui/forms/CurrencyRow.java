package ru.open.ui.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CurrencyRow {
    @Getter
    @Setter
    String currencyName;

    @Getter
    @Setter
    String buyRate;

    @Getter
    @Setter
    String sellRate;
}
