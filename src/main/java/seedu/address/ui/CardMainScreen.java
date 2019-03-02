package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component for the list of cards and browser panel after entering a folder
 */
public class CardMainScreen extends UiPart<Region> {

    private static final String FXML = "CardMainScreen.fxml";

    @FXML
    private StackPane cardListPanelPlaceholder;
    @FXML
    private StackPane browserPlaceholder;

    public CardMainScreen(CardListPanel cardListPanel, BrowserPanel browser) {
        super(FXML);
        this.cardListPanelPlaceholder.getChildren().add(cardListPanel.getRoot());
        this.browserPlaceholder.getChildren().add(browser.getRoot());
    }

}
