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
public class PagaNegozioPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Paga con Hype']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='it.hype.app:id/content_panel']" +
            "/descendant::android.widget.TextView[@resource-id='it.hype.app:id/field_label']")
    private WebElement lblNegozio;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='it.hype.app:id/content_panel']" +
            "/descendant::android.widget.TextView[@resource-id='it.hype.app:id/field']")
    private WebElement lblNome;

    @AndroidFindBy(id = "it.hype.app:id/currency_amount_input")
    private WebElement txtAmount;

    @AndroidFindBy(xpath="//android.widget.Button[@resource-id='it.hype.app:id/btn_blue' and @text='Prosegui']")
    private WebElement btnProsegui;

    @Inject
    public PagaNegozioPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title) && checkIfLoaded(lblNegozio));
    }

    public void enterAmount(String amount){
        type(txtAmount, amount);
        click(btnProsegui);
    }

}
