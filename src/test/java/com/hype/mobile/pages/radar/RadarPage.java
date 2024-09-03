package com.hype.mobile.pages.radar;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import com.watermelon.core.Utils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
@Slf4j
public class RadarPage extends MobilePage {

    private final String totalIncomingsId = "it.hype.app:id/uscite";
    private final String totalExpensesId = "it.hype.app:id/entrate";
    private final String scrollAmoundPath = "new UiScrollable(new UiSelector().resourceId(\"it.hype.app:id/scroll\")).scrollIntoView(new UiSelector().text(\"%s\"))";
    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc='Radar' and @resource-id='it.hype.app:id/collapsing_toolbar']")
    private WebElement title;
    // es: 145,41 €
    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/amountTitle")
    private WebElement totalAmount;
    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/updateData")
    private WebElement lnkAggiorna;

    @Inject
    public RadarPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public double getTotalExpenses() {
        String locator = String.format(scrollAmoundPath, "Uscite");
        //scroll down the UI
        driver.findElement(AppiumBy.androidUIAutomator(locator));
        WebElement amount = driver.findElement(By.id(totalExpensesId));
        return Utils.getCurrencyValue(amount.getText());
    }

    public double getTotalIncomings() {
        String locator = String.format(scrollAmoundPath, "Uscite");
        //scroll down the UI
        driver.findElement(AppiumBy.androidUIAutomator(locator));
        WebElement amount = driver.findElement(By.id(totalIncomingsId));
        return Utils.getCurrencyValue(amount.getText());
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

}
