package ca.mcgill.ecse223.resto.view;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;

public class BillPageModeOne extends JFrame {
	private JScrollPane ScrollPaneItem;
	private DefaultListModel<OrderItem> listOfOrderItem = new DefaultListModel<>();
	private JList<OrderItem> Item = new JList<>(listOfOrderItem);
	private JScrollPane ScrollPaneQuantity;
	private DefaultListModel<Integer> listOfQuantity = new DefaultListModel<>();
	private JList<Integer> Quantity = new JList<>(listOfQuantity);
	private JScrollPane ScrollPanePrice;
	private DefaultListModel<Double> listOfPrice = new DefaultListModel<>();
	private JList<Double> Price = new JList<>(listOfPrice);
    private String error;
    
    BillPageModeOne() {
    	setTitle("Bill");
    	getContentPane().setLayout(null);
    	setSize(453, 394);
    	
    	
    	ScrollPaneItem = new JScrollPane(Item);
		this.add(ScrollPaneItem);
		ScrollPaneItem.setBounds(0, 0, 297, 225);
		
		setVisible(true);
		
    }

}
