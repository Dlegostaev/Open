package ru.open.ui.forms;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import ru.open.ui.models.WebsiteData;
import ru.open.util.TestException;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static ru.open.ui.util.UIJson.GetWebsiteDataFromJson;

public class OpenCurrenciesForm {

    String formLocator = ".main-page-exchange"; //1st level

    //---------TABS MODE START------------
    String tabLocator = " [role='tab']";   //2nd level
    String tabPanelLocator = " [role='tabpanel'][tabindex='0']"; //2nd level
    //---------TABS MODE END------------

    String tabPanelRowLocator = " .main-page-exchange__row"; //3rd level (2nd in non-tabs mode)
    String tabPanelRowCurrencyNameLocator = " .main-page-exchange__currency-name";  //4th level (3rd in non-tabs mode)
    String tabPanelRowCurrencyRateLocator = " .main-page-exchange__rate";  //4th level (3rd in non-tabs mode)

    String tabPanelHeaderLocator = " .main-page-exchange__table-header";  //3th level (2nd in non-tabs mode)
    String tabPanelHeaderTextLocator = " td > span:first-child";  //4th level (3rd in non-tabs mode)

    String websiteDataPath = "src/test/java/ru/open/ui/data/websiteData.json";
    WebsiteData websiteData = GetWebsiteDataFromJson(websiteDataPath);

    int currentTabIndex = 0;    // tab active by default
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

        PreInit();
    }

    private void PreInit() {
        if (isTabsMode()) {
            tabs = form.$$(tabLocator);
            for (SelenideElement tab : tabs) {
                tab.shouldBe(Condition.visible);
            }
            tabSize = tabs.size();

            InitFormTabs();
        } else {
            InitFormMain();
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

    private void InitFormMain() {
        tabPanelHeader = form.$(tabPanelHeaderLocator).shouldBe(Condition.visible);

        // which column is for sale which is for buy
        int buy = GetBuySellColumnNumberFromHeader(websiteData.getBuy()) - 1;    // -1 because we get number from 3 results
        int sell = GetBuySellColumnNumberFromHeader(websiteData.getSell()) - 1;  // but converting into 2 results

        tabPanelExchangeRow = form.$$(tabPanelRowLocator);
        currencyRowList = new ArrayList<CurrencyRow>();
        for (SelenideElement row : tabPanelExchangeRow) {
            String currencyName = row.$(tabPanelRowCurrencyNameLocator).getText();
            String currencyBuyRate = row.$$(tabPanelRowCurrencyRateLocator).get(buy).getText();  //TODO
            String currencySellRate = row.$$(tabPanelRowCurrencyRateLocator).get(sell).getText(); //TODO
            CurrencyRow currencyRow = new CurrencyRow(currencyName, currencyBuyRate, currencySellRate);
            currencyRowList.add(currencyRow);
        }
    }

    private void InitFormTabs() {
        tabPanel = form.$(tabPanelLocator).shouldBe(Condition.visible);

        tabPanelHeader = tabPanel.$(tabPanelHeaderLocator).shouldBe(Condition.visible);

        // which column is for sale which is for buy
        int buy = GetBuySellColumnNumberFromHeader(websiteData.getBuy()) - 1;    // -1 because we get number from 3 results
        int sell = GetBuySellColumnNumberFromHeader(websiteData.getSell()) - 1;  // but converting into 2 results

        tabPanelExchangeRow = tabPanel.$$(tabPanelRowLocator);
        currencyRowList = new ArrayList<CurrencyRow>();
        for (SelenideElement row : tabPanelExchangeRow) {
            String currencyName = row.$(tabPanelRowCurrencyNameLocator).getText();
            String currencyBuyRate = row.$$(tabPanelRowCurrencyRateLocator).get(buy).getText();  //TODO
            String currencySellRate = row.$$(tabPanelRowCurrencyRateLocator).get(sell).getText(); //TODO
            CurrencyRow currencyRow = new CurrencyRow(currencyName, currencyBuyRate, currencySellRate);
            currencyRowList.add(currencyRow);
        }
    }

    public int GetBuySellColumnNumberFromHeader(String headerString) {
        tabPanelHeaderExchange = tabPanelHeader.$$(tabPanelHeaderTextLocator);
        for (int i = 0; i < tabPanelHeaderExchange.size(); i++) {
            if (tabPanelHeaderExchange.get(i).getText().contains(headerString)) {
                return i;
            }
        }
        return -1;
    }

    public String GetCurrencyRateBankBuy(String currency) throws TestException {
        for (CurrencyRow currencyRow : currencyRowList) {
            if (currencyRow.currencyName.equals(currency)) {
                return currencyRow.buyRate;
            }
        }
        throw new TestException("Try to get non-exist buy rate currency");
    }

    public float GetCurrencyRateBankBuyAsFloat(String currency) throws TestException {
        return Float.parseFloat(GetCurrencyRateBankBuy(currency).replace(",", "."));
    }

    public String GetCurrencyRateBankSell(String currency) throws TestException {
        for (CurrencyRow currencyRow : currencyRowList) {
            if (currencyRow.currencyName.equals(currency)) {
                return currencyRow.sellRate;
            }
        }
        throw new TestException("Try to get non-exist sell rate currency");
    }

    public float GetCurrencyRateBankSellAsFloat(String currency) throws TestException {
        return Float.parseFloat(GetCurrencyRateBankSell(currency).replace(",", "."));
    }

    public void SwitchTab(int tabNumber) throws TestException {
        PreInit();
        if (tabNumber > tabSize) {
            throw new TestException("Trying switch to tab which doesn't exist");
        }
        tabs.get(tabNumber).click();
        currentTabIndex = tabNumber;
        InitFormTabs();
    }
}
