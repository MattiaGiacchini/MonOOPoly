package monoopoly.model.table.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * The class PropertyEffect is a decorator of card.
 */
public final class PropertyEffect extends AbstractCardDecorator {

    private static final int ZERO = 0;
    private static final int ONE = 1;

    private final Optional<Map<Integer, Integer>> propertyToModify;

    /**
     * This nested static class is used
     * to create a new instance of {@link PropertyEffect}.
     */
    public static class Builder {

        private Card cardToDecore;
        private Map<Integer, Integer> buildingsToModify;

        /**
         * this method is used to set the card to decore.
         *
         * @param card to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder cardToDecore(final Card card) {
            Objects.requireNonNull(card,
                    "The decorator cannot decor a null pointer");
            this.cardToDecore = card;
            return this;
        }

        /**
         * This method is used to set the map of propety's number on the table.
         *
         * @param property on the table
         * @return {@link Builder} for a fluent programming
         */
        public Builder buildingsToModify(final Map<Integer, Integer> property) {
            Objects.requireNonNull(property,
                    "the property map cannot has null value");
            this.buildingsToModify = property;
            return this;
        }

        /**
         * this method is used to create a new instance of
         * {@link PropertyEffect} using all parameters you already
         * pass in the {@link Builder}.
         *
         * @return a new Instance of {@link PropertyEffect}
         * @throws NullPointerException if the parameters aren't formatted
         *                              correctly
         *
         */
        public PropertyEffect build() {
            Objects.requireNonNull(this.cardToDecore,
                    "The PropertyEffect cannot decore a null pointer");
            Objects.requireNonNull(this.buildingsToModify,
                    "The PropertyEffect must have a map of Buildings");
            return new PropertyEffect(this);
        }
    }

    private PropertyEffect(final Builder builder) {
        super(builder.cardToDecore);

        final Map<Integer, Integer> mapOfProperty = new HashMap<>();

        final Random rnd = new Random();

        for (final var value : builder.buildingsToModify.entrySet()) {
            mapOfProperty.put(value.getKey(),
                    this.calculateTheNumberOfBuildings(value.getValue(),
                            getLastChangeApplyed(value), rnd));
        }

        if (mapOfProperty.values().stream()
                .allMatch(x -> x.equals(PropertyEffect.ZERO))) {
            this.propertyToModify = Optional.empty();
        } else {
            this.propertyToModify = Optional.of(
                    Collections.unmodifiableMap(mapOfProperty));
        }
    }

    @Override
    public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
        return this.propertyToModify;
    }

    private Integer calculateTheNumberOfBuildings(final Integer actualProperty,
            final Integer lastChangeApplyed, final Random rnd) {
        return actualProperty - lastChangeApplyed == PropertyEffect.ZERO
                ? lastChangeApplyed : lastChangeApplyed + rnd.nextInt(
                        actualProperty - lastChangeApplyed + PropertyEffect.ONE);
    }

    private int getLastChangeApplyed(final Entry<Integer, Integer> value) {
        return super.getNumberOfBuildingsToRemove().isPresent()
                ? super.getNumberOfBuildingsToRemove().get().get(value.getKey())
                        : PropertyEffect.ZERO;
    }

}
