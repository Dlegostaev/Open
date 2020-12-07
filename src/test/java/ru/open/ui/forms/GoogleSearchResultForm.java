package ru.open.ui.forms;

import com.codeborne.selenide.SelenideElement;

public class GoogleSearchResultForm {
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
