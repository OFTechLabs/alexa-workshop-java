package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class FinancialPlanningSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds = new HashSet<>();

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add(
                "amzn1.ask.skill.f6b2e43b-6840-4b0b-8ace-ec66863d4b88"
        );
        supportedApplicationIds.add(
                "amzn1.ask.skill.ae6d2f64-67cc-41ea-8826-b3dfeb86368d"
        );
    }

    public FinancialPlanningSpeechletRequestStreamHandler() {
        super(new FinancialPlanningSpeechlet(), supportedApplicationIds);
    }
}
