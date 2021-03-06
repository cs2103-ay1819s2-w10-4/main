package seedu.knowitall.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code CardFolder} that keeps track of its own history.
 */
public class VersionedCardFolder extends CardFolder {

    private final List<ReadOnlyCardFolder> cardFolderStateList;
    private int currentStatePointer;

    public VersionedCardFolder(ReadOnlyCardFolder initialState) {
        super(initialState);

        cardFolderStateList = new ArrayList<>();
        cardFolderStateList.add(new CardFolder(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code CardFolder} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        cardFolderStateList.add(new CardFolder(this));
        currentStatePointer++;
        indicateModified();
    }

    /**
     * Resets the state history of the VersionedCardFolder.
     */
    public void resetStates() {
        currentStatePointer = 0;
        cardFolderStateList.clear();
        cardFolderStateList.add(new CardFolder(this));
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        cardFolderStateList.subList(currentStatePointer + 1, cardFolderStateList.size()).clear();
    }

    /**
     * Restores the card folder to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(cardFolderStateList.get(currentStatePointer));
    }

    /**
     * Restores the card folder to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(cardFolderStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has card folder states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has card folder states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < cardFolderStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedCardFolder)) {
            return false;
        }

        VersionedCardFolder otherVersionedCardFolder = (VersionedCardFolder) other;

        // check past states have same folder name and cards
        for (int i = 0; i < cardFolderStateList.size(); i++) {
            ReadOnlyCardFolder state = cardFolderStateList.get(i);
            ReadOnlyCardFolder otherState = otherVersionedCardFolder.cardFolderStateList.get(i);
            if (!state.equals(otherState) || !state.hasSameCards(otherState.getCardList())) {
                return false;
            }
        }

        // present state check
        return super.equals(otherVersionedCardFolder)
                && hasSameCards(otherVersionedCardFolder.getCardList())
                && currentStatePointer == otherVersionedCardFolder.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of cardFolderState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of cardFolderState list, unable to redo.");
        }
    }
}
