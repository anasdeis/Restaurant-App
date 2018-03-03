package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -6463272516157863988L;

	// Error Message
	private JLabel errorMessage;
	
	private String error = null;

	//Create Table
	private JButton createTableButton;
	private JLabel numberOfSeatsLabel;
	private JLabel xLocationLabel;
	private JLabel yLocationLabel;
	private JLabel lengthLabel;
	private JLabel widthLabel;
	private JTextField numberOfSeatsTextField;
	private JTextField xLocationTextField;
	private JTextField yLocationTextField;
	private JTextField lengthTextField;
	private JTextField widthTextField;

	// Remove Table
	private JComboBox<String> tableList;
	private JLabel tablesLabel;
	private JButton removeTableButton;
	private Integer selectedTable = -1;
	private RestoAppDisplay displayApp;
	/** Creates new form RestoAppPage */
	public RestoAppPage() {
		initComponents();
		refreshData();
	}
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		// Error Message Elements
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// Create Driver Elements
		createTableButton = new JButton();
		numberOfSeatsLabel = new JLabel();
		xLocationLabel = new JLabel();
		yLocationLabel = new JLabel();
		lengthLabel = new JLabel();
		widthLabel = new JLabel();
		numberOfSeatsTextField = new JTextField();
		xLocationTextField = new JTextField();
		yLocationTextField = new JTextField();
		lengthTextField = new JTextField();
		widthTextField = new JTextField();
		
		createTableButton.setText("Create Table: ");
		numberOfSeatsLabel.setText("Number Of Seats: ");
		xLocationLabel.setText("X Location: ");
		yLocationLabel.setText("Y Location: ");
		lengthLabel.setText("Length: ");
		widthLabel.setText("Width: ");
		
		createTableButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				createTableButtonActionPerformed(evt);
			}
				
		});
		
		// Delete Table Elements
		tableList = new JComboBox<String>(new String[0]);
		tablesLabel = new JLabel();
		removeTableButton = new JButton();
		
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		tablesLabel.setText("Tables: ");
		removeTableButton.setText("Remove Table");
		
		// horizontal line elements
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();
		//initialize JPanel
		displayApp = new RestoAppDisplay();


		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createParallelGroup())
				.addComponent(displayApp)
				.addComponent(errorMessage)
				.addComponent(horizontalLineTop)
				.addComponent(horizontalLineBottom)
				
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(numberOfSeatsLabel)
								.addComponent(widthLabel)
								.addComponent(lengthLabel)
								.addComponent(xLocationLabel)
								.addComponent(yLocationLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(numberOfSeatsTextField, 200, 200, 400)
								.addComponent(widthTextField, 200, 200, 400)
								.addComponent(lengthTextField, 200, 200, 400)
								.addComponent(yLocationTextField, 200, 200, 400)
								.addComponent(xLocationTextField, 200, 200, 400)
								.addComponent(createTableButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(tablesLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(tableList)
								.addComponent(removeTableButton))
						

								));
		

		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)

				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineTop))
				.addGroup(layout.createParallelGroup()
						.addComponent(numberOfSeatsLabel)
						.addComponent(numberOfSeatsTextField)
						.addComponent(tablesLabel)
						.addComponent(tableList))		
				.addGroup(layout.createParallelGroup()
						.addComponent(widthLabel)
						.addComponent(widthTextField)
						.addComponent(removeTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(lengthTextField)
						.addComponent(lengthLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(xLocationTextField)
						.addComponent(xLocationLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(yLocationTextField)
						.addComponent(yLocationLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(createTableButton))
				.addGroup(layout.createParallelGroup()
						.addComponent(horizontalLineBottom))
				.addGroup(layout.createParallelGroup()
						.addComponent(displayApp))

				
				);
		pack();
		
	}

	protected void createTableButtonActionPerformed(ActionEvent evt) {
		// clear error message
		error = null;
		// call the controller

		

		
		try {
			RestoAppController.createTable(RestoAppController.generateTableNumber(), Integer.parseInt(numberOfSeatsTextField.getText()), Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()), Integer.parseInt(widthTextField.getText()), Integer.parseInt(lengthTextField.getText()));
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

	refreshData();
		repaint();
	}

	private void refreshData() {
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			// populate page with data
			// number of seats
			numberOfSeatsTextField.setText("");
			// width
			widthTextField.setText("");
			// length
			lengthTextField.setText("");
			// xLocation
			xLocationTextField.setText("");
			// yLocation
			yLocationTextField.setText("");
			
			// table list
			tableList.removeAllItems();
			for (Table table : RestoAppController.getTables()) {
				tableList.addItem("#" + table.getNumber());
			};
			selectedTable = -1;
			tableList.setSelectedIndex(selectedTable);
		}
		pack();
	}

}
