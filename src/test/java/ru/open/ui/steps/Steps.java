package ru.open.ui.steps;

import com.codeborne.selenide.Configuration;
import org.testng.Assert;
import ru.open.ui.models.BrowserConfig;
import ru.open.ui.pages.google.GoogleSearchPage;
import ru.open.ui.pages.google.GoogleResultPage;
import ru.open.ui.pages.open.OpenMainPage;
import ru.open.util.TestException;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class Steps {
    public static void startBrowser(BrowserConfig browserConfig) {
        Configuration.browser = browserConfig.getBrowserName();
        Configuration.timeout = browserConfig.getTimeout();
        open();
    }

    public static void closeBrowser() {
        closeWebDriver();
    }

    public static void openUrl(String url) {
        open(url);
    }

    public static GoogleSearchPage openGoogle(String url) {
        openUrl(url);
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        return googleSearchPage.waitUntilPageIsLoaded();
    }

    public static GoogleSearchPage googleInputSearchText(GoogleSearchPage googleSearchPage, String text) {
        return googleSearchPage.inputText(text);
    }

    public static GoogleResultPage googleClickSearchButton(GoogleSearchPage googleSearchPage) {
        googleSearchPage.clickSearchButton();
        return new GoogleResultPage();
    }

    public static GoogleResultPage checkSearchResultContainsUrl(GoogleResultPage googleResultPage, String url) {
        Assert.assertTrue(googleResultPage.ifUrlContains(url), "URL of goal page is not present in results. URL: " + url);
        return googleResultPage;
    }

    public static OpenMainPage clickToOpenBankResult(GoogleResultPage googleResultPage, String goalPage) throws TestException {
        googleResultPage.selectResultWithUrl(goalPage);
        googleResultPage.openSelectedResult();
        OpenMainPage openMainPage = new OpenMainPage();
        return openMainPage.waitUntilPageIsLoaded();
    }

    public static void checkCurrenciesSellBuyDelta(OpenMainPage openMainPage, String currency) throws TestException {
        float currencyBuyRate = openMainPage.currenciesForm.getCurrencyRateBankBuyAsFloat(currency);
        float currencySellRate = openMainPage.currenciesForm.getCurrencyRateBankSellAsFloat(currency);

        Assert.assertTrue(currencySellRate > currencyBuyRate,
                "Sell rate of " + currency + " less than buy rate of " + currency + " or equal to it");
    }
}
