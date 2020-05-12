package monoopoly.model.item;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractPurchasable extends AbstractTileDecorator
                                          implements Purchasable {

	private final static double PERCENTAGE_TO_REMOVE_MORTGAGE = 1.1;
	private final static double BASE_QUOTATION = 1.0;

	private final double morgageValue;
	private final double salesValue;

	private Optional<Integer> ownerIdentify;
	private Double quotation;
	private boolean mortgageStatus;

	protected AbstractPurchasable(final Tile decorated,
 								  final double mortgageValue,
 								  final double salesValue) {
		super(decorated);
		if(!decorated.isPurchasable()) {
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
		return this.applyQuotationOnValue(
		       this.morgageValue *
		       AbstractPurchasable.PERCENTAGE_TO_REMOVE_MORTGAGE);
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
    abstract public Map<Integer, Double> getLeaseList();

	protected final Double applyQuotationOnValue(final Double value) {
        return value * this.getQuotation();
	}
}
