package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppDisplay extends JPanel {

    public RestoAppDisplay() {
        super();
        setFocusable(true);
    }

    protected void paintComponent(Graphics g) {    //paint component

        RestoAppController.getTables();
        for (Table table : RestoAppController.getTables()) {
            g.drawRect(table.getX(), table.getY(), table.getWidth(), table.getLength());

        }
    }

}
