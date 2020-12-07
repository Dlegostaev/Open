package ru.open.ui.steps;

import com.codeborne.selenide.Configuration;
import ru.open.ui.models.BrowserConfig;
import ru.open.ui.pages.google.GoogleSearchPage;
import ru.open.ui.pages.google.GoogleResultPage;
import ru.open.ui.pages.open.OpenMainPage;
import ru.open.util.TestException;

import static com.codeborne.selenide.Selenide.open;

public class Steps {
    public static void StartBrowser(BrowserConfig browserConfig) {
        Configuration.browser = browserConfig.getBrowserName();
        Configuration.timeout = browserConfig.getTimeout();
        open();
    }

    public static void OpenUrl(String url) {
        open(url);
    }

    public static GoogleSearchPage OpenGoogle(String url) {
        OpenUrl(url);
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        return googleSearchPage.WaitUntilPageIsLoaded();
    }

    public static GoogleSearchPage GoogleInputSearchText(GoogleSearchPage googleSearchPage, String text) {
        return googleSearchPage.InputText(text);
    }

    public static GoogleResultPage GoogleClickSearchButton(GoogleSearchPage googleSearchPage) {
        googleSearchPage.ClickSearchButton();
        return new GoogleResultPage();
    }

    public static GoogleResultPage CheckSearchResultContainsUrl(GoogleResultPage googleResultPage, String url) {
        assert googleResultPage.IfUrlContains(url): "URL of goal page is not present in results. URL: " + url;
        return googleResultPage;
    }

    public static OpenMainPage ClickToOpenBankResult(GoogleResultPage googleResultPage, String goalPage) throws TestException {
        googleResultPage.SelectResultWithUrl(goalPage);
        googleResultPage.OpenSelectedResult();
        OpenMainPage openMainPage = new OpenMainPage();
        return openMainPage.WaitUntilPageIsLoaded();
    }

    public static void CheckCurrenciesSellBuyDelta(OpenMainPage openMainPage, String currency) throws TestException {
        String currencyBuyRate = openMainPage.currenciesForm.GetCurrencyRateBankBuy(currency);
        String currencySellRate = openMainPage.currenciesForm.GetCurrencyRateBankSell(currency);

        float currencyBuy = Float.parseFloat(currencyBuyRate.replace(",", "."));
        float currencySell = Float.parseFloat(currencySellRate.replace(",", "."));

        assert currencySell > currencyBuy: "Sell rate of" + currency + " less than buy rate of " + currency + " or equal to it";
    }
}
