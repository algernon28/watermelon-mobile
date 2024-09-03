@login
Feature: Login
  Testa la login con varie combinazioni di credenziali

  Background:
    Given Sono nella pagina di login

  @positive
  Scenario Outline: Login con le credenziali corrette senza associare il device
    When Inserisco la username '<username>' e password '<password>'
    Then Atterro su una Home Page con device '<stato>'
    Examples:
      | username                      | password | stato         |
      | next.noassocia@automation.com | gnufgnuf | non associato |
      | reloss@yahoo.it               | gnufgnuf | associato     |


  @negative
  Scenario Outline: Login con credenziali mancanti
    When Inserisco la username '<username>' e password '<password>'
    Then Vengono visualizzati <errori> messaggi di errore

    Examples:
      | username                      | password    | errori |
      |                               |             | 2      |
      |                               | anypassword | 1      |
      | next.noassocia@automation.com |             | 1      |
      | anyusername                   |             | 1      |

  @negative
  Scenario Outline: Login con credenziali errate
    When Inserisco la username '<username>' e password '<password>'
    Then Viene visualizzata una pagina di errore
    Examples:
      | username                      | password                                        |
      | next.noassocia@automation.com | wrongpassword                                   |
      | next.noassocia@automation.com | veryverylongwrongpasswordwithverymanycharacters |
      | next.noassocia@automation.com | pwdwithspecialcharacters§ç                      |
      | next.noassociaautomation.com  | gnufgnuf                                        |
      | wrongusername                 | gnufgnuf                                        |

  @negative
  Scenario Outline: Login con cliente bloccato
    When Inserisco la username '<username>' con password errata per bloccare l'account
    Then Viene visualizzato una pagina di superamento soglia contenente '<errore>'
    Examples:
      | username                     | errore                      |
      | next.bloccato@automation.com | numero massimo di tentativi |

