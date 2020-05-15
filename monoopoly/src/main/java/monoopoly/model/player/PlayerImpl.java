package monoopoly.model.player;

import monoopoly.utilities.States;

/**
 * This class represents the player as a pawn on the board, managed by the
 * {@link PlayerManager}.
 */
public class PlayerImpl implements Player {

    private final int playerID;
    private final String name;
    private Double balance;
    private int position;
    private States state;
    private boolean prisonCard;

    /**
     * A simple builder for the player.
     */
    public static class Builder {

        private int playerID;
        private String name;
        private Double balance;
        private Integer position = 0;
        private States state = States.IN_GAME;
        private boolean leavePrisonForFree;

        /**
         * This is a builder function to set the {@link Player}'s identifier.
         * 
         * @param id to set.
         * @return {@link Builder}
         */
        public Builder playerId(final int id) {
            this.playerID = id;
            return this;
        }

        /**
         * This is a builder function to set the {@link Player}'s name.
         * 
         * @param name to set
         * @return {@link Builder}
         */
        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * This is a builder function to set the {@link Player}'s balance.
         * 
         * @param balance to set
         * @return {@link Builder}
         */
        public Builder balance(final Double balance) {
            this.balance = balance;
            return this;
        }

        /**
         * This is a builder function to set the {@link Player}'s position.
         * 
         * @param position to set
         * @return {@link Builder}
         */
        public Builder position(final int position) {
            this.position = position;
            return this;
        }

        /**
         * This is a builder function to set the {@link Player}'s state.
         * 
         * @param state to set
         * @return {@link Builder}
         */
        public Builder state(final States state) {
            this.state = state;
            return this;
        }

        /**
         * This is a builder function to set the {@link Player}'s leavePrisonForFree
         * card.
         * 
         * @param leave boolean to set
         * @return {@link Builder}
         */
        public Builder leavePrisonForFree(final boolean leave) {
            this.leavePrisonForFree = leave;
            return this;
        }

        /**
         * This method builds the {@link PlayerImpl} instance.
         * 
         * @return {@link PlayerImpl}
         * @throws IllegalStateException
         */
        public Player build() {
            if (this.playerID < 0 || this.name == null || this.balance == null || this.state == null
                    || this.position < 0) {
                throw new IllegalStateException("Wrong player creation");
            }

            return new PlayerImpl(this.playerID, this.name, this.balance, this.position, this.state,
                    this.leavePrisonForFree);
        }

    }

    /**
     * This method creates an instance of {@link Player} with an unique ID.
     * 
     * @param playerID player unique identifier
     * @param name     of the player you want to create
     * @param balance  of the player at the beginning of the game
     * @param position of the player at the beginning of the game
     * @param state    of the player at the beginning of the game
     * @param leave    if the player has a collectable card that allows him to leave
     *                 prison for free
     */
    public PlayerImpl(final int playerID, final String name, final Double balance, final Integer position,
            final States state, final boolean leave) {
        super();
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        this.position = position;
        this.state = state;
        this.prisonCard = leave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getID() {
        return playerID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getBalance() {
        return balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBalance(final Double balance) {
        this.balance = balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public States getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(final States state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBalance(final Double value) {
        this.balance = this.balance + value;
        if (this.balance <= 0) {
            this.state = States.BROKE;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPrisonCard() {
        return this.prisonCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrisonCard(final boolean leaveForFree) {
        this.prisonCard = leaveForFree;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + playerID;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final PlayerImpl other = (PlayerImpl) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return playerID == other.playerID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PlayerImpl [playerID=" + this.playerID + ", name=" + this.name + ", balance=" + this.balance
                + ", position=" + this.position + ", state=" + this.state + ", prisonCard=" + this.prisonCard + "]";
    }

}
