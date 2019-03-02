package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Card;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final CommandParser commandParser;
    private boolean cardFolderModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        commandParser = new CommandParser();

        // Set cardFolderModified to true whenever the models' card folder is modified.
        model.getActiveCardFolder().addListener(observable -> cardFolderModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        cardFolderModified = false;

        CommandResult commandResult;
        try {
            Command command = commandParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (cardFolderModified) {
            logger.info("card folder modified, saving to file.");
            try {
                storage.saveCardFolder(model.getActiveCardFolder());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCardFolder getCardFolder() {
        return model.getActiveCardFolder();
    }

    @Override
    public ObservableList<Card> getFilteredCardList() {
        return model.getFilteredCards();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getcardFolderFilesPath() {
        return model.getcardFolderFilesPath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return model.selectedCardProperty();
    }

    @Override
    public void setSelectedCard(Card card) {
        model.setSelectedCard(card);
    }
}
