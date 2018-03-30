package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;

import java.io.Serializable;

public class NextQuestionFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String get(Session session) {
        if (!session.getAttributes().containsKey(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY)) {
            return FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION;
        }
        if (!session.getAttributes().containsKey(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY)) {
            return FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_QUESTION;
        }
        if (!session.getAttributes().containsKey(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY)) {
            return FinancialPlanningSpeechlet.GOAL_PERIOD_QUESTION;
        }

        return "The feasibility of your goal is " + FeasibilityCalculator.calculate(
                0,
                getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY)),
                getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY)),
                getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY))
        );
    }

    /**
     * Ensure we get a double for both integer value as well as double value
     * @param value Object containing either int or double
     * @return a valid double
     */
    private static double getNumericValue(Object value) {
        if (value instanceof Integer)
            return ((Integer)value).doubleValue();
        return (double) value;
    }
}
