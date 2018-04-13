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

    @Test
    public void shouldBeAbleToHaveRegularFinancialPlanningConversation() {
        IntentRequest request = getRequest(FinancialPlanningIntents.FINANCIAL_PLANNING_INTENT, slots);
        SpeechletResponse response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION)
        );

        request = getRequest(FinancialPlanningIntents.SET_GOAL_AMOUNT_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat((double) session.getAttribute(GOAL_AMOUNT_KEY), Is.is(1000.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION)
        );

        request = getRequest(FinancialPlanningIntents.SET_MONTHLY_CONTRIBUTION_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat((double) session.getAttribute(MONTHLY_CONTRIBUTION_KEY), Is.is(100.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION)
        );

        request = getRequest(FinancialPlanningIntents.SET_GOAL_PERIOD_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(true));
        assertThat((double) session.getAttribute(GOAL_PERIOD_KEY), Is.is(24.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is("The feasibility of your goal is High")
        );
    }

    @Ignore
    @Test
    public void shouldBeAbleToHaveDynamicFinancialPlanningConversation() {
        IntentRequest request = getRequest(FinancialPlanningIntents.FINANCIAL_PLANNING_INTENT, slots);
        SpeechletResponse response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION)
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("1000.0").build());
        request = getRequest(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat((double) session.getAttribute(GOAL_AMOUNT_KEY), Is.is(1000.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION)
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("100.0").build());
        request = getRequest(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(false));
        assertThat((double) session.getAttribute(MONTHLY_CONTRIBUTION_KEY), Is.is(100.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION)
        );

        slots.put(DYNAMIC_NUMBER_KEY, Slot.builder().withName(DYNAMIC_NUMBER_KEY).withValue("24.0").build());
        request = getRequest(FinancialPlanningIntents.SET_DYNAMIC_NUMBER_INTENT, slots);
        response = financialPlanningSpeechlet.onIntent(request, session);

        assertThat(response.getShouldEndSession(), Is.is(true));
        assertThat((double) session.getAttribute(GOAL_PERIOD_KEY), Is.is(24.0));
        assertThat(((PlainTextOutputSpeech) response.getOutputSpeech()).getText(),
                Is.is("The feasibility of your goal is High")
        );
    }

    public IntentRequest getRequest(String intentName, Map<String, Slot> slots) {
        Intent intent = Intent.builder().withName(intentName).withSlots(slots).build();

        return IntentRequest.builder().withIntent(intent).withRequestId("r323").build();
    }
}