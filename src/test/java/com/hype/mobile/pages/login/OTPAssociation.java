package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@ScenarioScoped
public class OTPAssociation extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Codice di sicurezza\")")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/field")
    private WebElement txtOTP;

    @AndroidFindBy(id = "it.hype.app:id/subtitle")
    private WebElement subtitle;

    @AndroidFindBy(id = "it.hype.app:id/accedi")
    private WebElement btnAssocia;

    @Inject
    protected OTPAssociation(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void submit() {
        click(btnAssocia);
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
        return "Pagina di OTP di associazione device";
    }
}
