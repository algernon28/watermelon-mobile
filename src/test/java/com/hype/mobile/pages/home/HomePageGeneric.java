package com.hype.mobile.pages.home;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class HomePageGeneric extends MobilePage {

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/recharge")
    protected WebElement btnRecharge;
    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/show_detail")
    protected WebElement btnShowDetails;
    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='it.hype.app:id/collapsing_toolbar']" +
            "/child::android.widget.TextView[@resource-id='it.hype.app:id/title_large']")
    protected WebElement moneyAvailable;

    @AndroidFindBy(id = "it.hype.app:id/bottomsheet_alert")
    protected WebElement bottomSheet;

    @Inject
    DettagliPage dettagliPage;

    @Inject
    public HomePageGeneric(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void recharge() {
        btnRecharge.click();
    }

    public void showDetails() {
        btnShowDetails.click();
    }

    public String getMoneyAvailable() {
        String money = moneyAvailable.getText();
        return money.trim();
    }

    public DettagliPage getDettagliPage() {
        return dettagliPage;
    }

    @Override
    public boolean isLoaded() {
        closeBottomSheet();
        return checkIfLoaded(btnRecharge);
    }

    @Override
    public String getPagename() {
        return getClass().getSimpleName();
    }

    public void closeBottomSheet() {
        if (checkIfLoaded(bottomSheet, Duration.ofSeconds(5))) {
            //If the BottomSheet is displayed, close it
            log.debug("BottomSheet presente: chiusura...");
            driver.navigate().back();
        } else {
            log.debug("BottomSheet non presente: ignora...");
        }
    }

    public enum HomePageType {minore, nuovo, esistente}

}
