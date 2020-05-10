package monoopoly.model.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

public class PropertyImpl extends AbstractPurchasable implements Property {

	private static final Double  PERCENTAGE_TO_APPLY_FOR_SELLING = 0.5;
	private static final Double  VALUE_ZERO = 0.0;
	private static final Integer UNIT_TO_INCREASE_OR_DECREASE = 1;
	private static final Integer PROPERTY_WITHOUT_BUILDINGS = 0;
	private static final Integer MAX_NUMBER_OF_HOUSE = 4;
	private static final Integer MAX_NUMBER_OF_HOTEL = 1;
	private static final Integer SERIES_COMPLETE = 6;

    private final Predicate<Integer> isCategoryAllOwned;
	private final Map<Integer, Double> leaseListBaseValue;
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
		private Double mortgageValue;
		private Double salesValue;
		private Double valueToBuildHouse;
		private Double valueToBuildHotel;
		private Map<Integer,Double> leaseMap;
        private Predicate<Integer> allCategoryOwned;
		
		public Builder(){
			this.leaseMap = new HashMap<>();
			this.valueToBuildHouse = null;
			this.valueToBuildHotel = null;
			this.decorated = null;
		    this.allCategoryOwned = null;
		    this.mortgageValue = null;
		    this.salesValue = null;
		}
		
		public Builder tile(Tile decorated) {
		    Objects.requireNonNull(decorated, 
		                        "the tile to decore cannot has null value!");
			if(!decorated.isBuildable()) {
				throw new IllegalArgumentException("the Tile isn't Buildable");
			}
			this.decorated = decorated;
			return this;
		}

        public Builder predAreAllPropOwned(Predicate<Integer> predicate) {
            Objects.requireNonNull(predicate,
                                "The function cannot has null value!");
            this.allCategoryOwned = predicate;
            return this;
        }
		
		public Builder mortgage(Double mortgageValue) {
		    this.doubleChecker(mortgageValue, "Mortgage value");
			this.mortgageValue = mortgageValue;
			return this;
		}
		
        public Builder sales(Double salesValue) {
            this.doubleChecker(salesValue, "Sales value");
			this.salesValue = salesValue;
			return this;
		}

		public Builder valueToBuildHouse(Double value) {
		    this.doubleChecker(value, "cost to build house");
			this.valueToBuildHouse = value;
			return this;
		}
		
		public Builder valueToBuildHotel(Double value) {
            this.doubleChecker(value, "cost to build hotel");
			this.valueToBuildHotel = value;
			return this;
		}
		
		public Builder leaseWithNoBuildings(Double value) {
		    this.doubleChecker(value, "lease without buildings");
			this.leaseMap.put(LEASE_NO_BUILDING, value);
			return this;
		}

		public Builder leaseWithOneHouse(Double value) {
		    this.doubleChecker(value, "lease with one house");
			this.leaseMap.put(LEASE_ONE_HOUSE, value);
			return this;
		}

		public Builder leaseWithTwoHouse(Double value) {
            this.doubleChecker(value, "lease with two house");
			this.leaseMap.put(LEASE_TWO_HOUSE, value);
			return this;
		}

		public Builder leaseWithThreeHouse(Double value) {
            this.doubleChecker(value, "lease with three house");
			this.leaseMap.put(LEASE_THREE_HOUSE, value);
			return this;
		}

		public Builder leaseWithFourHouse(Double value) {
            this.doubleChecker(value, "lease with four house");
			this.leaseMap.put(LEASE_FOUR_HOUSE, value);
			return this;
		}

		public Builder leaseWithOneHotel(Double value) {
            this.doubleChecker(value, "lease with an hotel");
			this.leaseMap.put(LEASE_ONE_HOTEL, value);
			return this;
		}
		
		public PropertyImpl build() {
		    this.objectRequireNonNull(this.decorated, "Card to decore");
		    this.objectRequireNonNull(this.allCategoryOwned, "bifunction");
		    this.objectRequireNonNull(this.mortgageValue, "Mortgage value");
		    this.objectRequireNonNull(this.salesValue, "Salses value");
		    this.objectRequireNonNull(this.leaseMap.get(LEASE_NO_BUILDING), 
		                              "Lease withoutBuildings");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_ONE_HOUSE), 
                                      "Lease with one house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_TWO_HOUSE), 
                                      "Lease with two house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_THREE_HOUSE), 
                                      "Lease with three house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_FOUR_HOUSE), 
                                      "Lease with four house");
            this.objectRequireNonNull(this.leaseMap.get(LEASE_ONE_HOTEL), 
                                      "Lease with one hotel");
            
            // the series complete has double value of lease with no buildings
			this.leaseMap.put(Builder.LEASE_SERIES_COMPLETE, 
					          this.leaseMap.get(Builder.LEASE_NO_BUILDING) *
					          Builder.MULTIPLIER_SERIES_COMPLETE);
			
			return new PropertyImpl(this);
		}

		private void objectRequireNonNull(Object obj, String string) {
		    Objects.requireNonNull(obj, "PROPERTY: "+ string + " is unsetted!");
		}
		
        private void doubleChecker(Double value, String string) {
            Objects.requireNonNull(value, 
                        "the " + string + " cannot has null value!");
            if(value.isNaN() || value.isInfinite()) {
                throw new IllegalArgumentException(
                        "the "+ string + " hasn't a right format");
            }
            
        }

	}	

	private PropertyImpl(Builder builder) {
		super(builder.decorated, builder.mortgageValue, builder.salesValue);
		this.numberOfConstructionBuilt = PropertyImpl.
		                                 PROPERTY_WITHOUT_BUILDINGS;
		this.leaseListBaseValue = builder.leaseMap;
		this.valueTobuildHouse = builder.valueToBuildHouse;
		this.valueTobuildHotel = builder.valueToBuildHotel;
		this.isCategoryAllOwned = builder.allCategoryOwned;
	}

	@Override
	public void buildOn(){
		if(!this.isCategoryOfPropertiesAllOwned()) {
			throw new IllegalStateException("The " + super.getCategory() + 
			               " Category hasn't the same owner in all properties");
		} else if(this.numberOfConstructionBuilt
		              .equals(getMaxNumberOfBuildings())) {
			throw new IllegalStateException(
			                             "Maximum number of buildings reached");
		}
		this.numberOfConstructionBuilt = this.numberOfConstructionBuilt + 
		                              PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
	}

	@Override
	public double sellBuilding() {
		if(this.numberOfConstructionBuilt.equals(
		        PropertyImpl.PROPERTY_WITHOUT_BUILDINGS)) {
			throw new IllegalStateException(
			        "The property hasn't buildings to sell"); 
		}
		Double buildingsValue = this.getQuotationToSellActualBuildings();
		this.numberOfConstructionBuilt = this.numberOfConstructionBuilt
		                    - PropertyImpl.UNIT_TO_INCREASE_OR_DECREASE;
		return buildingsValue;
	}

	@Override
	public Integer getNumberOfHouseBuilt() {
		return this.numberOfConstructionBuilt <=PropertyImpl.MAX_NUMBER_OF_HOUSE
		       ? this.numberOfConstructionBuilt
		       : PropertyImpl.MAX_NUMBER_OF_HOUSE;
	}

	@Override
	public Integer getNumberOfHotelBuilt() {
		return this.numberOfConstructionBuilt > PropertyImpl.MAX_NUMBER_OF_HOUSE
		       ? PropertyImpl.MAX_NUMBER_OF_HOTEL
		       : PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
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
			if(this.isCategoryOfPropertiesAllOwned() && 
			   this.numberOfConstructionBuilt.equals(
			   PropertyImpl.PROPERTY_WITHOUT_BUILDINGS)){
				return this.leaseListBaseValue.get(PropertyImpl.SERIES_COMPLETE)
				       * super.getQuotation();
			} else {
				return this.leaseListBaseValue.get(
				       this.numberOfConstructionBuilt)
				       * super.getQuotation();
			}
		}
		return PropertyImpl.VALUE_ZERO;
	}
	
	@Override
	public Map<Integer, Double> getLeaseList() {
		Map<Integer,Double> listWithQuotationApplied = new HashMap<>();
		for(Entry<Integer, Double> elem : this.leaseListBaseValue.entrySet()) {
			listWithQuotationApplied.put(elem.getKey(), elem.getValue() *
			                             super.getQuotation());
		}
		return listWithQuotationApplied;
	}	

    @Override
    public boolean isBuildOnEnabled() {
        return super.getOwner().isPresent() && 
               this.isCategoryAllOwned.test(super.getOwner().get()) &&
               this.numberOfConstructionBuilt < getMaxNumberOfBuildings();
    }

    @Override
    public boolean isSellBuildingsEnabled() {
        return super.getOwner().isPresent() &&
               this.numberOfConstructionBuilt > 
               PropertyImpl.PROPERTY_WITHOUT_BUILDINGS;
    }

    
	private double getQuotationToSellActualBuildings() {
		if(this.getNumberOfHotelBuilt() > PropertyImpl.
		                                  PROPERTY_WITHOUT_BUILDINGS) {
			return this.getQuotationToSellHotel();
		}
		return this.getQuotationToSellHouse();
	}
	
	private boolean isCategoryOfPropertiesAllOwned() {
		return super.getOwner().isEmpty() ? false :
		       this.isCategoryAllOwned.test(super.getOwner().get());
	}
	
    private int getMaxNumberOfBuildings() {
        return PropertyImpl.MAX_NUMBER_OF_HOTEL + 
               PropertyImpl.MAX_NUMBER_OF_HOUSE;
    }
}
