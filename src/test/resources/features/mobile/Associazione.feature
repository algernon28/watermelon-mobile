@login @associazione
Feature: Associazione
  Verifica l'associazione del device

  Background:
    Given Sono nella pagina di login

  @nuovocliente @nonassociato
  Scenario Outline: Verifico Associazione device
    When Inserisco la username '<username>' e password '<password>'
    And Atterro su una Home Page con device '<stato>'
    Then Apro il profilo e verifico che il cliente sia: '<stato>'
    Examples:
      | username                      | password | stato         |
      | next.noassocia@automation.com | gnufgnuf | non associato |
      | next.associa@automation.com   | gnufgnuf | associato     |
