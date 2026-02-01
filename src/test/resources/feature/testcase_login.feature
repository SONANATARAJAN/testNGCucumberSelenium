Feature: Login Page
  Scenario Outline: User Start Login test Case
    When user Enter Login Page
    And User Enter Valid "<username>" and "<password>"
     Examples:
    |username |password|
    |practice |SuperSecretPassword!|

