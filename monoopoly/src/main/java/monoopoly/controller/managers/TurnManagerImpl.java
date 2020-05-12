package monoopoly.controller.managers;

import java.util.ArrayList;
import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.utilities.States;

public class TurnManagerImpl implements TurnManager {
		
	private int currentPlayerID;
	private List<PlayerManager> playersList = new ArrayList<>();
	private Integer round;
	
	public TurnManagerImpl() {
		this.round = -1;
	}
	
	@Override
	public PlayerManager nextTurn() {
		int flag = 0;
		for (PlayerManager pM: this.playersList) {
			if (flag == 1) {
				this.setCurrentPlayer(pM.getPlayerManagerID());
				return pM;
			}
			if (pM.getPlayerManagerID() == this.currentPlayerID) {
				flag = flag+1;
			}
		}
		this.setCurrentPlayer(0);
		return this.playersList.get(0);
	}

	@Override
	public Boolean areThereOtherPlayersInGame() {
	    int check = 0;
	    for (PlayerManager pM: this.playersList) {
	        if (pM.getPlayer().getState() != States.BROKE) {
	            check = check + 1;
	        }
	    }
	    if (check > 1) {
	        return true;
	    }
	    return false;
	}

	public Integer getRound() {
		return this.round;
	}
	
	public void setRound() {
		this.round = this.round + 1;
	}
	
	public Integer getCurrentPlayer() {
		return this.currentPlayerID;
	}
	
	public void setCurrentPlayer(Integer currentPlayer) {
		this.currentPlayerID = currentPlayer;
	}
	
	public List<PlayerManager> getPlayersList() {
		return playersList;
	}
	
	public void setCurrentID(final Integer ID) {
		this.currentPlayerID = ID;
	}


}
