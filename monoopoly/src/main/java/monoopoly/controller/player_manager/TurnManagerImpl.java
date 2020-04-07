package monoopoly.controller.player_manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import monoopoly.model.player.Player;

public class TurnManagerImpl implements TurnManager {
		
	private int currentPlayerID;
	
	private List<PlayerManager> playersList = new ArrayList<>();

	public TurnManagerImpl(final int firstPlayer) {
		this.currentPlayerID = firstPlayer;
	}	

	@Override
	public PlayerManager nextTurn(final List<PlayerManager> playersList) {
		int flag = 0;
		Iterator it = playersList.iterator();
		while (it.hasNext()) {
			if (((PlayerManager) it.next()).getPlayerManagerID() == this.currentPlayerID) {
				flag = flag + 1;
			}
			if (flag == 1) {
				this.setCurrentPlayer(((PlayerManager) it.next()).getPlayerManagerID());
				return (PlayerManager)it.next(); 
			}
		}
		this.setCurrentPlayer(0);
		return playersList.get(0);
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
