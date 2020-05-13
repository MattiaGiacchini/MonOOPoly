package monoopoly.model.item.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import monoopoly.model.item.Tile.Category;

/**
 * The class MoveEffect is a decorator of card.
 */
public final class MoveEffect extends AbstractCardDecorator {

    private static final Integer ZERO = 0;

    private final Optional<Map<Integer, Integer>> absoluteMoveToApply;
    private final Optional<Map<Integer, Integer>> relativeMove;

    /**
     * This nested static class is used
     * to create a new instance of {@link MoveEffect}.
     */
    public static class Builder {
        private Card cardToDecore;
        private Integer tableSize;
        private Map<Integer, Integer> playersPosition;
        private Integer idDrawer;
        private Optional<BiFunction<Integer, Category, Integer>> tileRetriver;
        private Optional<Category> categoryToReach;
        private Optional<Integer> stepsToDo;
        private Optional<Integer> tilePositionToGo;
        private boolean applyToPlayer;
        private boolean applyToOthers;
        private boolean randomStep;

        /**
         * {@link Builder}'s Constructor.
         */
        public Builder() {
            super();
            this.cardToDecore = null;
            this.tableSize = null;
            this.playersPosition = null;
            this.idDrawer = null;
            this.tileRetriver = Optional.empty();
            this.categoryToReach = Optional.empty();
            this.stepsToDo = Optional.empty();
            this.tilePositionToGo = Optional.empty();
            this.applyToPlayer = false;
            this.applyToOthers = false;
            this.randomStep = false;
        }

        /**
         * this method is used to set the card to decorate!
         *
         * @param card to decore
         * @return {@link Builder} for a fluent programming
         */
        public Builder cardToDecore(final Card card) {
            Objects.requireNonNull(card, "The decorator cannot decor "
                    + "a null pointer");
            this.cardToDecore = card;
            return this;
        }

        /**
         * this method is used to pass the players position.
         *
         * @param playersPosition is a map where the key are the idPlayer and
         *                        the value are the respective position
         * @return {@link Builder} for a fluent programming
         */
        public Builder playersPosition(
                final Map<Integer, Integer> playersPosition) {
            Objects.requireNonNull(playersPosition, "the Map of players "
                    + "position cannot has null value");
            this.playersPosition = playersPosition;
            return this;
        }

        /**
         * this method is used to set the id of player who has drawn.
         *
         * @param idDrawer player's id
         * @return {@link Builder} for a fluent programming
         */
        public Builder idDrawers(final Integer idDrawer) {
            Objects.requireNonNull(idDrawer, "the id Drawer "
                    + "cannot has null value");
            this.idDrawer = idDrawer;
            return this;
        }

        /**
         * this method is used to set the table size.
         *
         * @param tableSize value
         * @return {@link Builder} for a fluent programming
         */
        public Builder tableSize(final Integer tableSize) {
            Objects.requireNonNull(tableSize, "the table Size "
                    + "cannot has null value");
            if (tableSize < 1) {
                throw new IllegalArgumentException("the table size "
                        + "must have a positive number");
            }
            this.tableSize = tableSize;
            return this;
        }

        /**
         * this method is used to set the value of steps which
         * the target must do.
         *
         * @param steps to do
         * @return {@link Builder} for a fluent programming
         */
        public Builder stepToDo(final Integer steps) {
            this.stepsToDo = Optional.ofNullable(steps);
            return this;
        }

        /**
         * this method is used to set the function to use for
         * retrieve the next position of a specific tile category from an
         * actual position.
         *
         * @param biFunction to use
         * @return {@link Builder} for a fluent programming
         */
        public Builder tileRetriverFromCategory(
                final BiFunction<Integer, Category, Integer> biFunction) {
            this.tileRetriver = Optional.ofNullable(biFunction);
            return this;
        }

        /**
         * this method is used to set a position which a target
         * must go to.
         *
         * @param position to go
         * @return {@link Builder} for a fluent programming
         */
        public Builder tilePositionToGo(final Integer position) {
            this.tilePositionToGo = Optional.ofNullable(position);
            return this;
        }

        /**
         * this method is used to set the category to reach.
         *
         * @param category to reach.
         * @return {@link Builder} for a fluent programming
         */
        public Builder nextTileCategoryToReach(final Category category) {
            this.categoryToReach = Optional.ofNullable(category);
            return this;
        }

        /**
         * this method enable or disable the function which create
         * random step for a specific target.
         *
         * @param value true will enable the function, false will disable the
         *        function.
         * @return {@link Builder} for a fluent programming
         */
        public Builder generateRandomStep(final boolean value) {
            this.randomStep = value;
            return this;
        }

        /**
         * this method is used to set if the target must consider the player
         * who has drawn or not.
         *
         * @param value true if the player must be considered, false if not.
         * @return {@link Builder} for a fluent programming
         */
        public Builder applyToPlayer(final boolean value) {
            this.applyToPlayer = value;
            return this;
        }

        /**
         * this method is used to set if the target must consider the players
         * without who has drawn or not.
         *
         * @param value true if the others player must be considered,
         *        false if not.
         * @return {@link Builder} for a fluent programming
         */
        public Builder applyToOthers(final boolean value) {
            this.applyToOthers = value;
            return this;
        }

        /**
         * This method is used to create the instance of
         * {@link MoveEffect} using all parameters you already pass
         * to the {@link Builder}.
         *
         * @return new Instance of {@link MoveEffect}
         * @throws IllegalStateException if parameters aren't
         *         formatted correctly
         * @throws NullPointerException if some important parameters
         *         aren't setted.
         */
        public MoveEffect build() {
            Objects.requireNonNull(this.cardToDecore,
                    "card To decore unsetted");
            Objects.requireNonNull(this.tableSize,
                    "table size unsetted");
            Objects.requireNonNull(this.playersPosition,
                    "players Position unsetted");
            Objects.requireNonNull(this.idDrawer,
                    "Id Drawer Unsetted");
            if (!this.applyToOthers && !this.applyToPlayer) {
                throw new IllegalStateException(
                        "the movement effect hasn't a target");
            }
            if (this.categoryToReach.isPresent()
                    && this.tileRetriver.isEmpty()) {
                throw new IllegalArgumentException(
                        "the BiFunction cannot has Null value to "
                                + "retrive the next category tile");
            } else if (this.categoryToReach.isEmpty()
                    && this.tileRetriver.isPresent()) {
                throw new IllegalArgumentException(
                        "the Category cannot has Null value to "
                                + "retrive the next category tile");
            }
            if (this.tilePositionToGo.isEmpty()
                    && this.stepsToDo.isEmpty()
                    && this.categoryToReach.isEmpty()
                    && !this.randomStep) {
                throw new IllegalStateException("there aren't movement to do");
            }
            return new MoveEffect(this);
        }
    }

    private MoveEffect(final Builder builder) {
        super(builder.cardToDecore);

        final Random rnd = new Random();

        final Map<Integer, Integer> stepsToDo = new HashMap<>();
        final Map<Integer, Integer> absoluteMove = new HashMap<>();
        final Set<Integer> idPlayers = new HashSet<>();

        // selection of players to apply the movement
        if (builder.applyToOthers && builder.applyToPlayer) {
            idPlayers.addAll(builder.playersPosition.keySet());
        } else if (builder.applyToOthers) {
            idPlayers.addAll(builder.playersPosition.keySet());
            idPlayers.remove(builder.idDrawer);
        } else if (builder.applyToPlayer) {
            idPlayers.add(builder.idDrawer);
        }

        if (builder.randomStep) {
            idPlayers.forEach(x -> {
                stepsToDo.put(x, this.newRandomStepsInteger(rnd,
                        builder.tableSize));
            });
        } else if (builder.stepsToDo.isPresent()) {
            idPlayers.forEach(x -> {
                stepsToDo.put(x, builder.stepsToDo.get());
            });
        } else if (builder.categoryToReach.isPresent()) {
            idPlayers.forEach(idPlayer -> {
                if (!builder.tileRetriver.get().apply(
                        builder.playersPosition.get(idPlayer),
                        builder.categoryToReach.get())
                        .equals(MoveEffect.ZERO)) {
                    stepsToDo.put(idPlayer, builder.tileRetriver
                            .get().apply(
                                    builder.playersPosition.get(idPlayer),
                                    builder.categoryToReach.get()));
                }
            });
        } else if (builder.tilePositionToGo.isPresent()) {
            idPlayers.forEach(idPlayer -> {
                absoluteMove.put(idPlayer, builder.tilePositionToGo.get());
            });
        }

        if (absoluteMove.isEmpty()) {
            this.absoluteMoveToApply = super.getAbsoluteMoveToPosition();
        } else {
            if (super.getAbsoluteMoveToPosition().isPresent()) {
                super.getAbsoluteMoveToPosition().get()
                .entrySet()
                .forEach(entry -> {
                    if (!absoluteMove.containsKey(entry.getKey())) {
                        absoluteMove.put(entry.getKey(), entry.getValue());
                    }
                });
            }

            final var tmpSet = absoluteMove.entrySet()
                    .stream()
                    .filter(x -> x.getValue()
                            .equals(builder.playersPosition
                                    .get(x.getKey())
                                    )
                            )
                    .map(x -> x.getKey())
                    .collect(Collectors.toSet());

            for (final var value : tmpSet) {
                absoluteMove.remove(value);
            }

            if (absoluteMove.isEmpty()) {
                this.absoluteMoveToApply = super.getAbsoluteMoveToPosition();
            } else {
                this.absoluteMoveToApply = Optional.of(Collections
                        .unmodifiableMap(
                                absoluteMove));
            }
        }

        if (this.absoluteMoveToApply.isPresent()) {
            this.relativeMove = Optional.empty();
        } else if (stepsToDo.isEmpty()) {
            this.relativeMove = super.getAbsoluteMoveToPosition();
        } else {
            if (super.getRelativeMoveToPosition().isPresent()) {
                super.getRelativeMoveToPosition().get()
                .entrySet()
                .forEach(entry -> {
                    if (stepsToDo.containsKey(entry.getKey())) {
                        if (entry.getValue()
                                + stepsToDo.get(entry.getKey())
                                == MoveEffect.ZERO) {
                            stepsToDo.remove(entry.getKey());
                        } else {
                            stepsToDo.put(entry.getKey(), entry.getValue()
                                    + stepsToDo.get(entry.getKey()));
                        }
                    } else {
                        stepsToDo.put(entry.getKey(), entry.getValue());
                    }
                });
            }
            if (stepsToDo.isEmpty()) {
                this.relativeMove = Optional.empty();
            } else {
                this.relativeMove = Optional.of(
                        Collections.unmodifiableMap(stepsToDo));
            }
        }
    }

    @Override
    public Optional<Map<Integer, Integer>> getRelativeMoveToPosition() {
        return  this.relativeMove;
    }

    @Override
    public Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition() {
        return  this.absoluteMoveToApply;
    }

    private Integer newRandomStepsInteger(final Random rnd,
            final Integer bound) {
        Integer value = MoveEffect.ZERO;
        do {
            value = rnd.nextInt(Math.abs(bound + bound)) - bound;
        } while (value.equals(MoveEffect.ZERO));
        return value;
    }

}
