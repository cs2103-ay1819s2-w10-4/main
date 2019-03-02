package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuestionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Question(invalidName));
    }

    @Test
    public void isValidName() {
        // null question
        Assert.assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid question
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid question
        assertTrue(Question.isValidQuestion("peter jack")); // alphabets only
        assertTrue(Question.isValidQuestion("12345")); // numbers only
        assertTrue(Question.isValidQuestion("peter the 2nd")); // alphanumeric characters
        assertTrue(Question.isValidQuestion("Capital Tan")); // with capital letters
        assertTrue(Question.isValidQuestion("David Roger Jackson Ray Jr 2nd")); // long questions
    }
}
