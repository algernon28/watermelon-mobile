package com.hype.mobile.pages.pagamenti;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ScenarioScoped
@Slf4j
public class PagamentiPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc='Pagamenti' and @resource-id='it.hype.app:id/collapsing_toolbar']")
    private WebElement title;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/margin24")
    private List<WebElement> functionList;

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
    public PagamentiPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }


    public enum Payment {
        INVIA("Invia Denaro"), RICHIEDI("Richiedi Denaro"), BONIFICO("Bonifico"), PAGA_IN_NEGOZIO("Paga in Negozio"), BOLLETTINO("Bollettino");
        private static final Map<String, Payment> lookup = Arrays.stream(Payment.values())
                .collect(Collectors.toMap(p -> p.label, Function.identity()));
        public final String label;

        Payment(String label) {
            this.label = label;
        }

        public static Payment fromLabel(String label) {
            return lookup.get(label);
        }
    }

    public void payWith(Payment payment){
        String locatorString = String.format("//android.widget.TextView[@resource-id='it.hype.app:id/title' and @text='%s']", payment.label);
        WebElement paymentMethod = driver.findElement(By.xpath(locatorString));
        click(paymentMethod);
    }

}
