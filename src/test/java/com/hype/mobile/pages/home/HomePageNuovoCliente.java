package com.hype.mobile.pages.home;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class HomePageNuovoCliente extends HomePageGeneric {

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/empty_state_placeholder")
    private WebElement pageBody;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/titolo_empty_state")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/subtitle_empty_state")
    private WebElement subtitle;


    @Inject
    protected HomePageNuovoCliente(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getSubtitle() {
        return subtitle.getText();
    }

    public String getTitle() {
        return title.getText();
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(pageBody));
    }

}
