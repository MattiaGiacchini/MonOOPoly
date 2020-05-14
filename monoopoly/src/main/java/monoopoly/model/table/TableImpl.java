package monoopoly.model.table;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import monoopoly.model.table.tile.Tile;
import monoopoly.model.table.tile.Tile.Category;
import monoopoly.model.table.tile.purchasable.Purchasable;

/**
 * This class contains the all tile of monopoly
 * and all method to retrieve them!
 */
public class TableImpl implements Table {

    private static final int BEGIN_POSITION = 0;
    private static final double START_VALUE = 200.0;

    private final Map<Integer, Tile> table;
    private Integer sumOfDicesThrownNotified;

    /**
     * this is the constructor of class TableImpl.
     */
    public TableImpl() {
        super();
        this.sumOfDicesThrownNotified = 0;
        this.table = new TableFactory().createTable(this);
    }

    @Override
    public final void setNewQuotationToSpecificPurchasableCategory(
            final Category category, final double quotation) {
        this.table.entrySet().stream()
        .filter(x -> x.getValue().getCategory() == category)
        .peek(x -> {
            if (!x.getValue().isPurchasable()) {
                throw new ClassCastException("The Tile["
                        + x.getKey() + "] isn't Purchasable");
            }
        })
        .map(x -> (Purchasable) x.getValue())
        .forEach(x -> x.setQuotation(quotation));
    }

    @Override
    public final Tile getTile(final Integer position) {
        this.inputCheckIntegerType(position);
        this.indexCheckTableBounds(position);
        return this.table.get(position);
    }

    @Override
    public final Set<Purchasable>
    getPurchasableTilesforSpecificPlayer(final Integer idPlayer) {
        this.inputCheckIntegerType(idPlayer);
        return this.table.entrySet().stream()
                .filter(x -> x.getValue().isPurchasable())
                .map(x -> (Purchasable) x.getValue())
                .filter(x -> !x.getOwner().isEmpty()
                        && x.getOwner().get().equals(idPlayer))
                .collect(Collectors.toSet());
    }

    @Override
    public final Integer getTableSize() {
        return this.table.size();
    }

    @Override
    public final int getNotifiedDices() {
        return this.sumOfDicesThrownNotified;
    }

    @Override
    public final double getValueToRetrieveFromStart() {
        return TableImpl.START_VALUE;
    }

    @Override
    public final Integer getJailPosition() {
        for (final var elem : this.table.entrySet()) {
            if (elem.getValue().getCategory().equals(Category.JAIL)) {
                return elem.getKey();
            }
        }
        throw new IllegalStateException("The jail does not exist!");
    }

    @Override
    public final void notifyDices(final Integer sum) {
        this.inputCheckIntegerType(sum);
        this.sumOfDicesThrownNotified = sum;
    }

    @Override
    public final <T extends Tile> Set<T> getFilteredTiles(final Class<T> type,
            final Predicate<Tile> filter) {
        return this.table
                .entrySet()
                .stream()
                .filter(x -> filter.test(x.getValue()))
                .peek((x) -> {
                    if (!type.isAssignableFrom(x.getValue().getClass())) {
                        throw new ClassCastException("the class "
                                + type + " isn't a superClass of "
                                + x.getValue().getClass());
                    }
                }
                        )
                .map(x -> type.cast(x.getValue()))
                .collect(Collectors.toSet());
    }

    @Override
    public final Integer getTilePosition(final Tile tile) {
        for (final var pos : this.table.keySet()) {
            if (this.table.get(pos).equals(tile)) {
                return pos;
            }
        }
        throw new IllegalArgumentException("The tile dosen't Exist!");
    }

    private void inputCheckIntegerType(final Integer elem) {
        Objects.requireNonNull(elem);
        if (!(elem.getClass().equals(Integer.class))) {
            throw new IllegalArgumentException(
                    "The Parameter isn't an Integer Type");
        }
    }

    private void indexCheckTableBounds(final Integer position) {
        Objects.requireNonNull(position);
        if (position < TableImpl.BEGIN_POSITION
                || this.getTableSize() <= position) {
            throw new IndexOutOfBoundsException(
                    "The Index is out of Table's Bounds");
        }
    }
}
