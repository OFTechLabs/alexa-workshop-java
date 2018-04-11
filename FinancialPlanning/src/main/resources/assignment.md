# Financial Planning assignment

All the code for this assignment can be found in `FinancialPlanning/src/main/java/com/ortecfinance/financialplanning/` we will continue to build on the `Java` code in there. Background information for Amazon Alexa skills can be found here:

```
FinancialPlanning/src/main/resources/alexa-reference.md
``` 

## Improve a basic financial planner 

A basic financial planner has been implemented and is running on your Echo. It performs the following actions:

- Introduction to the app when you say "Alexa start _ECHO_NAME_ON_STICKER_"
- Alexa then asks you for your target amount
- Answer an amount (see `FinancialPlanning/src/main/resources/intents.json` for sample phrases)
- Alexa then asks you for your monthly deposit
- Answer an amount
- Alexa then asks you for your time to target (number of months)
- Answer an amount 
- Alexa provides a feasibility of High

You are going to expand the code to be able to ask for an initial amount, after that expansion the basic conversation of a financial planner is ready.

### Adding the `SetInitialSavingsIntent`

First look at the intents.json:

```
FinancialPlanning/src/main/resources/intents.json
```

You will see an intent there that is not yet handled by the `Speechlet`:

```
{
  "name": "SetInitialSavings",
  "slots": [
    {
      "name": "InitialSavings",
      "type": "AMAZON.NUMBER"
    }
  ],
  "samples": [
    "my initial savings are {InitialSavings}",
    "savings are {InitialSavings}",
    "my current savings are {InitialSavings}"
  ]
}
```

We have to add the code to handle this `SetInitialSavings` intent.

#### Verify the current code can be build
First verify that the code compiles and all tests are working by running: 
   
``` 
mvn clean install
``` 

#### Adding intent

Add an intent to `FinancialPlanningIntents` with the value `SetInitialSavingsIntent`.

#### Add a question

Next add a question to the `FinancialPlanningSpeechlet`: "How much have you currently saved?". Also add a key here with the value `InitialSavings`.

#### Update the code that handles the question and response

Now update the following files: `EndSessionFactory`, `NextQuestionFactory` and `ResponseTextFactory` 
with the newly created constants. The new functionality should be very similar to the goal amount functionality.  

#### Add unit tests that verify the updated code

Now expand the test in line with your new code: `EndSessionFactoryTest`, `NextQuestionFactoryTest` 
and `ResponseTextFactoryTest`.

Run the tests again:

``` 
mvn clean install
```

#### Build the skill with updated code

If compilation and tests are successful, run the following command:

```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
```

#### Deploy & Test

Now it is time to upload your code and see if it works!
Contact a member of the team to deploy the solution.   


## Extra additions

Since you have a basic working solution now, here are several ideas on how to improve the current skill you have. You can do them in any order.

### Extra: Better feasibility calculation
* You can have a conversation in which she asks for input:
    * Target amount
    * Current amount
    * Monthly deposit
    * Time to target
* Alexa calculates an basic outcome (e.g. high / low feasibility)

Add a sensible calculation that provides a percentage as feasibility. This should incorporate the principle that a longer time period should yield a higher return and increase the feasibility to reach the desired goal.

_Note: Update the `FeasibilityCalculator` class with a better approach._

### Extra: Better UX through dynamic numbers
Enable Alexa to understand it if you only provide a number for each question instead of full utterances. See the ignored test in `FinancialPlanningSpeechletTest` for an example conversation your skill would have to handle. Once this is done users can simply answer in only numbers, instead of constantly uttering the full phrases.

_Note: in this approach all numbers uttered by the user map to the `SET_DYNAMIC_NUMBER_INTENT`, you will have to figure out a way to decide what question the user answered, it could be any of the four questions._

### Extra: Advanced financial planning conversation

Instead of stopping after the calculation, add the option to fix a low feasibility. Use three possible options to make the goal feasible:
 
* Lower the goal 
* Increase the monthly deposit
* Increase the time to target (a longer period should yield a higher feasibility) 

_Note: you will need to add your own intents here, update the `intents.json` and the `Java` code to handle the extra intents. We can deploy the `intents.json` and `.jar` file for you once you are ready._

### Extra: Your own idea

Feel free to improve on the current skill with your own idea's, the goal is to create a skill that helps people achieve their financial goals, in this extra you get total freedom to achieve that goal.

_Note: you will need to add your own intents here, update the `intents.json` and the `Java` code to handle the extra intents. We can deploy the `intents.json` and `.jar` file for you once you are ready._ 