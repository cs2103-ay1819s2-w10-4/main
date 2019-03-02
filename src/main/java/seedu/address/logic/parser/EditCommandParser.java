package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hint.Hint;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_HINT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditCardDescriptor editCardDescriptor = new EditCommand.EditCardDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editCardDescriptor.setQuestion(ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get()));
        }
        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editCardDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editCardDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCardDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseHintsForEdit(argMultimap.getAllValues(PREFIX_HINT)).ifPresent(editCardDescriptor::setHints);

        if (!editCardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editCardDescriptor);
    }

    /**
     * Parses {@code Collection<String> hints} into a {@code Set<Hint>} if {@code hints} is non-empty.
     * If {@code hints} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Hint>} containing zero hints.
     */
    private Optional<Set<Hint>> parseHintsForEdit(Collection<String> hints) throws ParseException {
        assert hints != null;

        if (hints.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> hintSet = hints.size() == 1 && hints.contains("") ? Collections.emptySet() : hints;
        return Optional.of(ParserUtil.parseHints(hintSet));
    }

}
