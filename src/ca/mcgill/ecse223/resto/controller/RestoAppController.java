package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.*;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

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
            error += "A table must be specified for removing. ";
        }

        if (table.hasReservations()) {
            error += "Table #" + table.getNumber() + " is reserved. Cannot be removed. ";
        }

        if (!table.getStatusFullName().equals("Available")) {
            error += "Table #" + table.getNumber() + " is not available at the moment, you cannot remove it. ";
        }

        if (error.length() > 0) {
            throw (new InvalidInputException(error.trim()));
        }

        List<Order> currentOrders = r.getCurrentOrders();
        for (Order order : currentOrders) {
            List<Table> tables = order.getTables();
            if (tables.contains(table)) {
                throw (new InvalidInputException("Table #" + table.getNumber() + " is in use!"));
            }
        }

        r.removeCurrentTable(table);

        try {
            RestoApplication.save();

        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static void reserveTable(Date date, Time time, int numberInParty, String contactName,
                                    String contactEmailAddress, String contactPhoneNumber, List<Table> tables)
            throws InvalidInputException {
        String error = "";
        RestoApp r = RestoApplication.getRestoApp();
        long currentTime = Calendar.getInstance().getTime().getTime();

        if (date == null) {
            error += "Please enter the date of the reservation. ";
        } else {
            if (currentTime > date.getTime()) {
                error += "Please enter a valid date/time for the reservation. Make sure it is in the future. ";
            }
        }

        if (time == null) {
            error += "Please enter the time of the reservation. ";
        }

        if (contactName == null || contactName.isEmpty()) {
            error += "Please enter your name. ";
        }

        if (contactEmailAddress == null || contactEmailAddress.isEmpty()) {
            error += "Please enter your email address. ";
        }

        if (contactPhoneNumber == null || contactPhoneNumber.isEmpty()) {
            error += "Please enter your phone number. ";
        }

        if (numberInParty <= 0) {
            error += "Please enter a positive number for the number of people (larger than 0). ";
        }

        if (error.length() > 0) {
            throw new InvalidInputException(error.trim());
        }

        List<Table> currentTables = r.getCurrentTables();
        Table[] allTablesArray = tables.toArray(new Table[tables.size()]);
        int seatCapacity = 0;

        for (Table table : tables) {
            if (!currentTables.contains(table)) {
                throw (new InvalidInputException("Table #" + table.getNumber() + " is not a current table!"));
            }

            seatCapacity += table.numberOfCurrentSeats();

            if (table.hasReservations()) {
                List<Reservation> reservations = table.getReservations();

                for (Reservation reservation : reservations) {
                    if (reservation.getDate() != null && reservation.getTime() != null) {
                        long timeDiffInHours = (Math.abs(reservation.getDate().getTime() - date.getTime())) / 3600000L;
                        if (timeDiffInHours < 2) {
                            throw (new InvalidInputException("Please choose another time, table reserved at the requested time. "));
                        }
                    }
                }
            }
        }

        if (seatCapacity < numberInParty) {
            throw (new InvalidInputException("Not enough seats for selected table(s). "));
        }

        try {

            Reservation reservation = new Reservation(date, time, numberInParty, contactName,
                    contactEmailAddress, contactPhoneNumber, r, allTablesArray);

            RestoApplication.save();

        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void endReservation(Reservation reservation) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Reservation> reservations = r.getReservations();

        if (reservation == null) {
            throw (new InvalidInputException("A reservation must be specified for deleting it. "));
        }

        if (reservations.contains(reservation)) {
            reservation.delete();

        } else {
            throw (new InvalidInputException("The reservation must exist for deleting it. "));
        }

        try {
            RestoApplication.save();
        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void deleteTableReservation(Reservation reservation, Table table) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Reservation> reservations = r.getReservations();

        if (reservation == null) {
            throw (new InvalidInputException("A reservation must be specified for deleting it. "));
        }

        if (reservations.contains(reservation)) {
            if (!reservation.removeTable(table)) {
                if (reservation.numberOfTables() == 1) {
                    reservation.delete();
                }
            }

        } else {
            throw (new InvalidInputException("The reservation must exist for deleting it. "));
        }

        try {
            RestoApplication.save();
        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void deleteOrder(Order order) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Order> orders = r.getOrders();
        List<Order> currentOrders = r.getCurrentOrders();

        if (order == null) {
            throw (new InvalidInputException("An order must be specified for deleting it. "));
        }

        if (currentOrders.contains(order)) {
            throw (new InvalidInputException("Cannot delete a current order."));
        } else {
            if (orders.contains(order)) {
                order.delete();
            } else {
                throw (new InvalidInputException("The order must exist for deleting it. "));
            }
        }
        try {
            RestoApplication.save();
        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void deleteTableOrder(Order order, Table table) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Order> orders = r.getOrders();
        List<Order> currentOrders = r.getCurrentOrders();

        if (order == null) {
            throw (new InvalidInputException("An order must be specified for deleting it. "));
        }

        if (currentOrders.contains(order)) {
            throw (new InvalidInputException("Cannot delete a current order, only past orders that haven't been removed."));
        } else {
            if (orders.contains(order)) {
                if (!order.removeTable(table)) {
                    if (order.numberOfTables() == 1) {
                        order.delete();
                    }
                }
            } else {
                throw (new InvalidInputException("The order must exist for deleting it. "));
            }
        }

        try {
            RestoApplication.save();
        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void startOrder(List<Table> tables) throws InvalidInputException {

        RestoApp r = RestoApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();
        String error = "";

        if (tables == null) {
            throw (new InvalidInputException("No table(s) specified to start order. "));
        }
        for (Table table : tables) {
            if (!currentTables.contains(table)) {
                throw (new InvalidInputException("The table #" + table.getNumber() + " is not a current table. "));
            }
        }

        boolean orderCreated = false;
        Order newOrder = null;

        for (Table table : tables) {
            if (orderCreated) {
                boolean add = table.addToOrder(newOrder);
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
        if (orderCreated) {
            List<Table> tabless = newOrder.getTables();
            for (Table atable : tabless) {
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
            throw (new InvalidInputException("An order must be specified to end it. "));
        }

        if (!currentOrders.contains(order)) {
            throw (new InvalidInputException("The specified order is not a current order! "));
        }

        List<Table> tables = order.getTables();
        List<Table> copyOfTables = new ArrayList<Table>(tables);


        for (Table table : copyOfTables) {
            if ((table.numberOfOrders() > 0) && (table.getOrder(table.numberOfOrders() - 1).equals(order))) {
                boolean success = table.endOrder(order);
                if (!success) {
                    System.out.println("Cannot end order for table #" + table.getNumber());
                }
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
            if ((table.getStatusFullName().equals("Available"))
                    || (!((table.getOrder(table.numberOfOrders() - 1)).equals(order)))) {
                return true;
            }
        }
        return false;
    }

    public static void cancelOrderItem(OrderItem orderItem) throws InvalidInputException {

        if (orderItem == null) {
            throw (new InvalidInputException("An order item must be specified for cancelling it. "));
        }

        List<Seat> seats = orderItem.getSeats();
        Order order = orderItem.getOrder();
        List<Table> tables = new ArrayList<Table>();

        for (Seat seat : seats) {
            Table table = seat.getTable();
            Order lastOrder = null;
            if (table.numberOfOrders() > 0) {
                lastOrder = table.getOrder(table.numberOfOrders() - 1);
            } else {
                throw (new InvalidInputException("The table #" + table.getNumber() + " must have an order if " +
                        "one of its seats has an order item. "));
            }
            if (lastOrder.equals(order) && !tables.contains(table)) {
                tables.add(table);
            }
        }
        for (Table table : tables) {
            table.cancelOrderItem(orderItem);
        }

        try {

            RestoApplication.save();

        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void cancelOrderItemForSeat(Seat seat) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();

        if (seat == null) {
            throw (new InvalidInputException("A seat must be specified for cancelling its order items. "));
        }

        Table table = seat.getTable();
        if (!currentTables.contains(table)) {
            throw (new InvalidInputException("The table #" + seat.getTable().getNumber() + " of the specified seat" +
                    " is not a current table. "));
        }

        List<Seat> currentSeats = table.getCurrentSeats();
        if (currentSeats.contains(seat)) {
            throw (new InvalidInputException("The specified seat is not a current seat. "));
        }

        Order lastOrder = null;
        if (table.numberOfOrders() > 0) {
            lastOrder = table.getOrder(table.numberOfOrders() - 1);
        } else {
            throw (new InvalidInputException("The table #" + table.getNumber() + " has no order(s)"));
        }

        List<OrderItem> orderItems = seat.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Order order = orderItem.getOrder();
            if (lastOrder.equals(order)) {
                cancelOrderItem(orderItem);
            }
        }

        try {

            RestoApplication.save();
        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void cancelOrder(Table table) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();

        if (table == null) {
            throw (new InvalidInputException("A table must be specified for cancelling its order. "));
        }

        if (currentTables.contains(table)) {
            throw (new InvalidInputException("The table #" + table.getNumber() + " is not a current table. "));
        }

        table.cancelOrder();

        try {

            RestoApplication.save();

        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
    }

    public static void cancelOrder(Order order) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();

        if (order == null) {
            throw (new InvalidInputException("An order must be specified for cancelling it. "));
        }

        Order lastOrder = null;
        List<Table> tables = order.getTables();

        for (Table table : tables) {

            if (lastOrder == null) {

                if (table.numberOfOrders() > 0) {
                    lastOrder = table.getOrder(table.numberOfOrders() - 1);
                } else {
                    throw (new InvalidInputException("The table #" + table.getNumber() + " has no order(s)"));
                }
            } else {
                Order comparedOrder = null;
                if (table.numberOfOrders() > 0) {
                    comparedOrder = table.getOrder(table.numberOfOrders() - 1);
                } else {
                    throw (new InvalidInputException("The table #" + table.getNumber() + " has no order(s)"));
                }

                if (!comparedOrder.equals(lastOrder)) {
                    throw (new InvalidInputException("Current orders for selected tables do not match. "));
                }
            }
        }

        for (Table table : tables) {
            cancelOrder(table);
        }

        try {

            RestoApplication.save();

        } catch (RuntimeException e) {
            throw (new InvalidInputException(e.getMessage()));
        }
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
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
     */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void issueBill(List<Seat> seats) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();
        Order lastOrder = null;

        if (seats == null || seats.isEmpty()) {
            throw (new InvalidInputException("Seats have to be specified to issue bill. "));
        }

        for (Seat seat : seats) {
            Table table = seat.getTable();

            if (!currentTables.contains(table)) {
                throw new InvalidInputException("Table #" + table.getNumber() + " is not a current table!");
            }

            List<Seat> currentSeats = table.getCurrentSeats();
            if (!currentSeats.contains(seat)) {
                throw new InvalidInputException("The specified seat is not a current seat!");
            }
            lastOrder = table.getOrder(table.numberOfOrders() - 1);
            if (lastOrder == null) {
                if (table.numberOfOrders() > 0) {
                    lastOrder = table.getOrder(table.numberOfOrders() - 1);
                } else {
                    throw new InvalidInputException("There is no order for a specified seat!");
                }
            } else {
                Order comparedOrder = null;
                if (table.numberOfOrders() > 0) {
                    comparedOrder = table.getOrder(table.numberOfOrders() - 1);
                } else {
                    throw new InvalidInputException("There is no order for a specified seat!");
                }
                if (!comparedOrder.equals(lastOrder)) {
                    throw new InvalidInputException("Seats specified don't match to the same order!");
                }
            }
        }
        if (lastOrder == null) {
            throw new InvalidInputException("The specified seats don't have an order!");
        }

        boolean billCreated = false;
        Bill newBill = null;
        for (Seat seat : seats) {
            Table table = seat.getTable();
            if (billCreated) {
                table.addToBill(newBill, seat);
            } else {
                Bill lastBill = null;
                if (lastOrder.numberOfBills() > 0) {
                    lastBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
                }
                table.billForSeat(lastOrder, seat);
                if (lastOrder.numberOfBills() > 0 && !lastOrder.getBill(lastOrder.numberOfBills() - 1).equals(lastBill)) {
                    billCreated = true;
                    newBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
                }
            }
        }
        if (!billCreated) {
            throw new InvalidInputException("Error: There is an issue with generating a bill!");
        }
        RestoApplication.save();
    }


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
        Menu menu = ra.getMenu();
        List<MenuItem> menuItems = menu.getMenuItems();
        ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();

        for (MenuItem menuItem : menuItems) {
            boolean current = menuItem.hasCurrentPricedMenuItem();
            ItemCategory category = menuItem.getItemCategory();
            if (current && category.equals(itemCategory)) {
                itemList.add(menuItem);
            }
        }
        return itemList;
    }

    public static void moveTable(Table table, int x, int y) throws InvalidInputException {

        String error = "";
        if (table == null) {
            error += "A table must be specified for moving. ";
        }

        if (x < 0) {
            error += "The x location must be non-negative. ";
        }

        if (y < 0) {
            error += "The y location must be non-negative. ";
        }

        if (!table.getStatusFullName().equals("Available")) {
            error += "Table #" + table.getNumber() + " is not available at the moment, you cannot move it. ";
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

		checkIfExisting(name, menu);

		// if price is inputed
		if (!(price.equals(null) || price.equals("") || price.equals("0"))) {

			priceCheck(price);
			double priceDouble = Double.parseDouble(price);

			if (priceDouble < 0) {
				throw new InvalidInputException("Cannot have a negative price");
			} else {
				MenuItem newMenuItem = createNewItem(name, category, menu);
				PricedMenuItem newPricedMenuItem = newMenuItem.addPricedMenuItem(priceDouble, ra);
				newMenuItem.setCurrentPricedMenuItem(newPricedMenuItem);
			}
		} else {
			// if price not inputed by user, item is not currentPricedItem
			MenuItem newMenuItem = createNewItem(name, category, menu);
		}

		RestoApplication.save();

	}

	public static void priceCheck(String price) throws InvalidInputException {

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

	}

	public static boolean checkIfExisting(String name, Menu aMenu) throws InvalidInputException {
		boolean exists = false;

		List<MenuItem> menuItems = aMenu.getMenuItems();

		for (int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);

			if (menuItem.getName().equals(name)) {

				exists = true;
				String category = menuItem.getItemCategory().toString();
				throw new InvalidInputException(name + " existing in " + category);
			}
		}

		return exists;
	}

	public static MenuItem createNewItem(String name, String category, Menu aMenu) {

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

	public static void removeMenuItem(String name, String category) throws InvalidInputException {
		boolean removed = false;

		RestoApp ra = RestoApplication.getRestoApp();

		if (category.equals(null) || name.equals(null) || name.equals("")) {
			throw new InvalidInputException("Cannot have empty input for name");
		}

		Menu menu = ra.getMenu();
		List<MenuItem> menuItems = menu.getMenuItems();
		for (int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);

			if (menuItem.getName().equals(name)) {
				String tempCategory = menuItem.getItemCategory().toString();
				category = category.replaceAll("\\s", ""); // remove space in between Category types
				if (tempCategory.equals(category)) {
					menuItem.setCurrentPricedMenuItem(null);
					removed = true;
					break;
				}

				throw new InvalidInputException(name + " is in " + menuItem.getItemCategory().toString());

			}
		}

		if (!removed) {
			throw new InvalidInputException(name + " is not a valid item to remove");
		}

		RestoApplication.save();

	}

	public static String updateMenuItem(String oldName, String newName,String oldCategory, String newCategory,String oldPrice, String newPrice)
			throws InvalidInputException {
		
		//input validation
		String message="";		
		MenuItem menuItem = null;
		if (oldName.equals("") || oldName.equals(null))  {
			throw new InvalidInputException("Please input name of item to update");
		}else {
			RestoApp ra = RestoApplication.getRestoApp();
			Menu menu = ra.getMenu();
			List<MenuItem> menuItems = menu.getMenuItems();
			
			for (int i = 0; i < menuItems.size(); i++) {
				menuItem = menuItems.get(i);
				if (menuItem.getName().equals(oldName)) {
					break;
				}
			}
		}
		
		// if any of newName, newPrice, category fields are both empty
		if((newName.equals("") || newName.equals(null)) && (newPrice.equals(null) || newPrice.equals("")) && (newCategory.equals("") || newCategory.equals(null))) {
			throw new InvalidInputException("At least one of the fields is required to update");		
		}
		// if oldPrice field is filled, check if menuItem indeed has that price
		if(!(oldPrice.equals(null) || oldPrice.equals(""))){
			double tempPrice = Double.valueOf(oldPrice);
			if(!(tempPrice == (menuItem.getCurrentPricedMenuItem().getPrice()))) {
				throw new InvalidInputException("Wrong price of "+ oldName);
			}
		}else if(oldPrice.equals("")&& !newPrice.equals("")) {
			throw new InvalidInputException("Input price for "+ oldName+ " to update price");
		}
		// if oldCategory is filled, check if menuItem is indeed the category
			if(!oldCategory.equals("")) {
				if(!menuItem.getItemCategory().toString().equals(oldCategory.replaceAll("\\s", ""))) {
					throw new InvalidInputException(oldName + " is in "+ menuItem.getItemCategory().toString());
				}
			}else if(oldCategory.equals("")&& !newCategory.equals("")) {
				throw new InvalidInputException(oldName+"'s category must be selected in order to change into "+ newCategory);
			}
			
		
		
	
		message = message + "Successfully updated "+oldName;
		if(!(newName.equals("") || newName.equals(null))) {
			boolean duplicate = menuItem.setName(newName);
			message = message + " as "+ newName;
			if (!duplicate) {
				throw new InvalidInputException("Cannot set name to " + newName);
			}
		}
		
		if(!(newPrice.equals(null) || newPrice.equals(""))){
			
			priceCheck(newPrice);
			double newPriceDouble = Double.parseDouble(newPrice);
			if (newPriceDouble != menuItem.getCurrentPricedMenuItem().getPrice()) {
				RestoApp ra = RestoApplication.getRestoApp();
				PricedMenuItem pmi = menuItem.addPricedMenuItem(newPriceDouble, ra);
				menuItem.setCurrentPricedMenuItem(pmi);
				message = message + " to cost " + newPrice;
			}
		}

		boolean current = menuItem.hasCurrentPricedMenuItem();
		if (!current) {
			throw new InvalidInputException(menuItem.getName() + " is not a curent priced menu Item");
		}
		
		if(!(newCategory.equals("")|| newCategory.equals(null))) {
		
			ItemCategory category;
			if(newCategory.equals("Appetizer")) {
				category = MenuItem.ItemCategory.Appetizer;
			}else if(newCategory.equals("Main")) {
				category = MenuItem.ItemCategory.Main;
			}else if(newCategory.equals("Dessert")) {
				category = MenuItem.ItemCategory.Dessert;
			}else if(newCategory.equals("Alcoholic Beverage")) {
				category = MenuItem.ItemCategory.AlcoholicBeverage;
			}else if(newCategory.equals("Non Alcoholic Beverage")) {
				category = MenuItem.ItemCategory.NonAlcoholicBeverage;
			}else {
				throw new InvalidInputException(newCategory+" is not a valid category");
			}
			menuItem.setItemCategory(category);
			message = message + " into " + newCategory;
		}
		
		RestoApplication.save();
		return message;

	}

    
    

    public static void updateTable(Table table, int newNumber, int numberOfSeats) throws InvalidInputException {
        RestoApp ra = RestoApplication.getRestoApp();

        String error = "";
        if (table == null) {
            error += "A table must be specified for updating. ";
        }
        if (numberOfSeats <= 0) {
            error += "The number of seats must be greater than 0";
        }

        if (newNumber < 0) {
            error += "The table number must be non-negative.";
        }

        if (table.hasReservations()) {
            error += "Table #" + table.getNumber() + " is reserved, you cannot update its details. ";
        }

        if (!table.getStatusFullName().equals("Available")) {
            error += "Table #" + table.getNumber() + " is not available at the moment, you cannot update its details. ";
        }

        if (error.length() > 0) {
            throw new InvalidInputException(error.trim());
        }

        List<Order> currentOrders = ra.getCurrentOrders();
        for (Order order : currentOrders) {

            List<Table> tables = order.getTables();
            boolean inUse = tables.contains(table);

            if (inUse) {
                throw (new InvalidInputException("The table #" + table.getNumber() + " is in use, choose another table. "));
            }
        }

        if (!table.setNumber(newNumber))
            throw (new InvalidInputException("Cannot update table number due to duplicate number. "));

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

    public static void orderMenuItem(MenuItem menuitem, int quantity, List<Seat> seats) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        String error = "";

        if (menuitem == null) {
            error += "Please enter a menu item to order";
        }

        if (seats == null || seats.isEmpty()) {
            error += "Please enter the seats to order for";
        }

        if (quantity < 1) {
            error += "Please enter the quantity to order";
        }

        // **
        if (error.length() > 0) {
            throw (new InvalidInputException(error.trim()));
        }

        if (!menuitem.hasCurrentPricedMenuItem()) {
            throw (new InvalidInputException("No price associated with" + menuitem));
        }

        List<Table> currentTables = r.getCurrentTables();
        Order lastOrder = null;
        //Seat seat = new Seat();

        for (Seat seat : seats) {

            Table table = seat.getTable();

            if (!currentTables.contains(table)) {
                throw (new InvalidInputException("Table " + table.getNumber() + "is not in the list of tables on the system"));
            }

            List<Seat> currentSeats = table.getCurrentSeats();

            if (!currentSeats.contains(seat)) {
                throw (new InvalidInputException("Seat entered is not in the list of seats on table" + table.getNumber()));
            }

            if (lastOrder == null) {
                if (table.numberOfOrders() > 0) {
                    lastOrder = table.getOrder(table.numberOfOrders() - 1);
                } else {
                    throw (new InvalidInputException("No orders were made for table #" + table.getNumber()));
                }
            } else {
                Order comparedOrder = null;
                if (table.numberOfOrders() > 0) {
                    comparedOrder = table.getOrder(table.numberOfOrders() - 1);
                    if (!comparedOrder.equals(lastOrder)) {
                        throw (new InvalidInputException("Error: order doesn't match"));
                    }
                } else {
                    throw (new InvalidInputException("No orders were made"));
                }
            }
        }

        if (lastOrder == null) {
            throw (new InvalidInputException("No orders were made"));
        }

        PricedMenuItem pmi = menuitem.getCurrentPricedMenuItem();
        boolean itemCreated = false;
        OrderItem newitem = null;

        for (Seat seat : seats) {
            Table table = seat.getTable();

            if (itemCreated) {
                table.addToOrderItem(newitem, seat);
            } else {
                OrderItem lastitem = null;
                if (lastOrder.numberOfOrderItems() > 0) {
                    lastitem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);

                }
                table.orderItem(quantity, lastOrder, seat, pmi);

                if ((lastOrder.numberOfOrderItems() > 0) &&
                        (!lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1).equals(lastitem))) {
                    itemCreated = true;
                    newitem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);
                }
            }
        }

        if (!itemCreated) {
            throw (new InvalidInputException("Error: No order item created. "));
        }

        //**

        try {
            RestoApplication.save();
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException {
        RestoApp r = RestoApplication.getRestoApp();
        List<Table> currentTables = r.getCurrentTables();
        List<OrderItem> orderItemsFromTable = new ArrayList<OrderItem>();
        if (table == null) {
            throw (new InvalidInputException("A table must be specified to get order items. "));
        }

        if (currentTables.contains(table)) {
            if (table.getStatusFullName().equals("Available")) {
                throw (new InvalidInputException("Table #" + table.getNumber() + " is available!"));
            }

            Order lastOrder = null;
            if (table.numberOfOrders() > 0) {
                lastOrder = table.getOrder(table.numberOfOrders() - 1);
            } else {
                throw (new InvalidInputException("A table that is not available should have an order. "));
            }

            List<Seat> currentSeats = table.getCurrentSeats();
            if (currentSeats == null || currentSeats.isEmpty()) {
                throw (new InvalidInputException("Table #" + table.getNumber() + " must have current seats. Cannot get order items. "));
            }
            for (Seat seat : currentSeats) {
                List<OrderItem> orderItems = seat.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                    if (lastOrder.equals(orderItem.getOrder()) && !orderItemsFromTable.contains(orderItem)) {
                        orderItemsFromTable.add(orderItem);
                    }
                }
            }
        } else {
            throw (new InvalidInputException("Table #" + table.getNumber() + " is not a current table."));
        }

        return orderItemsFromTable;
    }
}
