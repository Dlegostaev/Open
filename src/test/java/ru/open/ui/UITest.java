package ru.open.ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.open.ui.steps.Steps.*;

public class UITest {
    @BeforeClass
    public void StartupMethod() {
        String browser = "firefox"; // TODO get from config.json
        Configuration.browser = browser;
    }

    @Test
    public void TestMethod() throws InterruptedException {
        System.out.println("UITest");
        // TODO get from config.json
        String startPage = "https://www.google.com";

        // TODO get from testData.json
        String searchString = "Открытие";
        String goalPage = "goalPage";
        String[] currencies = {"USD", "EUR"};

        StartBrowser(); //TODO remove?
        OpenUrl();
        WriteInSearchField();
        ClickSearchButton();
        CheckSearchResultContainsUrl();
        OpenTargetResult();
        CheckCurrenciesSellBuyDelta();
    }
}
