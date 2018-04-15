package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.Font;
import javax.swing.JMenuItem;

/*
 * This view class is the general menu page, with options to select the category to view and update/add/remove an item from the menu
 */
public class MenuPage extends JFrame {
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JComboBox<String> oldCategoryComboBox;
	private JComboBox<String> newCategoryComboBox;
	private String error;
	private JTextField nameTextField2;
	private JTextField priceTextField2;
	private JButton btnUpdateItem;
	private JLabel lblName;
	
	private JButton btnAppetizer;
	private JButton btnMain;
	private JButton btnDessert;
	private JButton btnAlcoholicBeverage;
	private JButton btnNonAlcoholicBeverage;
	
	private JLabel lblPrice;
	private JLabel lblCategory;
	private JLabel arrow1label;
	private JLabel arrow2label;
	private JLabel arrow3label;
	private JButton btnAddItem;
	private JButton btnRemoveItem;
	private int width = 453;
	private int height = 394;
	private int messageHeight = 20;
	private JButton btnHelp;
	
	MenuPage() {
		setTitle("Menu");
		getContentPane().setLayout(null);
		setSize(width,height);
		setVisible(true);

		btnAppetizer = new JButton("Appetizer");
		btnAppetizer.setToolTipText("Displays the appetizer items");
		btnAppetizer.setBounds(144, 13, 144, 25);
		getContentPane().add(btnAppetizer);

		btnMain = new JButton("Main");
		btnMain.setToolTipText("Displays the main items");
		btnMain.setBounds(144, 51, 144, 25);
		getContentPane().add(btnMain);

		btnDessert = new JButton("Dessert");
		btnDessert.setToolTipText("Displays the dessert items");
		btnDessert.setBounds(144, 89, 144, 25);
		getContentPane().add(btnDessert);

		btnAlcoholicBeverage = new JButton("Alcoholic Beverage");
		btnAlcoholicBeverage.setToolTipText("Displays the alcoholic beverages");
		btnAlcoholicBeverage.setBounds(144, 127, 144, 25);
		getContentPane().add(btnAlcoholicBeverage);

		

		btnNonAlcoholicBeverage = new JButton("Non Alcoholic Beverage");
		btnNonAlcoholicBeverage.setToolTipText("Displays the non acoholic beverages");
		btnNonAlcoholicBeverage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNonAlcoholicBeverage.setBounds(144, 165, 144, 25);
		getContentPane().add(btnNonAlcoholicBeverage);

		lblName = new JLabel("Name");
		lblName.setBounds(12, 227, 39, 16);
		getContentPane().add(lblName);

		nameTextField = new JTextField();
		nameTextField.setToolTipText("Input the name of the item to add/remove/update");
		nameTextField.setBounds(68, 224, 140, 22);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		lblPrice = new JLabel("Price");
		lblPrice.setBounds(12, 285, 39, 16);
		getContentPane().add(lblPrice);

		lblCategory = new JLabel("Category");
		lblCategory.setBounds(12, 256, 56, 16);
		getContentPane().add(lblCategory);

		priceTextField = new JTextField();
		priceTextField.setToolTipText("Input the price of the item to add/update");
		priceTextField.setBounds(68, 282, 140, 22);
		getContentPane().add(priceTextField);
		priceTextField.setColumns(10);

		btnAddItem = new JButton("Add Item");
		btnAddItem.setToolTipText("To add a new menu item, input its NAME,  CATEGORY and PRICE (the second Name/Price fields blank)");
		btnAddItem.setBounds(42, 309, 109, 25);
		getContentPane().add(btnAddItem);

		oldCategoryComboBox = new JComboBox<String>();
		oldCategoryComboBox.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		oldCategoryComboBox.setBounds(68, 253, 140, 22);
		getContentPane().add(oldCategoryComboBox);

		btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.setToolTipText("To remove a new menu item, input its NAME,  CATEGORY (the second Name/Price fields and category will not be used)");
		btnRemoveItem.setBounds(179, 309, 109, 25);
		getContentPane().add(btnRemoveItem);
		oldCategoryComboBox.addItem("");
		oldCategoryComboBox.addItem("Appetizer");
		oldCategoryComboBox.addItem("Main");
		oldCategoryComboBox.addItem("Dessert");
		oldCategoryComboBox.addItem("Alcoholic Beverage");
		oldCategoryComboBox.addItem("Non Alcoholic Beverage");
		oldCategoryComboBox.setToolTipText("Item category of the item to add/remove");
		
		nameTextField2 = new JTextField();
		nameTextField2.setToolTipText("new name to update to...");
		nameTextField2.setBounds(253, 224, 140, 22);
		getContentPane().add(nameTextField2);
		nameTextField2.setColumns(10);
		
		arrow1label = new JLabel("\u21D2");
		arrow1label.setLabelFor(nameTextField2);
		arrow1label.setBounds(224, 227, 12, 16);
		getContentPane().add(arrow1label);
		
		arrow2label = new JLabel("\u21D2");
		arrow2label.setBounds(224, 285, 12, 16);
		getContentPane().add(arrow2label);
		
		arrow3label = new JLabel("\u21D2");
		arrow3label.setBounds(224, 256, 12, 16);
		getContentPane().add(arrow3label);
		
		priceTextField2 = new JTextField();
		priceTextField2.setToolTipText("new price to update to...");
		priceTextField2.setColumns(10);
		priceTextField2.setBounds(253, 282, 140, 22);
		getContentPane().add(priceTextField2);
		
		btnUpdateItem = new JButton("Update Item");
		btnUpdateItem.setToolTipText("Can update name by inputting a NewNAME;     \r\nCan update category by inputting CurrentCategory and NewCategory;     \r\nCan update price by inputting currentPrice and newPrice;     \r\n\r\nOr, all 3 at once.");
		btnUpdateItem.setBounds(314, 309, 109, 25);
		getContentPane().add(btnUpdateItem);
		
		newCategoryComboBox = new JComboBox<String>();
		newCategoryComboBox.setToolTipText("New item category of the item to update to...");
		newCategoryComboBox.setFont(new Font("Arial Narrow", Font.BOLD, 13));
		newCategoryComboBox.setBounds(253, 253, 140, 22);
		getContentPane().add(newCategoryComboBox);
		
		JButton btnDiscontinuedItems = new JButton("Discontinued");
		btnDiscontinuedItems.setToolTipText("Displays the items that have been removed from the menu with most recent (2) prices");
		btnDiscontinuedItems.setFont(new Font("Arial Narrow", Font.PLAIN, 9));
		btnDiscontinuedItems.setBounds(350, 0, 85, 16);
		getContentPane().add(btnDiscontinuedItems);
		
		btnHelp = new JButton("HELP");
		btnHelp.setFont(new Font("Arial Narrow", Font.BOLD, 10));
		btnHelp.setBounds(0, 0, 68, 16);
		getContentPane().add(btnHelp);
		newCategoryComboBox.addItem("");
		newCategoryComboBox.addItem("Appetizer");
		newCategoryComboBox.addItem("Main");
		newCategoryComboBox.addItem("Dessert");
		newCategoryComboBox.addItem("Alcoholic Beverage");
		newCategoryComboBox.addItem("Non Alcoholic Beverage");
		
		btnHelp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHelpActionPerformed(evt);
			}

			private void btnHelpActionPerformed(ActionEvent evt) {
				JFrame helpFrame = new JFrame();
				helpFrame.add(new JLabel(new ImageIcon("./image/menuHelp.png")));
				helpFrame.pack();
				helpFrame.setVisible(true);
				helpFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				helpFrame.setTitle("Help");
			}
		});
		

		btnAppetizer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAppetizerActionPerformed(evt);
			}

			private void btnAppetizerActionPerformed(ActionEvent evt) {
				new MenuDisplay("Appetizer");

			}
		});

		btnMain.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMainActionPerformed(evt);
			}

			private void btnMainActionPerformed(ActionEvent evt) {
				new MenuDisplay("Main");

			}
		});

		btnDessert.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDessertActionPerformed(evt);
			}

			private void btnDessertActionPerformed(ActionEvent evt) {
				new MenuDisplay("Dessert");

			}
		});

		btnAlcoholicBeverage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAlcoholicBeverageActionPerformed(evt);
			}

			private void btnAlcoholicBeverageActionPerformed(ActionEvent evt) {
				new MenuDisplay("Alcoholic Beverage");

			}
		});

		btnNonAlcoholicBeverage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNonAlcoholicBeverageActionPerformed(evt);
			}

			private void btnNonAlcoholicBeverageActionPerformed(ActionEvent evt) {
				new MenuDisplay("Non Alcoholic Beverage");

			}
		});
		
		btnDiscontinuedItems.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDiscontinuedItemsActionPerformed(evt);
			}

			private void btnDiscontinuedItemsActionPerformed(ActionEvent evt) {
				new DiscontinuedMenuDisplay();

			}
		});

		btnAddItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddItemActionPerformed(evt);
			}

		});

		btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnRemoveItemActionPerformed(evt);
			}
		});
		
		btnUpdateItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnUpdateItemActionPerformed(evt);
			}

		});
		

	}

	protected void btnAddItemActionPerformed(ActionEvent evt) {
		try {
			String selected = this.oldCategoryComboBox.getItemAt(oldCategoryComboBox.getSelectedIndex());
			RestoAppController.addMenuItem(nameTextField.getText(), selected, priceTextField.getText());
			String message = "Successfully added " + nameTextField.getText() + " as a " + selected;
			JDialog successMessage = new JDialog(this, message, false);
			successMessage.setSize(width, messageHeight);
			successMessage.setLocationRelativeTo(this);
			successMessage.setVisible(true);
			clearFields();

		} catch (InvalidInputException e) {
			error = e.getMessage();
			JDialog er = new JDialog(this, error, false);
			er.setSize(453, messageHeight);
			er.setLocationRelativeTo(this);
			er.setVisible(true);
		}

		
	}

	protected void btnRemoveItemActionPerformed(ActionEvent evt) {
		String selected = this.oldCategoryComboBox.getItemAt(oldCategoryComboBox.getSelectedIndex());
		try {
			RestoAppController.removeMenuItem(nameTextField.getText(), selected);
			String message = "Successfully removed " + nameTextField.getText()+ " from "+ selected;
			JDialog successMessage = new JDialog(this, message, false);
			successMessage.setSize(width, messageHeight);
			successMessage.setLocationRelativeTo(this);
			successMessage.setVisible(true);
			clearFields();
		} catch (InvalidInputException e) {
			error = e.getMessage();
			JDialog er = new JDialog(this, error, false);
			er.setSize(width, messageHeight);
			er.setLocationRelativeTo(this);
			er.setVisible(true);
		}

	}
	
	protected void btnUpdateItemActionPerformed(ActionEvent evt) {
		String oldCategory = this.oldCategoryComboBox.getItemAt(oldCategoryComboBox.getSelectedIndex());
		String newCategory = this.newCategoryComboBox.getItemAt(newCategoryComboBox.getSelectedIndex());
		String oldName = nameTextField.getText();
		String newName = nameTextField2.getText();
		String oldPrice = priceTextField.getText();
		String newPrice = priceTextField2.getText();
		//newCategory = newCategory.replaceAll("\\s", ""); // remove space in between Category types
		
		try {
			String message = RestoAppController.updateMenuItem(oldName, newName,oldCategory, newCategory, oldPrice, newPrice);
			//String message = "Successfully updated "+ oldName+ " to " + newName+ " ("+ selected+")"+": " + price;
			JDialog successMessage = new JDialog(this, message, false);
			successMessage.setSize(width, messageHeight);
			successMessage.setLocationRelativeTo(this);
			successMessage.setVisible(true);
			clearFields();
		} catch (Exception e) {
			error = e.getMessage();
			JDialog er = new JDialog(this, error, false);
			er.setSize(width, messageHeight);
			er.setLocationRelativeTo(this);
			er.setVisible(true);
		}
		
	}


	private void clearFields() {

		this.nameTextField.setText("");
		this.priceTextField.setText("");
		this.nameTextField2.setText("");
		this.newCategoryComboBox.setSelectedIndex(0);
		this.oldCategoryComboBox.setSelectedIndex(0);
		this.priceTextField2.setText("");
	}
}