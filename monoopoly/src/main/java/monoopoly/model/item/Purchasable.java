package monoopoly.model.item;
import java.util.Optional;


/**
 *	This interface represents a generic Table's tile purchasable.
 */
public interface Purchasable extends Tile {

	/**
	 *	This enum lists the possible Category of Purchasable Tile
	 */
	enum Category {
		BROWN,
		LIGHT_BLUE,
		PINK,
		ORANGE,
		RED,
		YELLOW,
		GREEN,
		BLUE,
		SOCIETY,
		STATION;
	}
	
	/**
	 *  This method is used to set the Purchasable Tile in 
	 *  status Mortgage and receive the value back 
	 *  
	 *  @return the mortgage value of the purchasable tile 
	 */
	public double mortgage();

	/**
	 *  This method is used to know if the purchasable tile is
	 *  already in mortgage status 
	 *  
	 *  @return true if the purchasable tile in in mortgage status,
	 *  		false if not 
	 */
	public boolean isMortgage();

	/**
	 *  This method is used to remove the status of Mortgage from
	 *  the Purchasable Tile
	 */	
	public void removeMortgage();
	
	/**
	 *  this method is used to know the quoted lease value 
	 *  
	 *  @return the quoted lease value 
	 */
	public double getLeaseValue();

	/**
	 *  this method is used to know the quoted sales value
	 *  
	 *  @return the quoted sales value
	 */
	public double getSalesValue();
	
	/**
	 *  This method is used to know how much money you'll receive
	 *  to set status Mortgage 
	 *  
	 *  @return the mortgage value of the purchasable tile 
	 */
	public double getMortgageValue();
	
	/**
	 *  This method is used to know how much money you'll have
	 *  to pay to remove the status Mortgage 
	 *  
	 *  @return the unMortgage value of the purchasable tile 
	 */
	public double getCostToRemoveMortgage();
	
	/**
	 *  This method is used to know the Category of Purchasable 
	 *  tile 
	 *  
	 *  @return the specific Category of Purchase Tile 
	 *  		{@link monoopoly.utilities.PurchasableCategory}
	 */
	public Category getPurchaseCategory();

	/**
	 *  this method is used to get the actual quotation applied
	 *  
	 *  @return the value of quotation applied
	 */
	public double getQuotation();
	
	/**
	 *  this method is used to set a new quotation for this 
	 *  purchasable tile
	 *  
	 *  @param 	quotation new percentage of quotation
	 *  
	 *  @throws IllegalArgumentException if the argument isn't a
	 *  		double
	 */
	public void setQuotation(final double quotation);
	
	/**
	 * This method is used to set a new owner
	 * 
	 * @param newOwner the owner's ID (only positive numbers)
	 * 
	 * @throws IllegalArgumentException if the parameter isn't an
	 * 		   Integer
	 */
	public void setOwner(final Integer newOwnerIdentify);

	/**
	 * This method is used to know which player is the owner
	 * 
	 * @return the ID of the owner
	 */
	public Optional<Integer> getOwner(); 
}
