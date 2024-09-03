package com.hype.mobile.pages.cashback;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
@Slf4j
public class CashBackPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/title_page' and @text='Offerte per te']")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/search_bar")
    private WebElement txtSearchBar;

    @Inject
    protected CashBackPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void filterBy(String category) {
        String locator = String.format("new UiScrollable(new UiSelector().resourceId(\"it.hype.app:id/indicator\"))" +
                ".setAsHorizontalList().scrollIntoView(new UiSelector().text(\"%s\"))", category);
        WebElement filterButton = driver.findElement(AppiumBy.androidUIAutomator(locator));
        log.debug("Badge {} found", filterButton.getText());
        click(filterButton);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

}
