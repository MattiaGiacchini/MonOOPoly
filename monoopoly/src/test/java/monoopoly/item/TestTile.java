package monoopoly.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;

import monoopoly.model.item.Property;
import monoopoly.model.item.Purchasable;
import monoopoly.model.item.Table;
import monoopoly.model.item.TableImpl;
import monoopoly.model.item.Tile;
import monoopoly.model.item.TileImpl;

/**
 *  in this class there are the test for all type of tile.
 */
public class TestTile {

    private static final int A_28 = 28;
    private static final int A_12 = 12;
    private static final double A_400_0 = 400.0;
    private static final int A_25 = 25;
    private static final double A_500_0 = 500.0;
    private static final double A_250_0 = 250.0;
    private static final double A_160_0 = 160.0;
    private static final double A_30_0 = 30.0;
    private static final double A_60_0 = 60.0;
    private static final double A_40_0 = 40.0;
    private static final double A_50_0 = 50.0;
    private static final double A_20_0 = 20.0;
    private static final int DICE = 5;
    private static final double DOUBLE_SALES_VALUE = 300.0;
    private static final double SALES_VALUE_BASE = 150.0;
    private static final double BASE_STATION_LEASE = 25.0;
    private static final int STATION_STEP = 10;
    private static final int FIRST_STATION = DICE;
    private static final double BASE_SALES_STATION = 200.0;
    private static final double DOUBLE_ZERO = 0.0;
    private static final int STATION_4_POSITION = 35;
    private static final int GO_TO_JAIL_POSITION = 30;
    private static final int FREE_PARCKING_POSITION = 20;
    private static final int STATION_3_POSITION = A_25;
    private static final int STATION_2_POSITION = 15;
    private static final int JAIL_POSITION = 10;
    private static final int STATION_POSITION = DICE;
    private static final int START_POSITION = 0;

    /**
     * test on wrong composition.
     */
    @Test(expected = IllegalStateException.class)
    public void wrongSequenceBuild() {
        new TileImpl.Builder()
        .build();
    }

    /**
     * creation test Tile.
     */
    @Test
    public void creationAndGetterCheck() {
        final String name = "test";
        final Tile.Category category = Tile.Category.GREEN;
        final boolean purchasable = true;
        final boolean deck = false;
        final boolean buildable = true;

        final Tile tile;
        tile = new TileImpl.Builder()
                .name(name)
                .category(category)
                .deck(deck)
                .purchasable(purchasable)
                .buildable(buildable)
                .build();

        assertEquals(tile.getName(), name);
        assertEquals(tile.getCategory(), category);
        assertTrue(tile.isBuildable());
        assertFalse(tile.isDeck());
        assertTrue(tile.isPurchasable());

    }

    /**
     * this test verify a little portion of table.
     */
    @Test
    public void creationTable() {
        final Table table = new TableImpl();
        assertEquals(table.getTile(START_POSITION).getCategory(),
                Tile.Category.START);
        assertEquals(table.getTile(STATION_POSITION).getCategory(),
                Tile.Category.STATION);
        assertEquals(table.getTile(JAIL_POSITION).getCategory(),
                Tile.Category.JAIL);
        assertEquals(table.getTile(STATION_2_POSITION).getCategory(),
                Tile.Category.STATION);
        assertEquals(table.getTile(FREE_PARCKING_POSITION).getCategory(),
                Tile.Category.FREE_PARKING);
        assertEquals(table.getTile(STATION_3_POSITION).getCategory(),
                Tile.Category.STATION);
        assertEquals(table.getTile(GO_TO_JAIL_POSITION).getCategory(),
                Tile.Category.GO_TO_JAIL);
        assertEquals(table.getTile(STATION_4_POSITION).getCategory(),
                Tile.Category.STATION);
    }

    /**
     * this test verify the function of Station.
     */
    @Test
    public void stationTest() {
        final Table table = new TableImpl();
        Purchasable purchBase, purch;
        purchBase = (Purchasable) table.getTile(STATION_POSITION);

        assertEquals(purchBase.getSalesValue(), BASE_SALES_STATION);
        assertEquals(purchBase.getLeaseValue(), DOUBLE_ZERO);

        for (int i = 0; i < 4; i++) {
            purch = (Purchasable) table.getTile(
                    FIRST_STATION + (STATION_STEP * i));
            purch.setOwner(Optional.of(1));
            assertEquals(purchBase.getLeaseValue(),
                    BASE_STATION_LEASE * Math.pow(2.0, i));
        }

        purch = (Purchasable) table.getTile(A_25);
        purch.setOwner(Optional.empty());
        assertEquals(purchBase.getLeaseValue(),   100.0);

        table.setNewQuotationToSpecificPurchasableCategory(
                Tile.Category.STATION, 2.0);
        assertEquals(purchBase.getLeaseValue(),   BASE_SALES_STATION);
        assertEquals(purchBase.getSalesValue(),   A_400_0);
    }

    /**
     * this test verify the function of society tile.
     */
    @Test
    public void societyTest() {
        final Table table = new TableImpl();
        final Purchasable purch1 = (Purchasable) table.getTile(A_12);
        final Purchasable purch2 = (Purchasable) table.getTile(A_28);

        assertEquals(purch1.getSalesValue(), SALES_VALUE_BASE);
        assertEquals(purch2.getSalesValue(), SALES_VALUE_BASE);
        table.setNewQuotationToSpecificPurchasableCategory(
                Tile.Category.SOCIETY, 2.0);
        assertEquals(purch1.getSalesValue(), DOUBLE_SALES_VALUE);
        assertEquals(purch2.getSalesValue(), DOUBLE_SALES_VALUE);
        table.setNewQuotationToSpecificPurchasableCategory(
                Tile.Category.SOCIETY, 1.0);
        assertEquals(purch1.getLeaseValue(), 0.0);
        assertEquals(purch2.getLeaseValue(), 0.0);
        table.notifyDices(DICE);
        assertEquals(purch1.getLeaseValue(), 0.0);
        assertEquals(purch2.getLeaseValue(), 0.0);
        purch1.setOwner(Optional.of(1));
        assertEquals(purch1.getLeaseValue(), A_20_0);
        purch2.setOwner(Optional.of(1));
        assertEquals(purch1.getLeaseValue(), A_50_0);
        table.setNewQuotationToSpecificPurchasableCategory(
                Tile.Category.SOCIETY, 2.0);
        assertEquals(purch1.getLeaseValue(), 100.0);
        purch1.setOwner(Optional.of(2));
        assertEquals(purch1.getLeaseValue(), A_40_0);
    }

    /**
     * this test verify the Property Tile.
     */
    @Test
    public void propertyTest() {
        final Table table = new TableImpl();
        final Property prop1 = (Property) table.getTile(1);
        final Property prop2 = (Property) table.getTile(3);

        assertEquals(prop1.getSalesValue(),  A_60_0);
        assertEquals(prop2.getSalesValue(),  A_60_0);
        assertEquals(prop1.getLeaseValue(),   0.0);
        assertEquals(prop2.getLeaseValue(),   0.0);
        prop1.setOwner(Optional.of(1));
        assertEquals(prop1.getLeaseValue(),   2.0);
        prop2.setOwner(Optional.of(1));
        assertEquals(prop1.getLeaseValue(),   4.0);
        prop1.buildOn();
        assertEquals(prop1.getLeaseValue(),  10.0);
        prop1.buildOn();
        assertEquals(prop1.getLeaseValue(),  A_30_0);
        prop1.buildOn();
        assertEquals(prop1.getLeaseValue(),  90.0);
        prop1.buildOn();
        assertEquals(prop1.getLeaseValue(), A_160_0);
        prop1.buildOn();
        assertEquals(prop1.getLeaseValue(), A_250_0);
        prop2.setOwner(Optional.of(1));
        assertEquals(prop2.getLeaseValue(),   8.0);
        assertEquals(prop1.getOwner().get(),  1);
        prop1.setQuotation(2.0);
        assertEquals(prop1.getLeaseValue(), A_500_0);
        prop1.setOwner(Optional.empty());
        assertEquals(prop1.getLeaseValue(),   0.0);
    }

    /**
     *   this test verify the function of the wrong request to build.
     */
    @Test(expected = IllegalStateException.class)
    public void propertyTestingBuild() {
        final Table table = new TableImpl();
        final Property prop1 = (Property) table.getTile(1);
        prop1.buildOn();
    }

    /**
     *  this test verify the function of special getter inside the table.
     */
    @Test()
    public void specialGetterTableTest() {
        final Table table = new TableImpl();
        table.getFilteredTiles(Purchasable.class, x -> x.isPurchasable());
    }

    /**
     * this test verify the order of each tile inside the table.
     */
    @Test()
    public void positionTileTableTest() {
        final Table table = new TableImpl();
        for (Integer i = 0; i < table.getTableSize(); i++) {
            assertEquals(table.getTilePosition(table.getTile(i)), i);
        }
    }
}
