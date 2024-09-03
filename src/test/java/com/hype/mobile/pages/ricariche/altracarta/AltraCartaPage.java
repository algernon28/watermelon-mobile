package com.hype.mobile.pages.ricariche.altracarta;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import com.watermelon.core.webelements.ToggleButton;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class AltraCartaPage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ricarica con altra carta\")")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"it.hype.app:id/hype_text_input_text\").instance(0)")
    private WebElement txtCardNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Mese/Anno\")")
    private WebElement txtValidity;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Inserisci il cvv presente sul retro\")")
    private WebElement txtCVV;

    @AndroidFindBy(id = "it.hype.app:id/card_switch")
    private WebElement btnSaveInApp;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Dai un nome alla carta\")")
    private WebElement txtCardName;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnBack;

    @AndroidFindBy(id = "it.hype.app:id/wallet_add_card_submit")
    private WebElement btnProsegui;

    @Inject
    public AltraCartaPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void typeCardNumber(String value) {
        type(txtCardNumber, value);
    }

    public void typeValidity(String value) {
        type(txtValidity, value);
    }

    public void typeCVV(String value) {
        type(txtCVV, value);
    }

    public void saveInApp() {
        ToggleButton button = new ToggleButton(btnSaveInApp);
        button.check();
    }

    public void clearSave() {
        ToggleButton button = new ToggleButton(btnSaveInApp);
        button.uncheck();
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Altra carta";
    }

}
