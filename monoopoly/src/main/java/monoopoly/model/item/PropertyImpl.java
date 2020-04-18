package monoopoly.model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PropertyImpl extends AbstractPurchasable implements Property {

	private static final Double  PERCENTAGE_TO_APPLY_FOR_SELLING_ALL = 0.75;
	private static final Double  PERCENTAGE_TO_APPLY_FOR_SELLING = 0.5;
	private static final Double  VALUE_ZERO = 0.0;
	private static final Integer UNIT_TO_INCREASE_OR_DECREASE = 1;
	private static final Integer PROPERTY_WITHOUT_BUILDINGS = 0;
	private static final Integer MAX_NUMBER_OF_HOUSE = 4;
	private static final Integer MAX_NUMBER_OF_HOTEL = 1;
	private static final Integer SERIES_COMPLETE = 6;
	
	private final Map<Integer, Double> leaseListBaseValue;
	private final ObserverPurchasable table;
	private final Double valueTobuildHouse;
	private final Double valueTobuildHotel;
	
	private Integer numberOfConstructionBuilt;
	

	public static class Builder{
		
		private final static Integer LEASE_NO_BUILDING = 0;
		private final static Integer LEASE_ONE_HOUSE = 1;
		private final static Integer LEASE_TWO_HOUSE = 2;
		private final static Integer LEASE_THREE_HOUSE = 3;
		private final static Integer LEASE_FOUR_HOUSE = 4;
		private final static Integer LEASE_ONE_HOTEL = 5;
		private final static Integer LEASE_SERIES_COMPLETE = 6;
		private static final Double  MULTIPLIER_SERIES_COMPLETE = 2.0;
		
		private Tile decorated;
		private ObserverPurchasable table;
		private Double mortgageValue;
		private Double salesValue;
		private Double valueToBuildHouse;
		private Double valueToBuildHotel;
		private Map<Integer,Double> leaseMap;
		
		public Builder(){
			this.leaseMap = new HashMap<>();
			this.valueToBuildHouse = null;
			this.valueToBuildHotel = null;
		}
		
		public Builder tile(Tile decorated) {
			if(!decorated.isBuildable()) {
				throw new IllegalArgumentException("the Tile isn't Buildable");
			}
			this.decorated = decorated;
			return this;
		}
		
		public Builder table(ObserverPurchasable table) {
			this.table = table;
			return this;
		}
		
		public Builder mortgage(double mortgageValue) {
			if(this.mortgageValue == null) {
				this.mortgageValue = mortgageValue;
			}
			return this;
		}
		
		public Builder sales(double salesValue) {
			if(this.salesValue == null) {
				this.salesValue = salesValue;
			}
			return this;
		}

		public Builder valueToBuildHouse(double value) {
			if(this.valueToBuildHouse == null) {
				this.valueToBuildHouse = value;
			}
			return this;
		}
		
		public Builder valueToBuildHotel(double value) {
			if(this.valueToBuildHotel == null) {
				this.valueToBuildHotel = value;
			}
			return this;
		}
		
		public Builder leaseWithNoBuildings(double value) {
			if(!this.leaseMap.containsKey(LEASE_NO_BUILDING)) {
				this.leaseMap.put(LEASE_NO_BUILDING, value);
			}
			return this;
		}

		public Builder leaseWithOneHouse(double value) {
			if(!this.leaseMap.containsKey(Builder.LEASE_ONE_HOUSE)) {
				this.leaseMap.put(LEASE_ONE_HOUSE, value);
			}
			return this;
		}

		public Builder leaseWithTwoHouse(double value) {
			if(!this.leaseMap.containsKey(Builder.LEASE_TWO_HOUSE)) {
				this.leaseMap.put(LEASE_TWO_HOUSE, value);
			}
			return this;
		}

		public Builder leaseWithThreeHouse(double value) {
			if(!this.leaseMap.containsKey(Builder.LEASE_THREE_HOUSE)) {
				this.leaseMap.put(LEASE_THREE_HOUSE, value);
			}
			return this;
		}

		public Builder leaseWithFourHouse(double value) {
			if(!this.leaseMap.containsKey(Builder.LEASE_FOUR_HOUSE)) {
				this.leaseMap.put(LEASE_FOUR_HOUSE, value);
			}
			return this;
		}

		public Builder leaseWithOneHotel(double value) {
			if(!this.leaseMap.containsKey(Builder.LEASE_ONE_HOTEL)) {
				this.leaseMap.put(LEASE_ONE_HOTEL, value);
			}
			return this;
		}
		
		public PropertyImpl build() throws IllegalStateException{
			if(this.decorated == null ||
			   this.table == null ||
			   this.mortgageValue == null ||
			   this.salesValue == null ||
			   !this.leaseMap.containsKey(LEASE_NO_BUILDING) ||
			   !this.leaseMap.containsKey(Builder.LEASE_ONE_HOUSE) ||
			   !this.leaseMap.containsKey(Builder.LEASE_TWO_HOUSE) ||
			   !this.leaseMap.containsKey(Builder.LEASE_THREE_HOUSE) ||
			   !this.leaseMap.containsKey(Builder.LEASE_FOUR_HOUSE) ||
			   !this.leaseMap.containsKey(Builder.LEASE_ONE_HOTEL)) {
				throw new IllegalStateException("Wrong composition of Property!");
			}
			this.leaseMap.put(Builder.LEASE_SERIES_COMPLETE, 
					this.leaseMap.get(Builder.LEASE_NO_BUILDING)*
					Builder.MULTIPLIER_SERIES_COMPLETE);
			return new PropertyImpl(this);
		}
	}
		

	private PropertyImpl(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.numberOfConstructionBuilt = PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
		this.leaseListBaseValue = builder.leaseMap;
		this.valueTobuildHouse = builder.valueToBuildHouse;
		this.valueTobuildHotel = builder.valueToBuildHotel;
		this.table = builder.table;
	}

	@Override
	public void buildOn(){
		if(!this.isCategoryOfPropertiesAllOwned()) {
			throw new IllegalStateException("The " + super.getCategory() + " Category hasn't the same owner in all properties");
		} else if(PropertyImpl.MAX_NUMBER_OF_HOTEL + PropertyImpl.MAX_NUMBER_OF_HOUSE == this.numberOfConstructionBuilt) {
			throw new IllegalStateException("Maximum number of buildings reached");
		}
		this.numberOfConstructionBuilt = this.numberOfConstructionBuilt + PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
	}

	@Override
	public double sellBuilding() {
		if(this.numberOfConstructionBuilt == PropertyImpl.PROPERTY_WITHOUT_BUILDINGS) {
			throw new IllegalStateException("The property hasn't buildings to sell"); 
		}
		Double buildingsValue = this.getQuotationToSellActualBuildings();
		this.numberOfConstructionBuilt = this.numberOfConstructionBuilt - PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
		return buildingsValue;
	}

	@Override
	public Integer getNumberOfHouseBuilt() {
		return this.numberOfConstructionBuilt <= PropertyImpl.MAX_NUMBER_OF_HOUSE ? 
			   this.numberOfConstructionBuilt :
			   PropertyImpl.MAX_NUMBER_OF_HOUSE;
	}

	@Override
	public Integer getNumberOfHotelBuilt() {
		return this.numberOfConstructionBuilt > PropertyImpl.MAX_NUMBER_OF_HOUSE ?
			   PropertyImpl.MAX_NUMBER_OF_HOTEL :
			   PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
	}

	@Override
	public double getCostToBuildHouse() {
		return super.getQuotation() * this.valueTobuildHouse;
	}

	@Override
	public double getCostToBuildHotel() {
		return super.getQuotation() * this.valueTobuildHotel;
	}

	@Override
	public double getQuotationToSellHouse() {
		return this.getCostToBuildHouse() *
			   PropertyImpl.PERCENTAGE_TO_APPLY_FOR_SELLING;
	}

	@Override
	public double getQuotationToSellHotel() {
		return this.getCostToBuildHotel() *
			   PropertyImpl.PERCENTAGE_TO_APPLY_FOR_SELLING;
	}

	@Override
	public double getLeaseValue() {
		if(super.getOwner().isPresent() && !super.isMortgage()) {
			if(this.isCategoryOfPropertiesAllOwned() && this.numberOfConstructionBuilt == PropertyImpl.PROPERTY_WITHOUT_BUILDINGS) {
				return this.leaseListBaseValue.get(PropertyImpl.SERIES_COMPLETE) * super.getQuotation();
			} else {
				return this.leaseListBaseValue.get(this.numberOfConstructionBuilt) * super.getQuotation();
			}
		}
		return PropertyImpl.VALUE_ZERO;
	}
	
	@Override
	public Map<Integer, Double> getLeaseList() {
		Map<Integer,Double> listWithQuotationApplied = new HashMap<>();
		for(Entry<Integer, Double> elem : this.leaseListBaseValue.entrySet()) {
			listWithQuotationApplied.put(elem.getKey(), elem.getValue()*super.getQuotation());
		}
		return listWithQuotationApplied;
	}	
	
	@Override
	public double getSalesValue() {
		return super.getSalesValue() +
			   (this.getCostToBuildHotel() * this.getNumberOfHotelBuilt() +
			   this.getCostToBuildHouse() * this.getNumberOfHouseBuilt()) *
			   PropertyImpl.PERCENTAGE_TO_APPLY_FOR_SELLING_ALL;
	}
	
	private double getQuotationToSellActualBuildings() {
		if(this.getNumberOfHotelBuilt() > PropertyImpl.PROPERTY_WITHOUT_BUILDINGS) {
			return this.getQuotationToSellHotel();
		}
		return this.getQuotationToSellHouse();
	}
	
	private boolean isCategoryOfPropertiesAllOwned() {
		return this.table.getTilesforSpecificCategoty(super.getCategory())
						 .stream()
						 .map(x->(Purchasable)x)						  
						 .allMatch(x-> x.getOwner().isPresent() && 
								       x.getOwner().get() == super.getOwner().get());
	}

}
