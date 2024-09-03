package com.hype.mobile.pages.pagamenti;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
@Slf4j
public class ConclusionePage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "it.hype.app:id/image_confirm")
    private WebElement title;

    //Es: 44,44€ inviati
    @AndroidFindBy(id = "it.hype.app:id/title_text")
    private WebElement lblAmountConfirmation;

    @AndroidFindBy(id = "it.hype.app:id/body")
    private WebElement body;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='it.hype.app:id/button' and @text='Torna alla home'")
    private WebElement backToHome;

    @Inject
    public ConclusionePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    public String body(){
        return body.getText();
    }

    public String getAmount(){
        return lblAmountConfirmation.getText();
    }

    public void backToHome(){
        click(backToHome);
    }

}
