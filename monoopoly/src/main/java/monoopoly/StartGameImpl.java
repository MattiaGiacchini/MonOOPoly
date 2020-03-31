package monoopoly;

import java.util.*;

public class StartGameImpl implements StartGame {
	
	private Map<Integer, String> credentials = new HashMap<>(); 
	
	public StartGameImpl(final Map<Integer, String> credentials) {
		this.credentials = credentials;
	}

	@Override
	public GameEngine createEngine() {
		final GameEngine newGM = new GameEngineImpl(this.credentials);
		return newGM;
	}

	@Override
	public GameEngine openRecentGame(int game) {
		// TODO Auto-generated method stub
		return null;
	}

}
