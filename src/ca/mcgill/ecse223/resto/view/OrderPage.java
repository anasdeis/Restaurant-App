package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Table;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class OrderPage extends JFrame {
    private JComboBox<String> orderList;
    private String error;
    private Table table;
    List<OrderItem> ordersForTable;

    OrderPage(Integer tableIndex) throws Exception {
        setTitle("View Orders");
        getContentPane().setLayout(null);
        setSize(453, 394);
        setVisible(true);
     
        orderList = new JComboBox<String>();
        table = RestoAppController.getTables().get(tableIndex);
        ordersForTable = RestoAppController.getOrderedItemForOneTable(table);

    }
    



    
}