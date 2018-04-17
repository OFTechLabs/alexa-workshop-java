package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;

import java.io.Serializable;

import static com.ortecfinance.financialplanning.SessionManagementUtil.hasNotBeenStoredInSession;

/**
 * This class should determine what answer the user has given, and give the correct variable key for it. If the question
 * was FinancialPlanningSpeechlet.GOAL_AMOUNT_QUESTION it should return the FinancialPlanningSpeechlet
 * .GOAL_AMOUNT_KEY because we need to
 * store the answer of the user in GOAL_AMOUNT_KEY slot in the session.
 */
public class DynamicNumberKeyFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * Return the key in which to store the answer by the user in the session.
     * @param session the session includes what questions have already been answered, and what questions have not
     *                been answered.
     * @return the key for the question which the user has answered, we need to deduce what the question was here by
     * assuming what question
     * was asked.
     */
    public static String create(Session session) {
        if (hasNotBeenStoredInSession(session, FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY)) {
            return FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY;
        }
        if (hasNotBeenStoredInSession(session, FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY)) {
            return FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY;
        }
        if (hasNotBeenStoredInSession(session, FinancialPlanningSpeechlet.GOAL_PERIOD_KEY)) {
            return FinancialPlanningSpeechlet.GOAL_PERIOD_KEY;
        }

        return "";
    }


}
