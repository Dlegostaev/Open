package ru.open.ui.forms;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchResultForm {
    //String ads = "#tvcap .uEierd";
/*
    String noAds = "#search .g";
    String addressDomain = "#search .g cite";
    String addressFull = "#search .g .yuRUbf > a";
    String title = "#search .g a > h3 > span";
    String description = "#search .g .aCOpRe> span";
*/
    SelenideElement url;
    SelenideElement title;
    SelenideElement description;

    public GoogleSearchResultForm(SelenideElement url, SelenideElement title, SelenideElement description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public String GetURL() {
        return url.getAttribute("href");
    }

    public String GetTitle() {
        return title.getText();
    }

    public String GetDescription() {
        return description.getText();
    }

    public void ClickOnURL() {
        url.click();
    }

    public void ClickOnTitle() {
        title.click();
    }

    public void ClickOnDescription() {
        description.click();
    }
}
