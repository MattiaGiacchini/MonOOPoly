package monoopoly.view.utilities;

import monoopoly.model.player.Player;

public interface ButtonLogic {

    /**
     * This method checks if the player has got enough money to afford the purchase
     * 
     * @param balance of the player
     * @param value   of the purchase
     * @return true if the {@link Player} has enough money, false otherwise
     */
    public boolean enoughMoney(Double balance, Double value);

    /**
     * This method checks if the {@link Player} can build another house
     * 
     * @param numHouses already built
     * @return true if the player can build, false otherwise
     */
    public boolean maxHouses(int numHouses);

    /**
     * This method checks if the {@link Player} can sell a house
     * 
     * @param numHouses
     * @return true if there are at least 1 house, false otherwise
     */
    public boolean minHouses(int numHouses);

}
