package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class TestSession extends UiPart<Region> {

    private static final String FXML = "TestSession.fxml";

    private Card cardToTest; //final?

    @FXML
    private StackPane testSessionPage;
    @FXML
    private Label testCard;

    public TestSession() {
        super(FXML);
    }
    public TestSession(Card cardToTest) {
        super(FXML);
        this.cardToTest = cardToTest;
        displayCard(cardToTest);
    }

    /**
     * Updates the UI to show test session page with the question of the specified card
     * @param cardToTest card to be tested in this page of test session
     */
    public void displayCard(Card cardToTest) {
        testSessionPage.getChildren().clear();
        testCard.setText(cardToTest.getQuestion().fullQuestion);
        testSessionPage.getChildren().add(testCard);
    }
}

