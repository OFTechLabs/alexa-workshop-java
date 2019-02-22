package com.ortecfinance.financialplanning;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ortecfinance.financialplanning.FinancialPlanningIntents.*;
import static org.junit.Assert.assertThat;

/**
 * Test whether the questions are asked in the correct order, if the ordering changes or if another question is added
 * we need to update the tests here to reflect that.
 *
 * You can figure out the order by looking at the NextQuestionFactory, by default it is:
 * GOAL_AMOUNT_QUESTION, MONTHLY_CONTRIBUTION_QUESTION and then GOAL_PERIOD_QUESTION
 */
public class ResponseTextFactoryTest {

    private Session session;
    private Map<String, Object> attributes;
    private HashMap<String, Slot> slots;

    @Before
    public void setUp() {
        attributes = new HashMap<>();
        slots = new HashMap<>();
        session = Session.builder().withAttributes(attributes).withSessionId("s2412").build();
    }

    @Test
    public void shouldHandleStopIntent() {
        String response = ResponseTextFactory.create(AmazonIntents.STOP_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.STOP_MESSAGE));
    }

    @Test
    public void shouldHandleCancelIntent() {
        String response = ResponseTextFactory.create(AmazonIntents.CANCEL_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.STOP_MESSAGE));
    }

    @Test
    public void shouldHandleHelpIntent() {
        String response = ResponseTextFactory.create(AmazonIntents.HELP_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.HELP_MESSAGE));
    }

    @Test
    public void shouldHandleSetGoalAmountIntent() {
        slots.put(
                FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY).withValue("1000.0").build()
        );

        String response = ResponseTextFactory.create(SET_GOAL_AMOUNT_INTENT, session, slots);

        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY), Is.is(1000.0));
    }

    @Test
    public void shouldHandleSetMonthlyContribution() {
        slots.put(
                FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY).withValue("10.0").build()
        );

        String response = ResponseTextFactory.create(SET_MONTHLY_CONTRIBUTION_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY), Is.is(10.0));
    }

    @Test
    public void shouldHandleSetGoalPeriod() {
        slots.put(
                FinancialPlanningSpeechlet.GOAL_PERIOD_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY).withValue("24.0").build()
        );

        String response = ResponseTextFactory.create(SET_GOAL_PERIOD_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY), Is.is(24.0));
    }

    @Ignore
    @Test
    public void shouldHandleSetDynamicNumberForFirstQuestion() {
        slots.put(
                FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY).withValue("100.0").build()
        );

        String response = ResponseTextFactory.create(SET_DYNAMIC_NUMBER_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY), Is.is(100.0));
    }

    @Ignore
    @Test
    public void shouldHandleSetDynamicNumberForSecondQuestion() {
        slots.put(
                FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY).withValue("100.0").build()
        );

        session.setAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, "100.0");

        String response = ResponseTextFactory.create(SET_DYNAMIC_NUMBER_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY), Is.is(100.0));
    }

    @Ignore
    @Test
    public void shouldHandleSetDynamicNumberForThirdQuestion() {
        slots.put(
                FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY).withValue("100.0").build()
        );

        session.setAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 100.0);

        String response = ResponseTextFactory.create(SET_DYNAMIC_NUMBER_INTENT, session, slots);

        assertThat(response, Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY), Is.is(100.0));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY), Is.is(100.0));
    }

    @Ignore
    @Test
    public void shouldHandleSetDynamicNumberForFinalQuestion() {
        slots.put(
                FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY,
                Slot.builder().withName(FinancialPlanningSpeechlet.DYNAMIC_NUMBER_KEY).withValue("100.0").build()
        );

        session.setAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 100.0);
        session.setAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 100.0);

        String response = ResponseTextFactory.create(SET_DYNAMIC_NUMBER_INTENT, session, slots);

        assertThat(response, Is.is("The feasibility of your goal is High"));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY), Is.is(100.0));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY), Is.is(100.0));
        assertThat((double) session.getAttribute(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY), Is.is(100.0));
    }

}