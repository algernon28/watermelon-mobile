@homepage
Feature: Home Page
  Verifica la correttezza delle homepage

  Scenario: Visualizzazione homepage di un cliente minore con saldo nullo
    Given Sono nella pagina di login
    When Inserisco la username 'minore@automation.com' e password 'gnufgnuf'
    Then Atterro su una Home Page di un cliente minore con device 'non associato'
    And Verifico che il saldo sia esattamente 0

  Scenario: Visualizzazione homepage di un cliente premium esistente con saldo positivo
    Given Sono nella pagina di login
    When Inserisco la username 'premium@automation.com' e password 'gnufgnuf'
    And Atterro su una Home Page di un cliente esistente con device 'non associato'
    And Verifico che il profilo sia premium
    Then Verifico che il saldo sia positivo

  Scenario: Visualizzazione homepage di un cliente next nuovo con saldo nullo
    Given Sono nella pagina di login
    When Inserisco la username 'next.associa@automation.com' e password 'gnufgnuf'
    Then Atterro su una Home Page di un cliente nuovo con device 'associato'
    And Verifico che il saldo sia esattamente 0

  Scenario: Controllo Saldo disponibile
    Given Sono nella pagina di login
    When Inserisco la username 'reloss@yahoo.it' e password 'gnufgnuf'
    Then Atterro su una Home Page di un cliente esistente con device 'associato'
    And Verifico che il saldo sia esattamente 1091.20

  Scenario: Ricariche
    Given Sono loggato come cliente esistente con username reloss@yahoo.it e password gnufgnuf con device 'non associato'
    When Apro la sezione Ricariche da Home
    Then Sono nella sezione ricariche

  Scenario: Visibilità Movimenti
    Given Sono loggato come cliente esistente con username reloss@yahoo.it e password gnufgnuf con device 'non associato'
    And Apro i Dettagli da Home
    Then Viene visualizzata la sezione dettagli

  Scenario Outline: Funzionalità da Banner
    Given Sono loggato come cliente esistente con username next.noassocia@automation.com e password gnufgnuf con device 'non associato'
    Then Apro la funzionalità <funzionalità> da banner e viene visualizzata la pagina corretta
    Examples:
      | funzionalità |
      | STATISTICHE  |
      | RISPARMI     |
      | CASHBACK     |

  Scenario Outline: Funzionalità da Navbar
    Given Sono loggato come cliente esistente con username next.noassocia@automation.com e password gnufgnuf con device 'non associato'
    Then Apro la funzionalità <funzionalità> da navbar e viene visualizzata la pagina corretta
    Examples:
      | funzionalità |
      | Radar        |
      | Pagamenti    |
      | Investimenti |
      | Risparmi     |
      | Home         |
