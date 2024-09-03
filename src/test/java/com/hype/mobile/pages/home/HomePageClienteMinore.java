package com.hype.mobile.pages.home;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.navbar.MinorenniNavbar;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageClienteMinore extends HomePageClienteStandard {

    @Inject
    MinorenniNavbar navbar;

    @Inject
    public HomePageClienteMinore(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return navbar.isLoaded();
    }

}
