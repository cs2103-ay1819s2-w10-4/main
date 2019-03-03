package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Card;

/**
 * An Immutable CardFolder that is serializable to JSON format.
 */
@JsonRootName(value = "cardfolder")
class JsonSerializableCardFolder {

    public static final String MESSAGE_DUPLICATE_CARD = "Cards list contains duplicate card(s).";

    private final String folderName;
    private final List<JsonAdaptedCard> cards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCardFolder} with the given cards.
     */
    @JsonCreator
    public JsonSerializableCardFolder(@JsonProperty("folderName") String folderName,
                                      @JsonProperty("cards") List<JsonAdaptedCard> cards) {
        this.folderName = folderName;
        this.cards.addAll(cards);
    }

    /**
     * Converts a given {@code ReadOnlyCardFolder} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCardFolder}.
     */
    public JsonSerializableCardFolder(ReadOnlyCardFolder source) {
        cards.addAll(source.getCardList().stream().map(JsonAdaptedCard::new).collect(Collectors.toList()));
        folderName = source.getFolderName();
    }

    /**
     * Converts this card folder into the model's {@code CardFolder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CardFolder toModelType() throws IllegalValueException {
        CardFolder cardFolder = new CardFolder(folderName);
        for (JsonAdaptedCard jsonAdaptedCard : cards) {
            Card card = jsonAdaptedCard.toModelType();
            if (cardFolder.hasCard(card)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CARD);
            }
            cardFolder.addCard(card);
        }
        return cardFolder;
    }

}
