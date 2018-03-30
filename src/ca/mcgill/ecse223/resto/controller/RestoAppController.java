package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppController {

	public static List<Table> getTables() {
		return RestoApplication.getRestoApp().getCurrentTables();
	}

	public static void createTable(int numberOfSeats, int x, int y, int width, int length)
			throws InvalidInputException {
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
		if (isTableOverlapping(x, y, width, length)) {
			error = error + "Table will overlap with another table. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		try {

			Table table = new Table(generatedTableNumber, x, y, width, length, ra);
			ra.addCurrentTable(table);

			for (int i = 0; i < numberOfSeats; i++) {
				Seat seat = table.addSeat();
				table.addCurrentSeat(seat);
			}

			RestoApplication.save();

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void removeTable(Table table) throws InvalidInputException {
		RestoApp r = RestoApplication.getRestoApp();
		String error = "";

		if (table == null) {
			error += "Table cannot be null! ";
		}

		if (table.hasReservations()) {
			error += "Table is reserved! ";
		}

		if (error.length() > 0) {
			throw (new InvalidInputException(error.trim()));
		}

		List<Order> currentOrders = r.getCurrentOrders();
		for (Order order : currentOrders) {
			List<Table> tables = order.getTables();
			if (tables.contains(table)) {
				throw (new InvalidInputException("Table is in use!"));
			}
		}

		r.removeCurrentTable(table);

		try {
			RestoApplication.save();

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void reserveTable(Date aDate, Time aTime, int aNumberInParty, String aContactName,
			String aContactEmailAddress, String aContactPhoneNumber, Table[] aTables, Integer selectedTableNumber)
			throws InvalidInputException {
		String error = "";
		RestoApp ra = RestoApplication.getRestoApp();
		long time = System.currentTimeMillis();
		java.sql.Date dateNow = new java.sql.Date(time);

		if (aDate == null || dateNow.compareTo(aDate) > 0) {
			error = error + "Please enter the date of the reservation. Make sure it is in the future.";
		}

		if (aTime == null) {
			error = error + "Please enter the time of the reservation";
		}

		if (aContactName == null) {
			error = error + "Please enter your name";
		}

		if (aContactEmailAddress == null) {
			error = error + "Please enter your email address";
		}

		if (aContactPhoneNumber == null) {
			error = error + "Please enter your phone number";
		}

		if (aNumberInParty <= 0) {
			error = error + "Please enter a positive number (larger than 0)";
		}

		if (aContactPhoneNumber == null) {
			error = error + "Please enter your phone number";
		}

		List<Table> allTables = ra.getCurrentTables();
		Table[] allTablesArray = allTables.toArray(new Table[allTables.size()]);
		int seatCapacity = 0;

		try {
			for (Table table : allTables) {
				if (selectedTableNumber.equals(table.getNumber())) {

					seatCapacity = table.numberOfCurrentSeats();

					List<Reservation> reservationsInTable = table.getReservations();

					for (Reservation reservationa : reservationsInTable) {
						if ((reservationa.getDate().compareTo(aDate) == 0)
								&& (reservationa.getTime().compareTo(aTime) == 0)) {
							error = error + "Please choose another time, table reserved at the requested time";
							break;
						}
					}

					if (seatCapacity < aNumberInParty) {
						error = error + "There are no enough seats on this table. Please choose another table";
					}

					Reservation reservation = new Reservation(aDate, aTime, aNumberInParty, aContactName,
							aContactEmailAddress, aContactPhoneNumber, ra, aTables);
					table.addReservation(reservation);
					break;
				}
			}
			RestoApplication.save();
		} catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void startOrder(List<Table> tables) throws InvalidInputException {

		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		String error = "";

		if (tables == null) {
			throw (new InvalidInputException("tables is null, please add a table! "));
		}
		for (Table table : tables) {
			if (!currentTables.contains(table)) {
				throw (new InvalidInputException("Not a current table! "));
			}
		}

		boolean orderCreated = false;
		Order newOrder = null;

		for (Table table : tables) {
			if (orderCreated) {
				table.addToOrder(newOrder);
			} else {
				Order lastOrder = null;
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				}

				table.startOrder();
				if ((table.numberOfOrders() > 0) && (!table.getOrder(table.numberOfOrders() - 1).equals(lastOrder))) {
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders() - 1);
				}
			}
		}

		if (!orderCreated) {
			throw (new InvalidInputException("No order created! "));
		}

		r.addCurrentOrder(newOrder);

		try {

			RestoApplication.save();

		} catch (RuntimeException e) {
			throw (new InvalidInputException(e.getMessage()));
		}
	}

	public static void endOrder(Order order) throws InvalidInputException {

		RestoApp r = RestoApplication.getRestoApp();
		List<Order> currentOrders = r.getCurrentOrders();
		String error = "";

		if (order == null) {
			throw (new InvalidInputException("order is null, please add an order! "));
		}

		if (!currentOrders.contains(order)) {
			throw (new InvalidInputException("Not a current order! "));
		}

		List<Table> tables = order.getTables();

		for (Table table : tables) {
			if ((table.numberOfOrders() > 0) && (table.getOrder(table.numberOfOrders() - 1).equals(order))) {
				table.endOrder(order);
			}
		}

		if (allTablesAvailableOrDifferentCurrentOrder(tables, order)) {
			r.removeCurrentOrder(order);
		}

		try {

			RestoApplication.save();

		} catch (RuntimeException e) {
			throw (new InvalidInputException(e.getMessage()));
		}
	}

	private static boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order) {
		for (Table table : tables) {
			if ((table.getStatusFullName() == "Available")
					|| (!((table.getOrder(table.numberOfOrders() - 1)).equals(order)))) {
				return true;
			}
		}
		return false;
	}

	public static Seat getSpecificSeat(Table table, Integer seatIndex) throws Exception {
		List<Seat> seats = getSeatForEachCustomerAtOneTable(table);

		return seats.get(seatIndex);
	}
	
	public static Table getSpecificTable(Integer tableIndex) {
		return RestoApplication.getRestoApp().getCurrentTables().get(tableIndex);
	}

	/*
	 * situation1 Issued a bill for one table situation2 Issued a bill for each
	 * customer in one table situation3 Issued a bill for group of customers in one
	 * table situation4 Issued a bill for group of customers in different tables
	 */
	// shared helper method for displaying ordered items and prices
	public static List<Integer> getQuantityOfOrderedItem(List<OrderItem> orderList) {
		List<Integer> listOfQuantity = new ArrayList<Integer>();
		for (OrderItem individualOrderItem : orderList) {
			listOfQuantity.add(individualOrderItem.getQuantity());
		}
		return listOfQuantity;
	}

	// shared helper method for displaying ordered items and prices
	public static List<Double> getPriceOfOrderedItem(List<OrderItem> orderList) {
		List<Double> listOfPrice = new ArrayList<Double>();
		for (OrderItem individualOrderItem : orderList) {
			listOfPrice.add(individualOrderItem.getPricedMenuItem().getPrice());
		}
		return listOfPrice;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Situation1 Get all ordered items for the whole table
	public static List<OrderItem> getOrderedItemForOneTable(Table table) throws Exception {
		// test if the table input is valid, if not, throw exceptions
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> tables = r.getCurrentTables();
		boolean tableExist = false;

		for (Table currentTable : tables) {
			if (currentTable.equals(table)) {
				tableExist = true;
			}
		}
		if (tableExist == false) {
			throw new Exception("The input table does not exist");
		}
		if (table.getOrders() == null)
			throw new Exception("This table has no order!");

		List<OrderItem> orderTotal = new ArrayList<OrderItem>();
		List<Seat> currentSeat = table.getCurrentSeats();
		for (Seat seat : currentSeat) {
			for (OrderItem individualOrderedItem : seat.getOrderItems()) {
				orderTotal.add(individualOrderedItem);
			}
		}
		return orderTotal;
	}

	// situation2 Get ordered seats for each customers in one table
	public static List<Seat> getSeatForEachCustomerAtOneTable(Table table) throws Exception {
		// test if the table input is valid, if not, throw exceptions
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> tables = r.getCurrentTables();
		boolean tableExist = false;
		for (Table currentTable : tables) {
			if (currentTable.equals(table)) {
				tableExist = true;
			}
		}
		if (tableExist == false) {
			throw new Exception("This table does not exist");
		}
		if (table.getOrders() == null)
			throw new Exception("This table has no order!");
		if (!table.hasCurrentSeats())
			throw new Exception("This is no seat in this table!");
		// get a list of all current seats
		List<Seat> currentSeat = table.getCurrentSeats();
		return currentSeat;
	}

	// situation2 Get ordered items for each seat in on table (use the same order as
	// seatList to get all orderList one by one)
	public static List<OrderItem> getOrderForEacgCustomerAtOneTable(Seat seat) {
		return seat.getOrderItems();
	}

	// situation3 Get ordered items for a group of customers in one table
	public static List<OrderItem> getOrderForGroupCustomerAtOneTable(List<Seat> seatList) throws Exception {
		// test if the table seats are in one table, if not, throw exceptions
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> tables = r.getCurrentTables();
		for (Table individualtable : tables) {
			if (individualtable.getCurrentSeats().contains(seatList.get(0))) {
				for (Seat seat : seatList) {
					if (!individualtable.getCurrentSeats().contains(seat)) {
						throw new Exception("The input seats are not in the same table");
					}
				}

			}
		}
		// get ordered items for the group of seats
		List<OrderItem> orderTotal = new ArrayList<OrderItem>();
		for (Seat seat : seatList) {
			for (OrderItem order : seat.getOrderItems()) {
				orderTotal.add(order);
			}
		}
		return orderTotal;
	}

	// situation4 Get ordered items for a group of customers in multiple groups
	public static List<OrderItem> getOrderForGroupCustomerATMultiTable(List<Seat> seatList) {
		// get ordered items for the group of seats
		List<OrderItem> orderTotal = new ArrayList<OrderItem>();
		for (Seat seat : seatList) {
			for (OrderItem order : seat.getOrderItems()) {
				orderTotal.add(order);
			}
		}
		return orderTotal;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Double issuedBill(List<Integer> orderedQuantity, List<Double> price) throws Exception {
		if (orderedQuantity.size() > price.size())
			throw new Exception("Missing price!");
		if (orderedQuantity.size() < price.size())
			throw new Exception("Missing order item!");
		Double total = 0.0;
		for (int i = 0; i < orderedQuantity.size(); i++) {
			total = total + orderedQuantity.get(i) * price.get(i);
		}
		return total;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int generateTableNumber() {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		if (tables.size() == 0) {
			return 1;
		} else {
			return tables.get(tables.size() - 1).getNumber() + 1;
		}

	}

	// Checks if the table that you are trying to input overlaps with another
	// It starts counting at index [0][0] for x and y.
	public static boolean isTableOverlapping(int x, int y, int width, int length) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		int[][] tableMap = new int[9999][9999];

		for (int k = 0; k < tables.size(); k++) {
			for (int i = 0; i < tables.get(k).getWidth(); i++) {
				for (int j = 0; j < tables.get(k).getLength(); j++) {
					tableMap[tables.get(k).getX() + i][tables.get(k).getY() + j] = 1;
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				if (tableMap[i + x][j + y] == 1) {
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

		RestoApp ra = RestoApplication.getRestoApp();
		ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();
		Menu menu = ra.getMenu();

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
		if (table == null) {
			error += "Table cannot be null! ";
		}

		if (x < 0) {
			error += "x cannot be negative! ";
		}

		if (y < 0) {
			error += "y cannot be negative! ";
		}

		if (error.length() > 0) {
			throw (new InvalidInputException(error.trim()));
		}

		int width = table.getWidth();
		int length = table.getLength();
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();

		if ((table.doesOverlap(x, y, width, length))) {
			throw (new InvalidInputException("Tables overlap! Change table configuration."));
		}

		table.setX(x);
		table.setY(y);

		try {

			RestoApplication.save();

		} catch (RuntimeException e) {
			throw (new InvalidInputException(e.getMessage()));
		}
	}

	public static void addMenuItem(String name, String category, String price) throws Exception {
		String error = "";
		RestoApp ra = RestoApplication.getRestoApp();
		Menu menu = ra.getMenu();
		
		if (category.equals(null) || name.equals(null) || name.equals("")) {
			throw new InvalidInputException("Cannot have empty input for name");
		}
		
		checkIfExisting(name,menu);
			
		
		// if price is inputed
		if (!(price.equals(null) || price.equals("") || price.equals("0"))) {
			int dotCount = 0;
			for (int i = 0; i < price.length(); i++) {
				// Check if price is only numbers and .
				if ((price.charAt(i) > 47 && price.charAt(i) < 58) || price.charAt(i) == 46) {
					// Keep track of how many . (there can only be 1)
					if (price.charAt(i) == 46) {
						dotCount++;
					}
				} else {
					throw new InvalidInputException("Invalid price format (ex: 2.99)");
				}

			}

			if (dotCount > 1) {
				throw new InvalidInputException("You cannot have more than one decimal point");
			}

			double priceDouble = Double.parseDouble(price);
			if (priceDouble < 0) {
				throw new InvalidInputException("Cannot have a negative price");
			} else {
				MenuItem newMenuItem = createNewItem(name, category, menu);
				PricedMenuItem newPricedMenuItem = ra.addPricedMenuItem(priceDouble, newMenuItem);
				newMenuItem.setCurrentPricedMenuItem(newPricedMenuItem);
			}
		}else {
			// if price not inputed by user, item is not currentPricedItem
			MenuItem newMenuItem = createNewItem(name, category, menu);
		}

		RestoApplication.save();

	}
	
	public static boolean checkIfExisting (String name, Menu aMenu) throws InvalidInputException {
		boolean exists = false;
		
		List<MenuItem> menuItems = aMenu.getMenuItems();
		
		for (int i=0; i<menuItems.size();i++) {
			MenuItem menuItem = menuItems.get(i);
			
			if(menuItem.getName().equals(name)) {
				
				exists=true;
				String category = menuItem.getItemCategory().toString();
				throw new InvalidInputException(name+ " existing in "+category);
			}
		}
		
		
		
		return exists;
	}

	public static MenuItem createNewItem(String name, String category, Menu aMenu) throws RuntimeException {

		MenuItem newMenuItem = new MenuItem(name, aMenu);
		if (category.equals("Appetizer")) {
			newMenuItem.setItemCategory(MenuItem.ItemCategory.Appetizer);
		} else if (category.equalsIgnoreCase("Main")) {
			newMenuItem.setItemCategory(MenuItem.ItemCategory.Main);
		} else if (category.equalsIgnoreCase("Dessert")) {
			newMenuItem.setItemCategory(MenuItem.ItemCategory.Dessert);
		} else if (category.equalsIgnoreCase("Alcoholic Beverage")) {
			newMenuItem.setItemCategory(MenuItem.ItemCategory.AlcoholicBeverage);
		} else if (category.equalsIgnoreCase("Non Alcoholic Beverage")) {
			newMenuItem.setItemCategory(MenuItem.ItemCategory.NonAlcoholicBeverage);
		}

		return newMenuItem;
	}
	
	public static void removeMenuItem(String name, String category) throws InvalidInputException{
		boolean removed = false;
		
		RestoApp ra = RestoApplication.getRestoApp();
		
		if (category.equals(null) || name.equals(null) || name.equals("")) {
			throw new InvalidInputException("Cannot have empty input for name");
		}
		
		Menu menu = ra.getMenu();
		List<MenuItem> menuItems = menu.getMenuItems();
		for (int i =0; i < menuItems.size(); i++){		
			MenuItem menuItem = menuItems.get(i);
			
			if (menuItem.getName().equals(name)){
				String tempCategory = menuItem.getItemCategory().toString();
				category = category.replaceAll("\\s", ""); //remove space in between Category types
				if(tempCategory.equals(category)) {
					menuItem.delete();
					removed = true;
					break;
				}
				
				throw new InvalidInputException(name + " is in " + menuItem.getItemCategory().toString());
				
			}
		}
		
		if(!removed){
			throw new InvalidInputException(name+" is not a valid item to remove");
		}
		
		RestoApplication.save();
	

	}

	public static void updateTable(Table table, int newNumber, int numberOfSeats) throws InvalidInputException {
		RestoApp ra = RestoApplication.getRestoApp();

		String error = "";
		if (table == null) {
			error += "The table does not exist, please select a table";
		}
		if (numberOfSeats <= 0) {
			error += "The number of seats must be greater than 0";
		}

		if (newNumber < 0) {
			error += "You entered a negative number. Please enter a positive table number";
		}

		if (table.hasReservations()) {
			error += "The table is reserved, you cannot update its details";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		List<Order> currentOrders = ra.getCurrentOrders();
		for (Order order : currentOrders) {

			List<Table> tables = order.getTables();
			boolean inUse = tables.contains(table);

			if (inUse) {
				throw (new InvalidInputException("Table is in use, choose another table"));
			}
		}

		if (!table.setNumber(newNumber))
			throw (new InvalidInputException("Cannot create due to duplicate number"));

		int n = table.numberOfCurrentSeats();

		if (numberOfSeats > n) {
			for (int i = 1; i < numberOfSeats - n; i++) {
				Seat seat = table.addSeat();
				table.addCurrentSeat(seat);
			}
		} else if (numberOfSeats < n) {
			for (int i = 1; i < n - numberOfSeats; i++) {
				Seat seatToRemove = table.getCurrentSeat(0);
				table.removeCurrentSeat(seatToRemove);
			}
		}

		try {

			RestoApplication.save();

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
}