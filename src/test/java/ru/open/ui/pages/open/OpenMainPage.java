package ru.open.ui.pages.open;

import ru.open.ui.forms.OpenCurrenciesForm;
import ru.open.ui.pages.google.WebPage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class OpenMainPage implements WebPage {
    String internetBankButtonLocator = ".main-page-header__sub-nav-internet-bank-button";

    public OpenCurrenciesForm currenciesForm;

    public OpenMainPage WaitUntilPageIsLoaded() {
        element(internetBankButtonLocator).shouldBe(visible);
        element(internetBankButtonLocator).shouldBe(enabled);
        return this;
    }

    public OpenMainPage() {
        currenciesForm = new OpenCurrenciesForm();
    }
}
