package com.hype.mobile.pages.ricariche.cash;

import com.hype.mobile.pages.home.AbstractWalkthroughPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractCashWalkThruPage extends AbstractWalkthroughPage {

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnClose;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Scansiona il codice\")")
    private WebElement lblScansionaCodice;

    @AndroidFindBy(id = "it.hype.app:id/btn_prosegui")
    private WebElement btnContinue;

    public AbstractCashWalkThruPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void prosegui() {
        click(btnContinue);
    }

    public void skipAll() {
        super.skip();
        waitUntilVisible(lblScansionaCodice);
        click(btnContinue);
    }


}
