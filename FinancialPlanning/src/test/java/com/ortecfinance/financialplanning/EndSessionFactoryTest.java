package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ortecfinance.financialplanning.AmazonIntents.*;
import static com.ortecfinance.financialplanning.FinancialPlanningIntents.FINANCIAL_PLANNING_INTENT;
import static org.junit.Assert.assertThat;

public class EndSessionFactoryTest {

    private Session session;
    private Map<String, Object> attributes;

    @Before
    public void setUp() {
        attributes = new HashMap<>();
        session = Session.builder().withAttributes(attributes).withSessionId("s2412").build();
    }

    @Test
    public void shouldEndSessionIfAllQuestionsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);

        boolean sessionEnded = EndSessionFactory.shouldEndSession(FINANCIAL_PLANNING_INTENT, session);
        assertThat(sessionEnded, Is.is(true));
    }

    @Test
    public void shouldNotEndSessionIfNotAllQuestionsHaveBeenAnswered() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, 1000.0);
        attributes.put(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, 1000.0);

        boolean sessionEnded = EndSessionFactory.shouldEndSession(FINANCIAL_PLANNING_INTENT, session);
        assertThat(sessionEnded, Is.is(false));
    }

    @Test
    public void shouldEndSessionIfTheUserRequestsAStop() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);

        boolean sessionEnded = EndSessionFactory.shouldEndSession(STOP_INTENT, session);
        assertThat(sessionEnded, Is.is(true));
    }

    @Test
    public void shouldEndSessionIfTheUserRequestsACancel() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);

        boolean sessionEnded = EndSessionFactory.shouldEndSession(CANCEL_INTENT, session);
        assertThat(sessionEnded, Is.is(true));
    }

    @Test
    public void shouldNotEndSessionIfTheUserRequestsAHelp() {
        attributes.put(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, 1000.0);

        boolean sessionEnded = EndSessionFactory.shouldEndSession(HELP_INTENT, session);
        assertThat(sessionEnded, Is.is(false));
    }
}