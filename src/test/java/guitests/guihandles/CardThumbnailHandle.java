package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * Provides a handle to a card card in the card list panel.
 */
public class CardThumbnailHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#question";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String ANSWER_FIELD_ID = "#answer";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String HINTS_FIELD_ID = "#hints";

    private final Label idLabel;
    private final Label questionLabel;
    private final Label addressLabel;
    private final Label answerLabel;
    private final Label emailLabel;
    private final List<Label> hintLabel;

    public CardThumbnailHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        questionLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        answerLabel = getChildNode(ANSWER_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region hintContainer = getChildNode(HINTS_FIELD_ID);
        hintLabel = hintContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getAnswer() {
        return answerLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getHint() {
        return hintLabel
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code card}.
     */
    public boolean equals(Card card) {
        return getQuestion().equals(card.getQuestion().fullQuestion)
                && getAddress().equals(card.getAddress().value)
                && getAnswer().equals(card.getAnswer().fullAnswer)
                && getEmail().equals(card.getEmail().value)
                && ImmutableMultiset.copyOf(getHint()).equals(ImmutableMultiset.copyOf(card.getHints().stream()
                        .map(hint -> hint.hintName)
                        .collect(Collectors.toList())));
    }
}
