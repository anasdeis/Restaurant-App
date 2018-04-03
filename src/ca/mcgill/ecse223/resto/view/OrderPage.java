package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import java.util.List;
import java.awt.Color;

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
	private JComboBox<String> orderList;
	private JLabel tablesLabel;
	private JLabel orderItemsLabel;
	private JLabel orderItemDetailsLabel;
	private JLabel quantityLabel;
	private JLabel orderNumberLabel;
	private JLabel orderListLabel;

	private Table selectedTable;

	JSeparator horizontalLineTop = new JSeparator();
	JSeparator horizontalLineBottom = new JSeparator();

	List<OrderItem> ordersForTable;

	OrderPage() throws Exception {
		initComponents();
	}	


	protected void initComponents() {

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
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);



		List<Table> tables = RestoAppController.getTables();
		for (Table table : tables) {
			tableList.addItem(table.getNumber()+"");
		}

		tableList.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				Integer tableNumber = Integer.parseInt(cb.getSelectedItem().toString());
				for (Table table : tables) {
					if(tableNumber.equals(table.getNumber())) {
						selectedTable = table;
						refreshData();
						try {
							List<OrderItem> orderItems = RestoAppController.getOrderItems(table);
							for (OrderItem orderItem : orderItems) {
								orderItemList.addItem(orderItem.getPricedMenuItem().getMenuItem().getName());
							}
						} catch (InvalidInputException e) {
							error = e.getMessage();
						}
					}
				}
			}
			
		});
		orderItemList.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				String selectedOrderItem = cb.getSelectedItem().toString();

				try {
					List<OrderItem> orderItems = RestoAppController.getOrderItems(selectedTable);
					for (OrderItem orderItem : orderItems) {
						if(selectedOrderItem.equals(orderItem.getPricedMenuItem().getMenuItem().getName())) {
							quantityLabel.setText("Quantity: " + orderItem.getQuantity());
							orderNumberLabel.setText("Order Number: " + orderItem.getOrder().getNumber());
						}
					}
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}

				refreshData();
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
								.addComponent(orderList)
								)));
		pack();
	}
	protected void refreshData() {
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
			orderList.removeAllItems();
			tableList.removeAllItems();
			quantityLabel.setText("Quantity: ");
			orderNumberLabel.setText("Order Number: ");

			List<Table> tables = RestoAppController.getTables();
			for (Table table : tables) {
				tableList.addItem(table.getNumber()+"");
			}
			List<Order> orders = selectedTable.getOrders();
			for (Order order : orders) {
				orderList.addItem(order.getNumber()+"");
			}

		}
		pack();
	}
}