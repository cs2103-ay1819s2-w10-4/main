package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts flashcards based on score. Flashcards will be arranged in ascending order, from the lowest score to the
 * highest.
 */
public class SortCommand extends Command {
    public static final String COMMAND_USAGE = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted flashcards";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
