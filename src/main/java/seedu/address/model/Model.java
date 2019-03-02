package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.card.Card;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' card folder file path.
     */
    Path getcardFolderFilesPath();

    /**
     * Sets the user prefs' card folder file path.
     */
    void setcardFolderFilesPath(Path cardFolderFilesPath);

    /**
     * Replaces card folder data with the data in {@code cardFolder}.
     */
    void setCardFolder(ReadOnlyCardFolder cardFolder);

    /** Returns the active CardFolder */
    ReadOnlyCardFolder getActiveCardFolder();

    /** Returns all CardFolders */
    List<ReadOnlyCardFolder> getCardFolders();

    /**
     * Returns true if a card with the same identity as {@code card} exists in the card folder.
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * The card must exist in the card folder.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the card folder.
     */
    void addCard(Card card);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the card folder.
     * The card identity of {@code editedCard} must not be the same as another existing card in the card folder.
     */
    void setCard(Card target, Card editedCard);

    /** Returns an unmodifiable view of the filtered card list */
    ObservableList<Card> getFilteredCards();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCard(Predicate<Card> predicate);

    /**
     * Returns true if the model has previous card folder states to restore.
     */
    boolean canUndoCardFolder();

    /**
     * Returns true if the model has undone card folder states to restore.
     */
    boolean canRedoCardFolder();

    /**
     * Restores the model's card folder to its previous state.
     */
    void undoCardFolder();

    /**
     * Restores the model's card folder to its previously undone state.
     */
    void redoCardFolder();

    /**
     * Saves the current card folder state for undo/redo.
     */
    void commitCardFolder();

    /**
     * Selected card in the filtered card list.
     * null if no card is selected.
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Returns the selected card in the filtered card list.
     * null if no card is selected.
     */
    Card getSelectedCard();

    /**
     * Sets the selected card in the filtered card list.
     */
    void setSelectedCard(Card card);

    /**
     * Enters a test session using the specified card folder.
     */
    Card testCardFolder(ReadOnlyCardFolder cardFolder);
}
