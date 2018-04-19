package com.ortecfinance.financialplanning;

import java.io.Serializable;

public class FeasibilityCalculator implements Serializable {

    private static final long serialVersionUID = 1L;

    /* The baseline expected return is estimated at 2.75% per year, based on a balanced mix of stocks and bonds.
     * If there is stagnation, models show 1.50%, a high growth market results in 3.75%. */
    private static double BASELINE_YEARLY_RETURN   = 2.75 / 100;
 // private static double STAGNATION_YEARLY_RETURN = 1.50 / 100;
 // private static double HIGHGROWTH_YEARLY_RETURN = 3.75 / 100;

    /* Monthly returns are calculated by MR = (1+AR)^(1/12)-1 */
    private static double BASELINE_MONTHLY_RETURN   = Math.pow(1+BASELINE_YEARLY_RETURN  ,1/12.0) - 1;
 // private static double STAGNATION_MONTHLY_RETURN = Math.pow(1+STAGNATION_YEARLY_RETURN,1/12.0) - 1;
 // private static double HIGHGROWTH_MONTHLY_RETURN = Math.pow(1+HIGHGROWTH_YEARLY_RETURN,1/12.0) - 1;

    /**
     * Function that returns the verdict for a certain investment, based on whether the expected result exceeds
     * the target.
     * @param initialSavings the amount to start with
     * @param monthlyContribution the amount that is added at the start of every month
     * @param numberOfMonths the number of months to go
     * @param target the target end amount
     * @return a String with a verdict on the probability that the target is reached
     */
    public static String calculate(
            double initialSavings, double monthlyContribution, double numberOfMonths, double target
    ) {
        double expectedBaselineResult =
                calculateExpectedResult(initialSavings, monthlyContribution, numberOfMonths, BASELINE_MONTHLY_RETURN);

        if (target <= expectedBaselineResult) {
            return "High";
        }
        else {
            return "Low";
        }
    }

    /**
     * Calculate the end savings based on initial savings (S), monthly contribution (m), number of months (n)
     * and the return per month (r). This is defined as S*(1+r)^n + m*sum((1+r)^i,i=1..n), and this is mathematically
     * equivalent to the closed expression S*(1+r)^n + m*((1+r)^(n+1)-(1+r))/r which is used in the function.
     * @param initialSavings the amount to start with
     * @param monthlyContribution the amount that is added at the start of every month
     * @param numberOfMonths the number of months to go
     * @param monthlyReturn the monthly return as a double, 5% is 0.05 here
     * @return the end amount
     */
    private static double calculateExpectedResult(
            double initialSavings, double monthlyContribution, double numberOfMonths, double monthlyReturn
    ) {
        if (monthlyReturn==0) {
            return initialSavings + (monthlyContribution * numberOfMonths);
        }
        else {
            return initialSavings * Math.pow(1 + monthlyReturn, numberOfMonths) +
                    monthlyContribution *
                            (Math.pow(1 + monthlyReturn, numberOfMonths + 1) - (1 + monthlyReturn)) / monthlyReturn;
        }
    }
}
