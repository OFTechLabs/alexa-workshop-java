# Alexa Workshop in Java

There are several skills here that need an implementation for them to work, the templates are there as are the unit tests to verify whether they work. The skills can be implemented in any way as long as the tests verify the skill works according to the requirements.

After finishing the assignments you should be able to develop and publish Alexa skills on your own.

## Setup

The setup of the workshop requires Java 8 and Maven to be installed.

### Prerequisites

* Install Java 8
* Install maven

### Obtain workshop

Fork or clone the workshop repository

### Build

Open a terminal in the directory of the skill you want to build:
```
alexa-workshop-java/FinancialPlanning
```
To Build the skill run the following command:
```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
```
The `.jar` file will be stored in the following location:
```
alexa-workshop-java/FinancialPlanning/target/FinancialPlanning-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Unit testing

Open a terminal in the directory of the skill you want to test:
```
alexa-workshop-java/FinancialPlanning
```
Run maven:
```
mvn clean install
```

## Alexa Basics

The basics of an Alexa skill and its API will be covered here, as a reference throughout the assignments.

### Speechlet

The speechlet is the endpoint that gets called whenever the user interacts with your skill, all user input should be handled by the speechlet. A speechlet has the following API:

```java
SpeechletResponse onLaunch(LaunchRequest launch, Session session); 

SpeechletResponse onIntent(IntentRequest intent, Session session);
```

The `onLaunch(..)` gets called whenever the user says 'Alexa, Start `SKILL_NAME`', the `onIntent(..)` gets called whenever the user says  'Alexa, ask `SKILL_NAME` for `INTENT`'. The speechlet should then return the phrase Alexa should say.

### Intents

The intents are specific `String` id's which let your skill know what the user said. Each _utterance_ by the user is mapped to a specific _intent_, you can view `utterances.txt` to see what phrases will trigger which _intent_. The _intents_ are configured in the Alexa skill, we have done that part for you but you can view the conrfiguration in `intents.json`. 

```java
switch (intentRequest.getIntent().getName()) {
    case AMAZON_STOP_INTENT:
    case AMAZON_CANCEL_INTENT:
        outputSpeech.setText(STOP_RESPONSE_TEXT);
        return SpeechletResponse.newTellResponse(outputSpeech);
    case HELLO_WORLD_INTENT:
        outputSpeech.setText(HELLO_WORLD_RESPONSE_TEXT);
        return SpeechletResponse.newTellResponse(outputSpeech);
    case AMAZON_HELP_INTENT:
    default:
        outputSpeech.setText(HELP_RESPONSE_TEXT);
        return SpeechletResponse.newTellResponse(outputSpeech);
}
```

### Create responses

To create a response which Alexa can articulate, the following code can be used:

```java
public static SpeechletResponse Create(String text, boolean endSession)
{
    PlainTextOutputSpeech plainTextResponse = new PlainTextOutputSpeech();
    plainTextResponse.setText(text);

    SpeechletResponse speechletResponse = new SpeechletResponse();
    speechletResponse.setShouldEndSession(endSession);
    speechletResponse.setOutputSpeech(plainTextResponse);

    return speechletResponse;
}
```

The `text` will be spoken by Alexa and the `endSession` should be `true`  if the conversation is over and `false` if the conversation has not yet finished.

### Variables in user answers

The user might say a number to your skill, which is a variable, this can be handled in the following way:

```java
String value = intentRequest.getIntent().getSlot("SLOT_NAME").getValue();
```

The key (`SLOT_NAME`) is configured in the Alexa skill, but we did that for you and you can view the keys to use for the slots in the `FinancialPlanningSpeechlet`:

```java
public static String GOAL_AMOUNT_KEY = "GoalAmount";
public static String INITIAL_SAVINGS_KEY = "InitialSavings";
public static String MONTHLY_CONTRIBUTION_KEY = "MonthlyContribution";
public static String GOAL_PERIOD_KEY = "GoalPeriod";
public static String DYNAMIC_NUMBER_KEY = "Dynamic";
``` 

### Storing user answers

During the conversation we might have to store certain answers by the user, this can only be done in the `Session`. We can not store the variables in the skill themselves because each skill is supposed to be stateless. We can manage the user answers with the session in the following way:

```java
String value = intentRequest.getIntent().getSlot("SLOT_NAME").getValue();
session.setAttribute("SLOT_NAME", value);
```

If we do this we have to make sure the session is not ended, so we can keep the variables in the current session:

```java
SpeechletResponse speechletResponse = new SpeechletResponse();
speechletResponse.setShouldEndSession(false);
```

### Hello World Example

Below is an example of a working Alexa skill, including most of the basics covered above:

```java
public class HelloWorldSpeechlet implements Speechlet {

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) {

    }


    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Hello world!");
        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) {
        String intentName = intentRequest.getIntent().getName();
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        switch (intentName) {
            case "AMAZON.StopIntent":
            case "AMAZON>CancelIntent":
                outputSpeech.setText("Bye!");
                return SpeechletResponse.newTellResponse(outputSpeech);
            case "HelloWorldIntent":
                outputSpeech.setText("Hello world!");
                return SpeechletResponse.newTellResponse(outputSpeech);
            case "AMAZON.HelpIntent":
            default:
                outputSpeech.setText("I know nothing...");
                return SpeechletResponse.newTellResponse(outputSpeech);
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) {

    }
}
```
## Testing Skills
_Amazon accounts are required to deploy and test skills_

To test whether the skills would work on Alexa itself the skill will have to be deployed to Amazon Lambda and an Emulator can be used to see if it works.

### Deployment
_Please note that an Amazon Lambda account is required_

The following tutorial by Matthias Shapiro perfectly explains how to deploy a C# Amazon Alexa skill to Amazon Lambda: 

```
http://matthiasshapiro.com/2017/02/10/tutorial-alexa-skills-in-c-setup/
```
 
### Emulation
_Please note that an Amazon account is required_

 
To emulate whether a skill works without using an actual Amazon Echo or other hardware, the following online emulator can be used:

```
https://echosim.io/
``` 
