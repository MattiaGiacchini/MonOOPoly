package monoopoly.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *	This class represents the bank
 */
public class Bank {
	private final static int HARD_CAP = 500000;
	private final List<Property> allProperties;
	private Map<Property, Player> assignedProperties;
	private Map<Property, Player> mortgagedProperties;
	private boolean isBroke;
	
	private int currentBudget;
	
	public Bank(List<Property> property) {
		this.currentBudget = HARD_CAP;
		this.allProperties = property;
		this.assignedProperties = new HashMap<>();
		this.mortgagedProperties = new HashMap<>();
		this.isBroke = false;
	}
	
	public void giveMoney(int toGive) {
		this.currentBudget -= toGive;
		if (this.currentBudget < 0) {
			this.isBroke = true;
		}
	}
	
	public int getBankBudget() {
		return this.currentBudget;
	}
	
	public List<Property> getProperties(){
		return this.allProperties;
	}
	
	public Map<Property, Player> getAssignedProperties(){
		return this.assignedProperties;
	}
	
	public Map<Property, Player> getMortgagedProperties(){
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
