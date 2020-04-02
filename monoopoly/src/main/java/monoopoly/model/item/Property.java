package monoopoly.model.item;

/**
 *	This interface represents the single property of Monoopoly
 *  where you can build and sell house or hotel.
 */
public interface Property extends Purchasable {
	
	/**
	 *  this method add a new construction on 
	 *  the Property to increase the value of Lease. 
	 *  The sequence of building produced is:<br>
	 *  0 house -> buildOn() -> 1 house<br>
	 *  1 house -> buildOn() -> 2 house<br>
	 *  2 house -> buildOn() -> 3 house<br>
	 *  3 house -> buildOn() -> 4 house<br>
	 *  4 house -> buildOn() -> 1 hotel<br>
	 *  1 Hotel -> buildOn() -> exception<br>
	 *  
	 *  @throws java.io.Exception 
	 */
	public void buildOn() throws Exception;
	
	/**
	 *  this method sell one building you already 
	 *  built. if there is a building you'll give
	 *  back the listed selling value to build it
	 *  and decrease the number of buildings,
	 *  otherwise throws Exception.<br>
	 *  The sequence of building produced is:<br>
	 *  1 hotel -> sellBuilding() -> 4 house+hotelValue<br>
	 *  4 house -> sellBuilding() -> 3 house+houseValue<br>
	 *  3 house -> sellBuilding() -> 2 house+houseValue<br>
	 *  2 house -> sellBuilding() -> 1 house+houseValue<br>
	 *  1 house -> sellBuilding() -> exception<br>
	 *      
	 * @return the listed selling value for a hotel or house
	 * @throws java.io.Exception
	 */
	public Integer sellBuilding() throws Exception;
	
	/**
	 * this method is used to know how many constructions 
	 * are built in this Property
	 *  
	 * @return then number of constructions built on
	 */
	public Integer getNumberOfBuildingsBuilt();
	
	/**
	 * this method is used to know the cost of
	 * Building a house
	 * 
	 * @return value to pay for a new house
	 */
	public Integer getCostToBuildHouse();

	/**
	 * this method is used to know the cost of
	 * Building a Hotel
	 * 
	 * @return value to pay for a new Hotel
	 */
	public Integer getCostToBuildHotel();

	/**
	 * this method is used to know the quotation
	 * to sell a house
	 * 
	 * @return the listed selling value of a house
	 */
	public Integer getQuotationToSellHouse();

	/**
	 * this method is used to know the quotation
	 * to sell a hotel
	 * 
	 * @return the listed selling value of a hotel
	 */
	public Integer getQuotationToSellHotel();
	
}
	