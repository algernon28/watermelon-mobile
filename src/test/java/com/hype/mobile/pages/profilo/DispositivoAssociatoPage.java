package com.hype.mobile.pages.profilo;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.HomePageGeneric;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class DispositivoAssociatoPage extends HomePageGeneric {

    @AndroidFindBy(id = "it.hype.app:id/title")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/empty_titlePage' and @text='Nessun dispositivo associato']")
    private WebElement emptyTitle;

    @AndroidFindBy(id = "it.hype.app:id/info_device")
    private WebElement infoDevice;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/save_device_button")
    private WebElement btnAssocia;


    @Inject
    protected DispositivoAssociatoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    public void associa() {
        click(btnAssocia);
    }

    public boolean isAssociated() {
        checkIfLoaded(title);
        if (isDisplayed(btnAssocia) || isDisplayed(emptyTitle)) {
            return false;
        }
        if (isDisplayed(infoDevice)) {
            return true;
        }
        throw new RuntimeException("Nessuna informazione su associazione trovata");

    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Sezione Profilo -> Dispositivo Associato";
    }

}
