package monoopoly.model.item;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import monoopoly.model.item.Tile.Category;
import monoopoly.model.item.TableFactory;

public class TableImpl implements Table, ObserverPurchasable {
	
	private static final int BEGIN_POSITION = 0;
	private static final double START_VALUE = 200.0;

	private Integer sumOfDicesThrownNotified;
	private Map<Integer, Tile> table;

	public TableImpl() {
		super();
		this.sumOfDicesThrownNotified = 0;
		this.table = new TableFactory().createTable(this);
	}
	
	@Override
	public void setNewQuotationToSpecificPurchasableCategory(Category category, double quotation) {
		this.table.entrySet().stream()
							 .filter(x->x.getValue().getCategory()==category)
							 .peek(x->{
								 if(!x.getValue().isPurchasable()) {
									throw new IllegalArgumentException("The Tile[" + x.getKey() + "] isn't Purchasable");
								 }	})
							 .map(x->(Purchasable)x.getValue())
							 .forEach(x->x.setQuotation(quotation));
	}

	@Override
	public Tile getTile(Integer position) {
		this.inputCheckIntegerType(position);
		this.indexCheckTableBounds(position);
		return this.table.get(position);
	}

	@Override
	public Set<Purchasable> getPurchasableTilesforSpecificPlayer(Integer idPlayer) {
		this.inputCheckIntegerType(idPlayer);
		return this.table.entrySet().stream().filter(x->x.getValue().isPurchasable() == true)
											 .map(x->(Purchasable)x.getValue())
											 .filter(x->!x.getOwner().isEmpty() && x.getOwner().get() == idPlayer)
											 .collect(Collectors.toSet());
	}

	@Override
	public Integer getTableSize() {
		return this.table.size();
	}

	@Override
	public int getNotifiedDices() {
		return this.sumOfDicesThrownNotified;
	}

	@Override
	public double getValueToRetrieveFromStart() {
		return TableImpl.START_VALUE;
	}

	@Override
	public Integer getJailPosition() {
		for(Entry<Integer, Tile> elem : this.table.entrySet()) {
			if(elem.getValue().getCategory()==Tile.Category.JAIL) {
				return(elem.getKey());
			}
		}
		// TODO   trovare un errore adatto da tirare
		return null;
	}
	
	@Override
	public void notifyDices(Integer sum) {
		this.inputCheckIntegerType(sum);
		this.sumOfDicesThrownNotified = sum;
	}
	
	@Override
	public Set<Tile> getTilesforSpecificCategoty(Category category) {
		return this.table.entrySet().stream().filter(x->x.getValue().getCategory()==category)
											 .map(x->x.getValue())
											 .collect(Collectors.toSet());
	}

	private void inputCheckIntegerType(Object elem) {
		if (!(elem instanceof Integer)) {
			throw new IllegalArgumentException("The Parameter isn't an Integer Type");
		}
	}

	private void indexCheckTableBounds(Integer position) {
		if(position < TableImpl.BEGIN_POSITION || this.getTableSize() <= position) {
			throw new IndexOutOfBoundsException("The Index is out of Table's Bounds");
		}
	}

}
