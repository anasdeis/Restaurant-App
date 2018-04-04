package ca.mcgill.ecse223.resto.view;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;

public class BillPage extends JFrame {
    private JScrollPane ScrollPaneItem;
    private String error;
    private DefaultListModel<Bill> listModel = new DefaultListModel<>();
    private JList<Bill> list = new JList<>(listModel);
    
    public BillPage() {
        setTitle("Bill");
        getContentPane().setLayout(null);
        setSize(453, 394);
        
        ScrollPaneItem = new JScrollPane(list);
        this.add(ScrollPaneItem);
        ScrollPaneItem.setBounds(0, 0, 453, 394);
        
        setVisible(true);
        
    }
    public BillPage(List<Seat> selectedSeatList) throws InvalidInputException {
        
        /*
         if (!selectedSeatList.isEmpty()) {
         new BillPage();
         }
         */
        try {
            RestoAppController.issueBill(selectedSeatList);
            ScrollPaneItem = new JScrollPane(list);
            for (Seat seat : selectedSeatList) {
                for (Bill bill : seat.getBills()) {
                    listModel.addElement(bill);
                }
            }
        } catch (InvalidInputException e) {
            error = "Selected Seat List is Empty!";
        }
        /*
         for (Seat seat : selectedSeatList) {
         getContentPane().add((Component) seat.getBills());
         }
         */
        setVisible(true);
    }
}

