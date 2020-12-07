package ru.open.ui.forms;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.open.ui.models.WebsiteData;
import ru.open.util.TestException;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;

import static ru.open.ui.util.UIJson.GetWebsiteDataFromJson;

public class OpenCurrenciesForm {
    String formLocator = ".main-page-exchange"; //1
    //String formLocator = "//div[contains(@class, 'main-page-exchange')]"; //1
    //String tabLocator = ".//*[@role='tab']";   //2
    String tabLocator = " [role='tab']";   //2

    String tabPanelLocator = " [role='tabpanel'][tabindex='0']"; //2
    String tabPabelExchangeRowLocator = " .main-page-exchange__row"; //3
    String currencyNameLocator = " .main-page-exchange__currency-name";  //4
    String currencyRateLocator = " .main-page-exchange__rate";  //4

    String tabPanelHeaderLocator = " .main-page-exchange__table-header";  //3
    //String tabPanelHeaderExchangeLocator = " .text--exchange-card";  //4
    String tabPanelHeaderExchangeLocator = " td > span:first-child";  //4

    //String detailedButtonLocator = " .main-page-exchange__calculate";    //2

    String websiteDataPath = "src/test/java/ru/open/ui/data/websiteData.json";
    WebsiteData websiteData = GetWebsiteDataFromJson(websiteDataPath);

    int currentTabIndex = 0;    // tab active by default
    int tabSize;

    SelenideElement form;
    ElementsCollection tabs;
    SelenideElement tabPanel;
    ElementsCollection tabPanelExchangeRow;
    //SelenideElement currencyName;

    SelenideElement tabPanelHeader;
    ElementsCollection tabPanelHeaderExchange;

    //SelenideElement detailedButton;

    ArrayList<CurrencyRow> currencyRowList;


    public OpenCurrenciesForm() {
        form = element(formLocator);
        form.scrollTo().shouldBe(Condition.visible);
        tabs = form.$$(tabLocator);
        for (SelenideElement tab : tabs) {
            tab.shouldBe(Condition.visible);
        }
        tabSize = tabs.size();

        InitForm();
    }

    public void InitForm() {
        tabPanel = form.$(tabPanelLocator).shouldBe(Condition.visible);

        tabPanelHeader = tabPanel.$(tabPanelHeaderLocator).shouldBe(Condition.visible);

        // which column is for sale which is for buy
        int buy = GetBuySellColumnNumberFromHeader(websiteData.getBuy()) - 1;    // -1 because we get number from 3 results
        int sell = GetBuySellColumnNumberFromHeader(websiteData.getSell()) - 1;  // but converting into 2 results

        tabPanelExchangeRow = tabPanel.$$(tabPabelExchangeRowLocator);
        currencyRowList = new ArrayList<CurrencyRow>();
        for (SelenideElement row : tabPanelExchangeRow) {
            String currencyName = row.$(currencyNameLocator).getText();
            String currencyBuyRate = row.$$(currencyRateLocator).get(buy).getText();  //TODO
            String currencySellRate = row.$$(currencyRateLocator).get(sell).getText(); //TODO
            CurrencyRow currencyRow = new CurrencyRow(currencyName, currencyBuyRate, currencySellRate);
            currencyRowList.add(currencyRow);
        }
    }


    public int GetBuySellColumnNumberFromHeader(String headerString) {
        tabPanelHeaderExchange = tabPanelHeader.$$(tabPanelHeaderExchangeLocator);
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

    public String GetCurrencyRateBankSell(String currency) throws TestException {
        for (CurrencyRow currencyRow : currencyRowList) {
            if (currencyRow.currencyName.equals(currency)) {
                return currencyRow.sellRate;
            }
        }
        throw new TestException("Try to get non-exist sell rate currency");
    }

    public void SwitchTab(int tabNumber) throws TestException {
        if (tabNumber > tabSize) {
            throw new TestException("Trying switch to tab which doesn't exist");
        }
        tabs.get(tabNumber).click();
        currentTabIndex = tabNumber;
        InitForm();
    }

}
