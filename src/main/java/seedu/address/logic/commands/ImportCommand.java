package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports a .json file containing card folders data into the application
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a .json file containing "
            + "card folders information.\n"
            + "File imported must have a .json extension.\n"
            + "Default file path if not specified will be in ./data folder\n"
            + "Parameters: JSON_FILE_NAME\n"
            + "Example: " + COMMAND_WORD + "alice.json";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
