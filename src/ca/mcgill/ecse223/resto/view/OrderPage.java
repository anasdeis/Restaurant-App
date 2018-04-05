package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
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

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;

public class OrderPage extends JFrame {
	private String error = null;
	private JLabel errorMessage;

	private JComboBox<String> tableList;
	private JComboBox<String> orderItemList;
	private JComboBox<String> ordersList;

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

	private HashMap<Integer, Table> tables;
	private HashMap<Integer, Order> orders;
	private HashMap<Integer, OrderItem> orderItems;

	int i;

	JSeparator horizontalLineTop = new JSeparator();
	JSeparator horizontalLineBottom = new JSeparator();

	List<OrderItem> ordersForTable;

	public OrderPage() {
		initComponents();
		refreshData();
	}


	private void initComponents() {

		setTitle("View Orders");
		getContentPane().setLayout(null);
		setSize(453, 394);
		setVisible(true);

		tableList = new JComboBox<String>(new String[0]);
		orderItemList = new JComboBox<String>(new String[0]);
		ordersList = new JComboBox<String>(new String[0]);

		tablesLabel = new JLabel("Table: ");
		orderItemsLabel = new JLabel("Order Item(s): ");
		orderItemDetailsLabel = new JLabel("Order Item details");
		quantityLabel = new JLabel("Quantity: ");
		orderNumberLabel = new JLabel("Order Number: ");
		orderListLabel = new JLabel("Orders: ");
		orderDateLabel = new JLabel();
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		i = 0;

		tableList.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				i++;
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedTableIndex = cb.getSelectedIndex();
				System.out.println("so " + selectedTableIndex);
				//		if (i == 1) {
				//			selectedTableIndex = -1;
				//		}
				if (selectedTableIndex != -1) {

					Table table = tables.get(selectedTableIndex);

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
					} catch (RuntimeException e) {
						error = e.getMessage();
						selectedTableIndex = -1;
						selectedItemIndex = -1;
						selectedOrderIndex = -1;
						refreshData();
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
					try {
						System.out.println("works");
						Integer itemIndex = 0;
						orderItems = new HashMap<Integer, OrderItem>();
						orderItemList.removeAllItems();
						System.out.println("works2");
						List<OrderItem> orderItemsList = RestoAppController.getOrderItems(table);
						System.out.println("works3");
						for (OrderItem orderItem : orderItemsList) {
							orderItems.put(itemIndex, orderItem);
							orderItemList.addItem(orderItem.getPricedMenuItem().getMenuItem().getName());
							itemIndex++;
						}
					} catch (InvalidInputException e) {
						error = e.getMessage();
						selectedTableIndex = -1;
						selectedItemIndex = -1;
						selectedOrderIndex = -1;
						refreshData();
					}

				} else{
					selectedItemIndex = -1;
					selectedOrderIndex = -1;
					refreshData();
				}
			}

		});
		orderItemList.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedItemIndex = cb.getSelectedIndex();

				if(selectedItemIndex != -1) {
					try {
						List<OrderItem> orderItems = RestoAppController.getOrderItems(tables.get(selectedTableIndex));
						if (orderItems != null || !orderItems.isEmpty()) {
							String orderItemName = orderItems.get(selectedItemIndex).getPricedMenuItem().getMenuItem().getName();
							for (OrderItem orderItem : orderItems) {
								if (orderItemName.equals(orderItem.getPricedMenuItem().getMenuItem().getName())) {
									quantityLabel.setText("Quantity: " + orderItem.getQuantity());
									orderNumberLabel.setText("Order Number: " + orderItem.getOrder().getNumber());
								}
							}
						}
					} catch (InvalidInputException e) {
						error = e.getMessage();
					}
				}
				refreshData();
			}
		});

		ordersList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedOrderIndex = cb.getSelectedIndex();

				if (selectedOrderIndex > -1) {
					Order order = orders.get(selectedOrderIndex);
					orderDateLabel.setText(order.getDate().toString() + " " + order.getTime().toString());
				} else {
					refreshData();
				}
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
								.addComponent(tablesLabel))
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
								.addComponent(ordersList)
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
								.addComponent(orderItemDetailsLabel)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineTop)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(quantityLabel)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(orderNumberLabel)
						)
						.addGroup(layout.createParallelGroup()
								.addComponent(horizontalLineBottom)
								.addComponent(orderListLabel)
								.addComponent(ordersList)
						)));
		pack();
	}

	protected void refreshData() {
		errorMessage.setText(error);
		System.out.println("hello " + selectedTableIndex);
		if (error == null || error.length() == 0) {

			tableList.removeAllItems();
			orderItemList.removeAllItems();
			ordersList.removeAllItems();

			quantityLabel.setText("Quantity: ");
			orderNumberLabel.setText("Order Number: ");
			orderDateLabel.setText("");

			// select table - also combo box for table visualization
			tables = new HashMap<Integer, Table>();

			Integer index = 0;
			for (Table table : RestoAppController.getTables()) {
				tables.put(index, table);
				tableList.addItem("" + table.getNumber());
				index++;
			}

			tableList.setSelectedIndex(selectedTableIndex);
			ordersList.setSelectedIndex(selectedOrderIndex);
			orderItemList.setSelectedIndex(selectedItemIndex);

		}
		pack();
	}
}