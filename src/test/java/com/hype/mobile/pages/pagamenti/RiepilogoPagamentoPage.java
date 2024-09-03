package com.hype.mobile.pages.pagamenti;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
@Slf4j
public class RiepilogoPagamentoPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Riepilogo']")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/shop_address")
    private WebElement lblAddress;


    @AndroidFindBy(id = "it.hype.app:id/amount")
    private WebElement lblAmount;

    @AndroidFindBy(id="it.hype.app:id/confirm_button")
    private WebElement btnPaga;

    @Inject
    public RiepilogoPagamentoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    public String getAmount(){
        return lblAmount.getText();
    }

    public void pay(){
        click(btnPaga);
    }

}
