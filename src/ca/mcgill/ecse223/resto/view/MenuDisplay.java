package ca.mcgill.ecse223.resto.view;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class MenuDisplay extends JFrame{
	private String error;
	
	MenuDisplay() {
		//setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 600);
		setTitle("Menu");
		//getContentPane().setLayout(null);
		
		ArrayList<MenuItem> menuItems = null;
		try {
			menuItems = RestoAppController.getMenuItem(MenuItem.ItemCategory.Appetizer);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			JDialog er = new JDialog(this, error, false);
			er.setSize(325, 20);
			er.setLocationRelativeTo(this);
			er.setVisible(true);
		}
		
		JLabel lblAppetizer = new JLabel("Appetizer");
		lblAppetizer.setBounds(12, 13, 54, 32);
		this.getContentPane().add(lblAppetizer);
		
////		JPanel apptizerPanel = new JPanel();
////		apptizerPanel.setBounds(12, 58, 179, 494);
////		getContentPane().add(apptizerPanel);
//		
		JList<Object> appetizerList = new JList<>(menuItems.toArray());
		JScrollPane menuScrollPane = new JScrollPane(appetizerList);
		
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