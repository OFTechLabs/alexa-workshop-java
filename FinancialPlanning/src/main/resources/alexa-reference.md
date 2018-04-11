## Alexa Basics

The basics of an Alexa skill and its API will be covered here, as a reference throughout the assignments.

### Speechlet

The speechlet is the endpoint that gets called whenever the user interacts with your skill, all user input should be handled by the speechlet. A speechlet has the following API:

```java
SpeechletResponse onLaunch(LaunchRequest launch, Session session); 

SpeechletResponse onIntent(IntentRequest intent, Session session);
```

The `onLaunch(..)` gets called whenever the user says 'Alexa, Start `SKILL_NAME`', the `onIntent(..)` gets called whenever the user says  'Alexa, ask `SKILL_NAME` for `INTENT`'. The speechlet should then return the phrase Alexa should say.

#### Stateless

Remeber: a `Speechlet` can have no _state_, it has to be completely _stateless_. We have no idea in what order what users will perform requests on your Alexa skill, so we cannot store any information in the speechlet itself. In Java terms think of the speechlet as a `static` function, it can not use any instance fields. 

### Intents

The intents are specific `String` id's which let your skill know what the user said. Each _utterance_ by the user is mapped to a specific _intent_, you can view `FinancialPlanning/src/main/resources/intents.json` to see what phrases will trigger which _intent_ in the `samples` field. The _intents_ are configured in the Alexa skill, we have done that part for you but you can view the conrfiguration in the `intents.json` mentioned.  

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

#### What did the user say exactly?

In short: you don't know. The exact phrase said by the user is not part of the input to the `Speechlet`, the phrase the user has actually said has already been mapped to an `Intent` by _Amazon_. 

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

The `text` will be spoken by Alexa and the `endSession` should be `true`  if the conversation is over and `false` if the conversation has not yet finished. If you need more answers from the user, or if you want to remember certain answers by the user, the session should _not_ be ended.

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