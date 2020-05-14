package monoopoly.model.table.tile.purchasable;

/**
 *  This interface represents the single property of Monoopoly
 *  where you can build and sell house or hotel.
 */
public interface Property extends Purchasable {

    /**
     *  this method add a new construction on
     *  this Property to increase the value of Lease.<br><br>
     *  The sequence of building produced is:<br>
     *  0 house -> buildOn() -> 1 house<br>
     *  1 house -> buildOn() -> 2 house<br>
     *  2 house -> buildOn() -> 3 house<br>
     *  3 house -> buildOn() -> 4 house<br>
     *  4 house -> buildOn() -> 1 Hotel<br>
     *  1 Hotel -> buildOn() -> IllegalStateException<br>
     *
     *  @throws IllegalStateException if you try to build
     *          over an hotel
     *
     *  @throws IllegalStateException if you try to build
     *          a building when the owner of this Property
     *          isn't the same of the other Properties
     *          which the same category of this
     */
    void buildOn();

    /**
     * This method is used to know if is possible to
     * {@link #buildOn()}.
     *
     * @return <code>true</code> if {@link #buildOn()} is
     *         enabled to use, <code>false</code> if not.
     */
    boolean isBuildOnEnabled();

    /**
     *  this method sell one building you already
     *  built. if there is a building you'll give
     *  back the listed selling value and decrease
     *  the number of buildings, otherwise throws
     *  Exception.<br><br>
     *  The sequence of state produced is:<br>
     *  1 hotel -> sellBuilding() -> 4 house (hotelValue)<br>
     *  4 house -> sellBuilding() -> 3 house (houseValue)<br>
     *  3 house -> sellBuilding() -> 2 house (houseValue)<br>
     *  2 house -> sellBuilding() -> 1 house (houseValue)<br>
     *  1 house -> sellBuilding() -> 0 house (houseValue)<br>
     *  0 house -> sellBuilding() -> IllegalStateException<br>
     *
     * @return  the listed selling value for a hotel or house
     *
     * @throws  IllegalStateException if you try to sell
     *          a build that you haven't
     */
    Double sellBuilding();

    /**
     * This method is used to know if is possible to
     * {@link #sellBuilding()}.
     *
     * @return <code>true</code> if {@link #sellBuilding()} is
     *         enabled to use, <code>false</code> if not.
     */
    boolean isSellBuildingsEnabled();

    /**
     * this method is used to know how many house
     * are already built in this Property.
     *
     *
     * @return the number of houses built
     */
    Integer getNumberOfHouseBuilt();

    /**
     * this method is used to know how many hotel
     * are already built in this Property.
     *
     *
     * @return the number of Hotels built
     */
    Integer getNumberOfHotelBuilt();

    /**
     * this method is used to know the cost of
     * Building a house.
     *
     * @return value to pay for a new house
     */
    Double getCostToBuildHouse();

    /**
     * this method is used to know the cost of
     * Building a Hotel.
     *
     * @return value to pay for a new Hotel
     */
    Double getCostToBuildHotel();

    /**
     * this method is used to know the quotation
     * to sell a house.
     *
     * @return the listed selling value of a house
     */
    Double getQuotationToSellHouse();

    /**
     * this method is used to know the quotation
     * to sell a hotel.
     *
     * @return the listed selling value of a hotel
     */
    Double getQuotationToSellHotel();
}
