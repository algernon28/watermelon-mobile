package com.hype.mobile.pages.home.menu;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ScenarioScoped
public class MenuPage extends MobilePage {
    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/bottom_navbar")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/topList")
    private WebElement topList;

    //Upgrade, Club, Aiuto, Profilo
    @AndroidFindBy(xpath = "//*[@resource-id = 'it.hype.app:id/topList']//child::android.widget.LinearLayout[@resource-id='it.hype.app:id/element']")
    private List<WebElement> topButtons;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/show_detail")
    private WebElement btnShowDetails;

    @CacheLookup
    @AndroidFindBys({
            @AndroidBy(id = "it.hype.app:id/title_large"),
            @AndroidBy(uiAutomator = "new UiSelector().textContains(\"€\")")
    })
    private WebElement moneyAvailable;

    @Inject
    public MenuPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectTopButton(String buttonText) {
        String selector = String.format("//*[@text='%s']//ancestor::*[@resource-id='it.hype.app:id/element' and @clickable='true']", buttonText);
        //String selector = String.format("//android.widget.TextView[@text='%s']", buttonText);
        WebElement button = driver.findElement(By.xpath(selector));
        log.debug("Opening {} clicking {}...", buttonText, button.getAttribute("class"));
        click(button);
        log.debug("button {} clicked", buttonText);
    }

    public List<String> getTopButtons() {
        List<String> buttons = new ArrayList<>();
        topButtons.forEach(btn -> {
            buttons.add(btn.getText());
        });
        return buttons;
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Menu";
    }
}
