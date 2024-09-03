package com.hype.mobile.pages.risparmi;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.AbstractWalkthroughPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
@Slf4j
public class RisparmiWalkthruPage extends AbstractWalkthroughPage {

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/title' and @text='Dai vita ai tuoi risparmi']")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/btn_prosegui")
    private WebElement btnProsegui;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/desc")
    private WebElement lblDescription;

    @Inject
    public RisparmiWalkthruPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void skipAll() {
        super.skip();
        click(btnProsegui);
    }

    public String getDescription() {
        return lblDescription.getText();
    }


    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

}
