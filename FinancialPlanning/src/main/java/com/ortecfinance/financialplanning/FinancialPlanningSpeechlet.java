package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.*;

public class FinancialPlanningSpeechlet implements Speechlet {

    public static String GOAL_AMOUNT_KEY = "GoalAmount";
    public static String MONTHLY_CONTRIBUTION_KEY = "MonthlyContribution";
    public static String GOAL_PERIOD_KEY = "GoalPeriod";
    public static String DYNAMIC_NUMBER_KEY = "Dynamic";

    public static String
            HELP_MESSAGE =
            "I can help you achieve your financial goals, to start, tell me how much is necessary to achieve your goal?";
    public static String STOP_MESSAGE = "Bye!";

    public static String GOAL_AMOUNT_QUESTION = "What is the amount you need to achieve your financial goal?";
    public static String MONTHLY_CONTRIBUTION_QUESTION = "What will your monthly contribution be?";
    public static String GOAL_PERIOD_QUESTION = "How many months are left until the goal is achieved?";
    public static String WELCOME_MESSAGE = "Welcome. " + GOAL_AMOUNT_QUESTION;

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) {

    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) {
        return ResponseFactory.Create(WELCOME_MESSAGE, false);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) {
        String text = ResponseTextFactory.create(
                intentRequest.getIntent().getName(),
                session,
                intentRequest.getIntent().getSlots()
        );
        boolean endSession = EndSessionFactory.shouldEndSession(intentRequest.getIntent().getName(), session);

        return ResponseFactory.Create(text, endSession);
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) {

    }
}
