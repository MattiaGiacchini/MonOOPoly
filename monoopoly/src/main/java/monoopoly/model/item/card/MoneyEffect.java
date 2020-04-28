package monoopoly.model.item.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.collect.Maps;

public class MoneyEffect extends AbstractCardDecorator {
	
	private Optional<Map<Integer,Double>> valueToApplyOnPlayersBalance;
 	private Optional<Double> ValueToApplyOnBankBalance;
	
	public static class Builder {
		private Card cardToDecore;
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
		
		public Builder ExchangePlayerToOthers(Double value) {
			this.doubleChecker(value);
			this.playerToOthers = Optional.ofNullable(value);
			return this;
		}
		
		public Builder ExchangePlayerToBank(Double value) {
			this.doubleChecker(value);
			this.playerToBank = Optional.ofNullable(value);
			return this;
		}

		public Builder ExchangeValueHouseToBank(Double value) {
			this.doubleChecker(value);
			this.playerValueHouseToBank = Optional.ofNullable(value);
			return this;
		}
		
		public Builder ExchangeValueHotelToBank(Double value) {
			this.doubleChecker(value);
			this.playerValueHotelToBank = Optional.ofNullable(value);
			return this;
		}
		
		public Builder PlayerNumberOfHouse(Integer value) {
			this.playerNumberHouse = Optional.ofNullable(value);
			return this;
		}

		public Builder PlayerNumberOfHotel(Integer value) {
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
			   !Objects.isNull(cardToDecore)) {
				return new MoneyEffect(this);
			}
			throw new IllegalStateException("Wrong build sequence");
		}
		
		private void doubleChecker(Double value) {
			if(Objects.nonNull(value) && 
			   value.isInfinite() || 
			   value.isNaN()) {
				throw new IllegalArgumentException("The double value hasn't a correnct format");
			}
		}
	}
	
	private MoneyEffect(Builder builder) {
		super(builder.cardToDecore);

		Map<Integer,Double> copyActPlayersBal = Maps.newHashMap(super.getActualPlayersBalance());
		Integer numPlayers = copyActPlayersBal.size();
		Integer idPlayerDrawer = super.getIdPlayerWhoHasDrow();
		Stream<Entry<Integer,Double>> streamActPlayerBal = copyActPlayersBal.entrySet().stream();
		Map<Integer,Double> resultPlayer;
		Double resultBank = super.getActualBankBalance();

		
		// apply the last variation to players balance
		Optional<Map<Integer,Double>> variationToApply = super.getValueToApplyOnPlayersBalance();
		if(variationToApply.isPresent()) {
			streamActPlayerBal = streamActPlayerBal.peek(x->x.setValue(x.getValue() + variationToApply.get().get(x.getKey())));
		}
		 
		// apply the last variation to bank balance
		if(super.getValueToApplyOnBankBalance().isPresent()) {
			resultBank = resultBank + super.getValueToApplyOnBankBalance().get();
		}
		
		// generate the value 		
		if(builder.playerToOthers.isPresent()) {
			streamActPlayerBal = streamActPlayerBal.peek(x->{
																if(x.getKey() == idPlayerDrawer) {
																	x.setValue(x.getValue() - builder.playerToOthers.get() * (numPlayers - 1));
																} else {
																	x.setValue(x.getValue() + builder.playerToOthers.get());
																}
															});
		} else if(builder.playerToBank.isPresent()) {
			streamActPlayerBal = streamActPlayerBal.peek(x->{
															if(x.getKey() == idPlayerDrawer) {
																x.setValue(x.getValue() - builder.playerToBank.get());
															}});	
			resultBank = resultBank + builder.playerToBank.get();
			
		} else if(builder.playerValueHotelToBank.isPresent() &&
				  builder.playerValueHouseToBank.isPresent() &&
				  builder.playerNumberHotel.isPresent() &&
				  builder.playerNumberHouse.isPresent()) {
			Double moneyToPay = builder.playerValueHotelToBank.get() * 
					 			builder.playerNumberHotel.get() +
								builder.playerValueHouseToBank.get() * 
								builder.playerNumberHouse.get();
			streamActPlayerBal = streamActPlayerBal.peek(x->{
														if(x.getKey() == idPlayerDrawer) {
															x.setValue(x.getValue() - moneyToPay);
														}});	
			resultBank = resultBank + moneyToPay; 
			 
		} else if (builder.allToBank.isPresent()) {
			resultBank = resultBank + builder.allToBank.get() * numPlayers;
			streamActPlayerBal = streamActPlayerBal.peek(x->x.setValue(x.getValue() - builder.allToBank.get()));
			
		} else if (builder.allToBankPercentage.isPresent()) { 
			resultPlayer = streamActPlayerBal.collect(()->new HashMap<Integer,Double>(),
					   								  (map,el)->map.put(el.getKey(), el.getValue()),
					   								  (map1,map2)->map1.putAll(map2));
			
			for(Entry<Integer,Double> entry : resultPlayer.entrySet()) { 
				resultBank += (entry.getValue() * builder.allToBankPercentage.get());
				resultPlayer.put(entry.getKey() ,entry.getValue() * (1 - builder.allToBankPercentage.get()));
			}
			
			streamActPlayerBal = resultPlayer.entrySet().stream();
			
		} else if (builder.makeTheAvarage) {			
			resultPlayer = streamActPlayerBal.collect(
												   ()->new HashMap<Integer,Double>(),
												   (map,el)->map.put(el.getKey(), el.getValue()),
												   (map1,map2)->map1.putAll(map2));
			 
			Double avarage = resultPlayer.entrySet().stream().mapToDouble(x->(Double)x.getValue()).average().getAsDouble();
			for(Entry<Integer,Double> entry : resultPlayer.entrySet()) {
				resultPlayer.put(entry.getKey(), avarage - entry.getValue() + entry.getValue() );
			}
			
			streamActPlayerBal = resultPlayer.entrySet().stream();
			
		}
		
		resultPlayer = streamActPlayerBal.collect(()->new HashMap<Integer,Double>(),
		 		  				 			      (map,el)->map.put(el.getKey(), el.getValue()),
		 		  				 			      (map1,map2)->map1.putAll(map2));
		
		if(resultPlayer.entrySet().stream().allMatch(x-> this.doubleEqualsWithTollerance(super.getActualPlayersBalance().get(x.getKey()), x.getValue()))) {
			this.valueToApplyOnPlayersBalance = Optional.empty();
		} else {
			for(Entry<Integer,Double> entry : resultPlayer.entrySet()) {
				resultPlayer.put(entry.getKey(), entry.getValue()-super.getActualPlayersBalance().get(entry.getKey()));
			}
			this.valueToApplyOnPlayersBalance = Optional.of(resultPlayer);
		}

		
		if(this.doubleEqualsWithTollerance(resultBank, super.getActualBankBalance())) {
			this.ValueToApplyOnBankBalance = Optional.empty();
		} else {
			resultBank = resultBank  - super.getActualBankBalance();
			this.ValueToApplyOnBankBalance = Optional.of(resultBank);
		}
		
	}

	@Override
	public Optional<Map<Integer, Double>> getValueToApplyOnPlayersBalance() {
		return this.valueToApplyOnPlayersBalance;
	}

	@Override
	public Optional<Double> getValueToApplyOnBankBalance() {
		return this.ValueToApplyOnBankBalance;	
	}

	
	private boolean doubleEqualsWithTollerance(Double a, Double b) {
		return Math.abs(a-b) < Math.pow(10, -6);
	}
}
