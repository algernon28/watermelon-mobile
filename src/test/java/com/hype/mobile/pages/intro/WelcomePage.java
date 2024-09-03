package com.hype.mobile.pages.intro;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class WelcomePage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ciao HYPER!\")")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Salta\")")
    private WebElement skip;

    @AndroidFindBy(id = "it.hype.app:id/accedi_button")
    private WebElement btnAccedi;

    @AndroidFindBy(id = "it.hype.app:id/diventa_hype_button")
    private WebElement btnRegistrati;

    @Inject
    protected WelcomePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void accedi() {
        btnAccedi.click();
    }

    public void registrati() {
        btnRegistrati.click();
    }


    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Welcome";
    }

}
