package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = GildedRose.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void testNormalItemDegradesTwiceAsFastAfterSellIn() {
	    GildedRose inn = new GildedRose();
	    inn.setItem(new Item("+5 Dexterity Vest", 0, 20));
	    inn.oneDay();
	    
	    List<Item> items = GildedRose.getItems();
	    assertEquals("Normal item quality should decrease by 2 after SellIn", 18, items.get(0).getQuality());
	}

	@Test
	public void testAgedBrieIncreasesInQualityAfterSellIn() {
		GildedRose inn = new GildedRose();
	    inn.setItem(new Item("Aged Brie", -1, 0));
	    inn.oneDay();
	    
	    List<Item> items = GildedRose.getItems();
	    int quality = items.get(0).getQuality();
	    
	    assertEquals("Aged Brie quality should increase by 1", 2, quality);
	    assertEquals("SellIn should decrease by 1", -2, items.get(0).getSellIn());
	}
	
	@Test
	public void testSulfurasNeverChanges() {
	    GildedRose inn = new GildedRose();
	    inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
	    inn.oneDay();
	    
	    List<Item> items = GildedRose.getItems();
	    assertEquals("Sulfuras quality should remain constant", 80, items.get(0).getQuality());
	    assertEquals("Sulfuras sellIn should remain constant", 0, items.get(0).getSellIn());
	}
	
	@Test
	public void testBackstagePassesIncreaseBy3WhenSellIn5DaysOrLess() {
	    GildedRose inn = new GildedRose();
	    inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
	    inn.oneDay();
	    
	    List<Item> items = GildedRose.getItems();
	    assertEquals("Backstage passes quality should increase by 3", 23, items.get(0).getQuality());
	}
	
	@Test
	public void testBackstagePassesDropToZeroAfterConcert() {
	    GildedRose inn = new GildedRose();
	    inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
	    inn.oneDay();
	    
	    List<Item> items = GildedRose.getItems();
	    assertEquals("Backstage passes quality should drop to 0 after concert", 0, items.get(0).getQuality());
	}
	
	@Test
	public void testMainMethod() {
	    GildedRose.main(new String[]{});
	    List<Item> items = GildedRose.getItems();

	    assertEquals("+5 Dexterity Vest", items.get(0).getName());
	    assertEquals("Aged Brie", items.get(1).getName());
	    assertEquals("Elixir of the Mongoose", items.get(2).getName());
	    assertEquals("Sulfuras, Hand of Ragnaros", items.get(3).getName());
	    assertEquals("Backstage passes to a TAFKAL80ETC concert", items.get(4).getName());
	    assertEquals("Conjured Mana Cake", items.get(5).getName());
	}
	
	@Test
	public void testUpdateQualityWithEmptyList() {
	    GildedRose app = new GildedRose();
	    GildedRose.updateQuality();
	    assertTrue(GildedRose.getItems().isEmpty());
	}
	
	@Test
	public void testUpdateQualityWithOneItem() {
	    GildedRose app = new GildedRose();
	    app.setItem(new Item("Aged Brie", 10, 20));
	    GildedRose.updateQuality();
	    assertEquals(21, GildedRose.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateQualityWithMultipleItems() {
	    GildedRose app = new GildedRose();
	    app.setItem(new Item("Aged Brie", 10, 50));
	    app.setItem(new Item("Aged Brie", 10, 50));
	    app.setItem(new Item("Sulfuras, Hand of Ragnaros", -1, -1));
	    GildedRose.updateQuality();
	    
	    assertEquals(50, GildedRose.getItems().get(0).getQuality());
	    assertEquals(50, GildedRose.getItems().get(1).getQuality());
	    assertEquals("Sulfuras, Hand of Ragnaros", GildedRose.getItems().get(2).getName());
	}
	
	@Test
	public void testMainMethodAndUpdateQualityWithNewItems() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;
	    System.setOut(new PrintStream(outputStream));

	    try {
	        // Call the main method
	        GildedRose.main(new String[]{});

	        // Assert the printed output contains "OMGHAI!"
	        String output = outputStream.toString();
	        assertTrue("Main method should print 'OMGHAI!'", output.contains("OMGHAI!"));
	    } finally {
	        // Restore the original System.out
	        System.setOut(originalOut);
	    }
	    List<Item> items = GildedRose.getItems();

	    assertEquals("+5 Dexterity Vest", items.get(0).getName());
	    assertEquals("Aged Brie", items.get(1).getName());
	    assertEquals("Elixir of the Mongoose", items.get(2).getName());
	    assertEquals("Sulfuras, Hand of Ragnaros", items.get(3).getName());
	    assertEquals("Backstage passes to a TAFKAL80ETC concert", items.get(4).getName());
	    assertEquals("Conjured Mana Cake", items.get(5).getName());

	    GildedRose app = new GildedRose();
	    app.setItem(new Item("+5 Dexterity Vest", 10, 30));
	    app.setItem(new Item("Elixir of the Mongoose", 5, 10));

	    assertEquals(30, GildedRose.getItems().get(0).getQuality());	    
	    assertEquals("Elixir of the Mongoose", GildedRose.getItems().get(1).getName());
	    
	    GildedRose.updateQuality();
	    assertEquals(9, GildedRose.getItems().get(1).getQuality());
	    assertEquals(1, items.get(1).getQuality());
	}
	
	@Test
	public void testBackstagePassSellIn () {
		GildedRose app = new GildedRose();
	    app.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10));
	    app.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10));
	    GildedRose.updateQuality();
	    
	    assertEquals(11, GildedRose.getItems().get(0).getQuality());
	    assertEquals(12, GildedRose.getItems().get(1).getQuality());
	}
}
