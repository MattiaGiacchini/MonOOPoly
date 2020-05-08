package monoopoly.model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Station extends AbstractPurchasable {
	
	private static final Integer CORRECTION = 1;
	private static final Integer ZERO = 0;
	private static final Double BASE = 2.0;
	private final Double leaseBase;
	private final ObserverPurchasable table;

	public static class Builder{
		
		private Tile decorated;
		private ObserverPurchasable table;
		private Double mortgageValue;
		private Double salesValue;
		private Double lease;
		
		public Builder(){
		    this.decorated     = null;
		    this.table         = null;
		    this.mortgageValue = null;
		    this.salesValue    = null;
	        this.lease         = null;
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
		
		public Builder table(ObserverPurchasable table) {
		    Objects.requireNonNull(table, "Table cannot has null value");
			this.table = table;
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
		
		public Station build() {
		    Objects.requireNonNull(this.decorated, 
		                           "STATION: Card to decor is unsetted!");
		    Objects.requireNonNull(this.table,
		                           "STATION: The Table is unsetted!");
		    Objects.requireNonNull(this.mortgageValue, 
		                           "STATION: the mortgage value is unsetted!");
		    Objects.requireNonNull(this.salesValue, 
		                           "STATION: The sales Value is unsetted!");
		    Objects.requireNonNull(this.lease,
		                           "STATION: the lease value is Unsetted!");
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
		this.table = builder.table;
		this.leaseBase = builder.lease;
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
		Integer numOfStation = getNumberOfStations();
		Map<Integer, Double> map = new HashMap<>();
		for(Integer i = Station.ZERO;  i < numOfStation; i++) {
			map.put(i+Station.CORRECTION, 
			        this.leaseBase * Math.pow(Station.BASE, i));
		}
		return map;
	}

    private int getNumberOfStationOwned() {
        return (int) this.table
                         .getTilesforSpecificCategoty(this.getCategory())
                         .stream()
                         .map(x->(Purchasable)x)
                         .filter(x->x.getOwner().isPresent() &&
                                    super.getOwner().isPresent() &&
                                    x.getOwner().get().equals(
                                    super.getOwner().get()))
                         .count();
    }

    private int getNumberOfStations() {
        return this.table.getTilesforSpecificCategoty(super.getCategory())
		                 .size();
    }

}
