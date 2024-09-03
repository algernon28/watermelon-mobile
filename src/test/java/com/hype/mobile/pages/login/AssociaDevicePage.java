package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class AssociaDevicePage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Vuoi associare questo dispositivo?\")")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/subtitle")
    private WebElement subtitle;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/btn_blue")
    private WebElement btnAssocia;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/btn_white")
    private WebElement btnNoAssocia;

    @Inject
    protected AssociaDevicePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void associate(boolean shouldAssociate) {
        if (shouldAssociate) {
            btnAssocia.click();
        } else {
            btnNoAssocia.click();
        }

    }

    public String getSubtitle() {
        return subtitle.getText();
    }


    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

    @Override
    public String getPagename() {
        return "Pagina di associazione del device";
    }

}
