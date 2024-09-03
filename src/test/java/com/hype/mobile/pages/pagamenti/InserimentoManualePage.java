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
public class InserimentoManualePage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Paga con Hype']")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/text")
    private WebElement lblSubtitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='it.hype.app:id/field_label']")
    private WebElement lblCode;

    @AndroidFindBy(id = "//android.widget.EditText[@resource-id='it.hype.app:id/field']")
    private WebElement txtCode;

    @AndroidFindBy(xpath="//[@resource-id='it.hype.app:id/btn_blue' and @txt='Prosegui']")
    private WebElement btnProsegui;

    @Inject
    public InserimentoManualePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title) && checkIfLoaded(txtCode));
    }

    public void search(String code){
        type(txtCode, code);
    }

    public void prosegui(){
        click(btnProsegui);
    }
}
