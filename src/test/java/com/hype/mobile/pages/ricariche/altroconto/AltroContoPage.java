package com.hype.mobile.pages.ricariche.altroconto;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

@ScenarioScoped
public class AltroContoPage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ricarica con bonifico\")")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Condividi IBAN\")")
    private WebElement btnShareIBAN;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ricarica da altri conti\")")
    private WebElement btnOtherAccounts;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnBack;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"it.hype.app:id/iconLeft\")")
    private List<WebElement> availableMethods;

    @Inject
    public AltroContoPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectPoint(CASHPOINT cashpoint) {
        switch (cashpoint) {
            case IBAN -> click(btnShareIBAN);
            case ALTRI_CONTI -> click(btnOtherAccounts);
        }
    }

    public List<String> cashPoints() {
        List<String> result = new ArrayList<>();
        availableMethods.forEach(element -> {
            WebElement name = driver.findElement(with(By.id("it.hype.app:id/title")).toRightOf(element));
            result.add(name.getText());
        });
        return result;
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Altro Conto";
    }

    public enum CASHPOINT {IBAN, ALTRI_CONTI}

}
