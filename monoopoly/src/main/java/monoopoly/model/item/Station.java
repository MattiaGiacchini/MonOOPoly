package monoopoly.model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Station extends AbstractPurchasable {
	
	private static final Integer CORRECTION = 1;
	private static final Integer ZERO = 0;
	private static final Double BASE = 2.0;
	private final Double leaseBase;
    private final Integer numOfStations;
    private final Function<Integer, Integer> funToRetriveNumOfStationOwned;

	public static class Builder{
		
		private Tile decorated;
		private Double mortgageValue;
		private Double salesValue;
		private Double lease;
        private Integer numOfStations;
        private Function<Integer, Integer> funToRetriveNumOfStationOwned;
		
		public Builder(){
		    this.decorated     = null;
		    this.mortgageValue = null;
		    this.salesValue    = null;
	        this.lease         = null;
	        this.numOfStations = null;
	        this.funToRetriveNumOfStationOwned = null;
		}
		
		public Builder tile(Tile decorated) {
		    Objects.requireNonNull(decorated, 
		                              "Tile To decore cannot has null value!");
			if(decorated.getCategory() != Tile.Category.STATION) {
				throw new IllegalArgumentException("the Tile isn't a Society");
			}
			this.decorated = decorated;
			return this;
		}

        public Builder funNumOfCatOwned(Function<Integer, Integer> 
                                        funToGetNumbOfTypeOwned) {
            Objects.requireNonNull(funToGetNumbOfTypeOwned, 
                    "The function to retrive the number of station owner "
                    + "cannot has null value!");
            this.funToRetriveNumOfStationOwned = funToGetNumbOfTypeOwned;
            return this;
        }
		
		public Builder mortgage(Double mortgageValue) {
		    Objects.requireNonNull(mortgageValue, 
		                          "The mortgage Value cannot has null value!");
		    this.doubleChecker(mortgageValue, 
		                          "The morgage value double wrong format!");
			this.mortgageValue = mortgageValue;
			return this;
		}
		
        public Builder sales(Double salesValue) {
            Objects.requireNonNull(salesValue, 
                                   "The Sales Value cannot has null value!");
            this.doubleChecker(salesValue,
                                   "The sales value double wrong format!");
			this.salesValue = salesValue;
			return this;
		}

		public Builder leaseOneStation(Double lease) {
		    Objects.requireNonNull(lease, 
		                            "The lease Value cannot has null value!");
		    this.doubleChecker(lease, "the lease value double wrong format!");
			this.lease = lease;
			return this;
		}
		
        public Builder numOfStations(Integer numberOfStation) {
            Objects.requireNonNull(numberOfStation, 
                                "The number of station cannot has null value!");
            this.numOfStations = numberOfStation;
            return this;
        }
		
		public Station build() {
		    Objects.requireNonNull(this.decorated, 
		                           "STATION: Card to decor is unsetted!");
		    Objects.requireNonNull(this.mortgageValue, 
		                           "STATION: the mortgage value is unsetted!");
		    Objects.requireNonNull(this.salesValue, 
		                           "STATION: The sales Value is unsetted!");
		    Objects.requireNonNull(this.lease,
		                           "STATION: the lease value is Unsetted!");
		    Objects.requireNonNull(this.numOfStations, 
		                           "STATION: the num of stations is Unsetted!");
		    Objects.requireNonNull(this.funToRetriveNumOfStationOwned, 
		                            "STATION: the function to retrive the "
		                            + "number of station owned is unsetted!");
			return new Station(this);
		}
        
        private void doubleChecker(Double value, String string) {
            if(value.isNaN() || value.isInfinite()) {
                throw new IllegalArgumentException(string);
            }
            
        }

	}
	
	private Station(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.leaseBase = builder.lease;
		this.numOfStations = builder.numOfStations;
		this.funToRetriveNumOfStationOwned = 
		             builder.funToRetriveNumOfStationOwned;
	}

	@Override
	public double getLeaseValue() {
		int counter = getNumberOfStationOwned();
		if(counter == 0) {
			return 0.0;
		} else {
			return this.leaseBase * 
			       Math.pow(Station.BASE, counter - Station.CORRECTION) * 
			       super.getQuotation();
		}
	}

	@Override
	public Map<Integer, Double> getLeaseList() {
		Map<Integer, Double> map = new HashMap<>();
		for(Integer i = Station.ZERO;  i < this.numOfStations; i++) {
			map.put(i+Station.CORRECTION, 
			        this.leaseBase * Math.pow(Station.BASE, i));
		}
		return map;
	}

    private int getNumberOfStationOwned() {
        return super.getOwner().isEmpty() ? Station.ZERO :
                this.funToRetriveNumOfStationOwned.apply(
                        super.getOwner().get());
    }

}
