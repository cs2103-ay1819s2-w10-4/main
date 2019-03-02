package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hint.Hint;

/**
 * Jackson-friendly version of {@link Hint}.
 */
class JsonAdaptedHint {

    private final String hintName;

    /**
     * Constructs a {@code JsonAdaptedHint} with the given {@code hintName}.
     */
    @JsonCreator
    public JsonAdaptedHint(String hintName) {
        this.hintName = hintName;
    }

    /**
     * Converts a given {@code Hint} into this class for Jackson use.
     */
    public JsonAdaptedHint(Hint source) {
        hintName = source.hintName;
    }

    @JsonValue
    public String getHintName() {
        return hintName;
    }

    /**
     * Converts this Jackson-friendly adapted hint object into the model's {@code Hint} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted hint.
     */
    public Hint toModelType() throws IllegalValueException {
        if (!Hint.isValidHintName(hintName)) {
            throw new IllegalValueException(Hint.MESSAGE_CONSTRAINTS);
        }
        return new Hint(hintName);
    }

}
