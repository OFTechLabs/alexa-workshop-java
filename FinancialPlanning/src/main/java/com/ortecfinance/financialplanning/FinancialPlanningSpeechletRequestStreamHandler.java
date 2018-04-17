package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * There is no need to modify the stream handler
 */
public class FinancialPlanningSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds = new HashSet<>();

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.addAll(Arrays.asList(
                "amzn1.ask.skill.f6b2e43b-6840-4b0b-8ace-ec66863d4b88",
                "amzn1.ask.skill.ae6d2f64-67cc-41ea-8826-b3dfeb86368d",
                "amzn1.ask.skill.e6d89d3c-bb91-4f48-b58f-901436b7e3e0",
                "amzn1.ask.skill.9eea86d6-d631-405d-8adc-a3d7afbc7894",
                "amzn1.ask.skill.740281e8-043d-4cf7-9b97-0296940faaa7",
                "amzn1.ask.skill.d2aa94cf-b164-4876-9ec2-30e42c343189",
                "amzn1.ask.skill.f6b2e43b-6840-4b0b-8ace-ec66863d4b88"
        ));
    }

    public FinancialPlanningSpeechletRequestStreamHandler() {
        super(new FinancialPlanningSpeechlet(), supportedApplicationIds);
    }
}
