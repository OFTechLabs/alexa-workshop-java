package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;

import java.io.Serializable;

public class FinalAnswerFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /* The baseline expected return is estimated at 2.75% per year, based on a balanced mix of stocks and bonds.
     * If there is stagnation, models show 1.50%, a high growth market results in 3.75%. */
    private static double BASELINE_YEARLY_RETURN   = 2.75 / 100;
    private static double STAGNATION_YEARLY_RETURN = 1.50 / 100;
    private static double HIGHGROWTH_YEARLY_RETURN = 3.75 / 100;

    /**
     * Function that compiles the final answer, taking all known variables into account.
     * @param session    the session which contains all stored variables
     * @return a String with the final answer
     */
    public static String get(Session session) {
        double target              = getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.GOAL_AMOUNT_KEY));
        double monthlyContribution = getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.MONTHLY_CONTRIBUTION_KEY));
        double numberOfMonths      = getNumericValue(session.getAttribute(FinancialPlanningSpeechlet.GOAL_PERIOD_KEY));
        double initialSavings      = 0;

        return "The feasibility of your goal is " + feasibility(target, monthlyContribution, numberOfMonths, initialSavings);
    }

    /**
     * Function that returns a verdict on the feasibility of reaching the target, taking all known all variables into account.
     */
    public static String feasibility(
            double target, double monthlyContribution, double numberOfMonths, double initialSavings
    ) {
        if (expectedFinalAmount(monthlyContribution, numberOfMonths, initialSavings, BASELINE_YEARLY_RETURN) >= target) {
            return "High";
        }
        else {
            return "Low";
        }
    }

    /**
     * Calculate the end amount based on initial savings (S), monthly contribution (m), number of months (n)
     * and the return per month (r). This is defined as S*(1+r)^n + m*sum((1+r)^i,i=1..n), and this is mathematically
     * equivalent to the closed expression S*(1+r)^n + m*((1+r)^(n+1)-(1+r))/r which is used in the function.
     * @param monthlyContribution the amount that is added at the start of every month
     * @param numberOfMonths the number of months to go
     * @param initialSavings the amount to start with
     * @param yearlyReturn the yearly return as a double, 5% is 0.05 here
     * @return the end amount
     */
    private static double expectedFinalAmount(
            double monthlyContribution, double numberOfMonths, double initialSavings, double yearlyReturn
    ) {
        /* Monthly returns are calculated by MR = (1+AR)^(1/12)-1 */
        double monthlyReturn = Math.pow(1 + yearlyReturn, 1 / 12.0) - 1;

        if (monthlyReturn == 0) {
            return initialSavings + (monthlyContribution * numberOfMonths);
        }
        else {
            return initialSavings * Math.pow(1 + monthlyReturn, numberOfMonths) +
                    monthlyContribution *
                            (Math.pow(1 + monthlyReturn, numberOfMonths + 1) - (1 + monthlyReturn)) / monthlyReturn;
        }
    }

    /**
     * Ensure we get a double for both integer value as well as double value
     *
     * @param value Object containing either int or double
     * @return a valid double
     */
    private static double getNumericValue(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        }
        return (double) value;
    }
}
