package monoopoly.game_engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import monoopoly.controller.player.manager.PlayerManager;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Tile;
import monoopoly.model.item.TileImpl;
import monoopoly.utilities.States;

public class Test {

	public static void main(String[] args) {
		Map<Integer, String> name = new HashMap<>();
		Map<Integer, Double> balance = new HashMap<>(); 

		Map<Integer, Integer> position = new HashMap<>(); 

		Map<Integer, monoopoly.utilities.States> state = new HashMap<>();
		
		name.put(0, "aiman");
		name.put(1, "luca");
		name.put(2, "lucia");
		name.put(3, "mariaelena");
		name.put(4, "gianni");
		name.put(5, "jonny");
		balance.put(0, 32.2);
		balance.put(1, 23.4);
		balance.put(2, 2134.5);
		balance.put(3, 234.5);
		balance.put(4, 3254.0);
		balance.put(5, 324.0);
		position.put(0, 0);
		position.put(1, 1);
		position.put(2, 2);
		position.put(3, 3);
		position.put(4, 4);
		position.put(5, 5);
		state.put(0, States.IN_GAME);
		state.put(1, States.IN_GAME);
		state.put(2, States.IN_GAME);
		state.put(3, States.IN_GAME);
		state.put(4, States.IN_GAME);
		state.put(5, States.IN_GAME);
		GameEngine gameEngine = new GameEngineImpl(name, balance);
		gameEngine.createPlayers();
		for (PlayerManager pM: gameEngine.playersList()) {
			System.out.println(pM.getPlayer().getID() + " " + 
															pM.getPlayer().getName() + " " +
															pM.getPlayer().getPosition() + " " + 
															pM.getPlayer().getBalance() + " " +
															pM.getPlayer().getState());
		}
		for (PlayerManager pM: gameEngine.playersList()) {
			
		}
		System.out.println(gameEngine.getGameWinner());
		/*System.out.println(gameEngine.getPosition(7));
		System.out.println(gameEngine.currentPlayer().getPlayerManagerID() + 
						   gameEngine.getName(gameEngine.currentPlayer().getPlayerManagerID()));
		gameEngine.passPlayer();
		System.out.println(gameEngine.currentPlayer().getPlayerManagerID() +
							gameEngine.getName(gameEngine.currentPlayer().getPlayerManagerID()));
		
		gameEngine.passPlayer();
		System.out.println(gameEngine.currentPlayer().getPlayerManagerID() + 
							gameEngine.getName(gameEngine.currentPlayer().getPlayerManagerID()));
		gameEngine.passPlayer();
		gameEngine.passPlayer();
		gameEngine.passPlayer();
		System.out.println(gameEngine.currentPlayer().getPlayerManagerID() + 
				gameEngine.getName(gameEngine.currentPlayer().getPlayerManagerID()));
		gameEngine.passPlayer();
		System.out.println(gameEngine.currentPlayer().getPlayerManagerID() + 
				gameEngine.getName(gameEngine.currentPlayer().getPlayerManagerID()));*/

	}

}
