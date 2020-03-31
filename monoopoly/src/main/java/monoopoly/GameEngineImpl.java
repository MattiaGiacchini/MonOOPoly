package monoopoly;

import java.util.*;

public class GameEngineImpl implements GameEngine {
	
	private Map<Integer, String> players = new HashMap<>();

	public GameEngineImpl(final Map<Integer, String> players) {
		this.players = players;
	}
}
