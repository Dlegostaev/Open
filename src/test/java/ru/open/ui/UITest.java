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
        BrowserConfig browserConfig = GetBrowserConfigFromJson(configPath);
        TestConfig testConfig = GetTestConfigFromJson(dataPath);

        //запустить Chrome
        StartBrowser(browserConfig);

        //открыть https://www.google.com/
        GoogleSearchPage googleSearchPage = OpenGoogle(testConfig.getStartPage());

        //написать в строке поиска «Открытие»
        GoogleInputSearchText(googleSearchPage, testConfig.getSearchString());

        //нажать Поиск
        GoogleResultPage googleResultPage = GoogleClickSearchButton(googleSearchPage);

        //проверить, что результатах поиска есть https://www.open.ru
        CheckSearchResultContainsUrl(googleResultPage, testConfig.getGoalPage());

        //перейти на сайт https://www.open.ru
        OpenMainPage openMainPage = ClickToOpenBankResult(googleResultPage, testConfig.getGoalPage());

        //проверить в блоке «Курс обмена в интернет-банке», что курс продажи больше курса покупки, для USD и для EUR.
        CheckCurrenciesSellBuyDelta(openMainPage, testConfig.getUsdName());
        CheckCurrenciesSellBuyDelta(openMainPage, testConfig.getEurName());
    }
}
