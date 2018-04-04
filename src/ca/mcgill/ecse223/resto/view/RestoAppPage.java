package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.naming.spi.ResolveResult;
import javax.swing.*;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.Seat;
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
    private Integer selectedSeatIndex = -1;
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
    private JButton deleteReservationButton;
    private JButton deleteTableReservationButton;
    private JComboBox<String> reservationList;
    private Integer selectedReservationIndex = -1;
    private JLabel numberInPartyLabel;
    private JLabel contactNameLabel;
    private JLabel contactEmailAddressLabel;
    private JLabel contactPhoneNumberLabel;
    private JLabel reservedStatusLabel;
    private JLabel tableStatusLabel;
    private JLabel dateLabel;
    private JLabel reservationDate;

    private JTextField numberInPartyTextField;
    private JTextField contactNameTextField;
    private JTextField contactEmailAddressTextField;
    private JTextField contactPhoneNumberTextField;
    private JTextField dateTextField;

    private HashMap<Integer, Reservation> reservations;

    //Orders
    private JButton startOrderButton;
    private JButton deleteTableOrderButton;
    private JTextField selectedTablesTextField;
    private JLabel selectedTablesLabel;
    private JButton endOrderButton;
    private JButton deleteOrderButton;
    private JButton viewOrdersButton;
    private JButton cancelItemButton;
    private JButton cancelSeatButton;
    private JButton cancelTableButton;
    private JButton cancelOrderButton;
    private JComboBox<String> ordersList;
    private JLabel orderDate;
    private JLabel ordersLabel;
    private Integer selectedOrderIndex = -1;
    private HashMap<Integer, Order> orders;

    // Seats
    private JComboBox<String> seatList;
    private JLabel seatsLabel;
	private HashMap<Integer, Seat> seats;
    private JButton addSeatButton;
    
    // Bill
    private JButton addTableButton;
    private JButton issueBillForOneTable;
    private JButton issueBillForEachCustomerInOneTable;
    private JButton issueBillForGroupCustomerInOneTable;
    private JButton issueBillForGroupCustomerInMultipleTable;

    // seat overview
    private JScrollPane overviewScrollPane;
    private DefaultListModel<Seat> listModel = new DefaultListModel<>();
    private JList<Seat> seatListToBeBilled = new JList<>(listModel);
    //Table overview
    private JScrollPane overviewScrollPaneTable;
    private DefaultListModel<Table> listModelTable = new DefaultListModel<>();
    private JList<Table> tableListToBeBilled = new JList<>(listModelTable);

    //User
    private JLabel userLabel;
    private JLabel userNameLabel;
    int i;

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
        newNumberOfSeatsLabel = new JLabel("Enter New Number of Seats :");
        newTableNumberLabel = new JLabel("Enter New Table Number :");

        // Reserve Table elements
        numberInPartyLabel = new JLabel("Number of People:");
        contactEmailAddressLabel = new JLabel("Contact E-mail address:");
        contactPhoneNumberLabel = new JLabel("Contact Phone:");
        contactNameLabel = new JLabel("Contact Name:");
        reservedStatusLabel = new JLabel("Reservation Status: ");
        reservedStatusLabel.setForeground(Color.red);
        tableStatusLabel = new JLabel("Table Status: ");
        tableStatusLabel.setForeground(Color.red);
        dateLabel = new JLabel("Set date and time (yyyy/MM/dd HH:mm:ss)");
        reservationDate = new JLabel();

        numberInPartyTextField = new JTextField();
        contactEmailAddressTextField = new JTextField();
        contactPhoneNumberTextField = new JTextField();
        contactNameTextField = new JTextField();
        numberInPartyTextField = new JTextField();
        dateTextField = new JTextField();
        reservationList = new JComboBox<String>(new String[0]);

        // startOrder
        selectedTablesLabel = new JLabel("Enter table # separated by whitespace: ");
        selectedTablesTextField = new JTextField();
        ordersLabel = new JLabel("Orders:");


        ordersList = new JComboBox<String>(new String[0]);
        orderDate = new JLabel();
        // seats
        seatList = new JComboBox<String>(new String[0]);
        seatsLabel = new JLabel("Seats: ");
        //add seats to list of seat
        //addTableOrSeatButton = new JButton("Add Table/Seat To Bill");
        // 4 ways to issue bill
        overviewScrollPane = new JScrollPane(seatListToBeBilled);
        overviewScrollPane.setPreferredSize(new Dimension(48, 48));
        this.add(overviewScrollPane);
        overviewScrollPaneTable = new JScrollPane(tableListToBeBilled);
        overviewScrollPaneTable.setPreferredSize(new Dimension(48, 48));
        this.add(overviewScrollPaneTable);
        i = 0;

        //User
        userLabel = new JLabel("User: ");
        userNameLabel = new JLabel("ANAS BOSS");
        userNameLabel.setForeground(Color.BLUE);
        tableList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i++;
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedTableIndex = cb.getSelectedIndex();
                if (i == 1) {
                    selectedTableIndex = -1;
                }
                if (selectedTableIndex != -1) {
                    selectedTableNumber = Integer.parseInt(cb.getSelectedItem().toString());
                    List<Table> tables = RestoAppController.getTables();
                    for (Table table : tables) {
                        if (selectedTableNumber.equals(table.getNumber())) {
                        	seats = new HashMap<Integer, Seat>();
                            seatList.removeAllItems();
                            Integer seatIndex = 0;
                            try {
                                List<Seat> seatsInTable = RestoAppController.getSeatForEachCustomerAtOneTable(table);
                                
                                for (Seat seat : seatsInTable) {
                                    seats.put(seatIndex, seat);
                                    seatList.addItem("" + seatIndex);
                                    seatIndex++;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // select reservation - also combo box for reservation visualization
                            reservations = new HashMap<Integer, Reservation>();
                            reservationList.removeAllItems();
                            List<Reservation> reservationsTable = table.getReservations();
                            List<Long> reservationTimeArray = new ArrayList<Long>();

                            Integer reservationIndex = 0;
                            for (Reservation reservation : reservationsTable) {
                                reservationTimeArray.add(reservation.getDate().getTime());
                            }
                            try {
                                Collections.sort(reservationTimeArray);
                            } catch (ClassCastException | UnsupportedOperationException | IllegalArgumentException e) {
                                e.printStackTrace();
                                error += e.getMessage();
                            }
                            for (long time : reservationTimeArray) {
                                for (Reservation reservation : reservationsTable) {
                                    if (time == reservation.getDate().getTime()) {
                                        reservations.put(reservationIndex, reservation);
                                        reservationList.addItem("Reservation #" + (reservationIndex + 1));
                                        reservationIndex++;
                                    }
                                }
                            }


                            // select order - also combo box for order visualization
                            orders = new HashMap<Integer, Order>();
                            ordersList.removeAllItems();
                            List<Order> currentOrders = RestoApplication.getRestoApp().getCurrentOrders();
                            List<Order> ordersTable = table.getOrders();
                            List<Long> orderTimeArray = new ArrayList<Long>();

                            Integer orderIndex = 0;
                            for (Order order : ordersTable) {
                                orderTimeArray.add(order.getDate().getTime());
                            }
                            try {
                                Collections.sort(orderTimeArray);
                            } catch (ClassCastException | UnsupportedOperationException | IllegalArgumentException e) {
                                e.printStackTrace();
                                error += e.getMessage();
                            }
                            for (long time : orderTimeArray) {
                                for (Order order : ordersTable) {
                                    if (time == order.getDate().getTime()) {
                                        orders.put(orderIndex, order);
                                        if (currentOrders.contains(order)) {
                                            ordersList.addItem("(Current) Order #" + (orderIndex + 1));
                                        } else {
                                            ordersList.addItem("Order #" + (orderIndex + 1));
                                        }
                                        orderIndex++;
                                    }
                                }
                            }

                            numberSeatsLabel.setText("Seats: " + table.getCurrentSeats().size());
                            tableLocationXLabel.setText("Location X: " + table.getX());
                            tableLocationYLabel.setText("Location Y: " + table.getY());
                            tableWidthLabel.setText("Width: " + table.getWidth());
                            tableLengthLabel.setText("Length: " + table.getLength());
                            tableStatusLabel.setText("Table Status: " + table.getStatusFullName());

                            if (!table.hasOrders()) {
                                orderDate.setText("No orders for table #" + table.getNumber());
                            }

                            if (table.hasReservations()) {
                                reservedStatusLabel.setText("Reservation Status: Reserved, has "
                                        + table.getReservations().size() + " reservation(s)");
                            } else {
                                reservationDate.setText("No reservations for table #" + table.getNumber());
                                contactEmailAddressLabel.setText("Contact E-mail address: ");
                                contactNameLabel.setText("Contact Name:");
                                contactPhoneNumberLabel.setText("Contact Phone: ");
                                numberInPartyLabel.setText("Number of People: ");
                                reservedStatusLabel.setText("Reservation Status: Unreserved ");
                            }
                        }
                    }
                } else {
                    selectedReservationIndex = -1;
                    reservationList.removeAllItems();
                    selectedOrderIndex = -1;
                    ordersList.removeAllItems();
                    refreshData();
                }
            }
        });
        seatList.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedSeatIndex = cb.getSelectedIndex();
                List<Table> tables = RestoAppController.getTables();

                if (selectedSeatIndex != -1 && selectedTableIndex != -1) {
                    try {
                        // getting the seat object from the dropdown item
                        Seat specificSeat = seats.get(selectedSeatIndex);
                        //RestoAppController.getSpecificSeat(tables.get(selectedTableIndex),selectedSeatIndex);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    refreshData();
                }
            }
        });
        
        reservationList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedReservationIndex = cb.getSelectedIndex();
                if (selectedReservationIndex > -1) {
                    Reservation reservation = reservations.get(selectedReservationIndex);
                    reservationDate.setText(reservation.getDate().toString() + " " + reservation.getTime().toString());
                    contactEmailAddressLabel.setText(
                            "Contact E-mail address: " + reservation.getContactEmailAddress());
                    contactNameLabel.setText("Contact Name: " + reservation.getContactName());
                    contactPhoneNumberLabel
                            .setText("Contact Phone: " + reservation.getContactPhoneNumber());
                    numberInPartyLabel
                            .setText("Number of People: " + reservation.getNumberInParty());
                } else {
                    refreshData();
                }
            }
        });
                
        ordersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedOrderIndex = cb.getSelectedIndex();

                if (selectedOrderIndex > -1) {
                    Order order = orders.get(selectedOrderIndex);
                    orderDate.setText(order.getDate().toString() + " " + order.getTime().toString());
                } else {
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
        issueBillForOneTable = new JButton("Bill Mode1 (One Table)");
        issueBillForOneTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeOneButtonActionPerformed(evt);
            }
        });
        //TODO
        issueBillForEachCustomerInOneTable = new JButton("Bill Mode2 (Each Customer in One Table)");
        issueBillForGroupCustomerInOneTable = new JButton("Bill Mode3 (Group in One Table)");
        issueBillForGroupCustomerInMultipleTable = new JButton("Bill Mode4 (Group in Multiple Tables)");


        reserveButton = new JButton("Reserve Table(s)");
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });
        deleteReservationButton = new JButton("Delete Whole Reservation");
        deleteReservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteReservationButtonActionPerformed(evt);
            }
        });
        deleteTableReservationButton = new JButton("Delete Table Reservation");
        deleteTableReservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTableReservationButtonActionPerformed(evt);
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

        addSeatButton = new JButton("Add Seat To Order");
        addSeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSeatButtonActionPerformed(evt);
            }
        });

        addTableButton = new JButton("Add Table To Bill");
        addTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTableButtonActionPerformed(evt);
            }
        });
        viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderButtonActionPerformed(evt);
            }
        });
    /*    cancelItemButton = new JButton("Cancel Item");
        cancelItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelItemButtonActionPerformed(evt);
            }
        });
        cancelSeatButton = new JButton("Cancel Seat");
        cancelSeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelSeatButtonActionPerformed(evt);
            }
        });
        cancelTableButton = new JButton("Cancel Table");
        cancelTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelTableButtonActionPerformed(evt);
            }
        });
        cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOrderButtonActionPerformed(evt);
            }
        });*/
        deleteOrderButton = new JButton("Delete Whole Order");
        deleteOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteOrderButtonActionPerformed(evt);
            }
        });
        deleteTableOrderButton = new JButton("Delete Table Order");
        deleteTableOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTableOrderButtonActionPerformed(evt);
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

                .addGroup(layout.createParallelGroup().addComponent(displayApp, 1800, 1800, 1800)

                        .addComponent(errorMessage).addComponent(horizontalLineTop).addComponent(horizontalLineBottom))

                .addGroup(layout.createSequentialGroup()

                        .addGroup(layout.createParallelGroup()
                                .addComponent(userLabel)
                                .addComponent(numberOfSeatsLabel)
                                .addComponent(widthLabel)
                                .addComponent(lengthLabel)
                                .addComponent(xLocationLabel)
                                .addComponent(yLocationLabel)
                                .addComponent(newTableNumberLabel)
                                .addComponent(newNumberOfSeatsLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(userNameLabel)
                                .addComponent(numberOfSeatsTextField)
                                .addComponent(widthTextField)
                                .addComponent(lengthTextField)
                                .addComponent(yLocationTextField)
                                .addComponent(xLocationTextField)
                                .addComponent(createTableButton)
                                .addComponent(moveTableButton)
                                .addComponent(newTableNumberTextField)
                                .addComponent(newNumberOfSeatsTextField)
                                .addComponent(updateTableButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(tablesLabel)
                                .addComponent(numberSeatsLabel)
                                .addComponent(tableWidthLabel)
                                .addComponent(tableLengthLabel)
                                .addComponent(tableLocationXLabel)
                                .addComponent(tableLocationYLabel)
                                .addComponent(selectedTablesLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(tableList)
                                .addComponent(removeTableButton)
                                .addComponent(tableStatusLabel)
                                .addComponent(contactNameLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(numberInPartyLabel)
                                .addComponent(dateLabel)
                                .addComponent(reservedStatusLabel)
                                .addComponent(selectedTablesTextField)
                                .addComponent(reserveButton)
                                .addComponent(startOrderButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(menuButton)
                                .addComponent(viewOrdersButton)
                                .addComponent(endOrderButton)
                                .addComponent(contactNameTextField)
                                .addComponent(contactEmailAddressTextField)
                                .addComponent(contactPhoneNumberTextField)
                                .addComponent(numberInPartyTextField)
                                .addComponent(dateTextField)
                                .addComponent(reservationList)
                                .addComponent(reservationDate)
                                .addComponent(deleteReservationButton)
                                .addComponent(deleteTableReservationButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(seatsLabel)
                                .addComponent(ordersLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(seatList)
                                .addComponent(issueBillForOneTable)
                                .addComponent(issueBillForEachCustomerInOneTable)
                                .addComponent(issueBillForGroupCustomerInOneTable)
                                .addComponent(issueBillForGroupCustomerInMultipleTable)
                                .addComponent(ordersList)
                                .addComponent(orderDate)
                                .addComponent(deleteOrderButton)
                                .addComponent(deleteTableOrderButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(addTableButton)
                                .addComponent(addSeatButton)
                                .addComponent(overviewScrollPaneTable)
                                .addComponent(overviewScrollPane))
                )
        );


        layout.setVerticalGroup(layout.createParallelGroup()


                .addGroup(layout.createSequentialGroup()
                        .addComponent(errorMessage)

                        .addGroup(layout.createParallelGroup()
                                .addComponent(horizontalLineTop))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(userLabel)
                                .addComponent(userNameLabel)
                                .addComponent(tablesLabel)
                                .addComponent(tableList)
                                .addComponent(menuButton)
                                .addComponent(seatsLabel)
                                .addComponent(seatList)
                                .addComponent(addTableButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(numberOfSeatsLabel)
                                .addComponent(numberOfSeatsTextField)
                                .addComponent(numberSeatsLabel)
                                .addComponent(removeTableButton)
                                .addComponent(viewOrdersButton)
                                .addComponent(issueBillForOneTable)
                                .addComponent(addSeatButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(widthLabel)
                                .addComponent(widthTextField)
                                .addComponent(tableWidthLabel)
                                .addComponent(tableStatusLabel)
                                .addComponent(endOrderButton)
                                .addComponent(issueBillForEachCustomerInOneTable)
                                .addComponent(overviewScrollPaneTable))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(lengthLabel)
                                .addComponent(lengthTextField)
                                .addComponent(tableLengthLabel)
                                .addComponent(contactNameLabel)
                                .addComponent(contactNameTextField)
                                .addComponent(issueBillForGroupCustomerInOneTable))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(xLocationLabel)
                                .addComponent(xLocationTextField)
                                .addComponent(tableLocationXLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactEmailAddressTextField)
                                .addComponent(issueBillForGroupCustomerInMultipleTable)
                                .addComponent(overviewScrollPane))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(yLocationLabel)
                                .addComponent(yLocationTextField)
                                .addComponent(tableLocationYLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(contactPhoneNumberTextField))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(createTableButton)
                                .addComponent(numberInPartyLabel)
                                .addComponent(numberInPartyTextField))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(moveTableButton)
                                .addComponent(dateLabel)
                                .addComponent(dateTextField))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(newTableNumberLabel)
                                .addComponent(newTableNumberTextField)
                                .addComponent(reservedStatusLabel)
                                .addComponent(reservationList)
                                .addComponent(ordersLabel)
                                .addComponent(ordersList))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(newNumberOfSeatsLabel)
                                .addComponent(newNumberOfSeatsTextField)
                                .addComponent(selectedTablesLabel)
                                .addComponent(selectedTablesTextField)
                                .addComponent(reservationDate)
                                .addComponent(orderDate))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(updateTableButton)
                                .addComponent(reserveButton)
                                .addComponent(deleteReservationButton)
                                .addComponent(deleteOrderButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(startOrderButton)
                                .addComponent(deleteTableReservationButton)
                                .addComponent(deleteTableOrderButton))


                        .addComponent(horizontalLineBottom)

                        .addGroup(layout.createParallelGroup()
                                .addComponent(displayApp, 450, 450, 450))));
        pack();

    }


    protected void createTableButtonActionPerformed(ActionEvent evt) {

        error = null;

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

    protected void addSeatButtonActionPerformed(ActionEvent evt) {
        try {
            if (selectedSeatIndex != -1 && selectedTableIndex != -1) {
                listModel.addElement(RestoAppController.getSpecificSeat(tables.get(selectedTableIndex),
                        selectedSeatIndex));
            }
        } catch (Exception e) {

        }
        refreshData();
        repaint();
    }

    protected void addTableButtonActionPerformed(ActionEvent evt) {
        try {
            if (selectedTableIndex != -1) {
                listModelTable.addElement(RestoAppController.getSpecificTable(selectedTableIndex));
            }
        } catch (Exception e) {

        }
        refreshData();
        repaint();
    }

    protected void moveTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "Table needs to be selected for moving!";

        } else {
            try {
                RestoAppController.moveTable(tables.get(selectedTableIndex),
                        Integer.parseInt(xLocationTextField.getText()),
                        Integer.parseInt(yLocationTextField.getText()));
            } catch (InvalidInputException | NumberFormatException e) {
                error = e.getMessage();
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
                RestoAppController.updateTable(tables.get(selectedTableIndex),
                        Integer.parseInt(newTableNumberTextField.getText()),
                        Integer.parseInt(newNumberOfSeatsTextField.getText()));
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

    protected void orderButtonActionPerformed(ActionEvent evt) {

        error = null;

        try {
            new OrderPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void modeOneButtonActionPerformed(ActionEvent evt) {

        error = null;

        new BillPageModeOne();
    }

    protected void deleteReservationButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedReservationIndex < 0) {
            error = "Reservation needs to be selected for deleting!";

        } else {

            try {
                RestoAppController.endReservation(reservations.get(selectedReservationIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
    }

    protected void deleteTableReservationButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedReservationIndex < 0 && selectedTableIndex < 0) {
            error = "A table and its reservation needs to be specified for deleting!";

        } else {

            try {
                RestoAppController.deleteTableReservation(reservations.get(selectedReservationIndex), tables.get(selectedTableIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
    }

    protected void deleteOrderButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedOrderIndex < 0) {
            error = "Reservation needs to be selected for deleting!";

        } else {

            try {
                RestoAppController.deleteOrder(orders.get(selectedOrderIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
    }

    protected void deleteTableOrderButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedOrderIndex < 0 && selectedTableIndex < 0) {
            error = "The table and the order needs to be specified for deleting!";

        } else {

            try {
                RestoAppController.deleteTableOrder(orders.get(selectedOrderIndex), tables.get(selectedTableIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
    }

    protected void reserveButtonActionPerformed(ActionEvent evt) {

        error = null;

        String[] stringArray = selectedTablesTextField.getText().trim().split("\\s+");// remove any leading, trailing
        // white spaces and split the
        // string from rest of the white
        // spaces
        int[] intArray = new int[stringArray.length]; // create a new int array to store the int values

        if (intArray.length == 0) {
            error = "Enter the set of table(s) # that you want to reserve";
        } else {

            try {
                for (int i = 0; i < stringArray.length; i++) {
                    intArray[i] = Integer.parseInt(stringArray[i]);// parse the integer value and store it in the int
                    // array
                }

                for (int i = 0; i < intArray.length; i++)
                    for (int j = i + 1; j < intArray.length; j++)
                        if (j != i && intArray[j] == intArray[i]) {
                            error = "Do not enter duplicate table #";
                            throw (new InvalidInputException(error));
                        }

                List<Table> tables = RestoAppController.getTables();
                List<Table> selectedTables = new ArrayList<Table>();


                outerloop:
                for (int tableNumber : intArray) {
                    int i = 1;
                    for (Table table : tables) {
                        if (tableNumber == (table.getNumber())) {
                            selectedTables.add(table);
                            continue outerloop;
                        }
                        if (i == (tables.size())) {
                            error = "Table #" + tableNumber + " is not a current table!";
                            throw (new InvalidInputException(error));
                        }
                        i++;
                    }
                }

                String myDate = dateTextField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                java.util.Date date = sdf.parse(myDate);
                long dateTime = date.getTime();
                Date dateSql = new Date(dateTime);
                Time timeSql = new Time(dateTime);

                RestoAppController.reserveTable(dateSql, timeSql, Integer.parseInt(numberInPartyTextField.getText()),
                        contactNameTextField.getText().trim(), contactEmailAddressTextField.getText().trim(),
                        contactPhoneNumberTextField.getText().trim(), selectedTables);

            } catch (NumberFormatException | InvalidInputException | ParseException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void startOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        String[] stringArray = selectedTablesTextField.getText().trim().split("\\s+");// remove any leading, trailing
        // white spaces and split the
        // string from rest of the white
        // spaces
        int[] intArray = new int[stringArray.length]; // create a new int array to store the int values

        if (intArray.length == 0) {
            error = "Enter the set of table(s) # that you want to start order for";
        } else {

            try {
                for (int i = 0; i < stringArray.length; i++) {
                    intArray[i] = Integer.parseInt(stringArray[i]);// parse the integer value and store it in the int
                    // array
                }

                for (int i = 0; i < intArray.length; i++)
                    for (int j = i + 1; j < intArray.length; j++)
                        if (j != i && intArray[j] == intArray[i]) {
                            error = "Do not enter duplicate table #";
                            throw (new InvalidInputException(error));
                        }

                List<Table> tables = RestoAppController.getTables();
                List<Table> selectedTables = new ArrayList<Table>();

                outerloop:
                for (int tableNumber : intArray) {
                    int i = 1;
                    for (Table table : tables) {
                        if (tableNumber == (table.getNumber())) {
                            selectedTables.add(table);
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

        if (selectedOrderIndex < 0) {
            error = "Order needs to be selected to end it!";

        } else {
            try {

                RestoAppController.endOrder(orders.get(selectedOrderIndex));

            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }
    
    //***
    protected void AddSeatToOrderItemButtonActionPerformed(ActionEvent evt) {
    	//everytime pressed add to list selectedSeatsToOrderList
    	//
    }
    
  /* protected void cancelItemButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectorderitem < 0){
            error = "An order item must be specified for cancelling. ";
        }

        try{
            RestoAppController.cancelOrderItem(orderitem);
        } catch(InvalidInputException e){
            error = e.getMessage();
        }
    }

    protected void cancelSeatButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectseat < 0){
            error = "An seat must be specified for cancelling its order items. ";
        }

        try{
            RestoAppController.cancelOrderItemForSeat(seat);
        } catch(InvalidInputException e){
            error = e.getMessage();
        }
    }


    protected void cancelTableButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selecttable < 0){
            error = "A table must be specified for cancelling its order items. ";
        }

        try{
            RestoAppController.cancelOrder(table);
        } catch(InvalidInputException e){
            error = e.getMessage();
        }
    }

    protected void cancelOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectorder < 0){
            error = "An order must be specified for cancelling its order items. ";
        }

        try{
            RestoAppController.cancelOrder(order);
        } catch(InvalidInputException e){
            error = e.getMessage();
        }
    }*/

    private void refreshData() {
        // error
        errorMessage.setText(error);
        if (error == null || error.length() == 0) {

            // populate page with data
            // TextField
            // create table
            numberOfSeatsTextField.setText("");
            widthTextField.setText("");
            lengthTextField.setText("");
            xLocationTextField.setText("");
            yLocationTextField.setText("");
            // update table
            newNumberOfSeatsTextField.setText("");
            newTableNumberTextField.setText("");
            // reserve table
            numberInPartyTextField.setText("");
            contactNameTextField.setText("");
            contactEmailAddressTextField.setText("");
            contactPhoneNumberTextField.setText("");
            dateTextField.setText("");
            reservationDate.setText("");
            // start order
            selectedTablesTextField.setText("");
            orderDate.setText("");
            // LabelField
            // select table
            numberSeatsLabel.setText("Seats: ");
            tableLocationXLabel.setText("Location X: ");
            tableLocationYLabel.setText("Location Y: ");
            tableWidthLabel.setText("Width: ");
            tableLengthLabel.setText("Length: ");
            // reserve table
            contactEmailAddressLabel.setText("Contact E-mail address: ");
            contactNameLabel.setText("Contact Name:");
            contactPhoneNumberLabel.setText("Contact Phone: ");
            numberInPartyLabel.setText("Number of People: ");
            reservedStatusLabel.setText("Reservation Status: Unreserved ");
            tableStatusLabel.setText("Table Status: ");

            // select table - also combo box for table visualization
            tables = new HashMap<Integer, Table>();
            tableList.removeAllItems();

            Integer index = 0;
            for (Table table : RestoAppController.getTables()) {
                tables.put(index, table);
                tableList.addItem("" + table.getNumber());
                index++;
            }

            tableList.setSelectedIndex(selectedTableIndex);
            reservationList.setSelectedIndex(selectedReservationIndex);
            ordersList.setSelectedIndex(selectedOrderIndex);

        }
    }
    //TODO
}