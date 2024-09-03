package com.hype.mobile.steps.login;

import com.google.inject.Inject;
import com.hype.mobile.navigation.android.PreLogin;
import com.hype.mobile.pages.home.HomePageClienteMinore;
import com.hype.mobile.pages.home.HomePageClienteStandard;
import com.hype.mobile.pages.home.HomePageGeneric;
import com.hype.mobile.pages.home.HomePageNuovoCliente;
import com.hype.mobile.pages.home.navbar.MinorenniNavbar;
import com.hype.mobile.pages.intro.WelcomePage;
import com.hype.mobile.pages.login.*;
import com.watermelon.core.PageNotLoadedException;
import com.watermelon.steps.BaseSteps;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import static com.hype.mobile.pages.home.HomePageGeneric.HomePageType;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@ScenarioScoped
public class LoginSteps extends BaseSteps {

    @Inject
    PreLogin preLogin;

    @Inject
    AssociaDevicePage associaDevicePage;

    @Inject
    HomePageNuovoCliente homePageNuovoCliente;

    @Inject
    HomePageClienteStandard homePageClienteStandard;
    @Inject
    HomePageGeneric homePage;

    @Inject
    ErrorPageLogin errorPageLogin;
    @Inject
    private LoginPage loginPage;

    @Inject
    private WelcomePage welcomePage;

    @Inject
    private HomePageClienteMinore homePageClienteMinore;

    @Inject
    private MinorenniNavbar minorenniNavbar;

    @Inject
    private ConfermaDeviceAssociatoPage confermaDeviceAssociatoPage;

    @Inject
    private AccessoBloccatopage accessoBloccatopage;

    @Inject
    private ConfermaLoginRapidoPage confermaLoginRapidoPage;

    public LoginSteps() {
        super();
    }

    @Given("Sono nella pagina di login")
    public void i_land_on_the_login_page() throws Throwable {
        log.debug("Driver: {}", driver);
        //preLogin.deepLink();
        preLogin.skipWalkthru();
        assertThat(loginPage).as("Login page did not show").returns(true, from(LoginPage::isLoaded));
    }

    @When("Inserisco la username {string} e password {string}")
    public void i_enter_credentials(String username, String password) throws Throwable {
        log.debug("I enter credentials: {}/{}", username, password);
        loginPage.login(username, password);
    }

    private void associateDevice(boolean shouldAssociate) throws Throwable {
        assertThat(associaDevicePage).as("Pagina di associazione non caricata").returns(true, from(AssociaDevicePage::isLoaded));
        associaDevicePage.associate(shouldAssociate);
    }

    //Login con le credenziali corrette con device associato
    @Then("Atterro su una Home Page con device {string}")
    public void i_land_on_a_homepage(String associazione) throws Throwable {
        switch (associazione) {
            case "associato" -> {
                confermaDeviceAssociatoPage.verify();
                confermaDeviceAssociatoPage.submit();
                confermaLoginRapidoPage.verify();
                confermaLoginRapidoPage.close();
                homePageClienteStandard.verify();
            }
            case "non associato" -> {
                associateDevice(false);
                homePage.verify();
            }
            default -> fail("Scelta di associazione non contemplata (associato/non associato)");
        }
    }

    @Then("Vengono visualizzati {int} messaggi di errore")
    public void validation_messages(int errors) throws Throwable {
        int actual = loginPage.errorMessages();
        assertThat(loginPage).as("Non sono stati visualizzati gli errori attesi").returns(errors, LoginPage::errorMessages);
    }

    @Then("Viene visualizzata una pagina di errore")
    public void i_land_on_error_page() {
        assertThat(errorPageLogin).as("Pagina di errore non caricata").returns(true, from(ErrorPageLogin::isLoaded));
    }

    @When("Inserisco la username {string} con password errata per bloccare l'account")
    public void i_enter_credentials_n_times(String username) throws Throwable {
        int attempt = 1;
        do {
            i_enter_credentials(username, "wrongpassword");
            log.debug("Looping: {}/{} -> {}", username, "wrongpassword", attempt++);
            if (accessoBloccatopage.isLoaded()) {
                return;
            }
            errorPageLogin.verify();
            errorPageLogin.ok();
        } while (true);
    }

    @Then("Viene visualizzato una pagina di superamento soglia contenente {string}")
    public void i_land_on_max_attempts_errorpage(String expectedMessage) {
        String actual = accessoBloccatopage.getBody();
        assertThat(actual).as("L'utente non risulta correttamente bloccato").contains(expectedMessage);
    }

    @ParameterType("minore|nuovo|esistente")
    public HomePageType homePageType(String type) {
        return HomePageType.valueOf(type);
    }


    @Then("Atterro su una Home Page di un cliente {homePageType} con device {string}")
    public void i_land_on_profile_homepage(HomePageType profilo, String associazione) throws Throwable {
        i_land_on_a_homepage(associazione);
        switch (profilo) {
            case minore -> {
                homePageClienteMinore.verify();
                assertThat(minorenniNavbar).as("Homepage di cliente non minore").returns(true, MinorenniNavbar::isLoaded);
            }
            case nuovo -> {
                assertThat(homePageNuovoCliente).as("Homepage di cliente non nuovo").returns(true, HomePageNuovoCliente::isLoaded);
            }
            case esistente -> {
                assertThat(homePageClienteStandard).as("Homepage non corretta").returns(true, HomePageClienteStandard::isLoaded);
            }
            default -> {
                throw new PageNotLoadedException(String.format("Tipologia di profilo non gestita: %s", profilo));
            }
        }
    }
}