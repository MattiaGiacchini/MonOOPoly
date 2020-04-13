package monoopoly.model.item;

import java.util.Map;

public final class Society extends AbstractPurchasable {

	private static final Integer LEVEL_ONE = 1;
	private static final Integer LEVEL_TWO = 2;
	private final ObserverPurchasable table;
	private final double multiplierLevelOne;
	private final double multiplierLevelTwo;
	
	public static class Builder{
		
		private Tile decorated;
		private ObserverPurchasable table;
		private Double mortgageValue;
		private Double salesValue;
		private Double multiplierLevelOne;
		private Double multiplierLevelTwo;
		
		public Builder(){}
		
		public Builder tile(Tile decorated) {
			if(decorated.getCategory() != Tile.Category.SOCIETY) {
				throw new IllegalArgumentException("the Tile isn't a Society");
			}
			this.decorated = decorated;
			return this;
		}
		
		public Builder table(ObserverPurchasable table) {
			this.table = table;
			return this;
		}
		
		public Builder mortgage(double mortgageValue) {
			this.mortgageValue = mortgageValue;
			return this;
		}
		
		public Builder sales(double salesValue) {
			this.salesValue = salesValue;
			return this;
		}
		
		public Builder multiplierLevelOne(double multiplier) {
			this.multiplierLevelOne = multiplier;
			return this;
		}
		
		public Builder multiplierLevelTwo(double multiplier) {
			this.multiplierLevelTwo = multiplier;
			return this;
		}
		
		public Society build() throws IllegalStateException{
			if(this.decorated == null ||
			   this.table == null ||
			   this.mortgageValue == null ||
			   this.salesValue == null ||
			   this.multiplierLevelOne == null ||
			   this.multiplierLevelTwo == null) {
				throw new IllegalStateException("Wrong composition of Society!");
			}
			return new Society(this);
		}
	}
		
	private Society(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.table = builder.table;
		this.multiplierLevelOne = builder.multiplierLevelOne;
		this.multiplierLevelTwo = builder.multiplierLevelTwo;
	}
	
	

	@Override
	public Map<Integer, Double> getLeaseList() {
		return Map.of(Society.LEVEL_ONE, this.multiplierLevelOne*this.getQuotation(), 
					  Society.LEVEL_TWO, this.multiplierLevelTwo*this.getQuotation());
	}

	@Override
	public double getLeaseValue() {
		if(super.getOwner().isPresent()) { 
			int nSociety = this.getNumberOfSocietyOwned();
			if(nSociety == Society.LEVEL_ONE) {
				return this.multiplierLevelOne * this.getQuotation() * this.table.getNotifiedDices();
			} else if(nSociety == Society.LEVEL_TWO) {
				return this.multiplierLevelTwo * this.getQuotation() * this.table.getNotifiedDices();
			}
		}
		return 0.0;
	}
	
	private int getNumberOfSocietyOwned() {
		return (int)this.table.getTilesforSpecificCategoty(super.getCategory())
							  .stream()
							  .map(x->(Purchasable)x)
							  .filter(x-> x.getOwner().isPresent() && x.getOwner().get() == this.getOwner().get())
							  .count();
	}
	

}
