package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.card.Score;
import seedu.address.model.hint.Hint;

/**
 * Jackson-friendly version of {@link Card}.
 */
class JsonAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String question;
    private final String answer;
    private final String score;
    private final List<JsonAdaptedHint> hintList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCard} with the given card details.
     */
    @JsonCreator
    public JsonAdaptedCard(@JsonProperty("question") String question, @JsonProperty("answer") String answer,
                           @JsonProperty("score") String score, @JsonProperty("hint") List<JsonAdaptedHint> hintList) {
        this.question = question;
        this.answer = answer;
        this.score = score;
        if (hintList != null) {
            this.hintList.addAll(hintList);
        }
    }

    /**
     * Converts a given {@code Card} into this class for Jackson use.
     */
    public JsonAdaptedCard(Card source) {
        question = source.getQuestion().fullQuestion;
        answer = source.getAnswer().fullAnswer;
        score = source.getScore().toString();
        hintList.addAll(source.getHints().stream()
                .map(JsonAdaptedHint::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted card object into the model's {@code Card} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card.
     */
    public Card toModelType() throws IllegalValueException {
        final List<Hint> cardHints = new ArrayList<>();
        for (JsonAdaptedHint hint : hintList) {
            cardHints.add(hint.toModelType());
        }

        if (question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_CONSTRAINTS);
        }
        final Question modelQuestion = new Question(question);

        if (answer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName()));
        }
        if (!Answer.isValidAnswer(answer)) {
            throw new IllegalValueException(Answer.MESSAGE_CONSTRAINTS);
        }
        final Answer modelAnswer = new Answer(answer);

        if (score == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Score.class.getSimpleName()));
        }
        if (!Score.isValidScore(score)) {
            throw new IllegalValueException(Score.MESSAGE_CONSTRAINTS);
        }
        final Score modelScore = new Score(score);

        final Set<Hint> modelHints = new HashSet<>(cardHints);
        return new Card(modelQuestion, modelAnswer, modelScore, modelHints);
    }

}
