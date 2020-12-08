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

    public String getURL() {
        return url.getAttribute("href");
    }

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public void clickOnURL() {
        url.click();
    }

    public void clickOnTitle() {
        title.click();
    }

    public void clickOnDescription() {
        description.click();
    }
}
