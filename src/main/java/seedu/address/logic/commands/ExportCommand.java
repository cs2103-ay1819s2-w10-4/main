package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLDERNAME;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports single or multiple card folders into a .json file. Users must specify file name to export card folders to.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports single or multiple card folders into"
            + "a .json file. "
            + "Users must include the .json extension.\n"
            + "Parameters: "
            + PREFIX_FOLDERNAME + "CARD_FOLDER_NAME [MORE CARD_FOLDER_NAMES]..."
            + PREFIX_FILENAME + "Filename.json\n"
            + "Example: " + COMMAND_WORD + "f/Human_anatomy f/Bone_structure n/myfilename.json";
    public static final String MESSAGE_SUCCESS = "Successfully exported card folders to: $1%s";

    public ExportCommand(List<String> folderNames, String filename) {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // check whether model contains the card folders desired
        // Retrieve card folders from model to be passed on to storage

    }
}
