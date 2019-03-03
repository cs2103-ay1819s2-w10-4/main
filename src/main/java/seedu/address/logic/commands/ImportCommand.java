package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a card folder into the application. "
            + "File imported must be in .json format.\n"
            + "Parameters: JSON_FILE_NAME\n "
            + "Example: " + COMMAND_WORD + "Human_anatomy.json";

    
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
