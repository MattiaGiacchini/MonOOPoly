package monoopoly.model.item;

/**
 *	This interface represents the single property of Monoopoly
 */
public interface Property extends Purchasable {
	
	/**
	 *  this method build add a new construction on 
	 *  the Property to increase the value of Lease. 
	 *  The sequence of building produced is:<br>
	 *  0 house -> buildOn() -> 1 house<br>
	 *  1 house -> buildOn() -> 2 house<br>
	 *  2 house -> buildOn() -> 3 house<br>
	 *  3 house -> buildOn() -> 4 house<br>
	 *  4 house -> buildOn() -> 1 hotel<br>
	 *  1 Hotel -> buildOn() -> 1 hotel<br>
	 *  
	 *  @throws java.io.Exception
	 */
	public void buildOn();
	
	
	/**
	 *  this method sell one building you already 
	 *  built. if there is a building you'll give
	 *  back a portion of cost to build it,
	 *  otherwise throws Exception.<br>
	 *  The sequence of building produced is:<br>
	 *  1 hotel -> sellBuilding() -> 4 house+value<br>
	 *  4 house -> sellBuilding() -> 3 house+value<br>
	 *  3 house -> sellBuilding() -> 2 house+value<br>
	 *  2 house -> sellBuilding() -> 1 house+value<br>
	 *  1 house -> sellBuilding() -> exception<br>
	 *      
	 * @return The portion of value back.
	 * @throws java.io.Exception
	 */
	public Integer sellBuilding();
	
	/**
	 * this method is used to know how many constructions 
	 * are built in this Property
	 *  
	 * @return number of constructions built on
	 */
	public Integer getNumberOfBuildingOn();
	
}
	