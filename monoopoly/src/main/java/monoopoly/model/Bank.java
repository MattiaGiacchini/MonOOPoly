package monoopoly.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import monoopoly.model.player.Player;
import monoopoly.model.table.tile.Tile;

/**
 *    This class represents the bank.
 */
public class Bank {
    private static final double HARD_CAP = 150_000.0;
    private final Set<Tile> allProperties;
    private final Map<Tile, Player> assignedProperties;
    private final Map<Tile, Player> mortgagedProperties;
    private boolean isBroke;

    private double currentBudget;
    /**
     * constructor.
     * @param property Set of game tiles.
     */
    public Bank(final Set<Tile> property) {
        this.currentBudget = HARD_CAP;
        this.allProperties = property;
        this.assignedProperties = new HashMap<>();
        this.mortgagedProperties = new HashMap<>();
        this.isBroke = false;
    }
    /**
     * Method that reduces money from the total budget.
     * @param toGive amount to give.
     */
    public final void giveMoney(final double toGive) {
        this.currentBudget -= toGive;
        if (this.currentBudget < 0) {
            this.isBroke = true;
        }
    }
    /**
     * Bank total money.
     * @return the money.
     */
    public final double getBankBudget() {
        return this.currentBudget;
    }
    /**
     * Set of game properties.
     * @return the properties.
     */
    public final Set<Tile> getProperties() {
        return this.allProperties;
    }
    /**
     * Set of assigned properties.
     * @return the assigned properties.
     */
    public final Map<Tile, Player> getAssignedProperties() {
        return this.assignedProperties;
    }
    /**
     * Set of mortgaged properties.
     * @return the mortgaged properties.
     */
    public final Map<Tile, Player> getMortgagedProperties() {
        return this.mortgagedProperties;
    }
    /**
     * Is the bank broken?
     * @return a boolean that represents if the bank's gone bankrupt.
     */
    public final boolean isBankBroken() {
        return this.isBroke;
    }

    @Override
    public final String toString() {
        return "The Bank now has " + this.currentBudget + " in its caveau";
    }
    /**
     * The bank.
     * @return the bank.
     */
    public final Bank getBank() {
        return this;
    }
}
