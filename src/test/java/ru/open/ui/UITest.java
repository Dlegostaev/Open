package ru.open.ui;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.ui.models.BrowserConfig;
import ru.open.ui.models.TestConfig;
import ru.open.ui.pages.google.GoogleSearchPage;
import ru.open.ui.pages.google.GoogleResultPage;
import ru.open.ui.pages.open.OpenMainPage;
import ru.open.util.TestException;

import static ru.open.ui.steps.Steps.*;
import static ru.open.ui.util.UIJson.GetBrowserConfigFromJson;
import static ru.open.ui.util.UIJson.GetTestConfigFromJson;

public class UITest {
    @Parameters({"configPath", "dataPath"})
    @Test
    public void TestMethod(String configPath, String dataPath) throws TestException {
        System.out.println("UITest");

        BrowserConfig browserConfig = GetBrowserConfigFromJson(configPath);

        TestConfig testConfig = GetTestConfigFromJson(dataPath);

//        System.out.println(testConfig.getStartPage());
//        System.out.println(testConfig.getSearchString());
//        System.out.println(testConfig.getGoalPage());
//        System.out.println(testConfig.getUsdName());
//        System.out.println(testConfig.getEurName());

        //запустить Chrome
        StartBrowser(browserConfig);

        //открыть https://www.google.com/
//        OpenUrl(testConfig.getStartPage());
//        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
//        googleSearchPage.WaitUntilPageIsLoaded();
        GoogleSearchPage googleSearchPage = OpenGoogle(testConfig.getStartPage());

        //написать в строке поиска «Открытие»
//        googleSearchPage.InputText(testConfig.getSearchString());
        GoogleInputSearchText(googleSearchPage, testConfig.getSearchString());

        //нажать Поиск
//        googleSearchPage.ClickSearchButton();
//        GoogleSearchResultPage googleSearchResultPage = new GoogleSearchResultPage();
        GoogleResultPage googleResultPage = GoogleClickSearchButton(googleSearchPage);

        //проверить, что результатах поиска есть https://www.open.ru
//        assert googleResultPage.IfUrlContains(testConfig.getGoalPage()): "URL of goal page is not present in results. URL: " + testConfig.getGoalPage();
        CheckSearchResultContainsUrl(googleResultPage, testConfig.getGoalPage());

        //перейти на сайт https://www.open.ru
//        googleResultPage.SelectResultWithUrl(testConfig.getGoalPage());
//        googleResultPage.OpenSelectedResult();
//        OpenMainPage openMainPage = new OpenMainPage();
//        openMainPage.WaitUntilPageIsLoaded();
        OpenMainPage openMainPage = ClickToOpenBankResult(googleResultPage, testConfig.getGoalPage());

        //проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.
//        String usdBuyRate = openMainPage.currenciesForm.GetCurrencyRateBankBuy(testConfig.getUsdName());
//        String usdSellRate = openMainPage.currenciesForm.GetCurrencyRateBankSell(testConfig.getUsdName());
//
//        String eurBuyRate = openMainPage.currenciesForm.GetCurrencyRateBankBuy(testConfig.getEurName());
//        String eurSellRate = openMainPage.currenciesForm.GetCurrencyRateBankSell(testConfig.getEurName());
//
//        System.out.println(usdBuyRate);
//        System.out.println(usdSellRate);
//
//        System.out.println(eurBuyRate);
//        System.out.println(eurSellRate);
//
//        float usdBuy = Float.parseFloat(usdBuyRate.replace(",", "."));
//        float usdSell = Float.parseFloat(usdSellRate.replace(",", "."));
//
//        float eurBuy = Float.parseFloat(eurBuyRate.replace(",", "."));
//        float eurSell = Float.parseFloat(eurSellRate.replace(",", "."));
//
//        assert usdSell > usdBuy: "Sell rate of USD less than buy rate of USD or equal to it";
//        assert eurSell > eurBuy: "Sell rate of USD less than buy rate of USD or equal to it";

        CheckCurrenciesSellBuyDelta(openMainPage, testConfig.getUsdName());
        CheckCurrenciesSellBuyDelta(openMainPage, testConfig.getEurName());
    }
}
