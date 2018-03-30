package ortecfinance.helloworld;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;

/**
 * This skill should say 'Hello world!' to the user when prompted, it should also handle general Alexa intents such as Stop, Cancel and
 * Help. The intents described below should be handled and the responses below should be used.
 */
public class HelloWorldSpeechlet implements Speechlet {

    // Responses to be given by the skill
    public static final String HELLO_WORLD_RESPONSE_TEXT = "Hello world!";
    public static final String STOP_RESPONSE_TEXT = "Goodbye";
    public static final String HELP_RESPONSE_TEXT = "I know nothing...";

    // Intents to be handled by the skill
    public static final String AMAZON_STOP_INTENT = "AMAZON.StopIntent";
    public static final String AMAZON_CANCEL_INTENT = "AMAZON.CancelIntent";
    public static final String AMAZON_HELP_INTENT = "AMAZON.HelpIntent";
    public static final String HELLO_WORLD_INTENT = "HelloWorld";

    /**
     * Doesn't require an implementation for this skill
     */
    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) {

    }


    /**
     * If the user says 'Alexa, start Hello World', this method will be called, in this case we do not get a command from the user but just
     * a launch request. This skill should handle a launch request by saying 'Hello world!'.
     *
     * @param launchRequest we don't need to use the launchRequest here, can safely be ignored
     * @param session       we don't need to use the session for this skill, can be safely ignored
     * @return always return 'Hello world!'
     */
    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(HELLO_WORLD_RESPONSE_TEXT);
        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    /**
     * The skill should say 'Hello world!' to the user and handle all general Alexa intents (Stop, Cancel and Help).
     *
     * @param intentRequest the request has all the information needed to determine what to give for a response
     * @param session       we do not need the session for this skill, this can be safely ignored here
     * @return the response to be spoken by Alexa
     */
    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) {
        String intentName = intentRequest.getIntent().getName();

        if (AMAZON_STOP_INTENT.equals(intentName) || AMAZON_CANCEL_INTENT.equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(STOP_RESPONSE_TEXT);
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else if (AMAZON_HELP_INTENT.equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(HELP_RESPONSE_TEXT);
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else if (HELLO_WORLD_INTENT.equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(HELLO_WORLD_RESPONSE_TEXT);
            return SpeechletResponse.newTellResponse(outputSpeech);
        }

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(HELP_RESPONSE_TEXT);
        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    /**
     * Doesn't require an implementation for this skill
     */
    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) {

    }
}
