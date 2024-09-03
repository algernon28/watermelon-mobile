package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class ErrorPageLogin extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Attenzione\")")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/body")
    private WebElement body;

    @AndroidFindBy(id = "it.hype.app:id/image_confirm")
    private WebElement errorIcon;

    @AndroidFindBys({
            @AndroidBy(id = "it.hype.app:id/button"),
            @AndroidBy(xpath = "//*[@text='Ok, ho capito' and @resource-id='it.hype.app:id/button']")
    })
    private WebElement btnOK;

    @Inject
    public ErrorPageLogin(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getBody() {
        return body.getText();
    }

    public void ok() {
        click(btnOK);
    }

    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

    @Override
    public String getPagename() {
        return "Pagina di Errore Login";
    }

}
