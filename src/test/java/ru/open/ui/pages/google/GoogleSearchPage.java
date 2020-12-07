package ru.open.ui.pages.google;

import ru.open.ui.models.WebsiteData;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchPage implements WebPage {
    String uniqueImageLocator = "#hplogo";
    String searchFieldLocator = "[name=q]";
    String searchButtonLocator = "[name=btnK]";

    public GoogleSearchPage WaitUntilPageIsLoaded() {
        element(uniqueImageLocator).shouldBe(visible);
        element(uniqueImageLocator).shouldBe(enabled);
        return this;
    }

    public GoogleSearchPage InputText(String text) {
        element(searchFieldLocator).sendKeys(text);
        return this;
    }

    public GoogleSearchPage SumbitSearch() {
        element(searchFieldLocator).submit();
        return this;
    }

    public GoogleSearchPage ClickSearchButton() {
        element(searchButtonLocator).click();
        return this;
    }
}
