package com.ortecfinance.financialplanning;

import java.io.Serializable;

public class FeasibilityCalculator implements Serializable {

    private static final long serialVersionUID = 1L;

    private static double AVERAGE_ANNUAL_RETURN = 0.114122;
    private static double AVERAGE_MONTHLY_RETURN = AVERAGE_ANNUAL_RETURN / 12.0;

    public static String calculate(
            double initialSavings, double monthlyContribution, double numberOfMonths, double target
    ) {
        double gap = initialSavings + (monthlyContribution * numberOfMonths) - target;

        if (gap >= 0) {
            return "High";
        }

        double averageAmount = (initialSavings + (initialSavings + (monthlyContribution * numberOfMonths))) / 2;
        double requiredReturn = (-gap / averageAmount);
        double requiredMonthlyReturn = requiredReturn / numberOfMonths;

        if (requiredMonthlyReturn <= AVERAGE_MONTHLY_RETURN) {
            return "High";
        }

        return "Low";
    }
}
