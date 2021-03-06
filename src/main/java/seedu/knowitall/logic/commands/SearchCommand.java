package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.model.card.QuestionContainsKeywordsPredicate;

/**
 * Finds and lists all cards in card folder whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all cards or folders whose questions "
            + "or names contain any of the specified keywords (case-insensitive) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final QuestionContainsKeywordsPredicate predicate;

    public SearchCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.getState() != State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        requireNonNull(model);
        model.updateFilteredCard(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, model.getActiveFilteredCards().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
