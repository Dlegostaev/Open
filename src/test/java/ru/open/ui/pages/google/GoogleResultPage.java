package ru.open.ui.pages.google;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.open.ui.forms.GoogleSearchResultForm;
import ru.open.util.TestException;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.elements;

public class GoogleResultPage implements WebPage {
    String searchLocator = "#search .g";    // only non-ads results are supported
    String urlLocator = ".yuRUbf > a";
    String titleLocator = "a > h3 > span";
    String descriptionLocator = ".aCOpRe> span";

    ArrayList<GoogleSearchResultForm> resultsCollection = new ArrayList<>();
    GoogleSearchResultForm selectedResult = null;

    public GoogleResultPage() {
        this.initSearchForms();
    }

    public void initSearchForms() {
        ElementsCollection buttons = elements(searchLocator);

        for (SelenideElement button : buttons) {
            GoogleSearchResultForm searchResult = new GoogleSearchResultForm(button.find(urlLocator),
                    button.find(titleLocator),
                    button.find(descriptionLocator));
            resultsCollection.add(searchResult);
        }
    }

    public int numberOfSearchWithURLEquals(String url) {
        for (int i = 0; i < resultsCollection.size(); i++) {
            if (resultsCollection.get(i).getURL().equals(url)) {
                return i;
            }
        }
        return -1;
    }

    public int numberOfSearchWithURLContains(String url) {
        for (int i = 0; i < resultsCollection.size(); i++) {
            if (resultsCollection.get(i).getURL().contains(url)) {
                return i;
            }
        }
        return -1;
    }

    public boolean ifUrlEquals(String url) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.getURL().equals(url)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifUrlContains(String url) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.getURL().contains(url)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifExactTitlePresent(String title) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifExactDescriptionPresent(String description) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }

    public GoogleResultPage selectResultWithUrl(String url) throws TestException {
        int resultNumber = numberOfSearchWithURLContains(url);
        if (resultNumber == -1) {
            throw new TestException("Can't select result with URL, because it doesn't present in results. URL: " + url);
        } else {
            selectedResult = resultsCollection.get(resultNumber);
            return this;
        }
    }

    public GoogleResultPage openSelectedResult() throws TestException {
        if (selectedResult == null) {
            throw new TestException("Try to open result, where none of them selected");
        } else {
            selectedResult.clickOnURL();
            return this;
        }
    }
}
