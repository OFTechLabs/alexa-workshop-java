# Financial Planning assignment

#TODO:
- walktrough
- screenshots

1. A basic financial planner has been implemented and is running on your Echo. It performs the following actions:
    - Introduction to the app when you say "Alexa start financial planning"
    - Alexa then asks you for your target amount
    - Answer an amount
    - Alexa then asks you for your monthly deposit
    - Answer an amount
    - Alexa then asks you for your time to target (number of months)
    - Answer an amount 
    - Alexa provides a feasibility of High
    
    You are going to expand the code to be able to ask for an initial amount.
    First verify that the code compiles and all tests are working by running: 
   
    ``` 
    mvn clean install
    ``` 
    in a console.
        
    Then add an intent to "FinancialPlanningIntents" with the value "SetInitialSavingsIntent".
    
    Next add a question to the "FinancialPlanningSpeechlet": "How much have you currently saved?".
    Also add a key here with the value "InitialSavings".
        
    Now update the following files: "EndSessionFactory", "NextQuestionFactory" and "ResponseTextFactory" 
    with the newly created constants. The new functionality should be very similar to the goal amount functionality.  
    
    Now expand the test in line with your new code; "EndSessionFactoryTest", "NextQuestionFactoryTest" 
    and "ResponseTextFactoryTest".
    
    Run again:
    
    ``` 
    mvn clean install
    ```
       
    When all succeeds it is time to upload your code and see if it works!   

2. You now have an Alexa app working as basic financial planner;
    * You can have a conversation in which she asks for input:
        * Target amount
        * Current amount
        * Monthly deposit
        * Time to target
    * Alexa calculates an basic outcome (e.g. high / low feasibility)
    
   Add a sensible calculation that provides a percentage as feasibility. This 
   should incorporate the principle that a longer time period should yield a 
   higher return and increase the feasibility to reach the desired goal.

3. Instead of stopping after the calculation, add the option to fix a low 
   feasibility. Use three possible options to make the goal feasible:
    * Lower the goal 
    * Increase the monthly deposit
    * Increase the time to target (a longer period should yield a higher feasibility) 
