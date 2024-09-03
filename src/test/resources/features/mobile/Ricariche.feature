@ricariche
Feature: Ricariche
  Testa le ricariche

  Background:
    Given Sono loggato come cliente esistente con username reloss@yahoo.it e password gnufgnuf con device 'non associato'

  Scenario: Verifica tipologie di ricariche
    When Sono nella sezione ricariche
    Then Sono disponibili le tipologie di ricarica:
      | Con altra carta                 |
      | Con altro conto                 |
      | Con l'accredito dello stipendio |
      | In contanti                     |

  Scenario: Verifica metodi di ricariche in contanti
    When Sono nella sezione Tipologia ricariche in contanti
    Then Sono disponibili le opzioni:
      | Al supermercato      |
      | In un punto SisalPay |

  @contanti @supermercato
  Scenario: Ricarica presso supermercato
    When Sono nella sezione ricariche supermercato
    And Effettuo una ricarica di '25' €
    Then Atterro sulla pagina del barcode, con riportata correttamente la cifra di '25 €'
    And Torno alla Home Page
