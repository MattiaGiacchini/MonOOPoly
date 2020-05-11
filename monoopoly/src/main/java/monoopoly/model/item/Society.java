package monoopoly.model.item;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Society extends AbstractPurchasable {

    private static final Double NO_LEASE = 0.0;
	private static final Integer LEVEL_ONE = 1;
	private static final Integer LEVEL_TWO = 2;
	private final double multiplierLevelOne;
	private final double multiplierLevelTwo;
    private Supplier<Integer> supplierDiceResult;
    private Function<Integer, Integer> functionGetNumOfSocietyOwned;
	
	public static class Builder{
		
		private Tile decorated;
		private Double mortgageValue;
		private Double salesValue;
		private Double multiplierLevelOne;
		private Double multiplierLevelTwo;
        private Supplier<Integer> supplierDiceResult;
        private Function<Integer, Integer> functionGetNumOfSocietyOwned;
		
		public Builder() {
            super();
            this.decorated              = null;
            this.mortgageValue          = null;
            this.salesValue             = null;
            this.multiplierLevelOne     = null;
            this.multiplierLevelTwo     = null;
            this.supplierDiceResult     = null;
            this.functionGetNumOfSocietyOwned  = null;
        }

        public Builder tile(final Tile decorated) {
			if(decorated.getCategory() != Tile.Category.SOCIETY) {
				throw new IllegalArgumentException("the Tile isn't a Society");
			}
			this.decorated = decorated;
			return this;
		}

        public Builder supplierDiceResult
                                   (final Supplier<Integer> supplierDiceResult){
            Objects.requireNonNull(supplierDiceResult,
                                 "the supplier of dice cannot has null value!");
            this.supplierDiceResult = supplierDiceResult;
            return this;
        }

        public Builder funNumOfCatOwned
                (final Function<Integer, Integer> function) {
            Objects.requireNonNull(function, 
                    "the BiFunction of category Owned cannot has null value");
            this.functionGetNumOfSocietyOwned = function;
            return this;
        }
		
		public Builder mortgage(final Double mortgageValue) {
		    Objects.requireNonNull(mortgageValue,
		                           "mortgage cannot has null value!");
		    this.doubleChecker(mortgageValue, "mortgage value wrong format!");
			this.mortgageValue = mortgageValue;
			return this;
		}

        public Builder sales(final Double salesValue) {
            Objects.requireNonNull(salesValue, 
                                   "sales value cannot has null value!");
            this.doubleChecker(salesValue, "sales value wrong format!");
			this.salesValue = salesValue;
			return this;
		}
		
		public Builder multiplierLevelOne(final Double multiplier) {
		    Objects.requireNonNull(multiplier, 
		                     "the multiplier level one cannot has null value!");
		    this.doubleChecker(multiplier, "multiplier level one wrong format");
			this.multiplierLevelOne = multiplier;
			return this;
		}
		
		public Builder multiplierLevelTwo(final Double multiplier) {
		    Objects.requireNonNull(multiplier, 
		                     "the multiplier level two cannot has null value!");
		    this.doubleChecker(multiplier, "multiplier level one wrong format");
			this.multiplierLevelTwo = multiplier;
			return this;
		}
		
		public Society build() throws IllegalStateException{
            Objects.requireNonNull(this.decorated, 
                                "SOCIETY: Card to decor is unsetted!");
            Objects.requireNonNull(this.mortgageValue, 
                                "SOCIETY: the mortgage value is unsetted!");
            Objects.requireNonNull(this.salesValue, 
                                "SOCIETY: The sales Value is unsetted!");
            Objects.requireNonNull(this.multiplierLevelOne,
                                "SOCIETY: the multiplier one is Unsetted!");
            Objects.requireNonNull(this.multiplierLevelTwo,
                                "SOCIETY: the multiplier two is Unsetted!");
            Objects.requireNonNull(this.supplierDiceResult,
                                "SOCIETY: the multiplier two is Unsetted!");
            Objects.requireNonNull(this.functionGetNumOfSocietyOwned, 
                                "SOCIETY: the BiFunction to get the "
                                + "number of Society owned is unsetted!");
			return new Society(this);
		}
        
        private void doubleChecker(final Double value, final String string) {
            if(value.isInfinite() || value.isNaN()) {
                throw new IllegalArgumentException(string);
            }
        }
	}
		
	private Society(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.multiplierLevelOne    = builder.multiplierLevelOne;
		this.multiplierLevelTwo    = builder.multiplierLevelTwo;
		this.supplierDiceResult    = builder.supplierDiceResult;
		this.functionGetNumOfSocietyOwned = 
		                             builder.functionGetNumOfSocietyOwned;
	}
	
	@Override
	public Map<Integer, Double> getLeaseList() {
		return Map.of(Society.LEVEL_ONE, 
		              super.applyQuotationOnValue(this.multiplierLevelOne), 
					  Society.LEVEL_TWO, 
					  super.applyQuotationOnValue(this.getQuotation()));
	}

	@Override
	public double getLeaseValue() {
		if(super.getOwner().isPresent()) {
			int nSociety = this.functionGetNumOfSocietyOwned.apply(
			                                           super.getOwner().get());

			if(Society.LEVEL_ONE.equals(nSociety)) {
				return super.applyQuotationOnValue(
				       this.multiplierLevelOne *
				       this.supplierDiceResult.get());
				
			} else if(Society.LEVEL_TWO.equals(nSociety)) {
				return super.applyQuotationOnValue(
				       this.multiplierLevelTwo *
                       this.supplierDiceResult.get());
			}
		}
		return Society.NO_LEASE;
	}
	
}
