package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;

public class BillPage extends JFrame {
    private String error = null;
    private JLabel errorMessage;
    private JComboBox<String> orderItemList;
    private JComboBox<String> orderList;
    
    private JLabel orderItemsLabel;
    private JLabel totalLabel;
    //private JLabel orderItemDetailsLabel;
    
    public BillPage() {
        
    }
    
    public BillPage(List<Seat> selectedSeats) throws InvalidInputException {
        setTitle("Bills");
        getContentPane().setLayout(null);
        setSize(453, 394);
        double total = 0;
        
        orderItemList = new JComboBox<String>();
        orderItemsLabel = new JLabel("Order Item(s): ");
        totalLabel = new JLabel("Total: ");
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        error = null;
        orderItemList.removeAllItems();
        int i = 1;
        /*
         for (Seat seat : selectedSeats) {
         for (Bill bill : seat.getBills()) {
         for (OrderItem item : bill.getOrder().getOrderItems()) {
         orderItemList.addItem(i++ + ". " + item.getPricedMenuItem()
         .getMenuItem().getName()  + " , Quantity: " + item.getQuantity() + " , Price: " + item.getPricedMenuItem().getPrice());
         }
         */
        for (Seat seat : selectedSeats) {
            for (OrderItem item : seat.getOrderItems()) {
                orderItemList.addItem(i++ + ". " + item.getPricedMenuItem()
                                      .getMenuItem().getName()  + " , Quantity: " + item.getQuantity() + " , Price: $" + item.getPricedMenuItem().getPrice());
                total = total + item.getQuantity() * item.getPricedMenuItem().getPrice();
                
            }
        }
        totalLabel.setText("Total: $" + total);
        
        
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup()
                                                      .addComponent(errorMessage))
                                            .addGroup(layout.createParallelGroup()
                                                      .addComponent(orderItemsLabel)
                                                      .addComponent(totalLabel)
                                                      )
                                            .addGroup(layout.createParallelGroup()
                                                      .addComponent(orderItemList)
                                                      )
                                            )
                                  );
        
        layout.setVerticalGroup(layout.createParallelGroup()
                                
                                .addGroup(layout.createSequentialGroup()
                                          .addGroup(layout.createParallelGroup()
                                                    .addComponent(errorMessage))
                                          .addGroup(layout.createParallelGroup()
                                                    .addComponent(orderItemsLabel)
                                                    .addComponent(orderItemList)
                                                    )
                                          .addGroup(layout.createParallelGroup()
                                                    .addComponent(totalLabel)
                                                    )));
        pack();
        
        setVisible(true);
        
        refreshData();
        
    }
    
    
    protected void refreshData() {
        errorMessage.setText(error);
        if (error == null || error.length() == 0) {
            //totalLabel.setText("Total: ");
            orderList.removeAllItems();
            
        }
        pack();
    }
    
}

