package com.hype.mobile.pages.ricariche.cash;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class ImportoRicaricaPage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Importo ricarica\")")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/currency_amount_input")
    private WebElement inputAmount;

    @AndroidFindBy(id = "it.hype.app:id/currency_amount_helper")
    private WebElement tipCommissione;

    @AndroidFindBy(id = "it.hype.app:id/go_to_map")
    private WebElement btnPuntiAbilitati;

    @AndroidFindBy(id = "it.hype.app:id/go_ahead")
    private WebElement btnContinue;

    @AndroidFindBy(id = "it.hype.app:id/action")
    private WebElement btnInfo;

    @Inject
    public ImportoRicaricaPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void typeAmount(String amount) {
        type(inputAmount, amount);
    }

    public String getTipCommissione() {
        return tipCommissione.getText();
    }

    public void goToPuntiVendita() {
        click(btnPuntiAbilitati);
    }

    public void readInfo() {
        click(btnInfo);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina importo ricariche";
    }
}
