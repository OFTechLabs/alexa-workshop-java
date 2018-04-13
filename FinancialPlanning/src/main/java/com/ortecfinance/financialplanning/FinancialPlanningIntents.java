package com.ortecfinance.financialplanning;

import java.io.Serializable;

/**
 * Declares the Financial planning intents
 */
public class FinancialPlanningIntents implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Hint: When you add a new intent look at where the current intents are used to see what parts you have to update*/
    public static final String FINANCIAL_PLANNING_INTENT = "FinancialPlanningIntent";
    public static final String SET_GOAL_AMOUNT_INTENT = "SetGoalAmountIntent";
    public static final String SET_MONTHLY_CONTRIBUTION_INTENT = "SetMonthlyContributionIntent";
    public static final String SET_GOAL_PERIOD_INTENT = "SetGoalPeriodIntent";
    public static final String SET_DYNAMIC_NUMBER_INTENT = "SetDynamicNumberIntent";

}
