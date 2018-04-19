package com.ortecfinance.financialplanning;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class FeasibilityCalculatorTest {

    @Test
    public void shouldGiveCorrectFeasibilityVerdict() {

        // The four tests below have increasing targets and therefore decreasing feasibility.

        // This case reflects the situation that the expected end result is higher than the target in all scenarios,
        // so it is likely that the target is reached.
        assertThat(FeasibilityCalculator.calculate
                (1000.0,100.0,12.0,2220.0),
                Is.is("High"));

        // This case reflects the situation that the target does not exceed the end result in the stagnation scenario
        // but in the baseline scenario the target is still reached.
        assertThat(FeasibilityCalculator.calculate
                (1000.0,100.0,12.0,2245.0),
                Is.is("High"));

        // This case reflects the situation that the target does not exceed the end result in the baseline scenario
        // but in the highgrowth scenario the target is still reached.
        assertThat(FeasibilityCalculator.calculate
                (1000.0,100.0,12.0,2246.0),
                Is.is("Low"));

        // This case reflects the situation that the target is not reached in all economic scenarios
        assertThat(FeasibilityCalculator.calculate
                (1000.0,100.0,12.0,2270.0),
                Is.is("Low"));}

}