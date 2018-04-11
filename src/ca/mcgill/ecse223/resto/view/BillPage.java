package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.resto.model.*;

public class BillPage extends JFrame {

    private JComboBox<String> orderItemList;

    private JLabel orderItemsLabel;
    private JLabel totalLabel;

    private static DecimalFormat df2;


    public BillPage() {
    }

    public BillPage(List<Seat> selectedSeats) {
        setTitle("Bills");
        getContentPane().setLayout(null);
        setSize(453, 394);
        double total = 0;
        df2 = new DecimalFormat(".##");
        df2.setRoundingMode(RoundingMode.UP);

        orderItemList = new JComboBox<String>();
        orderItemsLabel = new JLabel("Order Item(s): ");
        totalLabel = new JLabel("Total: ");

        orderItemList.removeAllItems();
        int i = 1;
        List<OrderItem> list = new ArrayList<OrderItem>();

        for (Seat seat : selectedSeats) {

            Table table = seat.getTable();
            Order order = table.getOrder(table.numberOfOrders() - 1);

            for (OrderItem item : seat.getOrderItems()) {
                Order itemorder = item.getOrder();
                if (itemorder.equals(order) && !list.contains(item)) {
                    list.add(item);
                    orderItemList.addItem(i++ + ". " + item.getPricedMenuItem()
                            .getMenuItem().getName() + " / Quantity: " + item.getQuantity() + " / Shared by " +
                            item.getSeats().size() + " / Price: $" + item.getPricedMenuItem().getPrice());
                    total = total + (item.getQuantity() * (item.getPricedMenuItem().getPrice()) / (item.getSeats().size()));
                }
            }
        }

        totalLabel.setText("Total: $" + df2.format(total));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderItemsLabel)
                                .addComponent(totalLabel)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderItemList)
                        )));

        layout.setVerticalGroup(layout.createParallelGroup()

                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(orderItemsLabel)
                                .addComponent(orderItemList)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(totalLabel)
                        )));
        pack();

        setVisible(true);

    }
}

