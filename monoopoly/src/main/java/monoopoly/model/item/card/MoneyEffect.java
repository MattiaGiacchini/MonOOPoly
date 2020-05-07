package monoopoly.model.item.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import com.google.common.collect.Maps;

public class MoneyEffect extends AbstractCardDecorator {
	
	private static final Double ZERO_VALUE = 0.0;
	
	private Optional<Map<Integer, Double>> valueToApplyOnPlayersBalance;
	
 	public static class Builder {
		private Card cardToDecore;
		private Integer idDrawer;
		private Map<Integer,Double> ActualPlayersBalance;
		private Optional<Double>  playerToOthers;
		private Optional<Double>  playerToBank;
		private Optional<Double>  playerValueHouseToBank;
		private Optional<Double>  playerValueHotelToBank;
		private Optional<Integer> playerNumberHouse;
		private Optional<Integer> playerNumberHotel;
		private Optional<Double>  allToBankPercentage;
		private Optional<Double>  allToBank;
		private boolean 		  makeTheAvarage;

		public Builder() {
			super();
			this.cardToDecore 		 	= null;
			this.idDrawer 				= null;
			this.ActualPlayersBalance	= null;
			this.playerToOthers 	 	= Optional.empty();
			this.playerToBank 		 	= Optional.empty();
			this.playerValueHouseToBank = Optional.empty();
			this.playerValueHotelToBank = Optional.empty();
			this.playerNumberHouse 	 	= Optional.empty();
			this.playerNumberHotel 	 	= Optional.empty();
			this.allToBankPercentage 	= Optional.empty();
			this.allToBank 			 	= Optional.empty();
			this.makeTheAvarage      	= false;
		}

		public Builder cardToDecore(Card card) {
			Objects.requireNonNull(card,"The decorator cannot decor a null pointer");
			this.cardToDecore = card;
			return this;
		}
		
		public Builder idDrawer(Integer value) {
			Objects.requireNonNull(value, "the IdDrawer cannot has a null value");
			this.idDrawer = value;
			return this;
		}
		
		public Builder actualPlayersBalance(Map<Integer,Double> map){
			Objects.requireNonNull(map, "the IdDrawer cannot has a null value");
			for(Double value : map.values()) {
				this.doubleChecker(value);
			}
			this.ActualPlayersBalance = map;
			return this;
		}		
		
		public Builder exchangePlayerToOthers(Double value) {
			this.doubleChecker(value);
			this.playerToOthers = Optional.ofNullable(value);
			return this;
		}
		
		public Builder exchangePlayerToBank(Double value) {
			this.doubleChecker(value);
			this.playerToBank = Optional.ofNullable(value);
			return this;
		}

		public Builder exchangeValueHouseToBank(Double value) {
			this.doubleChecker(value);
			this.playerValueHouseToBank = Optional.ofNullable(value);
			return this;
		}
		
		public Builder exchangeValueHotelToBank(Double value) {
			this.doubleChecker(value);
			this.playerValueHotelToBank = Optional.ofNullable(value);
			return this;
		}
		
		public Builder playerNumberOfHouse(Integer value) {
			this.playerNumberHouse = Optional.ofNullable(value);
			return this;
		}

		public Builder playerNumberOfHotel(Integer value) {
			this.playerNumberHotel = Optional.ofNullable(value);
			return this;
		}

		public Builder exchangeAllToBank(Double value) {
			this.doubleChecker(value);
			this.allToBank = Optional.ofNullable(value);
			return this;
		}

		public Builder exchangeAllToBankPercentage(Double value) {
			this.doubleChecker(value);
			this.allToBankPercentage = Optional.ofNullable(value);
			return this;
		}
		
		public Builder makeTheAvaragePlayersBalance(boolean value) {
			this.makeTheAvarage = value;
			return this;
		}
		
		public MoneyEffect build() {
			if((this.makeTheAvarage == true || 
				this.allToBank.isPresent() ||
				this.playerToBank.isPresent() ||
				this.playerToOthers.isPresent() ||
				this.allToBankPercentage.isPresent() ||
				( this.playerNumberHotel.isPresent() &&
				  this.playerNumberHouse.isPresent() &&
				  this.playerValueHouseToBank.isPresent() &&
				  this.playerValueHotelToBank.isPresent())) &&
			   !Objects.isNull(this.cardToDecore) &&
			   !Objects.isNull(this.idDrawer) && 
			   !Objects.isNull(this.ActualPlayersBalance)) {
				return new MoneyEffect(this);
			}
			throw new IllegalStateException("Wrong build sequence");
		}
		
		private void doubleChecker(Double value) {
			if(Objects.nonNull(value) && 
			   (value.isInfinite() || 
			   value.isNaN())) {
				throw new IllegalArgumentException("The double value hasn't a correnct format");
			}
		}
	}
	
	private MoneyEffect(final Builder builder) {
		super(builder.cardToDecore);

		Map<Integer,Double> copyActPlayersBal = Maps.newHashMap(builder.ActualPlayersBalance);
		Integer numPlayers = copyActPlayersBal.size();
		
		Map<Integer,Double> result = new HashMap<>();
		copyActPlayersBal.keySet().forEach(x->result.put(x, MoneyEffect.ZERO_VALUE));

		if(super.getValueToApplyOnPlayersBalance().isPresent()) {
			super.getValueToApplyOnPlayersBalance().get().entrySet().stream().forEach(el->{
				if(!result.get(el.getKey()).equals(el.getValue())){
					result.put(el.getKey(), el.getValue());
				}
				
			});
		}
		
		// generate the value 		
		if(builder.playerToOthers.isPresent()) {
			result.entrySet().forEach(el->{
				if(el.getKey().equals( builder.idDrawer)) {
					result.put(el.getKey(), el.getValue() - ((numPlayers - 1) * builder.playerToOthers.get()));
				} else {
					result.put(el.getKey(), el.getValue() + builder.playerToOthers.get());
				}
			});
			
		} else if(builder.playerToBank.isPresent()) {
			Double el = result.get(builder.idDrawer);
			result.put(builder.idDrawer, (el - builder.playerToBank.get()));

		} else if(builder.playerValueHotelToBank.isPresent() &&
				  builder.playerValueHouseToBank.isPresent() &&
				  builder.playerNumberHotel.isPresent() &&
				  builder.playerNumberHouse.isPresent()) {
			Double moneyToPay = builder.playerValueHotelToBank.get() * 
					 			builder.playerNumberHotel.get() +
								builder.playerValueHouseToBank.get() * 
								builder.playerNumberHouse.get();
			Double el = result.get(builder.idDrawer);
			result.put(builder.idDrawer, el - moneyToPay);
			 
		} else if (builder.allToBank.isPresent()) {
			result.entrySet().forEach(el->{
				result.put(el.getKey(), el.getValue() - builder.allToBank.get());
			});
			
		} else if (builder.allToBankPercentage.isPresent()) { 
			copyActPlayersBal.entrySet().forEach(el->{
				copyActPlayersBal.put(el.getKey(), result.get(el.getKey())+el.getValue());
			});
			
			result.entrySet().forEach(el->{
				result.put(el.getKey(), 
						   el.getValue() - (copyActPlayersBal.get(el.getKey()) * builder.allToBankPercentage.get()));
			});
			
		} else if (builder.makeTheAvarage) {			 
			copyActPlayersBal.entrySet().forEach(el->{
				copyActPlayersBal.put(el.getKey(), result.get(el.getKey())+el.getValue());
			});
			
			Double avarage = copyActPlayersBal.entrySet().stream().mapToDouble(x->(Double)x.getValue()).average().getAsDouble();

			result.entrySet().forEach(el->{
				result.put(el.getKey(), (avarage - copyActPlayersBal.get(el.getKey())) + el.getValue());
			}); 
			
		}
		
		if(result.entrySet().stream()
							.allMatch(x-> this.doubleEqualsWithTollerance(x.getValue(), MoneyEffect.ZERO_VALUE))) {
			this.valueToApplyOnPlayersBalance = Optional.empty();
		} else {
			this.valueToApplyOnPlayersBalance = Optional.of(result);
		}
		
	}

	@Override
	public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
		return this.valueToApplyOnPlayersBalance;
	}
	
	private boolean doubleEqualsWithTollerance(Double a, Double b) {
		return Math.abs(a-b) < Math.pow(10, -6);
	}
}
