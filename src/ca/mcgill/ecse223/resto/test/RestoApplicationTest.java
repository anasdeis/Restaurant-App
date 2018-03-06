package ca.mcgill.ecse223.resto.test;


/*
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;

public class RestoApplicationTest {
	RestoApp resto;
	
	@Before
	public void setUp() {
		
		resto = RestoApplication.getRestoApp();
	}
	
	@Test
	public void getMenuItemTest() {
	Menu menu = resto.getMenu();
	MenuItem firstItem = menu.addMenuItem("KONG PAO Chicken");
	firstItem.setItemCategory(ItemCategory.Main);
	PricedMenuItem priceOfFirstItem = new PricedMenuItem(15,resto,firstItem);
	firstItem.setCurrentPricedMenuItem(priceOfFirstItem);
	
	MenuItem secondItem = menu.addMenuItem("Seven Up");
	firstItem.setItemCategory(ItemCategory.NonAlcoholicBeverage);
	PricedMenuItem priceOfSecondItem = new PricedMenuItem(3,resto,secondItem);
	secondItem.setCurrentPricedMenuItem(priceOfSecondItem);
	
	try {
		ArrayList<MenuItem> itemList1 = RestoAppController.getMenuItem(ItemCategory.Main);
		assertEquals(itemList1.size(),1);
		assertEquals(itemList1.get(0).getName(),"KONG BAO Chicken");
		RestoApplication.save();
	} catch (InvalidInputException e) {
		
	}
	
	try {
		ArrayList<MenuItem> itemList2 = RestoAppController.getMenuItem(ItemCategory.NonAlcoholicBeverage);
		assertEquals(itemList2.size(),1);
		assertEquals(itemList2.get(0).getName(),"Seven Up");
		RestoApplication.save();
	} catch (InvalidInputException e) {
		
	}
	try {
		ArrayList<MenuItem> itemList3 = RestoAppController.getMenuItem(null);	
	} catch (InvalidInputException e) {
		assertEquals(e.getMessage(),"Item category is null");
	}
	}
	
}
*/