package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@ScenarioScoped
public class AccessoBloccatopage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Accesso Bloccato\")")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/subtitle")
    private WebElement body;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/image")
    private WebElement errorIcon;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@clickable='true']")
    private WebElement btnClose;

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@text='Reimposta la password' and @resource-id='it.hype.app:id/btn_blue']")
    private WebElement btnReimpostaPassword;

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@text='Maggiori informazioni' and @resource-id='it.hype.app:id/btn_white']")
    private WebElement btnMoreInfo;

    @Inject
    public AccessoBloccatopage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getBody() {
        return body.getText();
    }

    public void reimpostaPwd() {
        click(btnReimpostaPassword);
    }

    public void moreInfo() {
        click(btnMoreInfo);
    }

    public void close() {
        click(btnClose);
    }

    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title, Duration.ofSeconds(10));
    }


    @Override
    public String getPagename() {
        return "Pagina di Accesso Bloccato";
    }

}
