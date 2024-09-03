package com.hype.mobile.pages.ricariche.cash;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RicaricaSupermercatoPage extends AbstractCashWalkThruPage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ricarica HYPE al supermercato\")")
    private WebElement title;

    @Inject
    public RicaricaSupermercatoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Ricarica Supermercato";
    }
}
