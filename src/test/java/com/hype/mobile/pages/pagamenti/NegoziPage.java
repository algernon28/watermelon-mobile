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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@ScenarioScoped
@Slf4j
public class NegoziPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.RelativeLayout/descendant::android.widget.TextView[@text='Paga con Hype']")
    private WebElement title;

    @AndroidFindBy(id = "it.hype.app:id/search_editText")
    private WebElement txtSearch;

    @AndroidFindBy(id = "it.hype.app:id/search_icon")
    private WebElement searchIcon;

    @AndroidFindBy(id = "it.hype.app:id/inse_cod_button")
    private WebElement btnManualInsert;

    @Inject
    public NegoziPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        try {
            //Questa pagina tende a ricaricarsi subito, creando problemi...
            wait.withTimeout(Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOf(btnManualInsert));
        } catch (Exception e) {
            //ignore
        }
        return (checkIfLoaded(title) && checkIfLoaded(btnManualInsert));
    }

    public void search(String shopText) {
        type(txtSearch, shopText);
    }

    public boolean shopsFound() {
        List<WebElement> shopsInView = driver.findElements(By.id("it.hype.app:id/shop_name"));
        return shopsInView.size() > 0;
    }

    public void toInsertCode() {
        click(btnManualInsert);
    }

}
