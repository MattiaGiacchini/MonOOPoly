package monoopoly.engine;

import java.util.Map;

public interface StartGame {
	
	void createEngine();
	
	GameEngine openRecentGame(final int game);
	
	void setName(Map<Integer, String> name);
	
	void setBalance(Map<Integer, Double> balance);
	
	/*void setPosition(Map<Integer, Integer> position);
	
	void setState(Map<Integer, monoopoly.utilities.States> state);*/
	
	

}
