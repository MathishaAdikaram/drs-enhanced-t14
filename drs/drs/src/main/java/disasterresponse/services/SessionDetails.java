package disasterresponse.services;

import disasterresponse.models.User;

/**
 * The {@code SessionDetails} class manages the session state for the
 * application. It stores details about the currently logged-in user and any
 * top-level messages. This class uses the Singleton pattern to ensure a single
 * instance throughout the application's life cycle.
 *
 * <p>
 * It provides methods to get and set the logged-in user and top message, and to
 * clear the session.</p>
 *
 * @see disasterresponse.models.User
 *
 * @author 12236202
 */
public class SessionDetails {

    private static SessionDetails instance;

    private User loggedInUser;
    private String topMessage;

    private SessionDetails() {
    }

    /**
     * Gets the singleton instance of {@code SessionDetails}.
     *
     * @return The singleton instance of {@code SessionDetails}.
     */
    public static SessionDetails getInstance() {
        if (instance == null) {
            instance = new SessionDetails();
        }
        return instance;
    }

    /**
     * Gets the currently logged-in user.
     *
     * @return The {@code User} object representing the logged-in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param user The {@code User} object representing the logged-in user.
     */
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    /**
     * Gets the top message for the session.
     *
     * @return The top message as a {@code String}.
     */
    public String getTopMessage() {
        return topMessage;
    }

    /**
     * Sets the top message for the session.
     *
     * @param topMessage The top message as a {@code String}.
     */
    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    /**
     * Clears the session by setting the logged-in user to {@code null}.
     */
    public void clearSession() {
        loggedInUser = null;
    }
}
