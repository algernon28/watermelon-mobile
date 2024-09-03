package com.hype.mobile.pages.home.navbar;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
@ScenarioScoped
public class Navbar extends MobilePage {

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/bottom_navbar")
    protected WebElement container;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/dashboard2' and @content-desc='Home']")
    protected WebElement btnHome;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/paymentMenuFragment' and @content-desc='Pagamenti']")
    protected WebElement btnPagamenti;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/statistiche_navigation' and @content-desc='Radar']")
    protected WebElement btnRadar;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/menuFragment' and @content-desc='Menu']")
    protected WebElement btnMenu;

    @Inject
    protected Navbar(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public String getPagename() {
        return getClass().getSimpleName();
    }

    public void toHome() {
        click(btnHome);
    }

    public void toPagamenti() {
        click(btnPagamenti);
    }

    public void toRadar() {
        click(btnRadar);
    }

    public void toMenu() {
        log.debug("Opening Menu by clicking {}", btnMenu);
        click(btnMenu);
        log.debug("{} [{}] clicked, the  menu page should open...", btnMenu.getText(), btnMenu.getAttribute("class"));
    }

}
