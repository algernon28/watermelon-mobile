package com.hype.mobile.pages.profilo;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.HomePageGeneric;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Informazioni personali
 * Giacenza media
 * Coordinate bancarie
 * Documenti
 * Modifica password
 * Tema
 */
@ScenarioScoped
public class ProfiloPage extends HomePageGeneric {
    @CacheLookup
    @AndroidFindBy(xpath = "//*[@text='Profilo']//parent::*[@resource-id='it.hype.app:id/titlebar']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/tuoconto']/following-sibling::*/descendant::*[@resource-id='it.hype.app:id/title']")
    private WebElement profilo;

    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/name_text")
    private WebElement fullName;
    //Es: IBAN: IT39M36772223000EM001958933
    @CacheLookup
    @AndroidFindBy(id = "iban")
    private WebElement iban;
    @CacheLookup
    @AndroidFindBy(id = "it.hype.app:id/tuoconto")
    private WebElement lblIlTuoConto;
    //Es: 100,00€ su 2500,00€
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"it.hype.app:id/counter_range\").instance(0)")
    private WebElement rangePrelievo;

    //Es: 0 su 2
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"it.hype.app:id/counter_range\").instance(1)")
    private WebElement rangeRicariche;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/premium_plan\"))")
    private WebElement btnDettagliConto;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/info_personali\"))")
    private WebElement btnInformazioniPersonali;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/giacenza_media\"))")
    private WebElement btnGiacenzaMedia;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/bank_coord\"))")
    private WebElement btnCoordinateBancarie;
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/documents\"))")
    private WebElement btnDocumenti;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/change_password\"))")
    private WebElement btnModificaPassword;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/change_theme\"))")
    private WebElement btnTema;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/triggers\"))")
    private WebElement btnNotifichePersonalizzate;
    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"it.hype.app:id/associate_device\"))")
    private WebElement btnDispositivoAssociato;

    @Inject
    protected ProfiloPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public ProfileType getType() {
        return ProfileType.fromLabel(profilo.getText());
    }

    public String getClientName() {
        return fullName.getText();
    }

    public String getIBAN() {
        return iban.getText().replaceAll("IBAN:", "").trim();
    }

    public void dettagliConto() {
        click(btnDettagliConto);
    }

    public Map<range, Double> rangePrelievi() {
        Map<range, Double> result = new HashMap<>();
        String[] values = rangePrelievo.getText().replaceAll("€", "").split("su");
        result.put(range.ESEGUITI, Double.parseDouble(values[0]));
        result.put(range.LIMITE, Double.parseDouble(values[1]));
        return result;
    }

    public Map<range, Double> rangeRicariche() {
        Map<range, Double> result = new HashMap<>();
        String[] values = rangeRicariche.getText().split("su");
        result.put(range.ESEGUITI, Double.parseDouble(values[0].trim()));
        result.put(range.LIMITE, Double.parseDouble(values[1].trim()));
        return result;
    }

    public void toInfoPersonali() {
        click(btnInformazioniPersonali);
    }

    public void toGiacenzaMedia() {
        click(btnGiacenzaMedia);
    }

    public void toCoordinateBancarie() {
        click(btnCoordinateBancarie);
    }

    public void toDocumenti() {
        click(btnDocumenti);
    }

    public void toModificaPassword() {
        click(btnModificaPassword);
    }

    public void toTema() {
        click(btnTema);
    }

    public void toNotifichePersonalizzate() {
        click(btnNotifichePersonalizzate);
    }

    public void toDispositivoAssociato() {
        click(btnDispositivoAssociato);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    @Override
    public String getPagename() {
        return "Profilo";
    }

    public enum ProfileType {
        NEXT("HYPE Next"), PREMIUM("HYPE Premium");
        private static final Map<String, ProfileType> LOOKUP = Arrays.stream(ProfileType.values())
                .collect(Collectors.toMap(ProfileType::toString, Function.identity()));
        private final String label;

        ProfileType(String label) {
            this.label = label;
        }

        public static ProfileType fromLabel(String label) {
            return LOOKUP.get(label);
        }

        @Override
        public String toString() {
            return this.label;
        }

    }

    public enum range {ESEGUITI, LIMITE}

}
