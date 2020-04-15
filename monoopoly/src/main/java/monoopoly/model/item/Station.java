package monoopoly.model.item;

import java.util.HashMap;
import java.util.Map;

public class Station extends AbstractPurchasable {
	
	private static final Integer CORRECTION = 1;
	private static final Integer FOR_START = 0;
	private static final Double BASE = 2.0;
	private final Double leaseBase;
	private final ObserverPurchasable table;

	public static class Builder{
		
		private Tile decorated;
		private ObserverPurchasable table;
		private Double mortgageValue;
		private Double salesValue;
		private Double lease;
		
		public Builder(){}
		
		public Builder tile(Tile decorated) {
			if(decorated.getCategory() != Tile.Category.STATION) {
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

		public Builder leaseOneStation(Double lease) {
			this.lease = lease;
			return this;
		}
		
		public Station build() throws IllegalStateException{
			if(this.decorated == null ||
			   this.table == null ||
			   this.mortgageValue == null ||
			   this.salesValue == null ||
			   this.lease == null) {
				throw new IllegalStateException("Wrong composition of Station!");
			}
			return new Station(this);
		}
	}
		
	
	public Station(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.table = builder.table;
		this.leaseBase = builder.lease;
	}
	

	@Override
	public double getLeaseValue() {
		int counter = (int) this.table.getTilesforSpecificCategoty(this.getCategory())
										  .stream().map(x->(Purchasable)x)
										  .filter(x->x.getOwner().isPresent() && x.getOwner().get() == super.getOwner().get())
										  .count();
		if(counter == 0) {
			return 0.0;
		} else {
			return this.leaseBase * Math.pow(Station.BASE, counter - Station.CORRECTION) * super.getQuotation();
		}
	}

	@Override
	public Map<Integer, Double> getLeaseList() {
		Integer numOfStation = this.table.getTilesforSpecificCategoty(super.getCategory()).size();
		Map<Integer, Double> map = new HashMap<>();
		for(Integer i = Station.FOR_START;  i < numOfStation; i++) {
			map.put(i+Station.CORRECTION, this.leaseBase * Math.pow(Station.BASE, i));
		}
		return map;
	}

}
