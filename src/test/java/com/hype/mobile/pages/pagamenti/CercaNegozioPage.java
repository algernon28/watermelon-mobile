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
public class CercaNegozioPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Paga con Hype']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/code_to_search']" +
            "/descendant::android.widget.EditText[@resource-id='it.hype.app:id/field']")
    private WebElement txtShopCode;


    @AndroidFindBy(xpath="//*[@resource-id='it.hype.app:id/btn_blue' and @text='Prosegui']")
    private WebElement btnProsegui;

    @Inject
    public CercaNegozioPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title) && checkIfLoaded(btnProsegui));
    }

    public void enterCode(String code){
        type(txtShopCode, code);
        click(btnProsegui);
    }

}
