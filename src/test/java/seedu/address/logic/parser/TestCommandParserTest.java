package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD_FOLDER;

import org.junit.Test;

import seedu.address.logic.commands.TestCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class TestCommandParserTest {

    private TestCommandParser parser = new TestCommandParser();

    @Test
    public void parse_validArgs_returnsTestCommand() {
        assertParseSuccess(parser, "1", new TestCommand(INDEX_FIRST_CARD_FOLDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestCommand.MESSAGE_USAGE));
    }
}
