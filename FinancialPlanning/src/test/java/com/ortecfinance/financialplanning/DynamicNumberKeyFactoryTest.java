package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class DynamicNumberKeyFactoryTest {

    private Session session;
    private Map<String, Object> attributes;

    @Before
    public void setUp() {
        attributes = new HashMap<>();
        session = Session.builder().withAttributes(attributes).withSessionId("s2412").build();
    }

    @Test
    public void shouldGetGoalifNoQuestionsHaveBeenAnsweredYet() {
        String question = DynamicNumberKeyFactory.create(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY));
    }

    @Test
    public void shouldGetMonthlyContributionIfGoalAndInitialSavingsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        String question = DynamicNumberKeyFactory.create(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY));
    }

    @Test
    public void shouldGetGoalPeriodIfAllOthersHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);
        String question = DynamicNumberKeyFactory.create(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY));
    }

    @Test
    public void shouldReturnEmptyStringIfAllQuestionsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);
        String question = DynamicNumberKeyFactory.create(session);

        assertThat(question, Is.is(""));
    }
}