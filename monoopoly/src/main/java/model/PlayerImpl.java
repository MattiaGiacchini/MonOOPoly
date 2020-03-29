package model;

/**
 * This class represents the player
 *
 */
public class PlayerImpl implements Player {

	protected final static int LAST_POSITION = 40;

	private final String name;
	private Double balance;
	private int position;
//	private States state;

	/**
	 * This method creates an instance of {@link Player}
	 * 
	 * @param name            player's name
	 * @param startingBalance balance set in the game settings or taken from an
	 *                        older game
	 * @param position        player's actual position
	 */
	public PlayerImpl(final String name, Double startingBalance, int position) {
		this.name = name;
		this.balance = startingBalance;
		this.position = position;
		// this.state = States.SLEEPING;
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

//	@Override
//	public States getState() {
//		return state;
//	}

//	@Override
//	public void setState(States state) {
//		this.state = state;
//	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void updateBalance(Double value) {
		this.balance = this.balance + value;

	}

	@Override
	public void updatePosition(int distance) {
		if (this.position + distance <= LAST_POSITION - 1) {
			this.position = this.position + distance;
		} else {
			this.position = this.position + distance - LAST_POSITION;
		}

		if (this.position < 0) {
			this.position = this.position + LAST_POSITION;
		}

	}

}