package com.hype.mobile.pages.ricariche.cash;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@ScenarioScoped
public class RicaricaCashPage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Ricarica in contanti\")")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"In un punto SisalPay\")")
    private WebElement sisalPay;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Al supermercato\")")
    private WebElement supermercato;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnBack;

    @AndroidFindBy(xpath = "(//android.widget.LinearLayout)/child::*[@resource-id='it.hype.app:id/title']")
    private List<WebElement> availableCashLocations;

    @Inject
    public RicaricaCashPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectCashPoint(CASHPOINT cashpoint) {
        switch (cashpoint) {
            case SUPERMERCATO -> click(supermercato);
            case SISALPAY -> click(sisalPay);
        }
    }

    public List<String> cashPoints() {
        List<String> result = new ArrayList<>();
        availableCashLocations.forEach(element -> {
            result.add(element.getText());
        });
        return result;
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina Ricarica contanti";
    }

    public enum CASHPOINT {SUPERMERCATO, SISALPAY}

}
