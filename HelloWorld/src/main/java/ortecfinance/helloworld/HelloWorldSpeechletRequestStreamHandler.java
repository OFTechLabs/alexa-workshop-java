package ortecfinance.helloworld;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class HelloWorldSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds = new HashSet<>();

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add(
                "amzn1.ask.skill.c2de7b94-e961-45ac-8ca9-2f36f0024a83"
        );
        supportedApplicationIds.add(
                "amzn1.ask.skill.20cb402c-1415-4950-98cd-41e8751ff7d5"
        );
    }

    public HelloWorldSpeechletRequestStreamHandler() {
        super(new HelloWorldSpeechlet(), supportedApplicationIds);
    }
}
