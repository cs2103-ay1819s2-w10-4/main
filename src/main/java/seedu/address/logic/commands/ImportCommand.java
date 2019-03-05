package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
    public static final String MESSAGE_INCORRECT_EXTENSION = "Incorrect file extension name";

    private String filename;

    public ImportCommand(String filename) {
        this.filename = filename;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // check for correct file extension
        if (!isCorrectFileExtension(filename)) {
            throw new CommandException(MESSAGE_INCORRECT_EXTENSION);
        }
        return null;
    }



    /**
     * Returns true if file extension is of .json format.
     */
    private boolean isCorrectFileExtension(String filename) {
        return filename.split("\\.(?=[^\\.]+$)")[1].equals("json");
    }
}
