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
    private JLabel orderItemDetailsLabel;
    
    public BillPage() {
    	 setTitle("Bills");
         getContentPane().setLayout(null);
         setSize(453, 394);

         orderItemList = new JComboBox<String>();
         orderItemsLabel = new JLabel("Order Item(s): ");
         orderItemDetailsLabel = new JLabel("Order Item details");
         errorMessage = new JLabel();
         errorMessage.setForeground(Color.RED);

    	 error = null;
         orderItemList.removeAllItems();
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
                                 .addComponent(orderItemDetailsLabel)      
                         )
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(orderItemList)   
                         )
                 )
         );
         
         layout.setVerticalGroup(layout.createParallelGroup()

                 .addGroup(layout.createSequentialGroup()
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(errorMessage)
                                 .addComponent(orderItemsLabel)
                                 .addComponent(orderItemList)
                         )
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(orderItemDetailsLabel)
                         )
                         ));
         pack();
         setVisible(true);
         
       


         refreshData();
         
    }
    
    public BillPage(List<Seat> selectedSeats) throws InvalidInputException {
    	 setTitle("Bills");
         getContentPane().setLayout(null);
         setSize(453, 394);

         orderItemList = new JComboBox<String>();
         orderItemsLabel = new JLabel("Order Item(s): ");
         orderItemDetailsLabel = new JLabel("Order Item details");
         errorMessage = new JLabel();
         errorMessage.setForeground(Color.RED);

    	 error = null;
         orderItemList.removeAllItems();
		  int i = 1;
		  for (Seat seat : selectedSeats) {
		       for (Bill bill : seat.getBills()) {
		           for (OrderItem item : bill.getOrder().getOrderItems()) {
		             	orderItemList.addItem(i++ + ". " + item.getPricedMenuItem()
		                .getMenuItem().getName()  + " , Quantity: " + item.getQuantity() + " , Price: " + item.getPricedMenuItem().getPrice());
		           
		           }
		             	}
		             }
         
         
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
                         )
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(orderItemList)   
                         )
                 )
         );
         
         layout.setVerticalGroup(layout.createParallelGroup()

                 .addGroup(layout.createSequentialGroup()
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(errorMessage)
                                 .addComponent(orderItemsLabel)
                                 .addComponent(orderItemList)
                         )
                         ));
         pack();
     
         setVisible(true);
       
         refreshData();
         
     }

    
    protected void refreshData() {
        errorMessage.setText(error);
        if (error == null || error.length() == 0) {
            orderList.removeAllItems();
            
        }
        pack();
    }

}

