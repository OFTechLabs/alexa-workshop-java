package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;

import java.io.Serializable;

import static com.ortecfinance.financialplanning.FinancialPlanningIntents.*;

/**
 * This determines if all questions have been answered, if so we can end the session. If not all questions have been answered
 * we need to ask more questions and should not end the session because we need to store user answers in the session.
 */
public class EndSessionFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * Indicate whether the session should be ended, it should be ended if all answers have been given or if the user requests
     * the skill to stop with the STOP or CANCEL intent.
     * @param intentName the intent of the user
     * @param session the session which includes the answers given by the user so far
     * @return true if all four questions have been answered, false if one or more questions have not been answered yet
     */
    public static boolean shouldEndSession(String intentName, Session session)
    {
        switch (intentName)
        {
            case FINANCIAL_PLANNING_INTENT:
            case SET_GOAL_AMOUNT_INTENT:
            case SET_MONTHLY_CONTRIBUTION_INTENT:
            case SET_GOAL_PERIOD_INTENT:
            case SET_DYNAMIC_NUMBER_INTENT:
                return
                        session.getAttributes().containsKey(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY)
                                && session.getAttributes().containsKey(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY)
                                && session.getAttributes().containsKey(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY);
            case AmazonIntents.HELP_INTENT:
                return false;
            default:
                return true;
        }
    }
}
