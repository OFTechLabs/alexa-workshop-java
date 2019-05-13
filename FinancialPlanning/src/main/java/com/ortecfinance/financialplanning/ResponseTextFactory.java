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

        // If the intent involves setting a variable, the corresponding slot will be filled here
        switch (intentName) {
            case SET_GOAL_AMOUNT_INTENT:
                fillSlot(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY, session, variables);
                break;
            case SET_MONTHLY_CONTRIBUTION_INTENT:
                fillSlot(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY, session, variables);
                break;
            case SET_GOAL_PERIOD_INTENT:
                fillSlot(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY, session, variables);
                break;
        }

        // For all intents a response is returned.
        switch (intentName) {
            case AmazonIntents.CANCEL_INTENT:
            case AmazonIntents.STOP_INTENT:
                return FinancialPlanningSpeechlet.STOP_MESSAGE;
            case AmazonIntents.HELP_INTENT:
                return FinancialPlanningSpeechlet.HELP_MESSAGE;
            case FINANCIAL_PLANNING_INTENT:
            case SET_GOAL_AMOUNT_INTENT:
            case SET_MONTHLY_CONTRIBUTION_INTENT:
            case SET_GOAL_PERIOD_INTENT:
                // First determine the next question, based on which slots are still unfilled.
                String response = NextQuestionFactory.get(session);
                // Is there is no next question, all slots have been filled and instead a final answer will be
                // constructed and compiled.
                if (response.isEmpty()) {
                    response = FinalAnswerFactory.get(session);
                }
                return response;
            default:
                // If the spoken message did not match any of the known intents you will end up here.
                // Necessarily, the same question will be asked again as no new slots have been filled.
                return FinancialPlanningSpeechlet.DID_NOT_UNDERSTAND_TEXT + NextQuestionFactory.get(session);
        }
    }

    /**
     * @param keyOfSuppliedVariable the key for which a value has been stored in the variables parameter.
     * @param session               we have to store any user answers in the session
     * @param variables             these are the variables in the answer given by the user, for instance: my amount is
     *                              {AmountSpokenByTheUser} will be available in variables under the
     *                              FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY key
     */
    private static void fillSlot(String keyOfSuppliedVariable, Session session, Map<String, Slot> variables) {
        Slot suppliedVariable = variables.get(keyOfSuppliedVariable);
        session.setAttribute(keyOfSuppliedVariable, Double.valueOf(suppliedVariable.getValue()));
    }
}
