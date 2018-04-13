package com.ortecfinance.financialplanning;

import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;

import java.io.Serializable;
import java.util.Map;

import static com.ortecfinance.financialplanning.FinancialPlanningIntents.*;

/**
 * Handles all the different available intents by mapping them to the correct response and storing any user answers
 * in the session during the conversation.
 */
public class ResponseTextFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @param intentName the intent the user wants to have handled here
     * @param session    we have to store any user answers in the session
     * @param variables  these are the variables in the answer given by the user, for instance: my amount is
     *                   {AmountSpokenByTheUser} will be available in variables under the
     *                   FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY key
     * @return the text Alexa has to say out loud in response to the intent
     */
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
                /* Hint: any extra intents will have to be handled else this exception below will be thrown */
                throw new IllegalStateException("Unknown intent");
        }
    }
}
