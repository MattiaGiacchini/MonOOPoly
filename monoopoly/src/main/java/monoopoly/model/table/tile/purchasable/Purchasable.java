package monoopoly.model.table.tile.purchasable;

import java.util.Map;
import java.util.Optional;

import monoopoly.model.table.tile.Tile;


/**
 *  This interface represents a generic Table's tile purchasable.
 */
public interface Purchasable extends Tile {

    /**
     *  This method is used to set the Purchasable Tile in
     *  status Mortgage and receive the value back.
     *
     *  @return the mortgage value of the purchasable tile
     */
    Double mortgage();

    /**
     *  This method is used to know if the purchasable tile is
     *  already in mortgage status.
     *
     *  @return true if the purchasable tile in in mortgage status,
     *          false if not
     */
    boolean isMortgage();

    /**
     *  This method is used to remove the status of Mortgage from
     *  the Purchasable Tile.
     */
    void removeMortgage();

    /**
     *  this method is used to know the quoted lease value.
     *
     *  @return the quoted lease value
     */
    Double getLeaseValue();

    /**
     *  this method is used to know the quoted sales value.
     *
     *  @return the quoted sales value
     */
    Double getSalesValue();

    /**
     *  This method is used to know how much money you'll receive
     *  to set status Mortgage.
     *
     *  @return the mortgage value of the purchasable tile
     */
    Double getMortgageValue();

    /**
     *  This method is used to know how much money you'll have
     *  to pay to remove the status Mortgage.
     *
     *  @return the unMortgage value of the purchasable tile
     */
    Double getCostToRemoveMortgage();

    /**
     *  this method is used to get the actual quotation applied.
     *
     *  @return the value of quotation applied
     */
    Double getQuotation();

    /**
     *  this method is used to set a new quotation for this
     *  purchasable tile.
     *
     *  @param  quotation new percentage of quotation
     *
     *  @throws IllegalArgumentException if the argument isn't a
     *          double
     */
    void setQuotation(double quotation);

    /**
     * This method is used to set a new owner.
     *
     * @param newOwnerIdentify the owner's ID (only positive numbers)
     *
     * @throws IllegalArgumentException if the parameter isn't an
     *         Integer
     */
    void setOwner(Optional<Integer> newOwnerIdentify);

    /**
     * This method is used to know which player is the owner.
     *
     * @return the ID of the owner
     */
    Optional<Integer> getOwner();

    /**
     * this method is used to get the all combination
     * of lease.
     *
     * @return a {@link Map} where the key indicate
     *         the level of buildings or the number
     *         of specific category tile that a player
     *         can own and the value indicate the
     *         respective value
     *
     */
    Map<Integer, Double> getLeaseList();
}
