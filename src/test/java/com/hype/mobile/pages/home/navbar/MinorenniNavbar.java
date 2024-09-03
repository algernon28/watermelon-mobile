package com.hype.mobile.pages.home.navbar;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class MinorenniNavbar extends Navbar {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/savings_navigation' and @content-desc='Risparmi']")
    private WebElement btnRisparmi;

    @Inject
    protected MinorenniNavbar(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return checkIfLoaded(btnRisparmi);
    }

}
