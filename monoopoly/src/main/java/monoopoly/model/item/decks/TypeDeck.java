/**
 * 
 */
package monoopoly.model.item.decks;

import java.util.Map;
import java.util.function.BiFunction;

import monoopoly.model.item.Tile.Category;

/**
 *
 */
public interface TypeDeck {
	
	void draw();

	Integer getNumberCard();

	String getDescription();

	boolean hasMoneyEffect();

	boolean hasStatusEffect();

	boolean hasMovementEffect();

	boolean hasPropertyEffect();

	Double getPlayersToOthers();

	Double getAllToBank();

	Double getPlayerToBank();

	Double getValueHouseToBank();

	Double getValueHotelToBank();

	Double getAllToBankPercentage();

	boolean getMakeTheAvaragePlayersBalance();

	boolean goToJail();

	boolean exitFromJail();

	boolean isMaintainable();

	Integer getStepsToDo();

	Integer getTilePositionToGo();

	Category getTileCategoryToReach();

	BiFunction<Integer, Category, Integer> getTileRetriver(Map<Integer, Category> mapOfCategory);

	boolean isGenerateRandomSteps();

	boolean isMoveToApplyToPlayer();

	boolean isMoveToApplyToOthers();

}
