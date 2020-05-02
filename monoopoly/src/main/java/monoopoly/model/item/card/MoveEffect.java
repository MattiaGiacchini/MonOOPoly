package monoopoly.model.item.card;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import monoopoly.model.item.Tile.Category;

public class MoveEffect extends AbstractCardDecorator {
	
	private static final Integer ZERO = 0;

	private final Optional<Map<Integer, Integer>> absoluteMoveToApply;
	private final Optional<Map<Integer, Integer>> relativeMove;

	public static class Builder {
		private Card cardToDecore;
		private Integer tableSize;
		private Map<Integer, Integer> playersPosition; 
		private Integer idDrawer;				
		private Optional<BiFunction<Integer,Category,Integer>> tileRetriverFromCategory; 
		private Optional<Category> categoryToReach; 	
		private Optional<Integer> stepsToDo;	
		private Optional<Integer> tilePositionToGo;		
		private boolean applyToPlayer;			
		private boolean applyToOthers;			
		private boolean randomStep;				
		
		public Builder() {
			super();
			this.cardToDecore = null;
			this.tableSize = null;
			this.playersPosition = null;
			this.idDrawer = null;
			this.tileRetriverFromCategory = Optional.empty();
			this.categoryToReach = Optional.empty();
			this.stepsToDo = Optional.empty();
			this.tilePositionToGo = Optional.empty();
			this.applyToPlayer = false;
			this.applyToOthers = false;
			this.randomStep = false;
		}

		public Builder cardToDecore(final Card card) {
			Objects.requireNonNull(card, "The decorator cannot decor a null pointer");
			this.cardToDecore = card;
			return this;
		}

		public Builder playersPosition(final Map<Integer, Integer> playersPosition) {
			Objects.requireNonNull(playersPosition, "the Map of players position cannot has null value");
			this.playersPosition = playersPosition;
			return this;
		}

		public Builder idDrawers(final Integer idDrawer) {
			Objects.requireNonNull(idDrawer,"the id Drawer cannot has null value");
			this.idDrawer = idDrawer;
			return this;
		}

		public Builder tableSize(Integer tableSize) {
			Objects.requireNonNull(tableSize, "the table Size cannot has null value");
			if (tableSize < 1) {
				throw new IllegalArgumentException("the table size must have a positive number");
			}
			this.tableSize = tableSize;
			return this;
		}

		public Builder stepToDo(Integer steps) {
			this.stepsToDo = Optional.ofNullable(steps);
			return this;
		}

		public Builder setTileRetriverFromCategory(BiFunction<Integer,Category,Integer> biFunction) {
			this.tileRetriverFromCategory = Optional.ofNullable(biFunction);
			return this;
		}

		public Builder tilePositionToGo(Integer position) {
			this.tilePositionToGo = Optional.ofNullable(position);
			return this;
		}

		public Builder nextTileCategoryToReach(Category category) {
			this.categoryToReach = Optional.ofNullable(category);
			return this;
		} 
		
		public Builder generateRandomStep(boolean value) {
			this.randomStep = value;
			return this;
		}

		public Builder applyToPlayer(boolean value) {
			this.applyToPlayer = value;
			return this;
		}

		public Builder applyToOthers(boolean value) {
			this.applyToOthers = value;
			return this;
		}

		public MoveEffect build() {
			Objects.requireNonNull(this.cardToDecore, "card To decore unsetted");
			Objects.requireNonNull(this.tableSize, "table size unsetted");
			Objects.requireNonNull(this.playersPosition, "players Position unsetted");
			Objects.requireNonNull(this.idDrawer,"Id Drawer Unsetted");
			if(!this.applyToOthers &&
			   !this.applyToPlayer) {
				throw new IllegalStateException("the movement effect hasn't a target");
			}
			if(this.categoryToReach.isPresent() && this.tileRetriverFromCategory.isEmpty()) {
				throw new NullPointerException("the BiFunction cannot has Null value to retrive the next category tile");
			} else if(this.categoryToReach.isEmpty() && this.tileRetriverFromCategory.isPresent()) {
				throw new NullPointerException("the Category cannot has Null value to retrive the next category tile");
			}
			if(this.tilePositionToGo.isEmpty() &&
			   this.stepsToDo.isEmpty() &&
			   this.categoryToReach.isEmpty() &&
			   !this.randomStep) {
				throw new IllegalStateException("there aren't movement to do");
			}
			return new MoveEffect(this);
		}
	}

	private MoveEffect(Builder builder) {
		super(builder.cardToDecore);
		
		Random rnd = new Random();
		
		Map<Integer,Integer> stepsToDo = new HashMap<>();
		Map<Integer,Integer> absoluteMove = new HashMap<>();
		Set<Integer> idPlayers = new HashSet<>(); 

		if(builder.applyToOthers && builder.applyToPlayer) {
			idPlayers.addAll(builder.playersPosition.keySet());
		} else if (builder.applyToOthers) {
			idPlayers.addAll(builder.playersPosition.keySet());
			idPlayers.remove(builder.idDrawer);
		} else if (builder.applyToPlayer) {
		  	idPlayers.add(builder.idDrawer);
		}
		
		if(builder.randomStep) {
			idPlayers.forEach(x->{
				stepsToDo.put(x, this.newRandomStepsInteger(rnd, builder.tableSize));
			});
		} else if (builder.stepsToDo.isPresent()) {
			idPlayers.forEach(x->{
				stepsToDo.put(x, builder.stepsToDo.get());
			});
		} else if (builder.categoryToReach.isPresent()) {
			idPlayers.forEach(idPlayer->{
				stepsToDo.put(idPlayer, builder.tileRetriverFromCategory.get().apply(
							  builder.playersPosition.get(idPlayer), builder.categoryToReach.get()));
			});
		} else if (builder.tilePositionToGo.isPresent()) {
			idPlayers.forEach(idPlayer->{
				absoluteMove.put(idPlayer, builder.tilePositionToGo.get());
			});
		}

		if(absoluteMove.isEmpty()) {
			this.absoluteMoveToApply = super.getAbsoluteMoveToPosition();
		} else {
			if (super.getAbsoluteMoveToPosition().isPresent()) {
				super.getAbsoluteMoveToPosition().get().entrySet().forEach(entry->{
					if(!absoluteMove.containsKey(entry.getKey())) {
						absoluteMove.put(entry.getKey(), entry.getValue());
					}
				}); 
			}
			
			var eddai = absoluteMove.entrySet().stream().filter(x->x.getValue().equals(builder.playersPosition.get(x.getKey())))
														.map(x->x.getKey())
													    .collect(Collectors.toSet());
			for(Integer value : eddai) {
				absoluteMove.remove(value);
			}
			
			if(absoluteMove.isEmpty()) {
				this.absoluteMoveToApply = super.getAbsoluteMoveToPosition();
			} else {
				this.absoluteMoveToApply = Optional.of(Collections.unmodifiableMap(absoluteMove));
			}
		}
		
		if(this.absoluteMoveToApply.isPresent()) {
			this.relativeMove = Optional.empty();
		} else if(stepsToDo.isEmpty()) {
			this.relativeMove = super.getAbsoluteMoveToPosition();
		} else {
			if(super.getRelativeMoveToPosition().isPresent()) {
				super.getRelativeMoveToPosition().get().entrySet().forEach(entry->{
					if(stepsToDo.containsKey(entry.getKey())) {
						if(entry.getValue() + stepsToDo.get(entry.getKey()) == 0) {
							stepsToDo.remove(entry.getKey());
						} else {
							stepsToDo.put(entry.getKey(), entry.getValue() +
										  stepsToDo.get(entry.getKey()));
						}
					} else {
						stepsToDo.put(entry.getKey(), entry.getValue());
					}
				});
			}
			if(stepsToDo.isEmpty()){
				this.relativeMove = Optional.empty();
			} else {
				this.relativeMove = Optional.of(Collections.unmodifiableMap(stepsToDo));
			}
		}
	}

	@Override
	public Optional<Map<Integer, Integer>> getRelativeMoveToPosition() {
		return  this.relativeMove;
	}

	@Override
	public Optional<Map<Integer, Integer>> getAbsoluteMoveToPosition() {
		return  this.absoluteMoveToApply;
	}
	
	private Integer newRandomStepsInteger(Random rnd, Integer bound) {
		Integer value = MoveEffect.ZERO;
		do {
			value = rnd.nextInt(Math.abs(bound + bound)) - bound;
		} while(value == MoveEffect.ZERO);
		return value;
	}

}
