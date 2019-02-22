package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * Test whether the questions are asked in the correct order, if the ordering changes or if another question is added
 * we need to update the tests here to reflect that.
 *
 * You can figure out the order by looking at the NextQuestionFactory, by default it is:
 * GOAL_AMOUNT_QUESTION, MONTHLY_CONTRIBUTION_QUESTION and then GOAL_PERIOD_QUESTION
 */
public class NextQuestionFactoryTest {

    private Session session;
    private Map<String, Object> attributes;

    @Before
    public void setUp() {
        attributes = new HashMap<>();
        session = Session.builder().withAttributes(attributes).withSessionId("s2412").build();
    }

    @Test
    public void shouldGetGoalifNoQuestionsHaveBeenAnsweredYet() {
        String question = NextQuestionFactory.get(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION));
    }

    @Test
    public void shouldGetMonthlyContributionIfGoalAndInitialSavingsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        String question = NextQuestionFactory.get(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION));
    }

    @Test
    public void shouldGetGoalPeriodIfAllOthersHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);
        String question = NextQuestionFactory.get(session);

        assertThat(question, Is.is(FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION));
    }

    // Hint: if you add a question it needs to be added here!
    @Test
    public void shouldGiveFeasibilityIfAllQuestionsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);
        String question = NextQuestionFactory.get(session);

        assertThat(question, Is.is("The feasibility of your goal is High"));
    }

    // Hint: if you add a question it needs to be added here!
    @Ignore
    @Test
    public void shouldGiveFeasibilityIfAllQuestionsHaveBeenAnsweredWithInts() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000);
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000);
        String question = NextQuestionFactory.get(session);

        assertThat(question, Is.is("The feasibility of your goal is High"));
    }

}