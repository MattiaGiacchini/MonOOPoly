package monoopoly.model.item;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.deck.Deck;
import monoopoly.model.item.deck.DeckImpl;

final class TableFactory {
    
    private interface SerializableFunction<X,Y> extends Function<X,Y>,
                                                        Serializable{}
    
    private interface SerializableBiFunction<X,Y,Z> extends BiFunction<X,Y,Z>,
                                                            Serializable{}
    
    private interface SerializableBiPredicate<X,Y> extends BiPredicate<X,Y>,
                                                           Serializable{}
    
    private interface SerializableSupplier<X> extends Supplier<X>,
                                                        Serializable{}
    
	private enum TableTile{
		//	  								name						sale 	mort	O		I		II		III		IV		V		houVal	hotVal	
		TILE00(0,  Category.START,			"START",					  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE01(1,  Category.BROWN, 			"Vicolo Corto",				 60.0,	 30.0,	  2.0,	 10.0,	 30.0,	  90.0,	 160.0,	 250.0,	 50.0,	 50.0),	
		TILE02(2,  Category.PROBABILITY,	"Probabilità",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE03(3,  Category.BROWN, 			"Vicolo Stretto",			 60.0,	 30.0,	  4.0,	 20.0,	 60.0,	 180.0,	 320.0,	 450.0,	 50.0,	 50.0),	
		TILE04(4,  Category.CALAMITY,		"Calamità",					  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE05(5,  Category.STATION,		"Stazione Sud",				200.0,	100.0,	 25.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE06(6,  Category.LIGHT_BLUE,		"Bastioni Gran Sasso",		100.0,	 50.0,	  6.0,	 30.0,	 90.0,	 270.0,	 400.0,	 550.0,	 50.0,	 50.0),	
		TILE07(7,  Category.UNEXPECTED,		"Imprevisti",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE08(8,  Category.LIGHT_BLUE,		"Viale Monte Rosa",			100.0,	 50.0,	  6.0,	 30.0,	 90.0,	 270.0,	 400.0,	 550.0,	 50.0,	 50.0),	
		TILE09(9,  Category.LIGHT_BLUE,		"Viale Vesuvio",			120.0,	 60.0,	  8.0,	 40.0,	100.0,	 300.0,	 450.0,	 600.0,	 50.0,	 50.0),	
		TILE10(10, Category.JAIL,			"Prigione",					  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE11(11, Category.PINK, 			"Via Accademia",			140.0,	 70.0,	 10.0,	 50.0,	150.0,	 450.0,	 625.0,	 750.0,	100.0,	100.0),		
		TILE12(12, Category.SOCIETY, 		"Società Elettrica",		150.0,	 75.0,	  4.0,	 10.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),
		TILE13(13, Category.PINK, 			"Corso Ateneo",				140.0,	 70.0,	 10.0,	 50.0,	150.0,	 450.0,	 625.0,	 750.0,	100.0,	100.0),	
		TILE14(14, Category.PINK, 			"Piazza Università",		200.0,	 80.0,	 12.0,	 60.0,	180.0,	 500.0,	 700.0,	 900.0,	100.0,	100.0),	
		TILE15(15, Category.STATION, 		"Stazione Ovest",			200.0,	100.0,	 25.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE16(16, Category.ORANGE,			"Via Verdi",				180.0,	 90.0,	 14.0,	 70.0,	200.0,	 550.0,	 750.0,	 950.0,	100.0,	100.0),	
		TILE17(17, Category.PROBABILITY, 	"Probabilità",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE18(18, Category.ORANGE, 		"Corso Raffaello",			180.0,	 90.0,	 14.0,	 70.0,	200.0,	 550.0,	 750.0,	 950.0,	100.0,	100.0),	
		TILE19(19, Category.ORANGE, 		"Piazza Dante",				200.0,	100.0,	 16.0,	 80.0,	220.0,	 600.0,	 800.0,	1000.0,	100.0,	100.0),	
		TILE20(20, Category.FREE_PARKING,	"Parcheggio Gratuito",		  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE21(21, Category.RED, 			"Via Marco Polo",			220.0,	110.0,	 18.0,	 90.0,	250.0,	 700.0,	 875.0,	1050.0,	150.0,	150.0),	
		TILE22(22, Category.UNEXPECTED, 	"Imprevisti",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE23(23, Category.RED, 			"Corso Magellano",			220.0,	110.0,	 18.0,	 90.0,	250.0,	 700.0,	 875.0,	1050.0,	150.0,	150.0),	
		TILE24(24, Category.RED, 			"Largo Colombo",			240.0,	120.0,	 20.0,	100.0,	300.0,	 750.0,	 925.0,	1100.0,	150.0,	150.0),	
		TILE25(25, Category.STATION, 		"Stazione Nord",			200.0,	100.0,	 25.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE26(26, Category.YELLOW,			"Viale Costantino",			260.0,	130.0,	 22.0,	110.0,	330.0,	 800.0,	 975.0,	1150.0,	150.0,	150.0),	
		TILE27(27, Category.YELLOW, 		"Viale Traiano",			260.0,	130.0,	 22.0,	110.0,	330.0,	 800.0,	 975.0,	1150.0,	150.0,	150.0),	
		TILE28(28, Category.SOCIETY, 		"Società Acqua Potabile",	150.0,	 75.0,	  4.0,	 10.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE29(29, Category.YELLOW, 		"Piazza Giulio Cesare",		280.0,	140.0,	 24.0,	120.0,	360.0,	 850.0,	1025.0,	1200.0,	150.0,	150.0),	
		TILE30(30, Category.GO_TO_JAIL,		"In Prigione!",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE31(31, Category.GREEN, 			"Via Roma",					300.0,	150.0,	 26.0,	130.0,	390.0,	 900.0,	1100.0,	1275.0,	200.0,	200.0),	
		TILE32(32, Category.GREEN, 			"Corso Impero",				300.0,	150.0,	 26.0,	130.0,	390.0,	 900.0,	1100.0,	1275.0,	200.0,	200.0),	
		TILE33(33, Category.PROBABILITY, 	"Probabilità",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE34(34, Category.GREEN, 			"Largo Augusto",			320.0,	160.0,	 28.0,	150.0,	450.0,	1000.0,	1200.0,	1400.0,	200.0,	200.0),	
		TILE35(35, Category.STATION, 		"Stazione Est",				200.0,	100.0,	 25.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE36(36, Category.UNEXPECTED,		"Imprevisti",				  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),	
		TILE37(37, Category.BLUE, 			"Viale Dei Giardini",		350.0,	175.0,	 35.0,	175.0,	500.0,	1100.0,	1300.0,	1500.0,	200.0,	200.0),	
		TILE38(38, Category.CALAMITY, 		"Calamità",					  0.0,	  0.0,	  0.0,	  0.0,	  0.0,	   0.0,	   0.0,	   0.0,	  0.0,	  0.0),
		TILE39(39, Category.BLUE,			"Parco Della Vittoria",		400.0,	200.0,	 50.0,	200.0,	600.0,	1400.0,	1700.0,	2000.0,	200.0,	200.0);
		
		private final int position;
		private final Tile.Category category;
		private final String name;
		private final double mortgage;	
		private final double saleValue;
		private final double leaseValueLevel0;
		private final double leaseValueLevelI;
		private final double leaseValueLevelII;
		private final double leaseValueLevelIII;
		private final double leaseValueLevelIV;
		private final double leaseValueLevelV;
		private final double costToBuildHouse;
		private final double costToBuildHotel;

		private TableTile(int position, Category category, String name,  
		                  double saleValue, double mortgage,
		                  double leaseValueLeve0, double leaseValueLevelI, 
		                  double leaseValueLevelII, double leaseValueLevelIII,
		                  double leaseValueLevelIV, double leaseValueLevelV, 
		                  double costToBuildHouse, double costToBuildHotel) {
			this.position = position;
			this.category = category;
			this.name = name;
			this.mortgage = mortgage;
			this.saleValue = saleValue;
			this.leaseValueLevel0 = leaseValueLeve0;
			this.leaseValueLevelI = leaseValueLevelI;
			this.leaseValueLevelII = leaseValueLevelII;
			this.leaseValueLevelIII = leaseValueLevelIII;
			this.leaseValueLevelIV = leaseValueLevelIV;
			this.leaseValueLevelV = leaseValueLevelV;
			this.costToBuildHouse = costToBuildHouse;
			this.costToBuildHotel = costToBuildHotel;
		}
	}
	
	public TableFactory() {
	    super();	
	}

	protected Map<Integer, Tile> createTable(Table table) {
		
		Map<Integer, Tile> map = new HashMap<>();
		Deck deck = generateNewDeck();

		for(TableTile value : TableTile.values()) {
			map.put(value.position, this.generateNewTile(value, deck, table));
		}
		
		return map;
	}
	
	private Tile generateNewTile(final TableTile value, 
								 final Deck deck, 
								 final Table table) {
		 switch (value.category){
		 	// the categories forward represents general tiles
			case START:
			case JAIL:
			case GO_TO_JAIL:
			case FREE_PARKING: return this.generateTileBase(value);

			// the categories forward represents property
			case BROWN:
			case LIGHT_BLUE:
			case PINK:
			case ORANGE:
			case RED:
			case YELLOW:
			case GREEN:
			case BLUE:			return this.generateTileProperty(value,table);
			
			// the categories forward represents deck
			case UNEXPECTED:
			case PROBABILITY:
			case CALAMITY:		return this.generateTileDeck(value,deck,table);
			
			// the next category represents a societies
			case SOCIETY:		return this.generateTileSociety(value,table);
			
			// the next category represents the stations
			case STATION:		return this.generateTileStation(value,table);
			
			// wrong configuration
			default: 			throw new IllegalArgumentException(
					 				"TableFactory: invalid Category format");
		 }
		 
	}

	private Tile generateTileBase(final TableTile value) {
		return new TileImpl.Builder()
			   		   	   .name(value.name)
			   		   	   .category(value.category)
				   		   .deck(this.isDeck(value.category))
				   		   .purchasable(this.isPurchasable(value.category))
				   		   .buildable(this.isBuildable(value.category))
				   		   .build();
	}

	private Tile generateTileStation(final TableTile value, 
									 final Table table) {
		return new Station.Builder()
	                	  .tile(this.generateTileBase(value))
	                	  .mortgage(value.mortgage)
	                	  .numOfStations(this.getNumberOfCategory(
	                	                 value.category))
	                	  .funNumOfCatOwned(
	                	          this.funToGetNumbOfTypeOwned(table, 
	                	                                       value.category))
	                	  .sales(value.saleValue)
	                	  .leaseOneStation(value.leaseValueLevel0)
	                	  .build();
	}

    private Tile generateTileSociety(final TableTile value, 
									 final Table table) {
		return new Society.Builder()
					      .tile(this.generateTileBase(value))
					      .supplierDiceResult(this.supplierDiceResult(table))
					      .biFunNumOfCategoryOwned(
					              this.biFunctionNumOfCategoryOwned(table))
					      .mortgage(value.mortgage)
					      .sales(value.saleValue)
					      .multiplierLevelOne(value.leaseValueLevel0)
					      .multiplierLevelTwo(value.leaseValueLevelI)
					      .build();
	}

	private Tile generateTileDeck(final TableTile value,
								  final Deck deck,
								  final Table table) {
		return new TileDeckImpl.Builder()
			 				   .tileToDecore(this.generateTileBase(value))
			 				   .deck(deck)
			 				   .table(table)
			 				   .build();
	}

	private Tile generateTileProperty(final TableTile value,
									  final Table table) {
		return new PropertyImpl.Builder()
							   .tile(this.generateTileBase(value))
							   .mortgage(value.mortgage)
							   .sales(value.saleValue)
							   .valueToBuildHouse(value.costToBuildHouse)
							   .valueToBuildHotel(value.costToBuildHotel)
							   .leaseWithNoBuildings(value.leaseValueLevel0)
							   .leaseWithOneHouse(value.leaseValueLevelI)
							   .leaseWithTwoHouse(value.leaseValueLevelII)
							   .leaseWithThreeHouse(value.leaseValueLevelIII)
							   .leaseWithFourHouse(value.leaseValueLevelIV)
							   .leaseWithOneHotel(value.leaseValueLevelV)
                               .bipred(this.BiPredicateAllCategoryOwned(table))
							   .build();
	}

	private DeckImpl generateNewDeck() {
		return new DeckImpl(Collections.unmodifiableSet(
						    Stream.of(Category.values())
		                       	  .filter(x->this.isDeck(x))
		                       	  .collect(Collectors.toSet())));
	}
	
	private boolean isPurchasable(final Category category) {
		switch (category){
			 case BROWN:
			 case LIGHT_BLUE:
			 case PINK:
			 case ORANGE:
			 case RED:
			 case YELLOW:
			 case GREEN:
			 case BLUE:
			 case SOCIETY:
			 case STATION: 	return true;
			 default: 		return false;
		}
	}

	private boolean isDeck(final Category category) {
		switch (category){
			case PROBABILITY:
			case UNEXPECTED:
			case CALAMITY:	return true;
			default: 		return false;
		}
	}
	
	private boolean isBuildable(final Category category) {
		switch (category){
			case BROWN:
			case LIGHT_BLUE:
			case PINK:
			case ORANGE:
			case RED:
			case YELLOW:
			case GREEN:
			case BLUE:		return true;
			default: 		return false;
		}		
	}
	
	private Function<Integer,Integer> funToGetNumbOfTypeOwned(Table board, 
	                                                         Category category){
	    return new SerializableFunction<Integer,Integer>() {
	        
            private static final long serialVersionUID = 149054793696676323L;
            private final Category cat = category;
            private final Table table = board;

            @Override
            public Integer apply(Integer idPlayer) {
                Objects.requireNonNull(idPlayer);
                return this.table
                           .getFilteredTiles(Purchasable.class, 
                                             x->x.isPurchasable() &&
                                             x.getCategory().equals(this.cat) &&
                                             ((Purchasable)x).getOwner()
                                                             .isPresent() &&
                                             ((Purchasable)x).getOwner()
                                                             .get()
                                                             .equals(idPlayer))
                           .size();
            }
	        
	    };
	}
	
	private Supplier<Integer> supplierDiceResult(Table board){
	    return new SerializableSupplier<>(){
	        
            private static final long serialVersionUID = -5082802526818405282L;
            private final Table table = board;
            
            @Override
            public Integer get() {
                return table.getNotifiedDices();
            }
	    };
	}

    private BiPredicate<Integer, Category> BiPredicateAllCategoryOwned(
                                                                  Table board){
        return new SerializableBiPredicate<>() {

            private static final long serialVersionUID = -3624186169928549214L;
            private final Table table = board;

            @Override
            public boolean test(Integer idPlayer, Category category) {
                Objects.requireNonNull(idPlayer);
                Objects.requireNonNull(category);
                return this.table.getFilteredTiles(Purchasable.class,
                                                   x->x.getCategory()
                                                       .equals(category))
                                 .stream().allMatch(x->x.getOwner().isPresent()
                                                    && x.getOwner().get()
                                                        .equals(idPlayer));
            }
        };
    }
    
    private BiFunction<Integer, Category, Integer> 
                                      biFunctionNumOfCategoryOwned(Table board){
        return new SerializableBiFunction<>() {

            private static final long serialVersionUID = -7384117543775718616L;
            private final Table table = board;

            @Override
            public Integer apply(Integer idPlayer, Category category) {
                Objects.requireNonNull(idPlayer);
                Objects.requireNonNull(category);
                return this.table
                           .getFilteredTiles(Purchasable.class, 
                                             x->x.isPurchasable() &&
                                             ((Purchasable)x).getOwner()
                                                             .isPresent() &&
                                             ((Purchasable)x).getOwner()
                                                             .get()
                                                             .equals(idPlayer))
                           .size();
            }
        };
    }

    private int getNumberOfCategory(Category category) {
        return (int) Stream.of(TableTile.values())
                           .filter(x->x.category.equals(category))
                           .count();
    }
}
