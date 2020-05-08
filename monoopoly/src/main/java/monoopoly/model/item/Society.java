package monoopoly.model.item;

import java.util.Map;
import java.util.Objects;

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
		
		public Builder() {
            super();
            this.decorated          = null;
            this.table              = null;
            this.mortgageValue      = null;
            this.salesValue         = null;
            this.multiplierLevelOne = null;
            this.multiplierLevelTwo = null;
        }

        public Builder tile(Tile decorated) {
			if(decorated.getCategory() != Tile.Category.SOCIETY) {
				throw new IllegalArgumentException("the Tile isn't a Society");
			}
			this.decorated = decorated;
			return this;
		}
		
		public Builder table(ObserverPurchasable table) {
		    Objects.requireNonNull(table, "table cannot has null value!");
			this.table = table;
			return this;
		}
		
		public Builder mortgage(Double mortgageValue) {
		    Objects.requireNonNull(mortgageValue,
		                           "mortgage cannot has null value!");
		    this.doubleChecker(mortgageValue, "mortgage value wrong format!");
			this.mortgageValue = mortgageValue;
			return this;
		}

        public Builder sales(Double salesValue) {
            Objects.requireNonNull(salesValue, 
                                   "sales value cannot has null value!");
            this.doubleChecker(salesValue, "sales value wrong format!");
			this.salesValue = salesValue;
			return this;
		}
		
		public Builder multiplierLevelOne(Double multiplier) {
		    Objects.requireNonNull(multiplier, 
		                     "the multiplier level one cannot has null value!");
		    this.doubleChecker(multiplier, "multiplier level one wrong format");
			this.multiplierLevelOne = multiplier;
			return this;
		}
		
		public Builder multiplierLevelTwo(Double multiplier) {
		    Objects.requireNonNull(multiplier, 
		                     "the multiplier level two cannot has null value!");
		    this.doubleChecker(multiplier, "multiplier level one wrong format");
			this.multiplierLevelTwo = multiplier;
			return this;
		}
		
		public Society build() throws IllegalStateException{
            Objects.requireNonNull(this.decorated, 
                                "SOCIETY: Card to decor is unsetted!");
            Objects.requireNonNull(this.table,
                                "SOCIETY: The Table is unsetted!");
            Objects.requireNonNull(this.mortgageValue, 
                                "SOCIETY: the mortgage value is unsetted!");
            Objects.requireNonNull(this.salesValue, 
                                "SOCIETY: The sales Value is unsetted!");
            Objects.requireNonNull(this.multiplierLevelOne,
                                "SOCIETY: the multiplier one is Unsetted!");
            Objects.requireNonNull(this.multiplierLevelTwo,
                                "SOCIETY: the multiplier two is Unsetted!");
			return new Society(this);
		}
        
        private void doubleChecker(Double value, String string) {
            if(value.isInfinite() || value.isNaN()) {
                throw new IllegalArgumentException(string);
            }
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
		return Map.of(Society.LEVEL_ONE, 
		              this.multiplierLevelOne * this.getQuotation(), 
					  Society.LEVEL_TWO, 
					  this.multiplierLevelTwo * this.getQuotation());
	}

	@Override
	public double getLeaseValue() {
		if(super.getOwner().isPresent()) {
		    
			int nSociety = this.getNumberOfSocietyOwned();
			
			if(Society.LEVEL_ONE.equals(nSociety)) {
				return this.multiplierLevelOne * 
				       this.getQuotation() * 
				       this.table.getNotifiedDices();
				
			} else if(Society.LEVEL_TWO.equals(nSociety)) {
				return this.multiplierLevelTwo * 
				       this.getQuotation() * 
				       this.table.getNotifiedDices();
			}
		}
		return 0.0;
	}
	
	private int getNumberOfSocietyOwned() {
		return (int)this.table.getTilesforSpecificCategoty(super.getCategory())
							  .stream()
							  .map(x->(Purchasable)x)
							  .filter(x-> x.getOwner().isPresent() &&
							              super.getOwner().isPresent() &&
							              x.getOwner().get().equals(
							              super.getOwner().get()))
							  .count();
	}
	
}
