package monoopoly.model.player;

import monoopoly.utilities.States;

/**
 * This class represents the player as a pawn on the board, managed by the
 * {@link PlayerManager}
 *
 */
public class PlayerImpl implements Player {

	protected final static int LAST_POSITION = 40;

	private final int playerID;
	private String name;
	private Double balance;
	private int position;
	private States state;

	/**
	 * This method creates an instance of {@link Player} with an unique ID
	 * 
	 * @param playerID player unique identifier
	 */
	public PlayerImpl(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public int getID() {
		return playerID;
	}

	@Override
	public Double getBalance() {
		return balance;
	}

	@Override
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public States getState() {
		return state;
	}

	@Override
	public void setState(States state) {
		this.state = state;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void updateBalance(Double value) {
		this.balance = this.balance + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + playerID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerImpl other = (PlayerImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerImpl [playerID=" + playerID + ", name=" + name + ", balance=" + balance + ", position=" + position
				+ ", state=" + state + "]";
	}

}