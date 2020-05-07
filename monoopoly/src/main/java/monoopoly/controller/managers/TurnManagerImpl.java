package monoopoly.controller.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.player.Player;

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
		for (PlayerManager pM: this.playersList) {
			if (pM.getPlayerManagerID() != this.currentPlayerID) {
				return true;
			}
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
