package monoopoly.controller.player_manager;

import java.util.Iterator;
import java.util.List;

import monoopoly.model.player.Player;

public class TurnManagerImpl implements TurnManager {
	
	//private Integer firstPlayer;
	
	private Integer currentPlayer;

	public TurnManagerImpl(final int firstPlayer) {
		//this.firstPlayer = firstPlayer;
		this.currentPlayer = firstPlayer;
	}	

	@Override
	public PlayerManager nextTurn(final List<PlayerManager> playersList) {
		int flag = 0;
		Iterator it = playersList.iterator();
		while (it.hasNext()) {
			if (it.next().getPlayerManagerID() == this.currentPlayer) {
				flag = flag + 1;
			}
			if (flag == 1) {
				this.setCurrentPlayer(it.next().getPlayerManagerID());
				return (PlayerManager)it.next(); 
			}
		}
		this.setCurrentPlayer(0);
		return playersList.get(0);
	}

	@Override
	public Boolean areThereOtherPlayersInGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getNumberOfRound() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void setCurrentPlayer(Integer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


}
