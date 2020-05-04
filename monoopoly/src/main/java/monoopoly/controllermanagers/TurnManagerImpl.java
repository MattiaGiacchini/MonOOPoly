package monoopoly.controllermanagers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.player.Player;

public class TurnManagerImpl implements TurnManager {
		
	private int currentPlayerID;
	
	private List<PlayerManager> playersList = new ArrayList<>();

	public TurnManagerImpl(final int firstPlayer) {
		this.currentPlayerID = firstPlayer;
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

	@Override
	public Integer getNumberOfRound() {
		// TODO Auto-generated method stub
		return null;
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


}
