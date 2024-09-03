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
public class ConfermaDeviceAssociatoPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dispositivo associato\")")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/subtitle")
    private WebElement subtitle;

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/btn_blue' and @text='Ho capito']")
    private WebElement btnHocapito;


    @Inject
    protected ConfermaDeviceAssociatoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void submit() {
        click(btnHocapito);
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
        return "Pagina di conferma dell'associazione del device";
    }

}
