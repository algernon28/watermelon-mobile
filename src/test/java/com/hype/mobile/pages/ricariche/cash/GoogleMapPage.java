package com.hype.mobile.pages.ricariche.cash;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class GoogleMapPage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Punti vendita abilitati\")")
    private WebElement title;

    @AndroidFindBy(accessibility = "Google Map")
    private WebElement googleMap;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnClose;

    @Inject
    public GoogleMapPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void back() {
        click(btnClose);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Google Map";
    }

}
