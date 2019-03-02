package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HINT_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.HINT_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HINT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HINT_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HINT_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.AMY;
import static seedu.address.testutil.TypicalCards.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.card.Address;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Email;
import seedu.address.model.card.Question;
import seedu.address.model.hint.Hint;
import seedu.address.testutil.CardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder(BOB).withHint(VALID_HINT_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple names - last name accepted
        assertParseSuccess(parser, QUESTION_DESC_AMY + QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + ANSWER_DESC_AMY + ANSWER_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple emails - last email accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple hints - all accepted
        Card expectedCardMultipleTags = new CardBuilder(BOB).withHint(VALID_HINT_FRIEND, VALID_HINT_HUSBAND)
                .build();
        assertParseSuccess(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND, new AddCommand(expectedCardMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Card expectedCard = new CardBuilder(AMY).withHint().build();
        assertParseSuccess(parser, QUESTION_DESC_AMY + ANSWER_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_QUESTION_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_BOB + VALID_ANSWER_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_BOB + VALID_ANSWER_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND, Question.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_BOB + INVALID_ANSWER_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND, Answer.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid hint
        assertParseFailure(parser, QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_HINT_DESC + VALID_HINT_FRIEND, Hint.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid fullAnswer reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_BOB + ANSWER_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + HINT_DESC_HUSBAND + HINT_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
