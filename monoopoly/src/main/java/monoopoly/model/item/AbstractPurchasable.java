package monoopoly.model.item;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractPurchasable extends AbstractTileDecorator implements Purchasable {

	private final static double PERCENTAGE_TO_REMOVE_MORTGAGE = 1.1;
	private final static double BASE_QUOTATION = 1.0;
	
	private final double morgageValue;
	private final double salesValue;
	
	private Optional<Integer> ownerIdentify;
	private double quotation;
	private boolean mortgageStatus;
	
	protected AbstractPurchasable(Tile decorated,
 								  double mortgageValue,
 								  double salesValue) {
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
	public double mortgage() {
		this.mortgageStatus = true;
		return this.morgageValue;
	}

	@Override
	public boolean isMortgage() {
		return this.mortgageStatus;
	}

	@Override
	public void removeMortgage() {
		this.mortgageStatus = false;
	}

	@Override
	public abstract double getLeaseValue();

	@Override
	public double getSalesValue() {
		return this.salesValue * this.getQuotation();
	}

	@Override
	public double getMortgageValue() {
		return this.morgageValue;
	}

	@Override
	public double getCostToRemoveMortgage() {
		return this.morgageValue * AbstractPurchasable.PERCENTAGE_TO_REMOVE_MORTGAGE;
	}

	@Override
	public double getQuotation() {
		return this.quotation;
	}

	@Override
	public void setQuotation(double quotation) {
		this.quotation = quotation;
	}

	@Override
	public void setOwner(final Optional<Integer> newOwnerIdentify) {
		this.ownerIdentify = newOwnerIdentify;
	}

	@Override
	public Optional<Integer> getOwner() {
		return this.ownerIdentify;
	}

	abstract public Map<Integer, Double> getLeaseList();
}
