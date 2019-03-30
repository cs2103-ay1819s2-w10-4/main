package seedu.address.storage.csvmanager;

import java.io.IOException;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;


/**
 * API for export and import of card folders
 */
interface CsvCommands {

    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException;

    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders) throws IOException;

}