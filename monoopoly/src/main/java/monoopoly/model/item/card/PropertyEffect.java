package monoopoly.model.item.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class PropertyEffect extends AbstractCardDecorator {

	private final static int ZERO = 0;
	private final static int ONE = 1;
	
	private final Optional<Map<Integer,Integer>> propertyToModify;

	public static class Builder {

		private Card cardToDecore;
		private Map<Integer,Integer> buildingsToModify;
		
		public Builder cardToDecore(Card card) {
			Objects.requireNonNull(card,"The decorator cannot decor a null pointer");
			this.cardToDecore = card;
			return this;
		}
		
		public Builder buildingsToModify(Map<Integer,Integer> property) {
			Objects.requireNonNull(property, "the property map cannot has null value");
			this.buildingsToModify = property;
			return this;
		}
		
		public PropertyEffect build() {
			Objects.requireNonNull(this.cardToDecore, "The PropertyEffect cannot decore a null pointer");
			Objects.requireNonNull(this.buildingsToModify, "The PropertyEffect must have a map of Buildings");
			return new PropertyEffect(this);
		}
	}
	
	private PropertyEffect(Builder builder) {
		super(builder.cardToDecore);

		Map<Integer,Integer> mapOfProperty = new HashMap<>();
		
		Random rnd = new Random();
		
		for(Entry<Integer,Integer> value : builder.buildingsToModify.entrySet()) {
			mapOfProperty.put(value.getKey(), this.calculateTheNumberOfBuildings(value.getValue(),
											  super.getNumberOfBuildingsToRemove().isPresent() ? 
										      super.getNumberOfBuildingsToRemove().get().get(value.getKey()) :
										      0, rnd));
		}
		
		if(mapOfProperty.values().stream().allMatch(x->x.equals(PropertyEffect.ZERO))) {
			this.propertyToModify = Optional.empty();
		} else {
			this.propertyToModify = Optional.of(Collections.unmodifiableMap(mapOfProperty));
		}
	}

	@Override
	public Optional<Map<Integer, Integer>> getNumberOfBuildingsToRemove() {
		return this.propertyToModify;
	}
	
	private Integer calculateTheNumberOfBuildings(final Integer actualProperty, 
												  final Integer lastChangeApplyed, 
												  final Random rnd) {
		return actualProperty - lastChangeApplyed == PropertyEffect.ZERO ?
			   lastChangeApplyed : 
			   lastChangeApplyed + rnd.nextInt(actualProperty - lastChangeApplyed + PropertyEffect.ONE);
	}

}
