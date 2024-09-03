@pagamenti
Feature: Pagamenti
  Testa i pagamenti

  Background:
    Given Sono nella sezione Pagamenti come cliente esistente con username next.noassocia@automation.com e password gnufgnuf con device 'non associato'

  @test
  Scenario: Verifica Pagamenti in negozio
    When Atterro sulla pagina Pagamenti in Negozio
    And Cerco manualmente il codice negozio 81FE3
    Then Pago un importo di 1.00 €