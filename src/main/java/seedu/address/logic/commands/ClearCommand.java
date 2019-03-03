package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CardFolder;
import seedu.address.model.Model;

/**
 * Clears the card folder.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "card folder has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        // Name of CardFolder is preserved in clear operation
        model.setCardFolder(new CardFolder(model.getActiveCardFolder().getFolderName()));
        model.commitActiveCardFolder();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
