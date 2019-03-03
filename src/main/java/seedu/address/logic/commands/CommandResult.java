package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.card.Card;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should enter a test session. */
    private final Card testSessionCard;

    /** The current test session should end. */
    private final boolean endTestSession;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Card testSessionCard,
                         boolean endTestSession) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.testSessionCard = testSessionCard;
        this.endTestSession = endTestSession;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default fullAnswer.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isEndTestSession() {
        return endTestSession;
    }

    /**
     * Check if command is to enter test session
     * @return a boolean variable to state if command is test or not
     */
    public boolean isTestSession() {
        if (testSessionCard == null) {
            return false;
        }
        return true;
    }

    public Card getTestSessionCard() {
        return testSessionCard;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, testSessionCard, endTestSession);
    }

}
