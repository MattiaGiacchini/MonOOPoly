package monoopoly.model.player;

import monoopoly.utilities.States;

/**
 * This class represents the player as a pawn on the board, managed by the
 * {@link PlayerManager}
 *
 */
public class PlayerImpl implements Player {

	private final int playerID;
	private final String name;
	private Double balance;
	private int position;
	private States state;

	public static class Builder {

		private int playerID;
		private String name;
		private Double balance;
		private Integer position;
		private States state;

		public Builder() {

		}

		public Builder playerId(final int id) {
			this.playerID = id;
			return this;
		}

		public Builder name(final String name) {
			this.name = name;
			return this;
		}

		public Builder balance(final Double balance) {
			this.balance = balance;
			return this;
		}

		public Builder position(final int position) {
			this.position = position;
			return this;
		}

		public Builder state(final States state) {
			this.state = state;
			return this;
		}

		public Player build() throws IllegalStateException {
			// TODO
			if (this.playerID < 0 || this.name == null || this.balance == null || this.state == null
					|| this.position < 0) {
				throw new IllegalStateException("Wrong player creation");
			}

			return new PlayerImpl(this.playerID, this.name, this.balance, this.position, this.state);
		}

	}

	/**
	 * This method creates an instance of {@link Player} with an unique ID
	 * 
	 * @param playerID player unique identifier
	 * @param name     of the player you want to create
	 * @param balance  of the player at the beginning of the game
	 * @param position of the player at the beginning of the game
	 * @param state    of the player at the beginning of the game
	 */
	public PlayerImpl(int playerID, String name, Double balance, Integer position, States state) {
		super();
		this.playerID = playerID;
		this.name = name;
		this.balance = balance;
		this.position = position;
		this.state = state;
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