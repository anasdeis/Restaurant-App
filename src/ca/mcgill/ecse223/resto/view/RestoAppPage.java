package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Reservation;
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

    // ReserveTable
    private JButton reserveButton;
    private JLabel numberInPartyLabel;
    private JLabel contactNameLabel;
    private JLabel contactEmailAddressLabel;
    private JLabel contactPhoneNumberLabel;
    private JLabel reservedStatus;

    private JTextField numberInPartyTextField;
    private JTextField contactNameTextField;
    private JTextField contactEmailAddressTextField;
    private JTextField contactPhoneNumberTextField;

    //startOrder
    private JButton startOrderButton;
    private JTextField selectedTablesTextField;
    private JLabel selectedTablesLabel;

    //endOrder
    private JButton endOrderButton;

    /**
     * Creates new form RestoAppPage
     */
    public RestoAppPage() {
        initComponents();
        refreshData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
    	
    	setResizable(false);

        setTitle("RestoApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Error Message Elements
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);

        // Create Table Elements
        numberOfSeatsLabel = new JLabel("Number Of Seats: ");
        xLocationLabel = new JLabel("X Location: ");
        yLocationLabel = new JLabel("Y Location: ");
        lengthLabel = new JLabel("Length: ");
        widthLabel = new JLabel("Width: ");

        numberOfSeatsTextField = new JTextField();
        xLocationTextField = new JTextField();
        yLocationTextField = new JTextField();
        lengthTextField = new JTextField();
        widthTextField = new JTextField();
        newNumberOfSeatsTextField = new JTextField();
        newTableNumberTextField = new JTextField();

        // Delete Table Elements
        tableList = new JComboBox<String>(new String[0]);
        tablesLabel = new JLabel("Table: ");

        // Table Info Elements
        numberSeatsLabel = new JLabel("Seats: ");
        tableLocationXLabel = new JLabel("Location X: ");
        tableLocationYLabel = new JLabel("Location Y: ");
        tableWidthLabel = new JLabel("Width: ");
        tableLengthLabel = new JLabel("Length: ");

        // Update Table Elemts
        newNumberOfSeatsLabel = new JLabel("Enter Number of Seats :");
        newTableNumberLabel = new JLabel("Enter New Table Number :");

        // Reserve Table elements
        numberInPartyLabel = new JLabel("Number of People:");
        contactEmailAddressLabel = new JLabel("Contact E-mail address:");
        contactPhoneNumberLabel = new JLabel("Contact Phone:");
        contactNameLabel = new JLabel("Contact Name:");
        reservedStatus = new JLabel("Reservation Status: ");
        reservedStatus.setForeground(Color.red);
        numberInPartyTextField = new JTextField();
        contactEmailAddressTextField = new JTextField();
        contactPhoneNumberTextField = new JTextField();
        contactNameTextField = new JTextField();
        numberInPartyTextField = new JTextField();

        //startOrder
        selectedTablesLabel = new JLabel("table # (separate by spaces): ");
        selectedTablesTextField = new JTextField();


        tableList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedTableIndex = cb.getSelectedIndex();
                if (selectedTableIndex != -1) {
                    selectedTableNumber = Integer.parseInt(cb.getSelectedItem().toString());
                    List<Table> tables = RestoAppController.getTables();
                    for (Table table : tables) {
                        if (selectedTableNumber.equals(table.getNumber())) {
                            numberSeatsLabel.setText("Seats: " + table.getCurrentSeats().size());
                            tableLocationXLabel.setText("Location X: " + table.getX());
                            tableLocationYLabel.setText("Location Y: " + table.getY());
                            tableWidthLabel.setText("Width: " + table.getWidth());
                            tableLengthLabel.setText("Length: " + table.getLength());

                            if (!table.getReservations().isEmpty()) {
                                contactEmailAddressLabel.setText("Contact E-mail address: " + table.getReservation(0).getContactEmailAddress());
                                contactNameLabel.setText("Contact Name: " + table.getReservation(0).getContactName());
                                contactPhoneNumberLabel.setText("Contact Phone: " + table.getReservation(0).getContactPhoneNumber());
                                numberInPartyLabel.setText("Number of People: " + table.getReservation(0).getNumberInParty());
                                reservedStatus.setText("Reservation Status: Reserved, has " + table.getReservations().size() + " reservation(s)");
                            } else {
                                contactEmailAddressLabel.setText("Contact E-mail address: ");
                                contactNameLabel.setText("Contact Name:");
                                contactPhoneNumberLabel.setText("Contact Phone: ");
                                numberInPartyLabel.setText("Number of People: ");
                                reservedStatus.setText("Reservation Status: Unreserved ");
                            }
                        }
                    }
                }

                else {
                    refreshData();
                }
            }
        });
        createTableButton = new JButton("Create Table");
        createTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTableButtonActionPerformed(evt);
            }
        });
        removeTableButton = new JButton("Remove Table");
        removeTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTableButtonActionPerformed(evt);
            }
        });
        moveTableButton = new JButton("Move Table");
        moveTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveTableButtonActionPerformed(evt);
            }
        });
        updateTableButton = new JButton("Update Table");
        updateTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableButtonActionPerformed(evt);
            }
        });
        menuButton = new JButton("Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });
        reserveButton = new JButton("Reserve Table");
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });
        startOrderButton = new JButton("Start Order");
        startOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startOrderButtonActionPerformed(evt);
            }
        });
        endOrderButton = new JButton("End Order");
        endOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endOrderButtonActionPerformed(evt);
            }
        });


        // horizontal line elements
        JSeparator horizontalLineTop = new JSeparator();
        JSeparator horizontalLineBottom = new JSeparator();
        // initialize JPanel
        displayApp = new RestoAppDisplay();

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(displayApp, 1500, 1500, 1500)
                        .addComponent(errorMessage)
                        .addComponent(horizontalLineTop)
                        .addComponent(horizontalLineBottom))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(numberOfSeatsLabel)
                                .addComponent(widthLabel)
                                .addComponent(lengthLabel)
                                .addComponent(xLocationLabel)
                                .addComponent(yLocationLabel)
                                .addComponent(selectedTablesLabel)
                                .addComponent(startOrderButton))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(numberOfSeatsTextField)
                                .addComponent(widthTextField)
                                .addComponent(lengthTextField)
                                .addComponent(endOrderButton)
                                .addComponent(yLocationTextField)
                                .addComponent(xLocationTextField)
                                .addComponent(createTableButton)
                                .addComponent(moveTableButton)
                                .addComponent(selectedTablesTextField)
                                .addComponent(endOrderButton))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tablesLabel)
                                .addComponent(numberSeatsLabel)
                                .addComponent(tableLocationXLabel)
                                .addComponent(tableLocationYLabel)
                                .addComponent(tableWidthLabel)
                                .addComponent(tableLengthLabel)
                                .addComponent(newTableNumberLabel)
                                .addComponent(newNumberOfSeatsLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tableList)
                                .addComponent(removeTableButton)
                                .addComponent(contactNameLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(numberInPartyLabel)
                                .addComponent(reservedStatus)
                                .addComponent(newTableNumberTextField)
                                .addComponent(newNumberOfSeatsTextField)
                                .addComponent(updateTableButton)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(menuButton)
                                .addComponent(contactNameTextField)
                                .addComponent(contactEmailAddressTextField)
                                .addComponent(contactPhoneNumberTextField)
                                .addComponent(numberInPartyTextField)
                                .addComponent(reserveButton)
                        )
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
                                .addComponent(lengthLabel)
                                .addComponent(lengthTextField)
                                .addComponent(numberSeatsLabel)
                                .addComponent(contactNameLabel)
                                .addComponent(contactNameTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(xLocationTextField)
                                .addComponent(xLocationLabel)
                                .addComponent(tableLocationXLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactEmailAddressTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(yLocationTextField)
                                .addComponent(yLocationLabel)
                                .addComponent(tableLocationYLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(contactPhoneNumberTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(createTableButton)
                                .addComponent(tableWidthLabel)
                                .addComponent(numberInPartyLabel)
                                .addComponent(numberInPartyTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(moveTableButton)
                                .addComponent(tableLengthLabel)
                                .addComponent(reservedStatus)
                                .addComponent(reserveButton)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(selectedTablesLabel)
                                .addComponent(selectedTablesTextField)
                                .addComponent(newTableNumberLabel)
                                .addComponent(newTableNumberTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(startOrderButton)
                                .addComponent(endOrderButton)
                                .addComponent(newNumberOfSeatsLabel)
                                .addComponent(newNumberOfSeatsTextField)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(updateTableButton))
                        .addComponent(horizontalLineBottom)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(displayApp, 450, 450, 450)))
        );
        pack();

    }

    protected void createTableButtonActionPerformed(ActionEvent evt) {

        try {
            RestoAppController.createTable(Integer.parseInt(numberOfSeatsTextField.getText()),
                    Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()),
                    Integer.parseInt(widthTextField.getText()), Integer.parseInt(lengthTextField.getText()));
        } catch (NumberFormatException | InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
        repaint();
    }

    protected void removeTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for removing!";

        } else {
            try {
                RestoAppController.removeTable(tables.get(selectedTableIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void moveTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for moving!";
        } else {
            if (error.length() == 0) {
                try {
                    RestoAppController.moveTable(tables.get(selectedTableIndex), Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()));
                } catch (InvalidInputException | NumberFormatException e) {
                    error = e.getMessage();
                }
            }
        }
        refreshData();
        repaint();
    }

    protected void updateTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for updating!";

        } else {
            try {
                RestoAppController.updateTable(tables.get(selectedTableIndex), Integer.parseInt(newTableNumberTextField.getText()), Integer.parseInt(newNumberOfSeatsTextField.getText()));
            } catch (NumberFormatException | InvalidInputException e) {
                error = e.getMessage();
            }
        }

        refreshData();
        repaint();
    }

    protected void menuButtonActionPerformed(ActionEvent evt) {

        error = null;

        new MenuPage();
    }

    protected void reserveButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for reserving!";
        } else {
            try {
                RestoAppController.reserveTable(null, null, Integer.parseInt(numberInPartyTextField.getText()), contactNameTextField.getText(), contactEmailAddressTextField.getText(), contactPhoneNumberTextField.getText(), null, selectedTableNumber);
            } catch (InvalidInputException e) {
                error = e.getMessage() + " Please do not leave blank fields";
            }
        }
        refreshData();
        repaint();
    }

    protected void startOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        String[] stringArray = selectedTablesTextField.getText().trim().split("\\s+");//remove any leading, trailing white spaces and split the string from rest of the white spaces
        int[] intArray = new int[stringArray.length]; //create a new int array to store the int values

        if (intArray.length == 0) {
            error = "Enter the set of table # that you want to start order for";
        }

        else {

            try {
                for (int i = 0; i < stringArray.length; i++) {
                    intArray[i] = Integer.parseInt(stringArray[i]);//parse the integer value and store it in the int array
                }

                for (int i = 0; i < intArray.length; i++)
                    for (int j = i + 1; j < intArray.length; j++)
                        if (j != i && intArray[j] == intArray[i]) {
                            error = "Do not enter duplicate table #";
                            throw (new InvalidInputException(error));
                        }

                List<Table> tables = RestoAppController.getTables();
                List<Table> selectedTables = new ArrayList<Table>();
                boolean isCurrentTable = true;
                int i = 1;

                outerloop:
                for (int tableNumber : intArray) {
                    System.out.println("tableNumber #" + tableNumber);
                    for (Table table : tables) {
                        isCurrentTable = false;
                        if (tableNumber == (table.getNumber())) {
                            selectedTables.add(table);
                            isCurrentTable = true;
                            System.out.println("Valid tableNumber #" + tableNumber);
                            continue outerloop;
                        }
                        if (i == (tables.size())) {
                            error = "Table #" + tableNumber + " is not a current table!";
                            throw (new InvalidInputException(error));
                        }
                        i++;
                    }
                }

                RestoAppController.startOrder(selectedTables);

            } catch (NumberFormatException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void endOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for e!";

        } else {
            //     try {
            //     RestoAppController.endOrder(order);
            //  } catch (InvalidInputException e) {
            //        error = e.getMessage();
            //  }
        }
        refreshData();
        repaint();
    }


    private void refreshData() {
        // error
        errorMessage.setText(error);
        if (error == null || error.length() == 0) {
            // populate page with data

            //TextField
            //create table
            numberOfSeatsTextField.setText("");
            widthTextField.setText("");
            lengthTextField.setText("");
            xLocationTextField.setText("");
            yLocationTextField.setText("");
            //update table
            newNumberOfSeatsTextField.setText("");
            newTableNumberTextField.setText("");
            //reserve table
            numberInPartyTextField.setText("");
            contactNameTextField.setText("");
            contactEmailAddressTextField.setText("");
            contactPhoneNumberTextField.setText("");
            //start order
            selectedTablesTextField.setText("");

            //LabelField
            //select table
            numberSeatsLabel.setText("Seats: ");
            tableLocationXLabel.setText("Location X: ");
            tableLocationYLabel.setText("Location Y: ");
            tableWidthLabel.setText("Width: ");
            tableLengthLabel.setText("Length: ");
            //reserve table
            contactEmailAddressLabel.setText("Contact E-mail address: ");
            contactNameLabel.setText("Contact Name:");
            contactPhoneNumberLabel.setText("Contact Phone: ");
            numberInPartyLabel.setText("Number of People: ");
            reservedStatus.setText("Reservation Status: Unreserved ");

            //select table - also combo box for table visualization
            tables = new HashMap<Integer, Table>();
            tableList.removeAllItems();
            Integer index = 0;
            for (Table table : RestoAppController.getTables()) {
                tables.put(index, table);
                tableList.addItem("" + table.getNumber());
                index++;
            }

            selectedTableIndex = -1;
            tableList.setSelectedIndex(selectedTableIndex);

        }
    }
}