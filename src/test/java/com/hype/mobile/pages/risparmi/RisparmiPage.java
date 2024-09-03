package com.hype.mobile.pages.risparmi;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.HomePageGeneric;
import com.watermelon.core.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class RisparmiPage extends HomePageGeneric {

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/amount")
    protected WebElement lblAMount;
    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc='Risparmi']")
    private WebElement title;


    @Inject
    public RisparmiPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public double getAmount() {
        String amount = lblAMount.getText().replace("Tot. ", "");
        double amountValue = Utils.getCurrencyValue(amount);
        log.debug("Tot. Risparmi: {}", amountValue);
        return amountValue;
    }

    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

}
