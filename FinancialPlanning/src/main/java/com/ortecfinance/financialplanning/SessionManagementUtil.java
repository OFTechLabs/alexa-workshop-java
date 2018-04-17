package com.ortecfinance.financialplanning;

import com.amazon.speech.speechlet.Session;

import java.io.Serializable;

/**
 * Manage answers already given by the user in the session
 */
public class SessionManagementUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Determine if a question has already been answered
     * @param session the session of the user
     * @param key the assigned key in which to store the value
     * @return true if no value is stored for key and false if a value for key has already been stored
     */
    public static boolean hasNotBeenStoredInSession(Session session, String key) {
        return !sessionContainsKey(session, key);
    }

    /**
     * Determine if a question has already been answered
     * @param session the session of the user
     * @param key the assigned key in which to store the value
     * @return true if a value is stored for key and false if a value for key is not stored
     */
    public static boolean hasBeenStoredInSession(Session session, String key) {
        return sessionContainsKey(session, key);
    }

    /**
     * Determine if all questions ahve been answered
     * @param session the session of the user
     * @param keys the keys which have to be stored
     * @return true if all values for keys have been stored, false if not
     */
    public static boolean haveAllBeenStoredInSession(Session session, String... keys) {
        for (String key : keys) {
            if (hasNotBeenStoredInSession(session, key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * If it contains a certain key it means the question has already been answered, if not we still need to store
     * the answer to that queston
     */
    private static boolean sessionContainsKey(Session session, String goalAmountKey) {
        return session.getAttributes().containsKey(goalAmountKey);
    }
}
