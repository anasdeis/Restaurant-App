package ca.mcgill.ecse223.resto.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;

/*
 *  This view class is the each individual item category menu (i.e. Main menu display)
 */
public class MenuDisplay extends JFrame {
	private String error;

	MenuDisplay(String category) {
		// setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ArrayList<MenuItem> menuItems = null;

		try {
			if (category.equals("Appetizer")) {
				menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Appetizer);
				setTitle("Appetizer");
			} else if (category.equals("Main")) {
				menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Main);
				setTitle("Main");
			} else if (category.equals("Dessert")) {
				menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Dessert);
				setTitle("Dessert");
			} else if (category.equals("Alcoholic Beverage")) {
				menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.AlcoholicBeverage);
				setTitle("Alc Bev");
			} else if (category.equals("Non Alcoholic Beverage")) {
				menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.NonAlcoholicBeverage);
				setTitle("NonAlc Bev");
			}
		} catch (InvalidInputException e) {
			error = e.getMessage();
			JDialog er = new JDialog(this, error, false);
			er.setSize(325, 20);
			er.setLocationRelativeTo(this);
			er.setVisible(true);
		}

		JList<Object> itemList;

		ArrayList<String> currentItems = new ArrayList<>();
	//	ArrayList <String> discontinuedItems = new ArrayList<>();
		for (int i = 0; i < menuItems.size(); i++) {
			String name = menuItems.get(i).getName();
			PricedMenuItem currentPMI = menuItems.get(i).getCurrentPricedMenuItem();

			if (currentPMI != null) {
				double price = currentPMI.getPrice();
				DecimalFormat df = new DecimalFormat("####0.00");
				currentItems.add(name + ": " + df.format(price));
			} else { // if the PriceMenuItem is null, this item is discontinued and is no longer on
						// the current menu
			//	discontinuedItems.add(name);
				//currentItems.add(name + "*");
			}

		}
		itemList = new JList<>(currentItems.toArray());
		JScrollPane menuScrollPane = new JScrollPane(itemList);

		getContentPane().add(menuScrollPane);

		pack();
		setSize(300, 200);
		setVisible(true);

	}

}