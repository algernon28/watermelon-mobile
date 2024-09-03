package com.hype.mobile.pages.intro;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.AbstractWalkthroughPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IntroWalkthroughPage extends AbstractWalkthroughPage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Paga ovunque\")")
    @iOSXCUITFindBy(accessibility = "Tutto sotto controllo")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Registrati con un selfie\")")
    @iOSXCUITFindBy(accessibility = "Non sei solo")
    private WebElement lblLastTitle;

    @AndroidFindBy(id = "it.hype.app:id/btn_prosegui")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Prosegui']")
    private WebElement btnContinue;

    @AndroidFindBy(id = "it.hype.app:id/accedi_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Accedi']")
    private WebElement btnAccess;

    @AndroidFindBy(id = "it.hype.app:id/diventa_hype_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Registrati ad HYPE']")
    private WebElement btnRegister;


    @Inject
    public IntroWalkthroughPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void skipAll() {
        super.skip();
        waitUntilVisible(lblLastTitle);
        btnContinue.click();
        acceptAlert();
        btnAccess.click();
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Walkthrough";
    }
}
