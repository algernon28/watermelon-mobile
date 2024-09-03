package com.hype.mobile.pages.statistiche;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import com.watermelon.core.Utils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ScenarioScoped
@Slf4j
public class StatistichePage extends MobilePage {

    private final String scrollView = "//*[@class='android.widget.ScrollView']";
    /**
     * Varie
     * Shopping
     * Alimentari
     * Ristoranti e...
     * Tasse e Servizi
     * Risparmi
     */
    private final String categoriaId = "it.hype.app:id/margin24";
    // es: 145,41 €
    private final String amountId = "it.hype.app:id/amount";
    @CacheLookup
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='it.hype.app:id/title' and @text='Uscite']")
    private WebElement title;

    @Inject
    public StatistichePage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //*[@resource-id='it.hype.app:id/title' and @text='Risparmi']/following-sibling::*[@resource-id='it.hype.app:id/subtitle']
    public List<Category> getCategories() {
        //Scroll down the list to the end to display all of the categories
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\")).flingToEnd(3)"));
        List<Category> result = new ArrayList<>();
        driver.findElements(By.id("it.hype.app:id/margin24")).forEach(el -> {
            String name = el.findElement(By.id("it.hype.app:id/title")).getText();
            var amount = el.findElement(By.id("it.hype.app:id/title")).getText();
            //Trasforma in double la cifra Es: 34,55 € -> 34.55
            double amountValue = Utils.getCurrencyValue(amount);
            var movements = el.findElement(By.id("it.hype.app:id/subtitle")).getText();
            //Prendi il primo numero della stringa Es: '3 movimenti' -> 3
            int movementsValue = new Scanner(movements).useDelimiter("\\D+").nextInt();
            result.add(new Category(name, amountValue, movementsValue));
        });
        log.debug("Categories found: {}", result);
        return result;
    }

    public int countCategories() {
        int count = getCategories().size();
        log.debug("Found num. {} categories", count);
        return count;
    }

    public void goToCategory(String label) {
        String locator = String.format("new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\"))" +
                ".scrollIntoView(new UiSelector().text(\"%s\"))", label);
        WebElement category = driver.findElement(AppiumBy.androidUIAutomator(locator));
        log.debug("CategoryType {} found", category.getText());
        click(category);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    public enum CategoryType {
        VARIE("Varie"), SHOPPING("Shopping"), ALIMENTARI("Alimentari"),
        RISTORANTI("Ristoranti e ..."), TASSE("Tasse e Servizi"), RISPARMI("Risparmi");
        public final String label;

        CategoryType(String label) {
            this.label = label;
        }
    }

    public static class Category {
        private final String name;
        private final double amount;
        private final int movements;

        public Category(String name, double amount, int movements) {
            this.name = name;
            this.amount = amount;
            this.movements = movements;
        }
    }

}
