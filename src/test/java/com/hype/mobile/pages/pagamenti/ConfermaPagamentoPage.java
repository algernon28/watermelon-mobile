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
public class ConfermaPagamentoPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Conferma pagamento']")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/code")
    private WebElement txtSecurityCode;

    @AndroidFindBy(id = "it.hype.app:id/btn_conferma")
    private WebElement btnConferma;

    @AndroidFindBy(id = "it.hype.app:id/btn_annulla")
    private WebElement btnAnnulla;

    @Inject
    public ConfermaPagamentoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    public void enterSecurityCode(String code){
        type(txtSecurityCode, code);
        click(btnConferma);
    }

    public void conferma(){
        click(btnConferma);
    }
    public void annulla(){
        click(btnAnnulla);
    }

}
