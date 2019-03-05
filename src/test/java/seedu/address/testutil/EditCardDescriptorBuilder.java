package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCardDescriptor;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.hint.Hint;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCommand.EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardDescriptor descriptor) {
        this.descriptor = new EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCommand.EditCardDescriptor();
        descriptor.setQuestion(card.getQuestion());
        descriptor.setAnswer(card.getAnswer());
        descriptor.setHints(card.getHints());
    }

    /**
     * Sets the {@code Question} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Parses the {@code hint} into a {@code Set<Hint>} and set it to the {@code EditCardDescriptor}
     * that we are building.
     */
    public EditCardDescriptorBuilder withHint(String... hint) {
        Set<Hint> hintSet = Stream.of(hint).map(Hint::new).collect(Collectors.toSet());
        descriptor.setHints(hintSet);
        return this;
    }

    public EditCardDescriptor build() {
        return descriptor;
    }
}
