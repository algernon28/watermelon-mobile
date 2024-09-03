package com.hype.mobile.pages.ricariche.cash;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import com.watermelon.core.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Currency;

@ScenarioScoped
public class BarCodePage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Bar code\")")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/field")
    private WebElement amount;

    @AndroidFindBy(id = "it.hype.app:id/commission_to_import")
    private WebElement infoCommissione;

    @AndroidFindBy(id = "it.hype.app:id/back_to_home")
    private WebElement btnBackHome;

    @AndroidFindBy(id = "it.hype.app:id/barcode_img")
    private WebElement imgBarcode;

    @Inject
    public BarCodePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getAmount() {
        Currency currency = Currency.getInstance("EUR");
        return String.valueOf(Utils.getCurrencyValue(amount.getText(), currency));
    }

    public String getInfoCommissione() {
        return infoCommissione.getText();
    }

    public boolean isBarCodeVisible() {
        return imgBarcode.isDisplayed();
    }

    public void goBackHome() {
        click(btnBackHome);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Barcode";
    }

}
