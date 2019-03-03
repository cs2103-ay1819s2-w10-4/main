package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;



/**
 * Exports single or multiple card folders into a .json file. Users must specify file name to export card folders to.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports single or multiple card folders into"
            + "a .json file.\n"
            + "Parameters: CARD_FOLDER_NAME [MORE CARD_FOLDER_NAMES]"
            + PREFIX_FILENAME + "Filename\n"
            + "Example: " + COMMAND_WORD + "Human_anatomy Bone_structure n/myfilename";
    public static final String MESSAGE_SUCCESS = "Successfully exported card folders to: $1%s";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
