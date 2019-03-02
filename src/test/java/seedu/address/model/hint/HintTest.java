package seedu.address.model.hint;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HintTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Hint(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hint(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null hint name
        Assert.assertThrows(NullPointerException.class, () -> Hint.isValidHintName(null));
    }

}
