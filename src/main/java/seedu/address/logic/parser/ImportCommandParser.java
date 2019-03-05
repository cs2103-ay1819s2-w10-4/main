package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parser input for import command arguments.
 */
public class ImportCommandParser implements Parser<ImportCommand> {


    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        String filename = userInput.trim();
        if (filename.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(userInput);
    }
}
