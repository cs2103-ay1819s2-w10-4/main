package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.commons.util.CollectionUtil;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.model.card.Score;
import seedu.knowitall.model.hint.Hint;

/**
 * Edits the details of an existing card in the card folder.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the card identified "
            + "by the index number used in the displayed card list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_ANSWER + "ANSWER] "
            + "[" + PREFIX_OPTION + "OPTION]... "
            + "[" + PREFIX_HINT + "HINT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ANSWER + "91234567 ";

    public static final String MESSAGE_EDIT_CARD_SUCCESS = "Edited Card: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the card folder.";

    private final Index index;
    private final EditCardDescriptor editCardDescriptor;

    /**
     * @param index of the card in the filtered card list to edit
     * @param editCardDescriptor details to edit the card with
     */
    public EditCommand(Index index, EditCardDescriptor editCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editCardDescriptor);

        this.index = index;
        this.editCardDescriptor = new EditCardDescriptor(editCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getState() != State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        List<Card> lastShownList = model.getActiveFilteredCards();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card cardToEdit = lastShownList.get(index.getZeroBased());
        Card editedCard = createEditedCard(cardToEdit, editCardDescriptor);

        if (!cardToEdit.isSameCard(editedCard) && model.hasCard(editedCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.setCard(cardToEdit, editedCard);
        model.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
        model.commitActiveCardFolder();
        return new CommandResult(String.format(MESSAGE_EDIT_CARD_SUCCESS, editedCard));
    }

    /**
     * Creates and returns a {@code Card} with the details of {@code cardToEdit}
     * edited with {@code editCardDescriptor}.
     */
    private static Card createEditedCard(Card cardToEdit, EditCardDescriptor editCardDescriptor)
            throws CommandException {
        assert cardToEdit != null;

        Question updatedQuestion = editCardDescriptor.getQuestion().orElse(cardToEdit.getQuestion());
        Answer updatedAnswer = editCardDescriptor.getAnswer().orElse(cardToEdit.getAnswer());
        // Score cannot be edited, so copy original
        Score originalScore = cardToEdit.getScore();
        Set<Option> updatedOptions = editCardDescriptor.getOptions().orElse(cardToEdit.getOptions());
        Set<Hint> updatedHints = editCardDescriptor.getHints().orElse(cardToEdit.getHints());
        // Check if options list contains answer in the edit command
        for (Option option : updatedOptions) {
            if (option.optionValue.equalsIgnoreCase(updatedAnswer.fullAnswer)) {
                throw new CommandException(Messages.MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER);
            }
        }

        return new Card(updatedQuestion, updatedAnswer, originalScore, updatedOptions, updatedHints);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editCardDescriptor.equals(e.editCardDescriptor);
    }

    /**
     * Stores the details to edit the card with. Each non-empty field value will replace the
     * corresponding field value of the card.
     */
    public static class EditCardDescriptor {
        private Question question;
        private Answer answer;
        private Set<Option> options;
        private Set<Hint> hints;

        public EditCardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code hints} is used internally.
         */
        public EditCardDescriptor(EditCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
            setOptions(toCopy.options);
            setHints(toCopy.hints);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answer, options, hints);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        /**
         * Sets {@code options} to this object's {@code options}.
         * A defensive copy of {@code options} is used internally.
         */
        public void setOptions(Set<Option> options) {
            this.options = (options != null) ? new HashSet<>(options) : null;
        }

        /**
         * Returns an unmodifiable option set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code options} is null.
         */
        public Optional<Set<Option>> getOptions() {
            return (options != null) ? Optional.of(Collections.unmodifiableSet(options)) : Optional.empty();
        }

        /**
         * Sets {@code hints} to this object's {@code hints}.
         * A defensive copy of {@code hints} is used internally.
         */
        public void setHints(Set<Hint> hints) {
            this.hints = (hints != null) ? new HashSet<>(hints) : null;
        }

        /**
         * Returns an unmodifiable hint set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code hints} is null.
         */
        public Optional<Set<Hint>> getHints() {
            return (hints != null) ? Optional.of(Collections.unmodifiableSet(hints)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCardDescriptor)) {
                return false;
            }

            // state check
            EditCardDescriptor e = (EditCardDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getAnswer().equals(e.getAnswer())
                    && getOptions().equals(e.getOptions())
                    && getHints().equals(e.getHints());
        }
    }
}
