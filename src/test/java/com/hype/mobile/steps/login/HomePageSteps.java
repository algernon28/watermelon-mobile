package com.hype.mobile.steps.login;

import com.google.inject.Inject;
import com.hype.mobile.pages.cashback.CashBackPage;
import com.hype.mobile.pages.home.DettagliPage;
import com.hype.mobile.pages.home.HomePageClienteStandard;
import com.hype.mobile.pages.home.HomePageGeneric;
import com.hype.mobile.pages.home.HomePageNuovoCliente;
import com.hype.mobile.pages.home.navbar.MinorenniNavbar;
import com.hype.mobile.pages.home.navbar.Navbar;
import com.hype.mobile.pages.home.navbar.StandardNavbar;
import com.hype.mobile.pages.investimenti.InvestimentiPage;
import com.hype.mobile.pages.pagamenti.PagamentiPage;
import com.hype.mobile.pages.radar.RadarPage;
import com.hype.mobile.pages.radar.RadarWalkthruPage;
import com.hype.mobile.pages.risparmi.RisparmiPage;
import com.hype.mobile.pages.risparmi.RisparmiWalkthruPage;
import com.hype.mobile.pages.statistiche.StatistichePage;
import com.hype.mobile.steps.ricariche.RicaricheSteps;
import com.watermelon.steps.BaseSteps;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hype.mobile.pages.home.HomePageClienteStandard.Badge;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class HomePageSteps extends BaseSteps {

    @Inject
    RadarWalkthruPage radarWalkthruPage;
    @Inject
    RisparmiWalkthruPage risparmiWalkthruPage;
    @Inject
    RadarPage radarPage;
    @Inject
    PagamentiPage pagamentiPage;
    @Inject
    DettagliPage dettagliPage;

    @Inject
    LoginSteps loginSteps;
    @Inject
    InvestimentiPage investimentiPage;
    @Inject
    RisparmiPage risparmiPage;
    @Inject
    CashBackPage cashBackPage;
    @Inject
    private HomePageGeneric homePage;
    @Inject
    private HomePageClienteStandard homePageClienteStandard;

    @Inject
    private HomePageNuovoCliente homePageNuovoCliente;
    @Inject
    private Navbar navbar;
    @Inject
    private StandardNavbar standardNavbar;
    @Inject
    private MinorenniNavbar minorenniNavbar;
    @Inject
    private StatistichePage statistichePage;

    public HomePageSteps() {
        super();
    }

    @When("Apro i Dettagli da Home")
    public void i_open_details_from_home() {
        homePage.showDetails();
    }

    @Then("Viene visualizzata la sezione dettagli")
    public void i_see_details() {
        dettagliPage.verify();
    }

    @ParameterType("STATISTICHE|RISPARMI|CASHBACK")
    public Badge badgeItem(String badgeName) {
        return Badge.valueOf(badgeName);
    }

    @When("Apro la funzionalità {badgeItem} da banner e viene visualizzata la pagina corretta")
    public void i_open_functionality_from_banner(Badge badgeItem) {
        log.debug("Apro la pagina '{}' da banner", badgeItem.name());
        homePageClienteStandard.toBadge(badgeItem);
        switch (badgeItem) {
            case STATISTICHE -> statistichePage.verify();
            case RISPARMI -> {
                if (risparmiWalkthruPage.isLoaded()) {
                    risparmiWalkthruPage.skipAll();
                }
                risparmiPage.verify();
            }
            case CASHBACK -> cashBackPage.verify();
        }
    }

    @ParameterType("navbar|banner")
    public Navigation navigation(String balance) {
        return Navigation.valueOf(balance);
    }

    @When("Apro la sezione Ricariche da Home")
    public void i_click_recharge() {
        homePage.recharge();
    }

    @ParameterType("Home|Radar|Pagamenti|Investimenti|Risparmi")
    public NavbarItem navbarButton(String buttonName) {
        return NavbarItem.fromLabel(buttonName);
    }

    @Then("Apro la funzionalità {navbarButton} da navbar e viene visualizzata la pagina corretta")
    public void i_open_functionality_from_navbar(NavbarItem navbarButton) {
        log.debug("Apro la pagina '{}' da navbar", navbarButton.label);
        switch (navbarButton) {
            case HOME -> {
                navbar.toHome();
                log.debug("Verifico '{}'", homePage.getPagename());
                homePage.verify();
            }
            case RADAR -> {
                navbar.toRadar();
                log.debug("Verifico Walkthru Radar...");
                if (radarWalkthruPage.isLoaded()) {
                    log.debug("Sezione Walkthru di Radar presente, skipping...");
                    radarWalkthruPage.skipAll();
                }
                log.debug("Verifico '{}'", radarPage.getPagename());
                radarPage.verify();
            }
            case PAGAMENTI -> {
                navbar.toPagamenti();
                log.debug("Verifico '{}'", pagamentiPage.getPagename());
                pagamentiPage.verify();
                //paga
            }
            case INVESTIMENTI -> {
                standardNavbar.toInvestimenti();
                log.debug("Verifico '{}'", investimentiPage.getPagename());
                investimentiPage.verify();
            }
        }
    }

    public enum NavbarItem {
        HOME("Menu"), INVESTIMENTI("Investimenti"), PAGAMENTI("Pagamenti"), RADAR("Radar"), RISPARMI("Risparmi");
        private static final Map<String, NavbarItem> lookup = Arrays.stream(NavbarItem.values())
                .collect(Collectors.toMap(e -> e.label, Function.identity()));
        public final String label;

        NavbarItem(String label) {
            this.label = label;
        }

        public static NavbarItem fromLabel(String label) {
            return lookup.get(label);
        }
    }

    public enum Navigation {navbar, banner}

    @Given("Sono loggato come cliente {type} con username {word} e password {word} con device {string}")
    public void i_am_logged_as_client(RicaricheSteps.ClientType type, String username, String password, String association) throws Throwable {
        loginSteps.i_land_on_the_login_page();
        loginSteps.i_enter_credentials(username, password);
        loginSteps.i_land_on_a_homepage(association);
        boolean isHomePageLoaded;
        switch (type) {
            case esistente -> isHomePageLoaded = homePageClienteStandard.isLoaded();
            case nuovo -> isHomePageLoaded = homePageNuovoCliente.isLoaded();
            default -> isHomePageLoaded = false;
        }
        assertThat(isHomePageLoaded).as("Home page non caricata correttamente").isTrue();
    }
}
