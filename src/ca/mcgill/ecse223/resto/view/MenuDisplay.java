package ca.mcgill.ecse223.resto.view;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;


public class MenuDisplay extends JFrame {
    private String error;

    MenuDisplay(String category) {
        //setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 600);

        //getContentPane().setLayout(null);

        ArrayList<MenuItem> menuItems = null;
        //JLabel lbl = null;
        try {
            if (category.equals("Appetizer")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Appetizer);
                //lbl = new JLabel("Appetizer");
                setTitle("Appetizer");
            } else if (category.equals("Main")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Main);
                //lbl = new JLabel("Main");
                setTitle("Main");
            } else if (category.equals("Dessert")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Dessert);
                //lbl = new JLabel("Dessert");
                setTitle("Dessert");
            } else if (category.equals("Alcoholic Beverage")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.AlcoholicBeverage);
                //lbl = new JLabel("Alcoholic Beverage");
                setTitle("Alcoholic Beverage");
            } else if (category.equals("Non Alcoholic Beverage")) {
                menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.NonAlcoholicBeverage);
                //lbl = new JLabel("Non Alcoholic Beverage");
                setTitle("Non Alcoholic Beverage");
            }
        } catch (InvalidInputException e) {
            error = e.getMessage();
            JDialog er = new JDialog(this, error, false);
            er.setSize(325, 20);
            er.setLocationRelativeTo(this);
            er.setVisible(true);
        }


        //lbl.setBounds(12, 13, 54, 32);
        //this.getContentPane().add(lbl);

////		JPanel apptizerPanel = new JPanel();
////		apptizerPanel.setBounds(12, 58, 179, 494);
////		getContentPane().add(apptizerPanel);
//		
        //TODO Currently displays the "MenuItem" not the fields of it
        JList<Object> itemList = new JList<>(menuItems.toArray());
        JScrollPane menuScrollPane = new JScrollPane(itemList);

        getContentPane().add(menuScrollPane);


        //apptizerPanel.add(menuScrollPane);


        pack();
        setVisible(true);

    }

	
	/*
    private String error;

	public MenuDisplay() {
		initComponents();
	}

	private void initComponents() {
		menuPageFrame = new JFrame("Menu");
		menuPageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		menuPageFrame.setResizable(true);
		menuPageFrame.getContentPane().setLayout(null);
		
		JLabel lblAppetizer = new JLabel("Appetizer");
		lblAppetizer.setBounds(12, 13, 56, 16);
		menuPageFrame.getContentPane().add(lblAppetizer);
		
		JPanel appetizerPanel = new JPanel();
		appetizerPanel.setBounds(12, 30, 122, 306);
		menuPageFrame.getContentPane().add(appetizerPanel);
		
		ArrayList<MenuItem> menuItems = null;
		try {
			menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Appetizer);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			JDialog er = new JDialog(MenuDisplay.menuPageFrame, error, false);
			er.setSize(325, 20);
			er.setLocationRelativeTo(MenuDisplay.menuPageFrame);
			er.setVisible(true);
		}
//		JList<String> appetizerList = new JList<>(menuItems.toArray(new String[0]));
		JList<Object> appetizerList = new JList<>(menuItems.toArray());
		JScrollPane scrollPane = new JScrollPane(appetizerList);
		
		appetizerPanel.add(appetizerList);
		

		
		/*
		ItemCategory[] itemCategories = MenuItem.ItemCategory.values();
		for (int i = 0; i < itemCategories.length; i++) {
			try {
				ArrayList<MenuItem> menuItems = RestoAppController.getMenuItem(itemCategories[i]);
			} catch (InvalidInputException e) {
				error = e.getMessage();
				JDialog er = new JDialog(MenuDisplay.menuPageFrame, error, false);
				er.setSize(325, 20);
				er.setLocationRelativeTo(MenuDisplay.menuPageFrame);
				er.setVisible(true);
			}

			JList<MenuItem> displayItems = new JList();
		}
 	*/
    //}
}