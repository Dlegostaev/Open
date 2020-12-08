package ru.open.ui;

import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.open.ui.models.BrowserConfig;
import ru.open.ui.models.TestConfig;
import ru.open.ui.pages.google.GoogleSearchPage;
import ru.open.ui.pages.google.GoogleResultPage;
import ru.open.ui.pages.open.OpenMainPage;
import ru.open.util.TestException;
import org.slf4j.Logger;

import static ru.open.ui.steps.Steps.*;
import static ru.open.ui.util.UIJson.getBrowserConfigFromJson;
import static ru.open.ui.util.UIJson.getTestConfigFromJson;

public class UITest {
    String browserConfigPath = "src/test/java/ru/open/ui/data/browserConfig.json";

    @Parameters("dataPath")
    @Test
    public void testMethod(String dataPath) throws TestException {
        TestConfig testConfig = getTestConfigFromJson(dataPath);
        Logger logger = LoggerFactory.getLogger("OpenUILogger");

        logger.info("Open https://www.google.com/");
        GoogleSearchPage googleSearchPage = openGoogle(testConfig.getStartPage());

        logger.info("Input in search field: '" + testConfig.getSearchString() + "'");
        googleInputSearchText(googleSearchPage, testConfig.getSearchString());

        logger.info("Click 'Search'");
        GoogleResultPage googleResultPage = googleClickSearchButton(googleSearchPage);

        logger.info("Chech that search results contains '" + testConfig.getGoalPage() + "'");
        checkSearchResultContainsUrl(googleResultPage, testConfig.getGoalPage());

        logger.info("Go to goal page: '" + testConfig.getGoalPage() + "'");
        OpenMainPage openMainPage = clickToOpenBankResult(googleResultPage, testConfig.getGoalPage());

        logger.info("Check that bank buy rate is greater than sell rate for " + testConfig.getUsdName() +
                " and " + testConfig.getEurName() + " currencies");
        checkCurrenciesSellBuyDelta(openMainPage, testConfig.getUsdName());
        checkCurrenciesSellBuyDelta(openMainPage, testConfig.getEurName());
    }

    @BeforeTest
    public void startupMethod() throws TestException {
        //запустить Chrome
        Logger logger = LoggerFactory.getLogger("OpenUILogger");
        logger.info("Start browser");
        BrowserConfig browserConfig = getBrowserConfigFromJson(browserConfigPath);
        startBrowser(browserConfig);
    }

    @AfterTest
    public void teardownMethod() {
        Logger logger = LoggerFactory.getLogger("OpenUILogger");
        logger.info("Close browser");
        closeBrowser();
    }
}
