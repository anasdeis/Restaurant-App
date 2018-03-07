package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -6463272516157863988L;

	// Error Message
	private JLabel errorMessage;

	private String error = null;

	// Create Table
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
	private Integer selectedTableIndex = -1;
	private Integer selectedTableNumber = null;
	private RestoAppDisplay displayApp;

	// Move Table
	private JButton moveTableButton;
	private HashMap<Integer, Table> tables;

	// Info Table Section
	private JLabel numberSeatsLabel;
	private JLabel tableLocationXLabel;
	private JLabel tableLocationYLabel;
	private JLabel tableWidthLabel;
	private JLabel tableLengthLabel;


	// Update Table Section Declaration
	private JLabel newNumberOfSeatsLabel;
	private JLabel newTableNumberLabel;
	private JTextField newNumberOfSeatsTextField;
	private JTextField newTableNumberTextField;
	private JButton updateTableButton;
	// Display Menu
	private JButton menuButton;



	/** Creates new form RestoAppPage */
	public RestoAppPage() {
		initComponents();
		refreshData();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		setTitle("RestoApp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		newNumberOfSeatsTextField = new JTextField();
	    newTableNumberTextField = new JTextField();

		createTableButton.setText("Create Table: ");
		numberOfSeatsLabel.setText("Number Of Seats: ");
		xLocationLabel.setText("X Location: ");
		yLocationLabel.setText("Y Location: ");
		lengthLabel.setText("Length: ");
		widthLabel.setText("Width: ");

		newNumberOfSeatsTextField.setText("");
		 newTableNumberTextField.setText("");

		// Delete Table Elements
		tableList = new JComboBox<String>(new String[0]);
		tablesLabel = new JLabel();
		removeTableButton = new JButton();

		// Table Info Elements
		numberSeatsLabel = new JLabel();
		tableLocationXLabel = new JLabel();
		tableLocationYLabel = new JLabel();
		tableWidthLabel = new JLabel();
		tableLengthLabel = new JLabel();

		numberSeatsLabel.setText("Seats: ");
		tableLocationXLabel.setText("Location X: ");
		tableLocationYLabel.setText("Location Y: ");
		tableWidthLabel.setText("Width: ");
		tableLengthLabel.setText("Length: ");

		// Move Table Elements
		moveTableButton = new JButton();
		moveTableButton.setText("Move Table");

		// Display Menu Elements
		menuButton = new JButton();
		menuButton.setText("Menu");

		createTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createTableButtonActionPerformed(evt);
			}
		});

		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedTableIndex = cb.getSelectedIndex();
				if (selectedTableIndex != -1) {
					selectedTableNumber = Integer.parseInt(cb.getSelectedItem().toString().charAt(1) + "");
					List<Table> tables = RestoAppController.getTables();
					for (Table table : tables) {
						if (selectedTableNumber.equals(table.getNumber())) {
							numberSeatsLabel.setText("Seats: " + table.numberOfSeats());
							tableLocationXLabel.setText("Location X: " + table.getX());
							tableLocationYLabel.setText("Location Y: " + table.getY());
							tableWidthLabel.setText("Width: " + table.getWidth());
							tableLengthLabel.setText("Length: " + table.getLength());
						}
					}
				}
			}
		});
		removeTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeTableButtonActionPerformed(evt);
			}
		});

		moveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveTableButtonActionPerformed(evt);
			}
		});

		menuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuButtonActionPerformed(evt);
			}
		});

		tablesLabel.setText("Tables: ");
		removeTableButton.setText("Remove Table");

		// horizontal line elements
		JSeparator horizontalLineTop = new JSeparator();
		JSeparator horizontalLineBottom = new JSeparator();
		// initialize JPanel
		displayApp = new RestoAppDisplay();

		newNumberOfSeatsLabel = new JLabel();
		newTableNumberLabel = new JLabel();
		newNumberOfSeatsLabel.setText("Enter Number of Seats :");
		newTableNumberLabel.setText("Enter New Table Number :");


		//Button Update Table
		updateTableButton = new JButton();      
		updateTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateTableButtonActionPerformed(evt);
			}
		});
		updateTableButton.setText("Update Table");
		//added method

		//added these in refreshData()
		//	          newNumberOfSeatsTextField.setText("");
		//	          newTableNumberTextField.setText("");




		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(displayApp, 800, 800, 800)
						.addComponent(errorMessage)
						.addComponent(horizontalLineTop)
						.addComponent(horizontalLineBottom))
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
								.addComponent(createTableButton)
								.addComponent(moveTableButton))

						.addGroup(layout.createParallelGroup()
								.addComponent(tablesLabel)
								.addComponent(numberSeatsLabel)
								.addComponent(tableLocationXLabel)
								.addComponent(tableLocationYLabel)
								.addComponent(tableWidthLabel)
								.addComponent(tableLengthLabel)
								.addComponent(newTableNumberLabel)
								.addComponent(newNumberOfSeatsLabel)
								.addComponent(updateTableButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(tableList)
								.addComponent(removeTableButton)
								 .addComponent(newTableNumberTextField)
								 .addComponent(newNumberOfSeatsTextField)
								)
						.addGroup(layout.createParallelGroup()

								.addComponent(menuButton))
						.addComponent(tableWidthLabel)
						));


		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineTop))
						.addGroup(layout.createParallelGroup()
								.addComponent(numberOfSeatsLabel)
								.addComponent(numberOfSeatsTextField)
								.addComponent(tablesLabel)
								.addComponent(tableList)
								.addComponent(menuButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(widthLabel)
								.addComponent(widthTextField)
								.addComponent(removeTableButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(numberSeatsLabel)
								.addComponent(lengthTextField)
								.addComponent(lengthLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(xLocationTextField)
								.addComponent(xLocationLabel)
								.addComponent(tableLocationXLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(yLocationTextField)
								.addComponent(yLocationLabel)
								.addComponent(tableLocationYLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(createTableButton)
								.addComponent(tableWidthLabel)
								.addComponent(tableLengthLabel,65,65,65))
					
						.addGroup(layout.createParallelGroup()
								.addComponent(newTableNumberLabel)
								 .addComponent(newTableNumberTextField)

								)
						.addGroup(layout.createParallelGroup()
								.addComponent(newNumberOfSeatsLabel)
								  .addComponent(newNumberOfSeatsTextField)
								)

						.addGroup(layout.createParallelGroup()
								.addComponent(moveTableButton)
								.addComponent(updateTableButton))


						.addComponent(horizontalLineBottom)
						.addGroup(layout.createParallelGroup()
								.addComponent(displayApp, 300, 300, 300)))
				.addComponent(menuButton)
				);
		pack();

	}
	protected void updateTableButtonActionPerformed(ActionEvent evt) {
		//clear error message
		error = null;

		      try {
		          RestoAppController.updateTable(RestoApplication.getRestoApp().getTable(selectedTableIndex), Integer.parseInt(newTableNumberTextField.getText()), Integer.parseInt(newNumberOfSeatsTextField.getText()));
		      } catch (InvalidInputException e) {
		          error = e.getMessage();
		      }

		refreshData();
		repaint();
	}

	protected void createTableButtonActionPerformed(ActionEvent evt) {
		// call the controller
		try {
			RestoAppController.createTable(Integer.parseInt(numberOfSeatsTextField.getText()),
					Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()),
					Integer.parseInt(widthTextField.getText()), Integer.parseInt(lengthTextField.getText()));
		} catch (Exception e) {
			error = e.getMessage();
		}

		refreshData();
		repaint();
	}

	protected void removeTableButtonActionPerformed(ActionEvent evt) {
		// clear error message
		error = null;
		// call the controller

		try {
			RestoAppController.removeTable(selectedTableNumber);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		refreshData();
		repaint();
	}

	protected void moveTableButtonActionPerformed(ActionEvent evt) {
		// clear error message
		error = "";
		// call the controller

		if (selectedTableNumber < 0) {
			error = "Table needs to be selected for moving!";
		}

		if (error.length() == 0) {
			try {
				RestoAppController.moveTable(tables.get(selectedTableIndex), Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()));
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		refreshData();
		repaint();
	}

	protected void menuButtonActionPerformed(ActionEvent evt) {
		// clear error message
		error = null;
		// open the menuDisplay
		new MenuPage();
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
			numberSeatsLabel.setText("Seats: ");
			tableLocationXLabel.setText("Location X: ");
			tableLocationYLabel.setText("Location Y: ");
			tableWidthLabel.setText("Width: ");
			tableLengthLabel.setText("Length: ");

			tables =  new HashMap<Integer, Table>();
			tableList.removeAllItems();
			Integer index = 0;
			for (Table table : RestoAppController.getTables()) {
				tables.put(index, table);
				tableList.addItem("#" + table.getNumber());
				index++;
			}

			selectedTableIndex = -1;
			tableList.setSelectedIndex(selectedTableIndex);

		}
	}

}