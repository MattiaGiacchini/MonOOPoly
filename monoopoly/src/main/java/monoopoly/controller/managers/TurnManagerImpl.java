package monoopoly.controller.managers;

import java.util.ArrayList;
import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;

/**
 * Class used to handle turns, that implements TurnManager. 
 */
public final class TurnManagerImpl implements TurnManager {

    private int currentPlayerID;
    private final List<PlayerManager> playersList = new ArrayList<>();
    private Integer round;

    /**
     * TurnManagerImpl's constructor.
     */
    public TurnManagerImpl() {
        this.round = -1;
    }

    @Override
    public PlayerManager nextTurn() {
        int flag = 0;
        for (final PlayerManager pM: this.playersList) {
            if (flag == 1) {
                this.setCurrentPlayer(pM.getPlayerManagerID());
                return pM;
            }
            if (pM.getPlayerManagerID() == this.currentPlayerID) {
                flag = flag + 1;
            }
        }
        this.setCurrentPlayer(0);
        return this.playersList.get(0);
    }

    @Override
    public Boolean areThereOtherPlayersInGame() {
        int check = 0;
        for (final PlayerManager pM: this.playersList) {
            if (!pM.isBroken()) {
                check = check + 1;
            }
        }
        return check > 1;
    }

    @Override
    public Integer getRound() {
        return this.round;
    }

    @Override
    public void setRound() {
        this.round = this.round + 1;
    }

    @Override
    public Integer getCurrentPlayer() {
        return this.currentPlayerID;
    }

    private void setCurrentPlayer(final Integer currentPlayer) {
        this.currentPlayerID = currentPlayer;
    }

    @Override
    public List<PlayerManager> getPlayersList() {
        return playersList;
    }

    @Override
    public void setCurrentID(final Integer iD) {
        this.currentPlayerID = iD;
    }


}
