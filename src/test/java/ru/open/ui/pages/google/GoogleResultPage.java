package ru.open.ui.pages.google;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.open.ui.forms.GoogleSearchResultForm;
import ru.open.util.TestException;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.elements;

public class GoogleResultPage implements WebPage{
    String searchLocator = "#search .g";    // only non-ads results are supported
    String urlLocator = ".yuRUbf > a";
    String titleLocator = "a > h3 > span";
    String descriptionLocator = ".aCOpRe> span";

    ArrayList<GoogleSearchResultForm> resultsCollection = new ArrayList<GoogleSearchResultForm>();
    GoogleSearchResultForm selectedResult = null;

    public GoogleResultPage() {
        this.InitSearchForms();
    }

    public void InitSearchForms() {
        ElementsCollection buttons = elements(searchLocator);

        for (SelenideElement button : buttons) {
            GoogleSearchResultForm searchResult = new GoogleSearchResultForm(button.find(urlLocator),
                    button.find(titleLocator),
                    button.find(descriptionLocator));
            resultsCollection.add(searchResult);
        }
    }

    public int NumberOfSearchWithURLEquals(String url) {
        for (int i = 0; i < resultsCollection.size(); i++) {
            if (resultsCollection.get(i).GetURL().equals(url)) {
                return i;
            }
        }
        return -1;
    }

    public int NumberOfSearchWithURLContains(String url) {
        for (int i = 0; i < resultsCollection.size(); i++) {
            if (resultsCollection.get(i).GetURL().contains(url)) {
                return i;
            }
        }
        return -1;
    }

    public boolean IfUrlEquals(String url) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.GetURL().equals(url)) {
                return true;
            }
        }
        return false;
    }

    public boolean IfUrlContains(String url) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.GetURL().contains(url)) {
                return true;
            }
        }
        return false;
    }

    public boolean IfExactTitlePresent(String title) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.GetTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean IfExactDescriptionPresent(String description) {
        for (GoogleSearchResultForm resultForm : resultsCollection) {
            if (resultForm.GetDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }

    public GoogleResultPage SelectResultWithUrl(String url) throws TestException {
        int resultNumber = NumberOfSearchWithURLContains(url);
        if (resultNumber == -1) {
            throw new TestException("Can't select result with URL, because it doesn't present in results. URL: " + url);
        } else {
            selectedResult = resultsCollection.get(resultNumber);
            return this;
        }
    }

    public GoogleResultPage OpenSelectedResult() throws TestException {
        if (selectedResult == null) {
            throw new TestException("Try to open result, where none of them selected");
        } else {
            selectedResult.ClickOnURL();
            return this;
        }
    }
}
