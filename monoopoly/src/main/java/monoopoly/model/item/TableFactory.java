package monoopoly.model.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import monoopoly.model.item.Tile;
import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.deck.Deck;
import monoopoly.model.item.deck.DeckImpl;

final class TableFactory {

	private final Map<Integer, Tile> map;
	
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

		private TableTile(int position, Category category, String name,  double saleValue, double mortgage,
				double leaseValueLeve0, double leaseValueLevelI, double leaseValueLevelII, double leaseValueLevelIII,
				double leaseValueLevelIV, double leaseValueLevelV, double costToBuildHouse, double costToBuildHotel) {
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
		this.map = new HashMap<>();
	}

	protected Map<Integer, Tile> createTable(Table table) {
		Tile tile;
		
		Deck deck = new DeckImpl(Collections.unmodifiableSet(
								 Stream.of(Category.values())
				                       .filter(x->this.isDeck(x))
				                       .collect(Collectors.toSet())));
		
		for(TableTile value : TableTile.values()) {
			 tile = new TileImpl.Builder()
					   		   	.name(value.name)
					   		   	.category(value.category)
					   		   	.deck(this.isDeck(value.category))
					   		   	.purchasable(this.isPurchasable(value.category))
					   		   	.buildable(this.isBuildable(value.category))
					   		   	.build();

			 if(this.isPurchasable(value.category)) {
				 switch (value.category){
					 case SOCIETY:
						 tile = new Society.Builder()
						 				   .tile(tile)
						 				   .table((ObserverPurchasable)table)
						 				   .mortgage(value.mortgage)
						 				   .sales(value.saleValue)
						 				   .multiplierLevelOne(value.leaseValueLevel0)
						 				   .multiplierLevelTwo(value.leaseValueLevelI)
						 				   .build();
						 break;
					 case STATION:
						 tile = new Station.Builder()
						                   .tile(tile)
						                   .table((ObserverPurchasable)table)
						                   .mortgage(value.mortgage)
						                   .sales(value.saleValue)
						                   .leaseOneStation(value.leaseValueLevel0)
						                   .build();
						 break;
					 default:
						 tile = new PropertyImpl.Builder()
						 						.tile(tile)
						 						.table((ObserverPurchasable)table)
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
						 						.build();
						 break;
				 }
			 } else if (this.isDeck(value.category)) {
				 tile = new TileDeckImpl.Builder()
						 				.tileToDecore(tile)
						 				.deck(deck)
						 				.table(table)
						 				.build();
			 }
			
			map.put(value.position, tile);
		}
		return map;
	}
	
	private boolean isPurchasable(Tile.Category category) {
		if(category == Category.BROWN 		||
		   category == Category.LIGHT_BLUE	||
		   category == Category.PINK		||
		   category == Category.ORANGE 		||
		   category == Category.RED			||
		   category == Category.YELLOW 		||
		   category == Category.GREEN		||
		   category == Category.BLUE		||
		   category == Category.SOCIETY		||
		   category == Category.STATION) {
			return true;
		}
		return false;
	}
	
	private boolean isDeck(Category category) {
		if(category == Category.PROBABILITY	||
		   category == Category.CALAMITY	||
		   category == Category.UNEXPECTED) {
			return true;
		}
		return false;
	}
	
	private boolean isBuildable(Category category) {
		if(category == Category.BROWN 		||
		   category == Category.LIGHT_BLUE	||
		   category == Category.PINK		||
		   category == Category.ORANGE 		||
		   category == Category.RED			||
		   category == Category.YELLOW 		||
		   category == Category.GREEN		||
		   category == Category.BLUE) {
			return true;
		}
		return false;
	}

}
