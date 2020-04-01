package monoopoly.model.item;
import monoopoly.utilities.PurchasableCategory;

/**
 *	This interface represents a generic Table's tile purchasable.
 */
public interface Purchasable extends Tile {

	/**
	 *  This method is used to set the Purchasable Tile in 
	 *  status Mortgage and receive the value back 
	 *  
	 *  @return the mortgage value of the purchasable tile 
	 */
	public Integer mortgage();

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
	public Integer getLeaseValue();

	/**
	 *  this method is used to know the quoted sales value
	 *  
	 *  @return the quoted sales value
	 */
	public Integer getSalesValue();
	
	/**
	 *  This method is used to know how much money you'll receive
	 *  to set status Mortgage 
	 *  
	 *  @return the mortgage value of the purchasable tile 
	 */
	public Integer getMortgageValue();
	
	/**
	 *  This method is used to know how much money you'll have
	 *  to pay to remove the status Mortgage 
	 *  
	 *  @return the unMortgage value of the purchasable tile 
	 */
	public Integer getCostToRemoveMortgage();
	
	/**
	 *  This method is used to know the Category of Purchasable 
	 *  tile 
	 *  
	 *  @return the specific Category of Purchase Tile 
	 *  		{@link monoopoly.utilities.PurchasableCategory}
	 */
	public PurchasableCategory getPurchaseCategory();
	
}
