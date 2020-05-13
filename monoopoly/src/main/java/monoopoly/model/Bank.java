package monoopoly.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;

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

    public Bank(final Set<Tile> property) {
        this.currentBudget = HARD_CAP;
        this.allProperties = property;
        this.assignedProperties = new HashMap<>();
        this.mortgagedProperties = new HashMap<>();
        this.isBroke = false;
    }

    public final void giveMoney(final double toGive) {
        this.currentBudget -= toGive;
        if (this.currentBudget < 0) {
            this.isBroke = true;
        }
    }

    public final double getBankBudget() {
        return this.currentBudget;
    }

    public final Set<Tile> getProperties() {
        return this.allProperties;
    }

    public final Map<Tile, Player> getAssignedProperties() {
        return this.assignedProperties;
    }

    public final Map<Tile, Player> getMortgagedProperties() {
        return this.mortgagedProperties;
    }

    public final boolean isBankBroken() {
        return this.isBroke;
    }

    @Override
    public final String toString() {
        return "The Bank now has " + this.currentBudget + " in its caveau";
    }

    public final Bank getBank() {
        return this;
    }
}
