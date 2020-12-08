package ru.open.ui.pages.google;

import ru.open.ui.models.WebsiteData;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchPage implements WebPage {
    String uniqueImageLocator = "#hplogo";
    String searchFieldLocator = "[name=q]";
    String searchButtonLocator = "[name=btnK]";

    public GoogleSearchPage waitUntilPageIsLoaded() {
        element(uniqueImageLocator).shouldBe(visible);
        element(uniqueImageLocator).shouldBe(enabled);
        return this;
    }

    public GoogleSearchPage inputText(String text) {
        element(searchFieldLocator).sendKeys(text);
        return this;
    }

    public GoogleSearchPage sumbitSearch() {
        element(searchFieldLocator).submit();
        return this;
    }

    public GoogleSearchPage clickSearchButton() {
        element(searchButtonLocator).click();
        return this;
    }
}
