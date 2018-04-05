package ca.mcgill.ecse223.resto.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Table;

public class OrderPage extends JFrame {
    private String error = null;
    private JLabel errorMessage;

    private JComboBox<String> tableList;
    private JComboBox<String> orderItemList;
    private JComboBox<String> orderList;

    private JButton cancelItemButton;
    private JButton cancelTableButton;
    private JButton cancelOrderButton;

    private JLabel tablesLabel;
    private JLabel orderItemsLabel;
    private JLabel orderItemDetailsLabel;
    private JLabel quantityLabel;
    private JLabel orderNumberLabel;
    private JLabel orderListLabel;
    private JLabel orderDateLabel;

    private Integer selectedTableIndex = -1;
    private Integer selectedOrderIndex = -1;
    private Integer selectedItemIndex = -1;
    private Integer tableNumber = -1;
    private Table selectedTable;
    private Integer cbSelectedItem;

    private HashMap<Integer, Table> tables;
    private HashMap<Integer, Order> orders;
    private HashMap<Integer, OrderItem> items;

    JSeparator horizontalLineTop = new JSeparator();
    JSeparator horizontalLineBottom = new JSeparator();

    List<OrderItem> ordersForTable;

    public OrderPage() {
        initComponents();
    }


    private void initComponents() {

        setTitle("View Orders");
        getContentPane().setLayout(null);
        setSize(453, 394);
        setVisible(true);

        tableList = new JComboBox<String>();
        orderItemList = new JComboBox<String>();
        orderList = new JComboBox<String>();

        tablesLabel = new JLabel("Table: ");
        orderItemsLabel = new JLabel("Order Item(s): ");
        orderItemDetailsLabel = new JLabel("Order Item details");
        quantityLabel = new JLabel("Quantity: ");
        orderNumberLabel = new JLabel("Order Number: ");
        orderListLabel = new JLabel("Orders: ");
        orderDateLabel = new JLabel();
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        List<Table> tables = RestoAppController.getTables();
        for (Table table : tables) {
            tableList.addItem(table.getNumber() + "");
        }

        tableList.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                error = null;
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedTableIndex = cb.getSelectedIndex();
                if (selectedTableIndex != -1) {
                    Integer tableNumber = Integer.parseInt(cb.getSelectedItem().toString());
                    for (Table table : tables) {
                        if (tableNumber.equals(table.getNumber())) {
                            selectedTable = table;

                            // select order - also combo box for order visualization
                            orders = new HashMap<Integer, Order>();
                            orderList.removeAllItems();
                            List<Order> currentOrders = RestoApplication.getRestoApp().getCurrentOrders();

                            Integer orderIndex = 0;

                            for (Order order : currentOrders) {
                                if (table.getOrders().contains(order)) {
                                    orders.put(orderIndex, order);
                                    orderList.addItem("Order #" + (order.getNumber()));
                                    orderIndex++;
                                }
                            }

                            try {
                                items = new HashMap<Integer, OrderItem>();
                                orderItemList.removeAllItems();

                                Integer itemIndex = 0;
                                List<OrderItem> orderItems = RestoAppController.getOrderItems(table);
                                for (OrderItem orderItem : orderItems) {
                                    items.put(itemIndex, orderItem);
                                    orderItemList.addItem(orderItem.getPricedMenuItem().getMenuItem().getName());
                                    itemIndex++;
                                }

                            } catch (InvalidInputException e) {
                                error = e.getMessage();
                            }
                        }
                    }
                }
                refreshData();
            }
        });
        orderItemList.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                cbSelectedItem = cb.getSelectedIndex();
                if (cbSelectedItem != -1 && selectedTableIndex != 1) {
                    //                 String selectedOrderItem = cb.getSelectedItem().toString(); You can still use this instead
                    // of HashMap, but my HashMap now works and is more intuitive than comparing strings.
                    OrderItem i = items.get(cbSelectedItem);

                    try {
                        List<OrderItem> orderItems = RestoAppController.getOrderItems(selectedTable);
                        for (OrderItem orderItem : orderItems) {
                            if (i.equals(orderItem)) {
                                quantityLabel.setText("Quantity: " + orderItem.getQuantity());
                                orderNumberLabel.setText("Order Number: " + orderItem.getOrder().getNumber());
                            }
                        }
                    } catch (InvalidInputException e) {
                        error = e.getMessage();
                    }
                }
            }
        });
        orderList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JComboBox<String> cb = (JComboBox<String>) evt.getSource();
                selectedOrderIndex = cb.getSelectedIndex();

                if (selectedOrderIndex != -1) {
                    Order order = orders.get(selectedOrderIndex);
                    orderDateLabel.setText(order.getDate().toString() + " " + order.getTime().toString());
                } else {
                    refreshData();
                }
            }
        });

        cancelItemButton = new JButton("Cancel Item");
        cancelItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelItemButtonActionPerformed(evt);
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
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(errorMessage))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tablesLabel)
                                .addComponent(cancelItemButton)
                                .addComponent(cancelTableButton)
                                .addComponent(cancelOrderButton))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tableList))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderItemsLabel)
                                .addComponent(orderItemDetailsLabel)
                                .addComponent(horizontalLineTop)
                                .addComponent(quantityLabel)
                                .addComponent(orderNumberLabel)
                                .addComponent(horizontalLineBottom)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderItemList)
                                .addComponent(orderListLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderList)
                        )
                )
        );

        layout.setVerticalGroup(layout.createParallelGroup()

                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(errorMessage)
                                .addComponent(tablesLabel)
                                .addComponent(tableList)
                                .addComponent(orderItemsLabel)
                                .addComponent(orderItemList)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(cancelItemButton)
                                .addComponent(orderItemDetailsLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(cancelTableButton)
                                .addComponent(horizontalLineTop)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(cancelOrderButton)
                                .addComponent(quantityLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderNumberLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(horizontalLineBottom)
                                .addComponent(orderListLabel)
                                .addComponent(orderList)
                        )));
        pack();
    }

    protected void cancelItemButtonActionPerformed(ActionEvent evt) {

        error = null;

        if (cbSelectedItem < 0 || selectedTableIndex < 0) {
            error = "An order item and its table must be specified for cancelling. ";
        } else {

            try {
                RestoAppController.cancelOrderItem(items.get(cbSelectedItem));
            } catch (InvalidInputException e) {
                error = e.getMessage();
            }
        }
        refreshData();
    }

    protected void cancelTableButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedTableIndex < 0) {
            error = "A table must be specified for cancelling its order items. ";
        }

        try {
            RestoAppController.cancelOrder(selectedTable);
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }
        refreshData();
    }

    protected void cancelOrderButtonActionPerformed(ActionEvent evt) {
        error = null;

        if (selectedOrderIndex < 0 || selectedTableIndex < 0) {
            error = "An order and its corresponding table must be specified for cancelling its order items. ";
        }

        try {
            RestoAppController.cancelOrder(orders.get(selectedOrderIndex));
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }
        refreshData();
    }

    protected void refreshData() {
        errorMessage.setText(error);
        if (error == null || error.length() == 0) {

            quantityLabel.setText("Quantity: ");
            orderNumberLabel.setText("Order Number: ");

            tableList.removeAllItems();

            // select table - also combo box for table visualization
            tables = new HashMap<Integer, Table>();
            Integer index = 0;
            List<Table> currentTables = RestoAppController.getTables();
            for (Table table : currentTables) {
                tableList.addItem(table.getNumber() + "");
                index++;
            }

            tableList.setSelectedIndex(selectedTableIndex);

            if (selectedTableIndex < 0)
                orderItemList.removeAllItems();

            orderItemList.setSelectedIndex(selectedItemIndex);
        }
        pack();
    }
}