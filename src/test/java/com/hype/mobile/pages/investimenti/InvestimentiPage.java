package com.hype.mobile.pages.investimenti;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@ScenarioScoped
@Slf4j
public class InvestimentiPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc='Investimenti' and @resource-id='it.hype.app:id/collapsing_toolbar']")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/margin24")
    private List<WebElement> functionList;


    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='it.hype.app:id/title' and @text='Fondi di investimento']")
    private WebElement fondiInvestimento;

    /**
     * //*[@resource-id='it.hype.app:id/margin24']//descendant::*[@text='Bonifico']
     * it.hype.app:id/title
     * Invia Denaro
     * Richiedi Denaro
     * Bonifico
     * Paga in Negozio
     * Bollettino
     */

    @Inject
    public InvestimentiPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void toFondiInvestimento() {
        click(fondiInvestimento);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Investimenti";
    }

}
