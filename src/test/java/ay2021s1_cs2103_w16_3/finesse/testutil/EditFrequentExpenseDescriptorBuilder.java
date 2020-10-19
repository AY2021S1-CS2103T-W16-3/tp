package ay2021s1_cs2103_w16_3.finesse.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.frequent.EditFrequentExpenseCommand.EditFrequentExpenseDescriptor;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.frequent.FrequentExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * A utility class to help with building EditFrequentExpenseDescriptor objects.
 */
public class EditFrequentExpenseDescriptorBuilder {

    private EditFrequentExpenseDescriptor descriptor;

    public EditFrequentExpenseDescriptorBuilder() {
        descriptor = new EditFrequentExpenseDescriptor();
    }

    public EditFrequentExpenseDescriptorBuilder(EditFrequentExpenseDescriptor descriptor) {
        this.descriptor = new EditFrequentExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFrequentExpenseDescriptorBuilder} with fields containing {@code transaction}'s details
     */
    public EditFrequentExpenseDescriptorBuilder(FrequentExpense frequentExpense) {
        descriptor = new EditFrequentExpenseDescriptor();
        descriptor.setTitle(frequentExpense.getTitle());
        descriptor.setAmount(frequentExpense.getAmount());
        descriptor.setCategories(frequentExpense.getCategories());
    }

    /**
     * Sets the {@code Title} of the {@code EditFrequentExpenseDescriptorBuilder} that we are building.
     */
    public EditFrequentExpenseDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditFrequentExpenseDescriptorBuilder} that we are building.
     */
    public EditFrequentExpenseDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the
     * {@code EditFrequentExpenseDescriptorBuilder} that we are building.
     */
    public EditFrequentExpenseDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditFrequentExpenseDescriptor build() {
        return descriptor;
    }
}
