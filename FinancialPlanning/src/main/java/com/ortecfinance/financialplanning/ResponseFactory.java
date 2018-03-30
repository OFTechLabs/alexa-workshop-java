package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import java.io.Serializable;

public class ResponseFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    public static SpeechletResponse Create(String text, boolean endSession)
    {
        PlainTextOutputSpeech plainTextResponse = new PlainTextOutputSpeech();
        plainTextResponse.setText(text);

        SpeechletResponse speechletResponse = new SpeechletResponse();
        speechletResponse.setShouldEndSession(endSession);
        speechletResponse.setOutputSpeech(plainTextResponse);

        return speechletResponse;
    }
}
