package com.hype.mobile.steps.ricariche;

import com.google.inject.Inject;
import com.hype.mobile.pages.ricariche.RicarichePage;
import com.hype.mobile.pages.ricariche.cash.BarCodePage;
import com.hype.mobile.pages.ricariche.cash.ImportoRicaricaPage;
import com.hype.mobile.pages.ricariche.cash.RicaricaCashPage;
import com.hype.mobile.pages.ricariche.cash.RicaricaCashPage.CASHPOINT;
import com.hype.mobile.pages.ricariche.cash.RicaricaSupermercatoPage;
import com.watermelon.steps.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RicaricheSteps extends BaseSteps {


    @Inject
    RicarichePage ricarichePage;

    @Inject
    RicaricaCashPage ricaricaCashPage;

    @Inject
    RicaricaSupermercatoPage ricaricaSupermercatoPage;

    @Inject
    ImportoRicaricaPage importoRicaricaPage;

    @Inject
    BarCodePage barCodePage;

    @ParameterType("nuovo|esistente")
    public ClientType type(String type) {
        return ClientType.valueOf(type);
    }

    @Given("Sono nella sezione ricariche")
    public void i_land_on_recharge_page() {
        assertThat(ricarichePage).as("Pagina Ricariche non caricata").returns(true, RicarichePage::isLoaded);
    }

    @Then("Sono disponibili le tipologie di ricarica:")
    public void charge_type_available(DataTable data) {
        List<String> expected = data.asList();
        List<String> actual = ricarichePage.paymentTypes();
        log.debug("Expected typologies: {}", expected);
        log.debug("Actual typologies: {}", actual);
        //Check size first to save time, just in case...
        assertThat(actual).as("Numero delle opzioni non corretto").hasSameSizeAs(expected);
        assertThat(actual).as("Tipologie di pagamento non corrette").hasSameElementsAs(expected);
    }

    @Then("Sono nella sezione Tipologia ricariche in contanti")
    public void i_land_on_cash_page() {
        i_land_on_recharge_page();
        ricarichePage.selectPayment(RicarichePage.PAYMENT_TYPE.CASH);
        assertThat(ricaricaCashPage).as("Tipologie di ricarica cash non caricata").returns(true, RicaricaCashPage::isLoaded);
    }

    @And("Sono disponibili le opzioni:")
    public void cash_type_available(DataTable data) {
        List<String> expected = data.asList();
        List<String> actual = ricaricaCashPage.cashPoints();
        log.debug("Expected options: {}", expected);
        log.debug("Actual options: {}", actual);
        assertThat(actual).as("Numero delle opzioni cash non corretto").hasSameSizeAs(expected);
        assertThat(actual).as("Tipologie di pagamento cash non corrette").hasSameElementsAs(expected);
    }

    @Given("Sono nella sezione ricariche supermercato")
    public void i_land_on_ricariche_supermercato() {
        i_land_on_recharge_page();
        ricarichePage.selectPayment(RicarichePage.PAYMENT_TYPE.CASH);
        ricaricaCashPage.verify();
        ricaricaCashPage.selectCashPoint(CASHPOINT.SUPERMERCATO);
        ricaricaSupermercatoPage.verify();
        ricaricaSupermercatoPage.skipAll();
        importoRicaricaPage.verify();
    }

    @And("Effettuo una ricarica di {string} €")
    public void i_recharge_money(String amount) {
        importoRicaricaPage.typeAmount(amount);
    }

    @Then("Atterro sulla pagina del barcode, con riportata correttamente la cifra di {string}")
    public void i_land_on_barcode_page_with_correct_amount(String expectedAmount) {
        assertThat(barCodePage).as("Pagina Barcode non visualizzata correttamente").returns(true, BarCodePage::isLoaded);
        assertThat(barCodePage).as("Cifra della ricarica non riportata correttamente").returns(expectedAmount, BarCodePage::getAmount);
    }

    @And("Torno alla Home Page")
    public void i_go_home() {
        barCodePage.goBackHome();
    }

    public enum ClientType {nuovo, esistente}
}
