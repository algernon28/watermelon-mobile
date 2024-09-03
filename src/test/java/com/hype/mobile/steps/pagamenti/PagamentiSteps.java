package com.hype.mobile.steps.pagamenti;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.HomePageGeneric;
import com.hype.mobile.pages.pagamenti.*;
import com.hype.mobile.steps.login.HomePageSteps;
import com.hype.mobile.steps.ricariche.RicaricheSteps;
import com.watermelon.core.Utils;
import com.watermelon.steps.BaseSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PagamentiSteps extends BaseSteps {

    @Inject
    HomePageSteps homePageSteps;

    @Inject
    PagamentiPage pagamentiPage;

    @Inject
    PagaNegozioPage pagaNegozioPage;

    @Inject
    CercaNegozioPage cercaNegozioPage;

    @Inject
    NegoziPage negoziPage;

    @Inject
    RiepilogoPagamentoPage riepilogoPagamentoPage;

    @Inject
    ConfermaPagamentoPage confermaPagamentoPage;

    @Inject
    ConclusionePage conclusionePage;

    @Inject
    HomePageGeneric homePage;

    @When("Sono nella sezione Pagamenti come cliente {type} con username {word} e password {word} con device {string}")
    public void i_land_in_pagamenti(RicaricheSteps.ClientType type, String username, String password, String association) throws Throwable {
        homePageSteps.i_am_logged_as_client(type, username, password, association);
        homePageSteps.i_open_functionality_from_navbar(HomePageSteps.NavbarItem.PAGAMENTI);
        pagamentiPage.verify();
    }

    @When("Atterro sulla pagina Pagamenti in Negozio")
    public void i_land_on_pagamenti_negozio(){
        pagamentiPage.payWith(PagamentiPage.Payment.PAGA_IN_NEGOZIO);
        negoziPage.verify();
    }

    @When("Cerco manualmente il codice negozio {word}")
    public void i_search_shopcode(String shopCode){
        negoziPage.toInsertCode();
        cercaNegozioPage.verify();
        cercaNegozioPage.enterCode(shopCode);
        pagaNegozioPage.verify();
    }

    @Then("Pago un importo di {float} €")
    public void i_pay_amount(double amount){
        String amountString = String.valueOf(amount);
        log.debug("Immetto pagamento di {} €", amountString);
        pagaNegozioPage.enterAmount(amountString);
        riepilogoPagamentoPage.verify();
        String actualString = riepilogoPagamentoPage.getAmount();
        double actualDouble = Utils.getCurrencyValue(actualString, Currency.getInstance("EUR"));
        assertThat(actualDouble).as("Il totale nel riepilogo non corrisponde").isEqualTo(amount);
        riepilogoPagamentoPage.pay();
        conclusionePage.verify();
        conclusionePage.backToHome();
        homePage.verify();
    }

}
