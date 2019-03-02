package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Address;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Email;
import seedu.address.model.card.Question;
import seedu.address.model.card.Score;
import seedu.address.model.hint.Hint;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ANSWER = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCORE = "0/0";

    private Question question;
    private Answer answer;
    private Email email;
    private Address address;
    private Score score;
    private Set<Hint> hints;

    public CardBuilder() {
        question = new Question(DEFAULT_NAME);
        answer = new Answer(DEFAULT_ANSWER);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        score = new Score(DEFAULT_SCORE);
        hints = new HashSet<>();
    }

    /**
     * Initializes the CardBuilder with the data of {@code cardToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        question = cardToCopy.getQuestion();
        answer = cardToCopy.getAnswer();
        email = cardToCopy.getEmail();
        address = cardToCopy.getAddress();
        score = cardToCopy.getScore();
        hints = new HashSet<>(cardToCopy.getHints());
    }

    /**
     * Sets the {@code Question} of the {@code Card} that we are building.
     */
    public CardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code hints} into a {@code Set<Hint>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withHint(String ... hint) {
        this.hints = SampleDataUtil.getHintSet(hint);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Card} that we are building.
     */
    public CardBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Card} that we are building.
     */
    public CardBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Score} of the {@code Card} that we are building.
     */
    public CardBuilder withScore(String score) {
        this.score = new Score(score);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Card} that we are building.
     */
    public CardBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Card build() {
        return new Card(question, answer, email, address, score, hints);
    }

}
