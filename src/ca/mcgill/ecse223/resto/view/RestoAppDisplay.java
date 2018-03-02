package ca.mcgill.ecse223.resto.view;

import java.awt.*;
import javax.swing.*;

public class RestoAppDisplay extends JPanel {
	
	public RestoAppDisplay() {
		super();

	}
	
	 protected void paintComponent(Graphics g){	//paint component
		
		int height = getHeight();
		 
		g.drawArc(20, height-50, 100, 50, 0, 180); //the feet
		g.drawLine(70,height-50,70,30);
		g.drawLine(70, 30, 170, 30);
		g.drawLine(170, 30, 170, 70);
	 }

}
