package com.hype.mobile.pages.ricariche;

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
public class RicarichePage extends MobilePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Ricariche\")")
    private WebElement title;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Con altra carta\")")
    private WebElement otherCard;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Con altro conto\")")
    private WebElement otherAccount;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Con l'accredito dello stipendio\")")
    private WebElement salaryCredit;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"In contanti\")")
    private WebElement cash;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageButton\").instance(0)")
    private WebElement btnBack;

    @AndroidFindBy(id = "it.hype.app:id/title")
    private List<WebElement> availablePaymentTypes;

    @Inject
    public RicarichePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectPayment(PAYMENT_TYPE type) {
        switch (type) {
            case OTHER_CARD -> click(otherCard);
            case OTHER_ACCOUNT -> click(otherAccount);
            case SALARY_CREDIT -> click(salaryCredit);
            case CASH -> click(cash);
        }
    }

    public List<String> paymentTypes() {
        List<String> result = new ArrayList<>();
        availablePaymentTypes.forEach(element -> result.add(element.getText()));
        return result;
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Pagina ricariche";
    }

    public enum PAYMENT_TYPE {OTHER_CARD, OTHER_ACCOUNT, SALARY_CREDIT, CASH}

}
