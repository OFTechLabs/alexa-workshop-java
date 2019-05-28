package com.ortecfinance.financialplanning;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import java.util.HashMap;
import java.util.Map;

import static com.ortecfinance.financialplanning.FinancialPlanningIntents.*;

public class FinancialPlanningTestSimulation {

    private static final FinancialPlanningSpeechlet FINANCIAL_PLANNING_SPEECHLET = new FinancialPlanningSpeechlet();

    /**
     * Uncomment the request you want to simulate, you can also debug run the function if you are comfortable
     * with the debugger.
     */
    public static void main(String[] args) {

        /* these are the existing intents */
        IntentRequest request = createSetGoalAmountIntent();
        //IntentRequest request = createSetMonthlyContributionIntent();
        //IntentRequest request = createSetGoalPeriodIntent();

        /* this is the new intent you have to handle in assignment A1*/
        //IntentRequest request = createSetInitialSavingsIntent();

        /* this the new intent you have to handle in assignment A2 */
        //IntentRequest request = createDynamicNumberIntent();

        Session session = Session.builder().withSessionId("s444").build();
        SpeechletResponse speechletResponse = FINANCIAL_PLANNING_SPEECHLET.onIntent(
                request,
                session
        );

        System.out.println("Intent: " + request.getIntent().getName());
        System.out.println("Output: " + ((PlainTextOutputSpeech) speechletResponse.getOutputSpeech()).getText());
        System.out.println("End Session: " + speechletResponse.getNullableShouldEndSession());
        System.out.println("Stored in session:");
        session.getAttributes().entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }

    public static IntentRequest createSetGoalAmountIntent() {
        Map<String, Slot> requestArguments = new HashMap<>();
        requestArguments.put(
                FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY).withValue("400.0").build()
        );

        return createRequest(
                SET_GOAL_AMOUNT_INTENT,
                requestArguments
        );
    }

    public static IntentRequest createSetMonthlyContributionIntent() {
        Map<String, Slot> requestArguments = new HashMap<>();
        requestArguments.put(
                FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY).withValue("100.0").build()
        );

        return createRequest(
                SET_MONTHLY_CONTRIBUTION_INTENT,
                requestArguments
        );
    }

    public static IntentRequest createSetGoalPeriodIntent() {
        Map<String, Slot> requestArguments = new HashMap<>();
        requestArguments.put(
                FinancialPlanningSpeechlet.GOAL_PERIOD_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY).withValue("10.0").build()
        );

        return createRequest(
                SET_GOAL_PERIOD_INTENT,
                requestArguments
        );
    }

    public static IntentRequest createSetInitialSavingsIntent() {
        Map<String, Slot> requestArguments = new HashMap<>();
        requestArguments.put(
                "InitialSavings",
                Slot.builder().withName("InitialSavings").withValue("10.0").build()
        );

        return createRequest(
                "SetInitialSavingsIntent",
                requestArguments
        );
    }

    public static IntentRequest createDynamicNumberIntent() {
        Map<String, Slot> requestArguments = new HashMap<>();
        requestArguments.put(
                "Dynamic",
                Slot.builder().withName("Dynamic").withValue("10.0").build()
        );

        return createRequest(
                "SetDynamicNumberIntent",
                requestArguments
        );
    }

    public static IntentRequest createRequest(String intentName, Map<String, Slot> slots) {
        Intent intent = Intent.builder().withName(intentName).withSlots(slots).build();

        return IntentRequest.builder().withIntent(intent).withRequestId("r323").build();
    }
}
