package ca.mcgill.ecse223.resto.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;

public class DiscontinuedMenuDisplay  extends JFrame {
	private String error;
	
	
	public DiscontinuedMenuDisplay() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Discontinued");
		JList<Object> itemList;
		List<MenuItem> menuItems = RestoApplication.getRestoApp().getMenu().getMenuItems();
		String display="";
		boolean noPrice = false;
		ArrayList<String> discontinuedItems = new ArrayList<>();
		for(int i =0; i< menuItems.size();i++) {
			MenuItem current = menuItems.get(i);
			if(current.getCurrentPricedMenuItem() == null) {
				String name = current.getName();
				List<PricedMenuItem>pricedMenuItems = current.getPricedMenuItems();
				double price1=0;
				double price2 = 0;
				char category = current.getItemCategory().toString().charAt(0);
				if(pricedMenuItems.size()==1) {
					price1 = pricedMenuItems.get(0).getPrice();
				}else if(pricedMenuItems.size()==2){
					price1 = pricedMenuItems.get(1).getPrice();
					price2 = pricedMenuItems.get(0).getPrice();			
				}else if(pricedMenuItems.size()==0){
					noPrice = true ;
								
				}else {
					//if it has history of more than 2 prices, only show the most recent 2
					price1 = pricedMenuItems.get(pricedMenuItems.size()-1).getPrice();
					price2 = pricedMenuItems.get(pricedMenuItems.size()-2).getPrice();
				}

				DecimalFormat df = new DecimalFormat ("####0.00");
				if(current.getItemCategory().equals(MenuItem.ItemCategory.AlcoholicBeverage)) {
					display = name + "("+ category+ current.getItemCategory().toString().charAt(1)+ ")"; //(Al)
				}else {
					display = name + "("+ category+ ")";
				}
				
				if (noPrice) {
					display = display +" price unknown";
				}
				if (price1!=0) {
					display = display + " was " + df.format(price1);
				}
				if (price2!=0) {
					display = display + "; "+df.format(price2);
				}
				discontinuedItems.add(display);
			}
					
		}
		itemList=new JList<>(discontinuedItems.toArray());
		JScrollPane discontinuedScrollPane = new JScrollPane(itemList);
		
		getContentPane().add(discontinuedScrollPane);
		pack();
		setSize(320, 200);
		setVisible(true);
		
	}
}
