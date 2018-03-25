package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class MenuPage extends JFrame {
    private JTextField newItemTextField;
    private JTextField priceTextField;
    private JComboBox<String> categoryComboBox;
    private String error;

    MenuPage() {
        setTitle("Menu");
        getContentPane().setLayout(null);
        setSize(453, 394);

        JButton btnAppetizer = new JButton("Appetizer");
        btnAppetizer.setBounds(23, 96, 97, 25);
        getContentPane().add(btnAppetizer);

        JButton btnMain = new JButton("Main");
        btnMain.setBounds(179, 96, 65, 25);
        getContentPane().add(btnMain);

        JButton btnDessert = new JButton("Dessert");
        btnDessert.setBounds(296, 96, 97, 25);
        getContentPane().add(btnDessert);

        JButton btnAlcoholicBeverage = new JButton("Alcoholic Beverage");
        btnAlcoholicBeverage.setBounds(42, 155, 144, 25);
        getContentPane().add(btnAlcoholicBeverage);


        setVisible(true);

        JButton btnNonAlcoholicBeverage = new JButton("Non Alcoholic Beverage");
        btnNonAlcoholicBeverage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnNonAlcoholicBeverage.setBounds(223, 155, 144, 25);
        getContentPane().add(btnNonAlcoholicBeverage);

        JLabel lblNewItem = new JLabel("New Item");
        lblNewItem.setBounds(23, 227, 56, 16);
        getContentPane().add(lblNewItem);

        newItemTextField = new JTextField();
        newItemTextField.setBounds(91, 224, 276, 22);
        getContentPane().add(newItemTextField);
        newItemTextField.setColumns(10);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(23, 285, 56, 16);
        getContentPane().add(lblPrice);

        JLabel lblCategory = new JLabel("Category");
        lblCategory.setBounds(23, 256, 56, 16);
        getContentPane().add(lblCategory);

        priceTextField = new JTextField();
        priceTextField.setBounds(91, 282, 276, 22);
        getContentPane().add(priceTextField);
        priceTextField.setColumns(10);

        JButton btnAddItem = new JButton("Add Item");
        btnAddItem.setBounds(157, 309, 97, 25);
        getContentPane().add(btnAddItem);

        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setBounds(91, 253, 276, 22);
        getContentPane().add(categoryComboBox);
        categoryComboBox.addItem("Appetizer");
        categoryComboBox.addItem("Main");
        categoryComboBox.addItem("Dessert");
        categoryComboBox.addItem("Alcoholic Beverage");
        categoryComboBox.addItem("Non Alcoholic Beverage");


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

        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }


        });

    }

    protected void btnAddItemActionPerformed(ActionEvent evt) {
        try {
            String selected = this.categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex());
            RestoAppController.addMenuItem(newItemTextField.getText(), selected, priceTextField.getText());
            String message = "Successfully added " + newItemTextField.getText() + " as a " + selected;
            JDialog successMessage = new JDialog(this, message, false);
            successMessage.setSize(500, 20);
            successMessage.setLocationRelativeTo(this);
            successMessage.setVisible(true);
        } catch (InvalidInputException e) {
            error = e.getMessage();
            JDialog er = new JDialog(this, error, false);
            er.setSize(325, 20);
            er.setLocationRelativeTo(this);
            er.setVisible(true);
        }

    }
}