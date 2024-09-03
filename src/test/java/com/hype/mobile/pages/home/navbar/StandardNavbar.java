package com.hype.mobile.pages.home.navbar;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class StandardNavbar extends Navbar {

    @CacheLookup
    @AndroidBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/conti_deposito_investimenti_navigation' and @content-desc='Investimenti']")
    private WebElement btnInvestimenti;

    @Inject
    protected StandardNavbar(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void toInvestimenti() {
        btnInvestimenti.click();
    }


    @Override
    public boolean isLoaded() {
        return checkIfLoaded(btnInvestimenti);
    }

}
