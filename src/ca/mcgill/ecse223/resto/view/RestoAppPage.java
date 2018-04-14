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
import ca.mcgill.ecse223.resto.model.*;

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

    int selectedTableIndex = -1;
    int selectedSeatIndex = -1;
    int selectedTableNumber = -1;

    private RestoAppDisplay displayApp;

    // Move Table
    private JButton moveTableButton;
    private List<Table> tables;

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

    private JLabel numberInPartyLabel;
    private JLabel contactNameLabel;
    private JLabel contactEmailAddressLabel;
    private JLabel contactPhoneNumberLabel;
    private JLabel reservedStatusLabel;
    private JLabel tableStatusLabel;
    private JLabel dateLabel;
    private JLabel reservationDate;
    private JLabel reservationLabel;

    private JTextField numberInPartyTextField;
    private JTextField contactNameTextField;
    private JTextField contactEmailAddressTextField;
    private JTextField contactPhoneNumberTextField;
    private JTextField dateTextField;

    int selectedReservationIndex = -1;

    private JComboBox<String> reservationList;
    private List<Reservation> reservationslist;

    //Orders
    private JButton startOrderButton;
    private JButton endOrderButton;
    private JButton viewOrdersButton;
    private JButton cancelSeatButton;

    private JLabel orderDate;
    private JLabel ordersLabel;

    int selectedOrderIndex = -1;

    private JComboBox<String> ordersList;
    private JComboBox<String> selectedTablesList;

    private List<Order> orders;
    private List<Table> selectedTables;
    List<Reservation> reservations;

    //Add Order
    private JButton addOrderItemButton;
    private JButton addTableButton;
    private JButton clearTableButton;

    private JLabel orderItemLabel;
    private JLabel categoryLabel;
    private JLabel orderItemQuantityLabel;

    private JTextField orderItemName;
    private JTextField orderItemQuantityTextField;

    private JComboBox orderItemCategory;

    // Seats

    private JButton addSeatButton;
    private JButton clearSeatButton;

    private JLabel seatsLabel;
    private JLabel selectedSeatsLabel;
    private JLabel seatStatusLabel;

    private JComboBox<String> seatList;
    private JComboBox<String> selectedSeatsList;

    private List<Seat> seats;
    private List<Seat> selectedSeats;

    // Bill
    private JButton issueBill;
    private JButton issueBillTable;
    private JButton issueBillOrder;

    // Waiter
    private JLabel userLabel;
    private JLabel userNameLabel;
    private JLabel waiterNameLabel;
    private JLabel waiterEmailAddressLabel;
    private JLabel waiterPhoneNumberLabel;
    private JButton addWaiterButton;
    private JTextField waiterNameTextField;
    private JTextField waiterPhoneNumberTextField;
    private JTextField waiterEmailAddressTextField;
    private JComboBox<String> waiterList;
    private JButton loginWaiterButton;
    private Integer selectedWaiterIndex = -1;

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

        // Update Table Elements
        newNumberOfSeatsLabel = new JLabel("New Seat # :");
        newTableNumberLabel = new JLabel("New Table # :");

        // Reserve Table elements
        numberInPartyLabel = new JLabel("Number of People:");
        contactEmailAddressLabel = new JLabel("Contact E-mail address:");
        contactPhoneNumberLabel = new JLabel("Contact Phone:");
        contactNameLabel = new JLabel("Contact Name:");
        reservedStatusLabel = new JLabel("Reservation Status:");
        reservedStatusLabel.setForeground(Color.red);
        tableStatusLabel = new JLabel("Table Status: ");
        tableStatusLabel.setForeground(Color.red);
        dateLabel = new JLabel("Set date and time (yyyy/MM/dd HH:mm:ss)");
        reservationDate = new JLabel();
        reservationLabel = new JLabel("Reservations:");

        numberInPartyTextField = new JTextField();
        contactEmailAddressTextField = new JTextField();
        contactPhoneNumberTextField = new JTextField();
        contactNameTextField = new JTextField();
        numberInPartyTextField = new JTextField();
        dateTextField = new JTextField();

        reservationList = new JComboBox<String>(new String[0]);

        // startOrder
        ordersLabel = new JLabel("Orders:");

        ordersList = new JComboBox<String>(new String[0]);
        orderDate = new JLabel();

        // seats
        seatsLabel = new JLabel("Seats: ");
        selectedSeatsLabel = new JLabel("Selected Seats: ");
        seatStatusLabel = new JLabel();
        seatStatusLabel.setForeground(Color.red);

        seatList = new JComboBox<String>(new String[0]);
        selectedSeatsList = new JComboBox<String>(new String[0]);
        selectedTablesList = new JComboBox<String>(new String[0]);

        //orderMenuItem
        categoryLabel = new JLabel("Category: ");
        orderItemLabel = new JLabel("Order Item: ");
        orderItemQuantityLabel = new JLabel("Quantity: ");

        orderItemQuantityTextField = new JTextField();
        orderItemName = new JTextField();

        orderItemCategory = new JComboBox<String>(new String[0]);
        orderItemCategory.setFont(new Font("Arial Narrow", Font.BOLD, 13));
        orderItemCategory.setBounds(68, 253, 140, 22);
        //getContentPane().add(orderItemCategory);

        orderItemCategory.addItem("");
        orderItemCategory.addItem("Appetizer");
        orderItemCategory.addItem("Main");
        orderItemCategory.addItem("Dessert");
        orderItemCategory.addItem("Alcoholic Beverage");
        orderItemCategory.addItem("Non Alcoholic Beverage");
        orderItemCategory.setToolTipText("Item category of the item to add/remove");

        reservationslist = new ArrayList<Reservation>(RestoApplication.getRestoApp().getReservations());

        for (Reservation reservation : reservationslist) {
            if (reservation.getDate().getTime() < Calendar.getInstance().getTime().getTime()) {
                reservation.delete();
            }
        }

        // Waiter
        userLabel = new JLabel("User: ");
        try {
            userNameLabel = new JLabel(RestoAppController.getCurrentUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        userNameLabel.setForeground(Color.BLUE);
        waiterNameLabel = new JLabel("Waiter Name: ");
        waiterEmailAddressLabel = new JLabel("Waiter Email Address: ");
        waiterPhoneNumberLabel = new JLabel("Waiter Phone Number: ");
        waiterNameTextField = new JTextField();
        waiterPhoneNumberTextField = new JTextField();
        waiterEmailAddressTextField = new JTextField();
        addWaiterButton = new JButton("Add Waiter");
        addWaiterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createWaiterButtonActionPerformed(evt);
            }
        });
        loginWaiterButton = new JButton("Login");
        loginWaiterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginWaiterButtonActionPerformed(evt);
            }
        });

        waiterList = new JComboBox<String>(new String[0]);
        waiterList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedWaiterIndex = cb.getSelectedIndex();
            }
        });


        tableList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedTableIndex = cb.getSelectedIndex();

                if (selectedTableIndex != -1) {
                    selectedTableNumber = Integer.parseInt(cb.getSelectedItem().toString());
                    List<Table> tables = RestoAppController.getTables();
                    for (Table table : tables) {
                        if (selectedTableNumber == table.getNumber()) {
                            seats = new ArrayList<Seat>();
                            seatList.removeAllItems();
                            int seatIndex = 1;

                            List<Seat> seatsInTable = table.getCurrentSeats();

                            for (Seat seat : seatsInTable) {
                                seats.add(seat);
                                seatList.addItem("" + seatIndex++);
                            }

                            numberSeatsLabel.setText("Seats: " + table.getCurrentSeats().size());
                            tableLocationXLabel.setText("Location X: " + table.getX());
                            tableLocationYLabel.setText("Location Y: " + table.getY());
                            tableWidthLabel.setText("Width: " + table.getWidth());
                            tableLengthLabel.setText("Length: " + table.getLength());
                            tableStatusLabel.setText("Table Status: " + table.getStatusFullName());

                            if (table.hasReservations()) {
                                reservedStatusLabel.setText("Reservation Status: "
                                        + table.getReservations().size() + " reservation(s)");
                            } else {
                                reservedStatusLabel.setText("Reservation Status: Unreserved");
                            }
                        }
                    }
                } else {
                    refreshData();
                }
            }
        });

        seatList.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedSeatIndex = cb.getSelectedIndex();
                List<Order> currentOrders = RestoApplication.getRestoApp().getCurrentOrders();

                if (selectedSeatIndex != -1 && selectedTableIndex != -1) {
                    Seat seat = seats.get(selectedSeatIndex);
                    Table table = seat.getTable();
                    List<Order> currentOrdersList = RestoApplication.getRestoApp().getCurrentOrders();
                    boolean current = false;

                    if (seat.hasOrderItems()) {
                        Order lastOrder = table.getOrder(table.numberOfOrders() - 1);
                        if (currentOrdersList.contains(lastOrder)) {
                            List<OrderItem> orderItems = seat.getOrderItems();
                            for (OrderItem orderItem : orderItems) {
                                Order order = orderItem.getOrder();
                                if (lastOrder.equals(order)) {
                                    current = true;
                                    seatStatusLabel.setText("Occupied");
                                }
                            }
                        }
                    }

                    if (!current) {
                        seatStatusLabel.setText("Vacant");
                    }

                    if (table.hasOrders() && currentOrders.contains(table.getOrder(table.numberOfOrders() - 1))) {
                        List<Bill> bills = table.getOrder(table.numberOfOrders() - 1).getBills();

                        if (seat.hasBills() && bills.contains(seat.getBill(seat.numberOfBills() - 1))) {
                            seatStatusLabel.setText("Billed");
                        }
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

                if (selectedOrderIndex != -1) {
                    Order order = orders.get(selectedOrderIndex);
                    orderDate.setText(order.getDate().toString() + " " + order.getTime().toString());
                } else {
                    refreshData();
                }
            }
        });

        clearSeatButton = new JButton("Clear Seats");
        clearSeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSeatButtonActionPerformed(evt);
            }
        });

        clearTableButton = new JButton("Clear Tables");
        clearTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearTableButtonActionPerformed(evt);
            }
        });

        addOrderItemButton = new JButton("Order Item");
        addOrderItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderItemButtonActionPerformed(evt);
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

        issueBill = new JButton("Issue Bill Seats");
        issueBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueBillButtonActionPerformed(evt);
            }
        });

        issueBillTable = new JButton("Issue Bill Table");
        issueBillTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueBillTableButtonActionPerformed(evt);
            }
        });

        issueBillOrder = new JButton("Issue Bill Order");
        issueBillOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueBillOrderButtonActionPerformed(evt);
            }
        });

        reserveButton = new JButton("Reserve Table(s)");
        reserveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserveButtonActionPerformed(evt);
            }
        });

        deleteReservationButton = new JButton("Cancel Whole Reservation");
        deleteReservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteReservationButtonActionPerformed(evt);
            }
        });

        deleteTableReservationButton = new JButton("Cancel Table Reservation");
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

        addSeatButton = new JButton("Select Seat");
        addSeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSeatButtonActionPerformed(evt);
            }
        });

        addTableButton = new JButton("Select Table");
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

        cancelSeatButton = new JButton("Cancel Seat");
        cancelSeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelSeatButtonActionPerformed(evt);
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

                .addGroup(layout.createParallelGroup().addComponent(displayApp, 1250, 1250, 1250)

                        .addComponent(errorMessage).addComponent(horizontalLineTop).addComponent(horizontalLineBottom))

                .addGroup(layout.createSequentialGroup()

                        .addGroup(layout.createParallelGroup()
                                .addComponent(userLabel)
                                .addComponent(waiterNameLabel)
                                .addComponent(waiterPhoneNumberLabel)
                                .addComponent(waiterEmailAddressLabel)
                                .addComponent(numberOfSeatsLabel)
                                .addComponent(widthLabel)
                                .addComponent(lengthLabel)
                                .addComponent(xLocationLabel)
                                .addComponent(yLocationLabel)
                                .addComponent(newTableNumberLabel)
                                .addComponent(newNumberOfSeatsLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(userNameLabel)
                                .addComponent(waiterNameTextField)
                                .addComponent(waiterPhoneNumberTextField)
                                .addComponent(waiterEmailAddressTextField)
                                .addComponent(addWaiterButton)
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
                                .addComponent(addTableButton)
                                .addComponent(clearTableButton)
                                .addComponent(waiterList)
                                .addComponent(loginWaiterButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(tableList)
                                .addComponent(removeTableButton)
                                .addComponent(reservedStatusLabel)
                                .addComponent(contactNameLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(numberInPartyLabel)
                                .addComponent(dateLabel)
                                .addComponent(reservationLabel)
                                .addComponent(selectedTablesList)
                                .addComponent(reserveButton)
                                .addComponent(startOrderButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(menuButton)
                                .addComponent(viewOrdersButton)
                                .addComponent(tableStatusLabel)
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
                                .addComponent(selectedSeatsLabel)
                                .addComponent(categoryLabel)
                                .addComponent(orderItemLabel)
                                .addComponent(orderItemQuantityLabel)
                                .addComponent(ordersLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(seatList)
                                .addComponent(selectedSeatsList)
                                .addComponent(orderItemCategory)
                                .addComponent(orderItemName)
                                .addComponent(orderItemQuantityTextField)
                                .addComponent(addOrderItemButton)
                                .addComponent(issueBill)
                                .addComponent(issueBillTable)
                                .addComponent(ordersList)
                                .addComponent(orderDate)
                                .addComponent(endOrderButton)
                                .addComponent(issueBillOrder))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(seatStatusLabel)
                                .addComponent(addSeatButton)
                                .addComponent(clearSeatButton)
                                .addComponent(cancelSeatButton)
                        )
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
                                .addComponent(seatStatusLabel))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(numberOfSeatsLabel)
                                .addComponent(numberOfSeatsTextField)
                                .addComponent(numberSeatsLabel)
                                .addComponent(removeTableButton)
                                .addComponent(viewOrdersButton)
                                .addComponent(selectedSeatsLabel)
                                .addComponent(selectedSeatsList)
                                .addComponent(addSeatButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(widthLabel)
                                .addComponent(widthTextField)
                                .addComponent(tableWidthLabel)
                                .addComponent(reservedStatusLabel)
                                .addComponent(tableStatusLabel)
                                .addComponent(categoryLabel)
                                .addComponent(orderItemCategory)
                                .addComponent(clearSeatButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(lengthLabel)
                                .addComponent(lengthTextField)
                                .addComponent(tableLengthLabel)
                                .addComponent(contactNameLabel)
                                .addComponent(contactNameTextField)
                                .addComponent(orderItemLabel)
                                .addComponent(orderItemName)
                                .addComponent(cancelSeatButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(xLocationLabel)
                                .addComponent(xLocationTextField)
                                .addComponent(tableLocationXLabel)
                                .addComponent(contactEmailAddressLabel)
                                .addComponent(contactEmailAddressTextField)
                                .addComponent(orderItemQuantityLabel)
                                .addComponent(orderItemQuantityTextField))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(yLocationLabel)
                                .addComponent(yLocationTextField)
                                .addComponent(tableLocationYLabel)
                                .addComponent(contactPhoneNumberLabel)
                                .addComponent(contactPhoneNumberTextField)
                                .addComponent(addOrderItemButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(createTableButton)
                                .addComponent(numberInPartyLabel)
                                .addComponent(numberInPartyTextField)
                                .addComponent(issueBill))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(moveTableButton)
                                .addComponent(dateLabel)
                                .addComponent(dateTextField)
                                .addComponent(issueBillTable))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(newTableNumberLabel)
                                .addComponent(newTableNumberTextField)
                                .addComponent(reservationLabel)
                                .addComponent(reservationList)
                                .addComponent(ordersLabel)
                                .addComponent(ordersList))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(newNumberOfSeatsLabel)
                                .addComponent(newNumberOfSeatsTextField)
                                .addComponent(addTableButton)
                                .addComponent(selectedTablesList)
                                .addComponent(reservationDate)
                                .addComponent(orderDate))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(updateTableButton)
                                .addComponent(clearTableButton)
                                .addComponent(reserveButton)
                                .addComponent(deleteReservationButton)
                                .addComponent(endOrderButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(waiterNameLabel)
                                .addComponent(waiterNameTextField)
                                .addComponent(startOrderButton)
                                .addComponent(deleteTableReservationButton)
                                .addComponent(issueBillOrder))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(waiterPhoneNumberLabel)
                                .addComponent(waiterPhoneNumberTextField)
                                .addComponent(waiterList))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(waiterEmailAddressLabel)
                                .addComponent(waiterEmailAddressTextField)
                                .addComponent(loginWaiterButton))

                        .addGroup(layout.createParallelGroup()
                                .addComponent(addWaiterButton))

                        .addComponent(horizontalLineBottom)

                        .addGroup(layout.createParallelGroup()
                                .addComponent(displayApp, 350, 350, 350))));
        pack();

    }


    protected void createTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        try {
            RestoAppController.createTable(Integer.parseInt(numberOfSeatsTextField.getText()),
                    Integer.parseInt(xLocationTextField.getText()), Integer.parseInt(yLocationTextField.getText()),
                    Integer.parseInt(widthTextField.getText()), Integer.parseInt(lengthTextField.getText()));
        } catch (RuntimeException | InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
        repaint();
    }

    protected void removeTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "A table must be specified for removing. ";
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

        if (selectedSeatIndex != -1 && selectedTableIndex != -1) {
            if (!selectedSeats.contains(seats.get(selectedSeatIndex))) {
                selectedSeats.add(seats.get(selectedSeatIndex));
                int seatNumber = selectedSeatIndex + 1;
                selectedSeatsList.addItem("Table #" + selectedTableNumber + " ; Seat #" + seatNumber);
            }
        }
        repaint();
    }

    protected void addTableButtonActionPerformed(ActionEvent evt) {

        if (selectedTableIndex != -1) {
            if (!selectedTables.contains(tables.get(selectedTableIndex))) {
                selectedTables.add(tables.get(selectedTableIndex));
                selectedTablesList.addItem("Table #" + selectedTableNumber);
            }
        }
        repaint();
    }

    protected void clearSeatButtonActionPerformed(ActionEvent evt) {
        selectedSeats = new ArrayList<Seat>();
        selectedSeatsList.removeAllItems();
        repaint();
    }

    protected void clearTableButtonActionPerformed(ActionEvent evt) {
        selectedTables = new ArrayList<Table>();
        selectedTablesList.removeAllItems();
        repaint();
    }

    protected void addOrderItemButtonActionPerformed(ActionEvent evt) {
        error = "";

        if (selectedSeats.isEmpty()) {
            error = "Select seats first to order items. ";
        }
        if (orderItemCategory.getSelectedIndex() <= 0) {
            error += "Select an item category. ";
        } else {

            String itemName = orderItemName.getText().trim();
            String itemCategory = (orderItemCategory.getItemAt(orderItemCategory.getSelectedIndex())).toString();
            String quantity = orderItemQuantityTextField.getText().trim();
            List<Seat> seatsToOrder = new ArrayList<Seat>();
            for (Seat aSeat : selectedSeats) {
                seatsToOrder.add(aSeat);
            }

            try {
                RestoAppController.orderMenuItem(itemName, itemCategory, Integer.parseInt(quantity), seatsToOrder);
            } catch (RuntimeException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void moveTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "A table must be specified for moving. ";
        } else {
            try {
                RestoAppController.moveTable(tables.get(selectedTableIndex),
                        Integer.parseInt(xLocationTextField.getText()),
                        Integer.parseInt(yLocationTextField.getText()));
            } catch (InvalidInputException | RuntimeException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void updateTableButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTableIndex < 0) {
            error = "A table must be specified for updating. ";
        } else {
            try {
                RestoAppController.updateTable(tables.get(selectedTableIndex),
                        Integer.parseInt(newTableNumberTextField.getText()),
                        Integer.parseInt(newNumberOfSeatsTextField.getText()));
            } catch (RuntimeException | InvalidInputException e) {
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

        new OrderPage();

    }

    protected void deleteReservationButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedReservationIndex < 0) {
            error = "A reservation must be specified for deleting. ";
        } else {
            try {
                RestoAppController.endReservation(reservations.get(selectedReservationIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void deleteTableReservationButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedReservationIndex < 0 || selectedTableIndex < 0) {
            error = "A table and its reservation needs to be specified for deleting. ";
        } else {

            try {
                RestoAppController.deleteTableReservation(reservations.get(selectedReservationIndex), tables.get(selectedTableIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }


    protected void reserveButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedTables.isEmpty()) {
            error = "Select the tables first to reserve. ";
        } else {
            try {
                String myDate = dateTextField.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                java.util.Date date = sdf.parse(myDate);
                long dateTime = date.getTime();
                Date dateSql = new Date(dateTime);
                Time timeSql = new Time(dateTime);

                RestoAppController.reserveTable(dateSql, timeSql, Integer.parseInt(numberInPartyTextField.getText()),
                        contactNameTextField.getText().trim(), contactEmailAddressTextField.getText().trim(),
                        contactPhoneNumberTextField.getText().trim(), selectedTables);

            } catch (RuntimeException | InvalidInputException | ParseException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void startOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedTables.isEmpty()) {
            error = "Select the tables first to start order. ";
        } else {
            try {

                RestoAppController.startOrder(selectedTables);

            } catch (RuntimeException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void endOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedOrderIndex < 0) {
            error = "An order must be specified to end it. ";

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

    protected void issueBillButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedSeats.isEmpty()) {
            error = "Select the seats first to issue bill. ";
        } else {
            try {
                RestoAppController.issueBill(selectedSeats);
                new BillPage(selectedSeats);
            } catch (RuntimeException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void issueBillTableButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedTableIndex < 0) {
            error = "A table has to be specified first to issue bill. ";
        } else {
            try {
                RestoAppController.issueBill(tables.get(selectedTableIndex));
                new BillPage(tables.get(selectedTableIndex).getCurrentSeats());
            } catch (RuntimeException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void issueBillOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedOrderIndex < 0) {
            error = "An order has to be specified to first to issue bill. ";
        } else {

            List<Table> tables = orders.get(selectedOrderIndex).getTables();
            List<Seat> seats = new ArrayList<Seat>();

            try {
                for (Table table : tables) {
                    seats.addAll(table.getCurrentSeats());
                }
                RestoAppController.issueBill(orders.get(selectedOrderIndex));
                new BillPage(seats);
            } catch (RuntimeException | InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
        repaint();
    }

    protected void cancelSeatButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedSeatIndex < 0 || selectedTableIndex < 0) {
            error = "A seat and its table must be specified for cancelling its order items. ";
        } else {

            try {
                RestoAppController.cancelOrderItemForSeat(seats.get(selectedSeatIndex));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }

        refreshData();
        repaint();
    }

    protected void createWaiterButtonActionPerformed(ActionEvent evt) {

        error = null;

        try {
            String name = waiterNameTextField.getText();
            String phoneNumber = waiterPhoneNumberTextField.getText();
            String emailAddress = waiterEmailAddressTextField.getText();
            RestoAppController.createWaiter(name, phoneNumber, emailAddress);

        } catch (Exception e) {
            error = e.getMessage();
        }

        refreshData();
        repaint();
    }

    protected void loginWaiterButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (selectedWaiterIndex < 0) {
            error = "A waiter has to be specified to login.";
        } else {

            try {
                String waiterToLogin = waiterList.getItemAt(selectedWaiterIndex);
                for (Waiter waiter : RestoAppController.getWaiters()) {
                    if (waiter.getWaiterName().equals(waiterToLogin)) {
                        RestoAppController.login(waiter);
                    }
                }

                userNameLabel.setText(RestoAppController.getCurrentUser());
            } catch (Exception e) {
                error = e.getMessage();
            }
        }

        refreshData();
        repaint();
    }

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
            reservedStatusLabel.setText("Reservation Status:");
            tableStatusLabel.setText("Table Status: ");

            seatStatusLabel.setText("");
            orderItemName.setText("");
            orderItemQuantityTextField.setText("");
            orderItemCategory.setSelectedIndex(0);
            // select table - also combo box for table visualization
            tables = new ArrayList<Table>();
            tableList.removeAllItems();
            List<Integer> tablesNumbers = new ArrayList<Integer>();

            for (Table table : RestoAppController.getTables()) {
                tables.add(table);
                tablesNumbers.add(table.getNumber());
            }

            try {
                Collections.sort(tablesNumbers);
            } catch (RuntimeException e) {
                e.printStackTrace();
                error += e.getMessage();
            }
            for (int tablenumber : tablesNumbers) {
                tableList.addItem("" + tablenumber);
            }

            // select order - also combo box for order visualization
            orders = new ArrayList<Order>();
            ordersList.removeAllItems();
            List<Order> currentOrders = RestoApplication.getRestoApp().getCurrentOrders();


            for (Order order : currentOrders) {
                List<Table> orderTables = order.getTables();
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (Table table : orderTables) {
                    list.add(table.getNumber());
                }
                orders.add(order);
                ordersList.addItem("Order #" + order.getNumber() + " for table(s): " + list.toString());
            }

            if (orders.isEmpty() || orders == null) {
                orderDate.setText("No current orders");
            }

            // select reservation - also combo box for reservation visualization
            reservations = new ArrayList<Reservation>();
            reservationList.removeAllItems();

            List<Reservation> reservationsApp = RestoApplication.getRestoApp().getReservations();
            List<Long> reservationTimeArray = new ArrayList<Long>();

            int reservationIndex = 1;
            for (Reservation reservation : reservationsApp) {
                reservationTimeArray.add(reservation.getDate().getTime());
            }
            try {
                Collections.sort(reservationTimeArray);
            } catch (RuntimeException e) {
                e.printStackTrace();
                error += e.getMessage();
            }
            for (long time : reservationTimeArray) {
                for (Reservation reservation : reservationsApp) {
                    if (time == reservation.getDate().getTime()) {
                        List<Table> reservationTables = reservation.getTables();
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        for (Table table : reservationTables) {
                            list.add(table.getNumber());
                        }
                        reservations.add(reservation);
                        reservationList.addItem("Reservation #" + reservationIndex++ + " for table(s) " + list.toString());
                    }
                }
            }


            if (reservations.isEmpty() || reservations == null) {
                reservationDate.setText("No reservations");
            }

            if (selectedTableIndex < 0) {
                seats = new ArrayList<Seat>();
                seatList.removeAllItems();
                selectedSeats = new ArrayList<Seat>();
                selectedSeatsList.removeAllItems();
                selectedTables = new ArrayList<Table>();
                selectedTablesList.removeAllItems();
            }

            tableList.setSelectedIndex(selectedTableIndex);
            reservationList.setSelectedIndex(selectedReservationIndex);
            ordersList.setSelectedIndex(selectedOrderIndex);
            seatList.setSelectedIndex(selectedSeatIndex);

            //Waiter
            waiterNameTextField.setText("");
            waiterPhoneNumberTextField.setText("");
            waiterEmailAddressTextField.setText("");

            waiterList.removeAllItems();
            try {
                for (Waiter waiter : RestoAppController.getWaiters()) {
                    waiterList.addItem(waiter.getWaiterName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            selectedWaiterIndex = -1;
            waiterList.setSelectedIndex(selectedWaiterIndex);

        }
    }
}

