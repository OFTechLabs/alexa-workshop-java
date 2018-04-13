package com.ortecfinance.financialplanning;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ortecfinance.financialplanning.FinancialPlanningSpeechlet.*;
import static org.junit.Assert.assertThat;

public class FinancialPlanningSpeechletTest {

    private Session session;
    private Map<String, Slot> slots;

    private FinancialPlanningSpeechlet financialPlanningSpeechlet;

    @Before
    public void setUp() {
        slots = new HashMap<>();

        /* Hint: if we store any new answers in the session, we will have to update the slots below to include it */
        slots.put(GOAL_AMOUNT_KEY, Slot.builder().withName(GOAL_AMOUNT_KEY).withValue("1000.0").build());
        slots.put(GOAL_PERIOD_KEY, Slot.builder().withName(GOAL_PERIOD_KEY).withValue("24.0").build());
        slots.put(MONTHLY_CONTRIBUTION_KEY,
                  Slot.builder().withName(MONTHLY_CONTRIBUTION_KEY).withValue("100.0").build()
        );

        session = Session.builder().withSessionId("s444").build();
        financialPlanningSpeechlet = new FinancialPlanningSpeechlet();
    }

    @Test
    public void shouldBeAbleToLaunchSkill() {
        LaunchRequest launchRequest = LaunchRequest.builder().withRequestId("r1").build();

        SpeechletResponse response = financialPlanningSpeechlet.onLaunch(launchRequest, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                   Is.is(FinancialPlanningSpeechlet.WELCOME_MESSAGE)
        );
    }

    /*
        Hint: If we update the conversation we need to update this test case to match the new conversation
        Think about the order in which questions are asked (see NextQuestionFactory) and apply the correct order
        below. The current order is:
        GOAL_AMOUNT_QUESTION -> MONTHLY_CONTRIBUTION_QUESTION -> GOAL_PERIOD_QUESTION -> feasibility score
        Add any new intents in the correct order below.
    */
    @Test
    public void shouldBeAbleToHaveRegularFinancialPlanningConversation() {
        handleIntentRequestCorrectly(FinancialPlanningIntents.FINANCIAL_PLANNING_INTENT,
                                     FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION,
                                     false
        );

        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_GOAL_AMOUNT_INTENT,
                                                        FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION,
                                                        false,
                                                        GOAL_AMOUNT_KEY,
                                                        1000.0
        );

        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_MONTHLY_CONTRIBUTION_INTENT,
                                                        FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION,
                                                        false,
                                                        MONTHLY_CONTRIBUTION_KEY,
                                                        1000.0
        );

        /* Hint: the session ended true and feasibility score should ALWAYS be the last response */
        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_GOAL_PERIOD_INTENT,
                                                        "The feasibility of your goal is High",
                                                        true,
                                                        GOAL_PERIOD_KEY,
                                                        1000.0
        );
    }

    @Ignore
    @Test
    public void shouldBeAbleToHaveDynamicFinancialPlanningConversation() {
        handleIntentRequestCorrectly(FinancialPlanningIntents.FINANCIAL_PLANNING_INTENT,
                                     FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION,
                                     false
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("1000.0").build());
        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT,
                                                        FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION,
                                                        false,
                                                        GOAL_AMOUNT_KEY,
                                                        1000.0
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("100.0").build());
        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT,
                                                        FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION,
                                                        false,
                                                        MONTHLY_CONTRIBUTION_KEY,
                                                        1000.0
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("24.0").build());
        handleIntentRequestWithSessionVariableCorrectly(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT,
                                                        "The feasibility of your goal is High",
                                                        true,
                                                        GOAL_PERIOD_KEY,
                                                        24.0
        );
    }

    private void handleIntentRequestCorrectly(String intent, String expectedResponse, boolean shouldEndSession) {
        IntentRequest request = getRequest(intent, slots);
        SpeechletResponse response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(shouldEndSession));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), Is.is(expectedResponse));
    }

    private void handleIntentRequestWithSessionVariableCorrectly(
            String intent, String expectedResponse, boolean shouldEndSession, String sessionKey, double sessionValue
    ) {
        IntentRequest request = getRequest(intent, slots);
        SpeechletResponse response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(shouldEndSession));
        assertThat((double) session.getAttribute(sessionKey), Is.is(sessionValue));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(), Is.is(expectedResponse));
    }

    public IntentRequest getRequest(String intentName, Map<String, Slot> slots) {
        Intent intent = Intent.builder().withName(intentName).withSlots(slots).build();

        return IntentRequest.builder().withIntent(intent).withRequestId("r323").build();
    }
}