package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditFolderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditFolderCommand object
 */
public class EditFolderCommandParser implements Parser<EditFolderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditFolderCommand
     * and returns an EditFolderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditFolderCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Pair<Index, String> parsedObjects;
        parsedObjects = ParserUtil.parseIndexAndFolderName(args);

        return new EditFolderCommand(parsedObjects.getKey(), parsedObjects.getValue());
    }
}