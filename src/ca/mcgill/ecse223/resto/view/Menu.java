package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame{
	
	Menu(){
		setTitle("Menu");
		getContentPane().setLayout(null);
		setSize(500, 400);
		
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
		
		
	}
}
