package monoopoly.model.item;

import java.util.Map;
import java.util.Optional;

/**
 * this Class is used to implement the pattern template method
 * for the Purchasable Class.
 */
public abstract class AbstractPurchasable extends AbstractTileDecorator
implements Purchasable {

    private static final double PERCENTAGE_TO_REMOVE_MORTGAGE = 1.1;
    private static final double BASE_QUOTATION = 1.0;

    private final double morgageValue;
    private final double salesValue;

    private Optional<Integer> ownerIdentify;
    private Double quotation;
    private boolean mortgageStatus;

    /**
     * {@link AbstractPurchasable} Constructor.
     *
     * @param decorated Tile to decore
     * @param mortgageValue of the Purchasable Tile
     * @param salesValue of the Purchasable Tile
     * @throws IllegalStateException if the tile to decore
     *         isn't a Purchasable Tile
     */
    protected AbstractPurchasable(final Tile decorated,
            final double mortgageValue,
            final double salesValue) {
        super(decorated);
        if (!decorated.isPurchasable()) {
            throw new IllegalStateException("The Tile isn't Purchasable");
        }
        this.mortgageStatus = false;
        this.quotation = AbstractPurchasable.BASE_QUOTATION;
        this.morgageValue = mortgageValue;
        this.salesValue = salesValue;
        this.ownerIdentify = Optional.empty();
    }

    @Override
    public final double mortgage() {
        this.mortgageStatus = true;
        return this.getMortgageValue();
    }

    @Override
    public final boolean isMortgage() {
        return this.mortgageStatus;
    }

    @Override
    public final void removeMortgage() {
        this.mortgageStatus = false;
    }

    @Override
    public abstract double getLeaseValue();

    @Override
    public final double getSalesValue() {
        return this.applyQuotationOnValue(this.salesValue);
    }

    @Override
    public final double getMortgageValue() {
        return this.applyQuotationOnValue(this.morgageValue);
    }

    @Override
    public final double getCostToRemoveMortgage() {
        return this.applyQuotationOnValue(this.morgageValue * AbstractPurchasable.PERCENTAGE_TO_REMOVE_MORTGAGE);
    }

    @Override
    public final double getQuotation() {
        return this.quotation;
    }

    @Override
    public final void setQuotation(final double quotation) {
        this.quotation = quotation;
    }

    @Override
    public final void setOwner(final Optional<Integer> newOwnerIdentify) {
        this.ownerIdentify = newOwnerIdentify;
    }

    @Override
    public final Optional<Integer> getOwner() {
        return this.ownerIdentify;
    }

    @Override
    public abstract Map<Integer, Double> getLeaseList();

    /**
     * this method is created for the class who extends
     * {@link AbstractPurchasable}, it's used to apply
     * quotation on a single value.
     *
     * @param value to apply the quotation
     * @return value with quotation applied
     */
    protected final Double applyQuotationOnValue(final Double value) {
        return value * this.getQuotation();
    }
}
