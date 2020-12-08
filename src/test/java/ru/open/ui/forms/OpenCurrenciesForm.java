package ru.open.ui.forms;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import ru.open.ui.models.WebsiteData;
import ru.open.util.TestException;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static ru.open.ui.util.UIJson.getWebsiteDataFromJson;

public class OpenCurrenciesForm {

    String formLocator = ".main-page-exchange";

    String tabLocator = " [role='tab']";
    String tabPanelLocator = " [role='tabpanel'][tabindex='0']";

    String tabPanelRowLocator = " .main-page-exchange__row";
    String tabPanelRowCurrencyNameLocator = " .main-page-exchange__currency-name";
    String tabPanelRowCurrencyRateLocator = " .main-page-exchange__rate";

    String tabPanelHeaderLocator = " .main-page-exchange__table-header";
    String tabPanelHeaderTextLocator = " td > span:first-child";

    String websiteDataPath = "src/test/java/ru/open/ui/data/websiteData.json";
    WebsiteData websiteData = getWebsiteDataFromJson(websiteDataPath);

    int currentTabIndex = 0;    // tab active by default
    boolean tabsModeInited = false;
    int tabSize;

    By tabsModeSelector = new By.ByCssSelector(".main-page-exchange .ant-tabs");

    SelenideElement form;
    ElementsCollection tabs;
    SelenideElement tabPanel;
    ElementsCollection tabPanelExchangeRow;

    SelenideElement tabPanelHeader;
    ElementsCollection tabPanelHeaderExchange;

    ArrayList<CurrencyRow> currencyRowList;

    public OpenCurrenciesForm() throws TestException {
        form = element(formLocator).scrollTo().shouldBe(Condition.visible);

        preInit();
    }

    private void preInit() {
        if (isTabsMode()) {
            tabs = form.$$(tabLocator);
            for (SelenideElement tab : tabs) {
                tab.shouldBe(Condition.visible);
            }
            tabSize = tabs.size();
            tabsModeInited = true;

            initFormTabs();
        } else {
            initFormMain();
        }
    }

    public void checkMode() {
        if (tabsModeInited != isTabsMode()) {
            preInit();
        }
    }

    public boolean isTabsMode() {
        try {
            getWebDriver().findElement(tabsModeSelector);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private void initFormMain() {
        try {
            tabPanelHeader = form.$(tabPanelHeaderLocator).shouldBe(Condition.visible);

            // which column is for sale which is for buy
            int buy = getBuySellColumnNumberFromHeader(websiteData.getBuy()) - 1;    // -1 because we get number from 3 results
            int sell = getBuySellColumnNumberFromHeader(websiteData.getSell()) - 1;  // but converting into 2 results

            tabPanelExchangeRow = form.$$(tabPanelRowLocator);
            currencyRowList = new ArrayList<>();
            for (SelenideElement row : tabPanelExchangeRow) {
                String currencyName = row.$(tabPanelRowCurrencyNameLocator).getText();
                String currencyBuyRate = row.$$(tabPanelRowCurrencyRateLocator).get(buy).getText();
                String currencySellRate = row.$$(tabPanelRowCurrencyRateLocator).get(sell).getText();
                CurrencyRow currencyRow = new CurrencyRow(currencyName, currencyBuyRate, currencySellRate);
                currencyRowList.add(currencyRow);
            }
        } catch (StaleElementReferenceException e) {
            preInit();
        }
    }

    private void initFormTabs() {
        tabPanel = form.$(tabPanelLocator).shouldBe(Condition.visible);

        tabPanelHeader = tabPanel.$(tabPanelHeaderLocator).shouldBe(Condition.visible);

        // which column is for sale which is for buy
        int buy = getBuySellColumnNumberFromHeader(websiteData.getBuy()) - 1;    // -1 because we get number from 3 results
        int sell = getBuySellColumnNumberFromHeader(websiteData.getSell()) - 1;  // but converting into 2 results

        tabPanelExchangeRow = tabPanel.$$(tabPanelRowLocator);
        currencyRowList = new ArrayList<>();
        for (SelenideElement row : tabPanelExchangeRow) {
            String currencyName = row.$(tabPanelRowCurrencyNameLocator).getText();
            String currencyBuyRate = row.$$(tabPanelRowCurrencyRateLocator).get(buy).getText();
            String currencySellRate = row.$$(tabPanelRowCurrencyRateLocator).get(sell).getText();
            CurrencyRow currencyRow = new CurrencyRow(currencyName, currencyBuyRate, currencySellRate);
            currencyRowList.add(currencyRow);
        }
    }

    public int getBuySellColumnNumberFromHeader(String headerString) {
        checkMode();
        tabPanelHeaderExchange = tabPanelHeader.$$(tabPanelHeaderTextLocator);
        for (int i = 0; i < tabPanelHeaderExchange.size(); i++) {
            if (tabPanelHeaderExchange.get(i).getText().contains(headerString)) {
                return i;
            }
        }
        return -1;
    }

    public String getCurrencyRateBankBuy(String currency) throws TestException {
        checkMode();
        for (CurrencyRow currencyRow : currencyRowList) {
            if (currencyRow.currencyName.equals(currency)) {
                return currencyRow.buyRate;
            }
        }
        throw new TestException("Try to get non-exist buy rate currency");
    }

    public float getCurrencyRateBankBuyAsFloat(String currency) throws TestException {
        checkMode();
        return Float.parseFloat(getCurrencyRateBankBuy(currency).replace(",", "."));
    }

    public String getCurrencyRateBankSell(String currency) throws TestException {
        checkMode();
        for (CurrencyRow currencyRow : currencyRowList) {
            if (currencyRow.currencyName.equals(currency)) {
                return currencyRow.sellRate;
            }
        }
        throw new TestException("Try to get non-exist sell rate currency");
    }

    public float getCurrencyRateBankSellAsFloat(String currency) throws TestException {
        checkMode();
        return Float.parseFloat(getCurrencyRateBankSell(currency).replace(",", "."));
    }

    public void switchTab(int tabNumber) throws TestException {
        preInit();
        if (tabNumber > tabSize) {
            throw new TestException("Trying switch to tab which doesn't exist");
        }
        tabs.get(tabNumber).click();
        currentTabIndex = tabNumber;
        initFormTabs();
    }
}
