package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;


public class RestoAppController {

	public static List<Table> getTables(){
		return RestoApplication.getRestoApp().getCurrentTables();
	}
	
	public static void createTable(int numberOfSeats, int x, int y, int width, int length) throws InvalidInputException {	
		RestoApp ra = RestoApplication.getRestoApp();
		int generatedTableNumber = RestoAppController.generateTableNumber();

		String error = "";
		if (numberOfSeats <= 0) {
			error = "The number of seats must be greater than 0. ";
		}
		if (x < 0) {
			error = error + "The x location must be non-negative. ";
		}
		if (y < 0) {
			error = error + "The y location must be non-negative. ";
		}
		if (width <= 0) {
			error = error + "The width must be non-negative. ";
		}
		if (length <= 0) {
			error = error + "The location must be non-negative. ";
		}
		if (isDuplicateTableNumber(generatedTableNumber)){
			error = error + "Table with table number " + generatedTableNumber + " already exists. ";
		}
		if(isTableOverlapping(x,y,width,length)){
			error = error + "Table will overlap with another table. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try {
			Table table = new Table(generatedTableNumber, x, y, width, length, ra);
			for(int i = 0; i < numberOfSeats; i++){
				table.addCurrentSeat(new Seat(table));
			}
			ra.addCurrentTable(table);
			RestoApplication.save();

		}
		catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void removeTable(Integer selectedTableNumber) throws InvalidInputException {
		RestoApp ra = RestoApplication.getRestoApp();
		String error = "";

		try {
			for (Table table : ra.getCurrentTables()) {
				if(selectedTableNumber.equals(table.getNumber())) {
					ra.removeCurrentTable(table);
					break;
				}
			}

			RestoApplication.save();

		}
		catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}

	}
	public static void reserveTable(Date aDate, int aNumberInParty, String aContactName, String aContactEmailAddress, String aContactPhoneNumber, Integer selectedTableNumber) throws InvalidInputException {
		String error = "";
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> allTables = ra.getCurrentTables();
		Table [] allTablesArray = allTables.toArray(new Table[allTables.size()]);
		
		Reservation reservation = new Reservation(aDate, aNumberInParty, aContactName, aContactEmailAddress, aContactPhoneNumber, ra, allTablesArray);
		try {
			for (Table table : allTables) {
				if(selectedTableNumber.equals(table.getNumber())) {
					table.addReservation(reservation);
					break;
				}
			}
			RestoApplication.save();
		}
		catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	private static boolean isDuplicateTableNumber(int number) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		for(int i = 0; i< tables.size(); i++){
			if(tables.get(i).getNumber() == number){
				return true;
			}
		}
		return false;
	}
	private static boolean isTableReserved(Integer selectedTableNumber) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		List<Reservation> reservations = ra.getReservations();
		for (Reservation reservation : reservations) {
			
		}
		return false;
	}
	
	public static int generateTableNumber() {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		if(tables.size() == 0) {
			return 1;
		}
		else {
			return tables.get(tables.size()-1).getNumber() + 1;
		}

	}

	// Checks if the table that you are trying to input overlaps with another
	// It starts counting at index [0][0] for x and y.
	public static boolean isTableOverlapping(int x, int y, int width, int length) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		int[][] tableMap = new int[9999][9999];

		for(int k = 0; k < tables.size(); k++){
			for(int i = 0; i< tables.get(k).getWidth(); i++){
				for(int j = 0; j < tables.get(k).getLength(); j++){
					tableMap[tables.get(k).getX() + i][tables.get(k).getY() + j] = 1;
				}
			}
		}
		for(int i = 0; i< width; i++){
			for(int j = 0; j < length; j++){
				if(tableMap[i + x][j + y] == 1){
					return true;
				}
			}
		}
		return false;
	}

	public static ArrayList<MenuItem> getMenuItem(ItemCategory itemCategory) throws InvalidInputException {

		if (itemCategory.equals(null)) {
			throw new InvalidInputException("Item category is null"); 
		}

		RestoApp resto = RestoApplication.getRestoApp();
		ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();
		Menu menu = resto.getMenu();

		for (int j = 0; j < menu.numberOfMenuItems(); j++) {
			MenuItem currItem = menu.getMenuItem(j);
			if (currItem.getItemCategory().equals(itemCategory)) {
				itemList.add(currItem);
			}
		}
		return itemList; 
	}

	public static void moveTable(Table table, int x, int y) throws InvalidInputException {

		String error = "";
		if(table == null){
			error += "Table cannot be null! ";
		}

		if(x < 0){
			error += "x cannot be negative! ";
		}

		if(y < 0){
			error += "y cannot be negative! ";
		}

		if (error.length() > 0){
			throw (new InvalidInputException(error.trim()));
		}

		int width = table.getWidth();
		int length = table.getLength();
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();

		try {
			for (Table currentTable : currentTables) {
				if ((isTableOverlapping(x, y, width, length))) {
					throw (new InvalidInputException("Tables overlap! Change table configuration."));
				}
			}

			table.setX(x);
			table.setY(y);
			RestoApplication.save();
			
			} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		}

	}
	public static void addMenuItem(String name, String category, String price) throws InvalidInputException{
        String error = "";
        RestoApp ra = RestoApplication.getRestoApp();
        
        MenuItem newMenuItem = new MenuItem(name, ra.getMenu());
        
        if(category.equals("Appetizer")) {
            newMenuItem.setItemCategory(MenuItem.ItemCategory.Appetizer);
        }else if(category.equalsIgnoreCase("Main")) {
            newMenuItem.setItemCategory(MenuItem.ItemCategory.Main);
        }else if(category.equalsIgnoreCase("Dessert")) {
            newMenuItem.setItemCategory(MenuItem.ItemCategory.Dessert);
        }else if(category.equalsIgnoreCase("Alcoholic Beverage")) {
            newMenuItem.setItemCategory(MenuItem.ItemCategory.AlcoholicBeverage);
        }else if(category.equalsIgnoreCase("Non Alcoholic Beverage")) {
            newMenuItem.setItemCategory(MenuItem.ItemCategory.NonAlcoholicBeverage);
        }

        
        int dotCount=0;
        for(int i=0; i < price.length(); i++) {
            
            if((price.charAt(i)>47 && price.charAt(i)<58)|| price.charAt(i)==46) { // Check if price is only numbers and .
                if(price.charAt(i)==46) {
                    dotCount++; // Keep track of how many . (there can only be 1)
                }
            }else {
                throw new InvalidInputException("Invalid price format (ex: 2.99");
            }

        }
        
        if (dotCount > 1 ) {
            throw new InvalidInputException("You cannot have more than one decimal point");
        }
        
        double priceDouble = Double.parseDouble(price);
        
        if(priceDouble<0) {
            throw new InvalidInputException("Cannot have a negative price");
        }else if(priceDouble==0) {
            throw new InvalidInputException("Cannot have 0 as price");
        }else {
            PricedMenuItem newPricedMenuItem = ra.addPricedMenuItem(priceDouble, newMenuItem);
        }
        
        RestoApplication.save();    
        
    }
	public static void updateTable(Table table, int newNumber, int numOfSeats) throws InvalidInputException{
        RestoApp ra = RestoApplication.getRestoApp();
        
        String error = "";
        if (table == null) {
            error = error + "The table does not exist, please add the table first";
        }
        if (numOfSeats <= 0) {
            error = error + "The number of seats must be greater than 0";
        }
        
        if (newNumber < 0) {
            error = error + "You entered a negative number. Please add a positive table number";
        }
        
        if (table.hasReservations() == true) {
            error = error + "The table is reserves, you can not update its details";
        }
        
        if (isDuplicateTableNumber(newNumber)) {
            error = error + "The new table number already exists. Please choose another number";
        }
        
        List<Order> currentOrders = ra.getCurrentOrders();
        for (int i=currentOrders.size(); i>0; i--) {
            List <Table> tables = currentOrders.get(i).getTables();
            boolean inUse = tables.contains(table);
            
            if (inUse == true) {
                error = "Table is in use, choose another table";
            }
        }
        
        try {
            table.setNumber(newNumber);
            int n = table.numberOfCurrentSeats();
            
            if (numOfSeats > n) {
                for (int j=numOfSeats-n; j>0; j--) {
                    Seat seatAdd = table.addSeat();
                    table.addCurrentSeat(seatAdd);
                }
            }
            else if (numOfSeats < n) {
                for (int j=n-numOfSeats; j>0; j--) {
                    Seat seatToRemove = table.getCurrentSeat(0);
                    table.removeCurrentSeat(seatToRemove);
                }
            }
            RestoApplication.save();
        }
        catch (Exception e){
            error = e.getMessage();
            throw new InvalidInputException(e.getMessage());
        }
    }
}
