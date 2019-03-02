package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCards.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Address;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Email;
import seedu.address.model.card.Question;
import seedu.address.testutil.Assert;

public class JsonAdaptedCardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_HINT = "#friend";

    private static final String VALID_QUESTION = BENSON.getQuestion().toString();
    private static final String VALID_ANSWER = BENSON.getAnswer().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SCORE = BENSON.getScore().toString();
    private static final List<JsonAdaptedHint> VALID_HINT = BENSON.getHints().stream()
            .map(JsonAdaptedHint::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        JsonAdaptedCard card = new JsonAdaptedCard(BENSON);
        assertEquals(BENSON, card.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(INVALID_QUESTION, VALID_ANSWER, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(null, VALID_ANSWER, VALID_EMAIL, VALID_ADDRESS, VALID_SCORE,
                VALID_HINT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, INVALID_ANSWER, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, INVALID_EMAIL, VALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, null, VALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, VALID_EMAIL, INVALID_ADDRESS,
                VALID_SCORE, VALID_HINT);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, VALID_EMAIL, null, VALID_SCORE,
                VALID_HINT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidHint_throwsIllegalValueException() {
        List<JsonAdaptedHint> invalidHint = new ArrayList<>(VALID_HINT);
        invalidHint.add(new JsonAdaptedHint(INVALID_HINT));
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCORE, invalidHint);
        Assert.assertThrows(IllegalValueException.class, card::toModelType);
    }

}
