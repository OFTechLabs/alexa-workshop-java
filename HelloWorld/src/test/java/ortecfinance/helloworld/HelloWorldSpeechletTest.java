package ortecfinance.helloworld;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static ortecfinance.helloworld.HelloWorldSpeechlet.*;

public class HelloWorldSpeechletTest {

    private IntentRequest basicIntent;
    private Intent intent;
    private HelloWorldSpeechlet skill;

    @Before
    public void setUp() {
        intent = Intent.builder().withName("HelloWorld").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();
        skill  = new HelloWorldSpeechlet();
    }

    @Test
    public void shouldSayHelloWorld() {
        SpeechletResponse result = skill.onIntent(basicIntent, null);
        String output = ((PlainTextOutputSpeech) result.getOutputSpeech()).getText();

        assertEquals(output, HELLO_WORLD_RESPONSE_TEXT);
    }

    @Test
    public void shouldHandleCancelCorrectlyInHelloWorld() {
        intent = Intent.builder().withName("AMAZON.CancelIntent").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        String output = ((PlainTextOutputSpeech) result.getOutputSpeech()).getText();

        assertEquals(output, STOP_RESPONSE_TEXT);
    }

    @Test
    public void shouldHandleStopCorrectlyInHelloWorld() {
        intent = Intent.builder().withName("AMAZON.StopIntent").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        String output = ((PlainTextOutputSpeech) result.getOutputSpeech()).getText();

        assertEquals(output, STOP_RESPONSE_TEXT);
    }

    @Test
    public void shouldHandleHelpCorrectlyInHelloWorld() {
        intent = Intent.builder().withName("AMAZON.HelpIntent").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        String output = ((PlainTextOutputSpeech) result.getOutputSpeech()).getText();

        assertEquals(output, HELP_RESPONSE_TEXT);
    }

    @Test
    public void shouldHandleUnknownIntentInHelloWorld() {
        intent = Intent.builder().withName("SomethingElse").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        String output = ((PlainTextOutputSpeech) result.getOutputSpeech()).getText();

        assertEquals(output, HELP_RESPONSE_TEXT);
    }

    @Test
    public void shouldEndSessionOnCancelIntentForHelloWorld() {
        intent = Intent.builder().withName("AMAZON.CancelIntent").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        assertTrue(result.getShouldEndSession());
    }

    @Test
    public void shouldEndSessionOnStopIntentForHelloWorld() {
        intent = Intent.builder().withName("AMAZON.StopIntent").build();
        basicIntent = IntentRequest.builder().withIntent(intent).withRequestId("SOME_ID").build();

        SpeechletResponse result = skill.onIntent(basicIntent, null);
        assertTrue(result.getShouldEndSession());
    }

    @Test
    public void shouldHandleLaunchRequest() {
        SpeechletResponse onLaunch = skill.onLaunch(LaunchRequest.builder().withRequestId("SOME_ID").build(), null);
        String output = ((PlainTextOutputSpeech) onLaunch.getOutputSpeech()).getText();

        assertEquals(output, HELLO_WORLD_RESPONSE_TEXT);
    }
}

