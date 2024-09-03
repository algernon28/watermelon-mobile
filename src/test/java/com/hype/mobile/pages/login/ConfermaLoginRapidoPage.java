package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class ConfermaLoginRapidoPage extends MobilePage {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='it.hype.app:id/toolbar_actionbar']/child::android.widget.ImageButton")
    protected WebElement btnClose;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login rapido\")")
    private WebElement title;
    @AndroidFindBy(id = "it.hype.app:id/subtitle")
    private WebElement subtitle;
    @CacheLookup
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/btn_blue' and @text='Prosegui']")
    private WebElement btnProsegui;

    @Inject
    protected ConfermaLoginRapidoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void close() {
        click(btnClose);
    }

    public void submit() {
        click(btnProsegui);
    }

    public String getSubtitle() {
        return subtitle.getText();
    }


    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

    @Override
    public String getPagename() {
        return "Pagina di conferma dell'associazione del device";
    }

}
