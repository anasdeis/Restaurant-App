package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;


public class MenuDisplay extends JFrame {
    private String error;

    MenuDisplay(String category) {
        //setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 600);

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
                setTitle("Alcoholic Beverage");
            } else if (category.equals("Non Alcoholic Beverage")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.NonAlcoholicBeverage);
                setTitle("Non Alcoholic Beverage");
            }
        } catch (InvalidInputException e) {
            error = e.getMessage();
            JDialog er = new JDialog(this, error, false);
            er.setSize(325, 20);
            er.setLocationRelativeTo(this);
            er.setVisible(true);
        }

        JList<Object> itemList;
        
        ArrayList <String> items = new ArrayList<>();
        for (int i=0;i<menuItems.size();i++) {
        	String name = menuItems.get(i).getName();
        	PricedMenuItem currentPMI = menuItems.get(i).getCurrentPricedMenuItem();
        	double price = currentPMI.getPrice();
        	
        	items.add(name + ": " + price);
        	
        }
        itemList = new JList<>(items.toArray());
        JScrollPane menuScrollPane = new JScrollPane(itemList);

        getContentPane().add(menuScrollPane);

        pack();
        setVisible(true);

    }

	

}