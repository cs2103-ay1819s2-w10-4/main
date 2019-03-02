package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;

/**
 * Represents the in-memory model of the card folder data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final List<VersionedCardFolder> versionedCardFolders;
    private int activeCardFolderIndex;
    private final UserPrefs userPrefs;
    private final List<FilteredList<Card>> filteredCardsList;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given cardFolders and userPrefs.
     */
    public ModelManager(List<ReadOnlyCardFolder> cardFolders, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(cardFolders, userPrefs);

        logger.fine("Initializing with card folder: " + cardFolders + " and user prefs " + userPrefs);

        versionedCardFolders = new ArrayList<>();
        for (ReadOnlyCardFolder cardFolder : cardFolders) {
            versionedCardFolders.add(new VersionedCardFolder(cardFolder));
        }
        this.userPrefs = new UserPrefs(userPrefs);

        filteredCardsList = new ArrayList<>();
        for (int i = 0; i < versionedCardFolders.size(); i++) {
            FilteredList<Card> filteredCards = new FilteredList<>(versionedCardFolders.get(i).getCardList());
            filteredCardsList.add(filteredCards);
            filteredCards.addListener(this::ensureSelectedCardIsValid);
        }

        // TODO: Address the following hardcoded line
        activeCardFolderIndex = 0;
    }

    public ModelManager() {
        this(Collections.singletonList(new CardFolder()), new UserPrefs());
    }

    private VersionedCardFolder getActiveVersionedCardFolder() {
        return versionedCardFolders.get(activeCardFolderIndex);
    }

    private FilteredList<Card> getActiveFilteredCards() {
        return filteredCardsList.get(activeCardFolderIndex);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getcardFolderFilesPath() {
        return userPrefs.getcardFolderFilesPath();
    }

    @Override
    public void setcardFolderFilesPath(Path cardFolderFilesPath) {
        requireNonNull(cardFolderFilesPath);
        userPrefs.setcardFolderFilesPath(cardFolderFilesPath);
    }

    //=========== CardFolder ================================================================================

    @Override
    public void setCardFolder(ReadOnlyCardFolder cardFolder) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.resetData(cardFolder);
    }

    @Override
    public ReadOnlyCardFolder getActiveCardFolder() {
        return getActiveVersionedCardFolder();
    }

    @Override
    public List<ReadOnlyCardFolder> getCardFolders() {
        return new ArrayList<>(versionedCardFolders);
    }

    @Override
    public Card testCardFolder(ReadOnlyCardFolder cardFolderToTest) {
        //TODO: Remove hardcoding, enter card folder and get the list of cards, enter test session mode
        Card cardToTest = cardFolderToTest.getCardList().get(0);
        return cardToTest;
    }

    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);

        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.removeCard(target);
    }

    @Override
    public void addCard(Card card) {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.addCard(card);
        updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);

        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.setCard(target, editedCard);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedCardFolders}
     */
    @Override
    public ObservableList<Card> getFilteredCards() {
        return getActiveFilteredCards();
    }

    @Override
    public void updateFilteredCard(Predicate<Card> predicate) {
        requireNonNull(predicate);
        FilteredList<Card> filteredCards = getActiveFilteredCards();
        filteredCards.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.canUndo();
    }

    @Override
    public boolean canRedoCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        return versionedCardFolder.canRedo();
    }

    @Override
    public void undoCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.undo();
    }

    @Override
    public void redoCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.redo();
    }

    @Override
    public void commitCardFolder() {
        VersionedCardFolder versionedCardFolder = getActiveVersionedCardFolder();
        versionedCardFolder.commit();
    }

    //=========== Selected card ===========================================================================

    @Override
    public ReadOnlyProperty<Card> selectedCardProperty() {
        return selectedCard;
    }

    @Override
    public Card getSelectedCard() {
        return selectedCard.getValue();
    }

    @Override
    public void setSelectedCard(Card card) {
        if (card != null && !getActiveFilteredCards().contains(card)) {
            throw new CardNotFoundException();
        }
        selectedCard.setValue(card);
    }

    /**
     * Ensures {@code selectedCard} is a valid card in {@code filteredCardsList}.
     */
    private void ensureSelectedCardIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedCardReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCard.getValue());
            if (wasSelectedCardReplaced) {
                // Update selectedCard to its new fullAnswer.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedCardRemoved = change.getRemoved().stream()
                    .anyMatch(removedCard -> selectedCard.getValue().isSameCard(removedCard));
            if (wasSelectedCardRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedCard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedCardFolders.equals(other.versionedCardFolders)
                && userPrefs.equals(other.userPrefs)
                && filteredCardsList.equals(other.filteredCardsList)
                && Objects.equals(selectedCard.get(), other.selectedCard.get());
    }

}
