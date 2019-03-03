package seedu.address.testutil;

import seedu.address.model.CardFolder;
import seedu.address.model.card.Card;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CardFolder ab = new CardFolderBuilder().withCard("John", "Doe").build();}
 */
public class CardFolderBuilder {

    private CardFolder cardFolder;

    public CardFolderBuilder() {
        cardFolder = new CardFolder(this.getClass().getName());
    }

    public CardFolderBuilder(CardFolder cardFolder) {
        this.cardFolder = cardFolder;
    }

    /**
     * Adds a new {@code Card} to the {@code CardFolder} that we are building.
     */
    public CardFolderBuilder withCard(Card card) {
        cardFolder.addCard(card);
        return this;
    }

    public CardFolder build() {
        return cardFolder;
    }
}
