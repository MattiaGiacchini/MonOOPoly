package monoopoly.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import monoopoly.model.item.Property;
import monoopoly.model.item.Tile;
import monoopoly.model.player.Player;

/**
 *	This class represents the bank
 */
public class Bank {
	private final static double HARD_CAP = 500000.0;
	private final Set<Tile> allProperties;
	private Map<Tile, Player> assignedProperties;
	private Map<Tile, Player> mortgagedProperties;
	private boolean isBroke;
	
	private double currentBudget;
	
	public Bank(Set<Tile> property) {
		this.currentBudget = HARD_CAP;
		this.allProperties = property;
		this.assignedProperties = new HashMap<>();
		this.mortgagedProperties = new HashMap<>();
		this.isBroke = false;
	}
	
	public void giveMoney(double toGive) {
		this.currentBudget -= toGive;
		if (this.currentBudget < 0) {
			this.isBroke = true;
		}
	}
	
	public double getBankBudget() {
		return this.currentBudget;
	}
	
	public Set<Tile> getProperties(){
		return this.allProperties;
	}
	
	public Map<Tile, Player> getAssignedProperties(){
		return this.assignedProperties;
	}
	
	public Map<Tile, Player> getMortgagedProperties(){
		return this.mortgagedProperties;
	}
	
	public boolean isBankBroken() {
		return this.isBroke;
	}
	
	@Override
	public String toString() {
		return "The Bank now has " + this.currentBudget + " in its caveau";
	}
	
	public Bank getBank() {
		return this;
	}
}
