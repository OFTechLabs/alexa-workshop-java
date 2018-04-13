package com.ortecfinance.financialplanning;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;

import java.io.Serializable;
import java.util.Map;

import static com.ortecfinance.financialplanning.FinancialPlanningIntents.*;

public class ResponseTextFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String create(String intentName, Session session, Map<String, Slot> variables) {
        switch (intentName) {
            case AmazonIntents.CANCEL_INTENT:
            case AmazonIntents.STOP_INTENT:
                return FinancialPlanningSpeechlet.STOP_MESSAGE;
            case AmazonIntents.HELP_INTENT:
                return FinancialPlanningSpeechlet.HELP_MESSAGE;
            case SET_GOAL_AMOUNT_INTENT:
                Slot goalAmount = variables.get(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY);
                session.setAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, Double.valueOf(goalAmount.getValue()));
                return NextQuestionFactory.get(session);
            case SET_MONTHLY_CONTRIBUTION_INTENT:
                Slot monthlyContribution = variables.get(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY);
                session.setAttribute(
                        FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY,
                        Double.valueOf(monthlyContribution.getValue())
                );
                return NextQuestionFactory.get(session);
            case SET_GOAL_PERIOD_INTENT:
                Slot goalPeriod = variables.get(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY);
                session.setAttribute(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, Double.valueOf(goalPeriod.getValue()));
                return NextQuestionFactory.get(session);
            case FINANCIAL_PLANNING_INTENT:
                return NextQuestionFactory.get(session);
            default:
                throw new IllegalStateException("Unknown intent");
        }
    }
}
