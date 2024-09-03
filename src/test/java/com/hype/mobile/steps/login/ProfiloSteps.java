package com.hype.mobile.steps.login;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.HomePageGeneric;
import com.hype.mobile.pages.home.HomePageNuovoCliente;
import com.hype.mobile.pages.home.menu.MenuPage;
import com.hype.mobile.pages.home.navbar.Navbar;
import com.hype.mobile.pages.profilo.DispositivoAssociatoPage;
import com.hype.mobile.pages.profilo.ProfiloPage;
import com.watermelon.core.Utils;
import com.watermelon.steps.BaseSteps;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.util.Currency;

import static com.hype.mobile.pages.profilo.ProfiloPage.ProfileType;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProfiloSteps extends BaseSteps {

    @Inject
    HomePageNuovoCliente homePageNuovoCliente;
    @Inject
    MenuPage menuPage;
    @Inject
    ProfiloPage profiloPage;
    @Inject
    DispositivoAssociatoPage dispositivoAssociatoPage;
    @Inject
    LoginSteps loginSteps;
    @Inject
    private HomePageGeneric homePage;
    @Inject
    private Navbar navbar;

    public ProfiloSteps() {
        super();
    }

    @ParameterType("positivo|negativo")
    public Balance balance(String balance) {
        return Balance.valueOf(balance);
    }

    public void goToProfilo() {
        log.debug("Navbar OK {}. Seleziono Menu da Navbar...", navbar.getPagename());
        navbar.toMenu();
        log.debug("Verifica pagina Menu");
        menuPage.verify();
        log.debug("Menu OK: Seleziono Profilo dal menu...");
        menuPage.selectTopButton("Profilo");
        log.debug("Verifica pagina Profilo...");
        profiloPage.verify();
        log.debug("Pagina Profilo verificata");
    }

    @Then("Apro il profilo e verifico che il cliente sia: {string}")
    public void i_land_on_association_state(String associazione) throws Throwable {
        boolean isAssociated;
        switch (associazione) {
            case "associato" -> isAssociated = true;
            case "non associato" -> isAssociated = false;
            default -> throw new IllegalArgumentException("Stato non previsto: " + associazione);
        }
        log.debug("Apertura Menu da navbar");
        goToProfilo();
        log.debug("Verifica pagina Dispositivo Associato");
        dispositivoAssociatoPage.verify();
        assertThat(dispositivoAssociatoPage).as("Associazione non corretta").returns(isAssociated, DispositivoAssociatoPage::isAssociated);
    }

    @Then("Verifico che il saldo sia esattamente {float}")
    public void i_check_the_exact_balance(double balance) {
        log.debug("Controllo saldo...");
        String saldo = homePage.getMoneyAvailable();
        log.debug("Saldo stringa = {}", saldo);
        String saldoCleaned = saldo.replace("\u00A0", "");
        double actual = Utils.getCurrencyValue(saldo, Currency.getInstance("EUR"));
        log.debug("Saldo currency = {}", actual);
        assertThat(actual).as("Il saldo non corrisponde").isEqualByComparingTo(balance);
    }

    @Then("Verifico che il saldo sia {balance}")
    public void i_check_the_balance(Balance expected) {
        navbar.toHome();
        String saldo = Utils.trimNonBreakingSpaces(homePage.getMoneyAvailable());
        double actual = Utils.getCurrencyValue(saldo, Currency.getInstance("EUR"));
        switch (expected) {
            case positivo -> assertThat(actual).as("Il saldo non corrisponde").isPositive();
            case negativo -> assertThat(actual).as("Il saldo non corrisponde").isNegative();
        }
    }

    @ParameterType("premium|next")
    public ProfileType profileType(String profile) {
        switch (profile) {
            case "premium" -> {
                return ProfileType.PREMIUM;
            }
            case "next" -> {
                return ProfileType.NEXT;
            }
            default -> throw new RuntimeException(String.format("Profilo non gestito: %s", profile));
        }
    }

    @Then("Verifico che il profilo sia {profileType}")
    public void i_check_profile_is_premium(ProfileType profileType) throws Throwable {
        goToProfilo();
        profiloPage.verify();
        assertThat(profiloPage).as("Profilo non corrispondente").returns(profileType, ProfiloPage::getType);
        driver.navigate().back();
    }

    public enum Balance {positivo, negativo}

}
