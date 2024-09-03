package com.hype.mobile.pages.home;

import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractWalkthroughPage extends MobilePage {

    @AndroidFindBy(id = "it.hype.app:id/salta_btn")
    @iOSXCUITFindBy(accessibility = "Salta")
    protected WebElement btnSkip;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='it.hype.app:id/toolbar_actionbar']/child::android.widget.ImageButton")
    protected WebElement btnClose;

    public AbstractWalkthroughPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void skip() {
        verify();
        btnSkip.click();
    }

    public void close() {
        click(btnClose);
    }

}
