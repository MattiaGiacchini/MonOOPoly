package monoopoly.view.utilities;

/**
 * This interface represents an utilities to check the button logic in {@link TileInfoController}.
 */
public interface ButtonLogic {

    /**
     * This method checks if the player has got enough money to afford the purchase.
     * 
     * @param balance of the player
     * @param value   of the purchase
     * @return true if the {@link Player} has enough money, false otherwise
     */
    boolean enoughMoney(Double balance, Double value);

}
