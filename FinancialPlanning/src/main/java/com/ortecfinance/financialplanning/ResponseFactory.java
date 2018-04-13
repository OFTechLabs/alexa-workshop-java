package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import java.io.Serializable;

/**
 * Helper function to create the correct object for the Amazon Alexa API with a certain text and whether or not to end
 * the session (or end the conversation in other words)
 */
public class ResponseFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Helper function
     * @param text the text Alexa will speak out loud
     * @param endSession whether or not the conversation should be ended
     * @return a response the Amazon Alexa API can work with
     */
    public static SpeechletResponse Create(String text, boolean endSession) {
        PlainTextOutputSpeech plainTextResponse = new PlainTextOutputSpeech();
        plainTextResponse.setText(text);

        SpeechletResponse speechletResponse = new SpeechletResponse();
        speechletResponse.setShouldEndSession(endSession);
        speechletResponse.setOutputSpeech(plainTextResponse);

        return speechletResponse;
    }
}
