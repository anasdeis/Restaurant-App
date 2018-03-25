/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.20.1.4071 modeling language!*/

package ca.mcgill.ecse223.resto.model;

import ca.mcgill.ecse223.resto.application.RestoApplication;

import java.io.Serializable;
import java.util.*;

// line 56 "../../../../../RestoAppPersistence.ump"
// line 1 "../../../../../RestoAppStates.ump"
// line 28 "../../../../../RestoApp v2.ump"
public class Table implements Serializable {

    //------------------------
    // STATIC VARIABLES
    //------------------------

    private static Map<Integer, Table> tablesByNumber = new HashMap<Integer, Table>();

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Table Attributes
    private int number;
    private int x;
    private int y;
    private int width;
    private int length;

    //Table State Machines
    enum Status {
        Available, NothingOrdered, Ordered
    }

    private Status status;

    //Table Associations
    private List<Seat> currentSeats;
    private List<Reservation> reservations;
    private List<Order> orders;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Table(int aNumber, int aX, int aY, int aWidth, int aLength, RestoApp ra) {
        x = aX;
        y = aY;
        width = aWidth;
        length = aLength;
        if (!setNumber(aNumber)) {
            throw new RuntimeException("Cannot create due to duplicate number");
        }
        currentSeats = new ArrayList<Seat>();
        reservations = new ArrayList<Reservation>();
        orders = new ArrayList<Order>();
        setStatus(Status.Available);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean doesOverlap(int x, int y, int width, int length) {
        RestoApp ra = RestoApplication.getRestoApp();
        List<ca.mcgill.ecse223.resto.model.Table> tables = ra.getCurrentTables();
        int[][] tableMap = new int[9999][9999];

        loop:
        for (int k = 0; k < tables.size(); k++) {

            if (this.equals(tables.get(k))) {
                continue loop;
            }

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

    public boolean setNumber(int aNumber) {
        boolean wasSet = false;
        int anOldNumber = getNumber();
        if (hasWithNumber(aNumber)) {
            return wasSet;
        }
        number = aNumber;
        wasSet = true;
        if (anOldNumber != 0) {
            tablesByNumber.remove(anOldNumber);
        }
        tablesByNumber.put(aNumber, this);
        return wasSet;
    }

    public boolean setX(int aX) {
        boolean wasSet = false;
        x = aX;
        wasSet = true;
        return wasSet;
    }

    public boolean setY(int aY) {
        boolean wasSet = false;
        y = aY;
        wasSet = true;
        return wasSet;
    }

    public boolean setWidth(int aWidth) {
        boolean wasSet = false;
        width = aWidth;
        wasSet = true;
        return wasSet;
    }

    public boolean setLength(int aLength) {
        boolean wasSet = false;
        length = aLength;
        wasSet = true;
        return wasSet;
    }

    public int getNumber() {
        return number;
    }

    public static Table getWithNumber(int aNumber) {
        return tablesByNumber.get(aNumber);
    }

    public static boolean hasWithNumber(int aNumber) {
        return getWithNumber(aNumber) != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getStatusFullName() {
        String answer = status.toString();
        return answer;
    }

    public Status getStatus() {
        return status;
    }

    public boolean startOrder() {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Available:
                // line 4 "../../../../../RestoAppStates.ump"
                new Order(new java.sql.Date(Calendar.getInstance().getTime().getTime()), new java.sql.Time(Calendar.getInstance().getTime().getTime()), this);
                setStatus(Status.NothingOrdered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean addToOrder(Order o) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Available:
                // line 7 "../../../../../RestoAppStates.ump"
                o.addTable(this);
                setStatus(Status.NothingOrdered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean orderItem(int quantity, Order o, Seat s, PricedMenuItem i) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case NothingOrdered:
                if (quantityIsPositive(quantity)) {
                    // line 12 "../../../../../RestoAppStates.ump"
                    // create a new order item with the provided quantity, order, seat, and priced menu item
                    setStatus(Status.Ordered);
                    wasEventProcessed = true;
                    break;
                }
                break;
            case Ordered:
                if (quantityIsPositive(quantity)) {
                    // line 27 "../../../../../RestoAppStates.ump"
                    // create a new order item with the provided quantity, order, seat, and priced menu item
                    setStatus(Status.Ordered);
                    wasEventProcessed = true;
                    break;
                }
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean addToOrderItem(OrderItem i, Seat s) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case NothingOrdered:
                // line 15 "../../../../../RestoAppStates.ump"
                // add provided seat to provided order item unless seat has already been added, in which case nothing needs to be done
                setStatus(Status.Ordered);
                wasEventProcessed = true;
                break;
            case Ordered:
                // line 30 "../../../../../RestoAppStates.ump"
                // add provided seat to provided order item unless seat has already been added, in which case nothing needs to be done
                setStatus(Status.Ordered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean endOrder(Order o) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case NothingOrdered:
                // line 18 "../../../../../RestoAppStates.ump"
                if (!o.removeTable(this)) {
                    if (o.numberOfTables() == 1) {
                        o.delete();
                    }
                }
                setStatus(Status.Available);
                wasEventProcessed = true;
                break;
            case Ordered:
                if (allSeatsBilled()) {
                    // line 52 "../../../../../RestoAppStates.ump"

                    setStatus(Status.Available);
                    wasEventProcessed = true;
                    break;
                }
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean cancelOrderItem(OrderItem i) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Ordered:
                if (iIsLastItem(i)) {
                    // line 33 "../../../../../RestoAppStates.ump"
                    // delete order item
                    setStatus(Status.NothingOrdered);
                    wasEventProcessed = true;
                    break;
                }
                if (!iIsLastItem(i)) {
                    // line 36 "../../../../../RestoAppStates.ump"
                    // delete order item
                    setStatus(Status.Ordered);
                    wasEventProcessed = true;
                    break;
                }
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean cancelOrder() {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Ordered:
                // line 39 "../../../../../RestoAppStates.ump"
                // delete all order items of the table
                setStatus(Status.NothingOrdered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean billForSeat(Order o, Seat s) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Ordered:
                // line 42 "../../../../../RestoAppStates.ump"
                // create a new bill with the provided order and seat; if the provided seat is already assigned to
                // another bill for the current order, then the seat is first removed from the other bill and if no seats
                // are left for the bill, the bill is deleted
                setStatus(Status.Ordered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean addToBill(Bill b, Seat s) {
        boolean wasEventProcessed = false;

        Status aStatus = status;
        switch (aStatus) {
            case Ordered:
                // line 47 "../../../../../RestoAppStates.ump"
                // add provided seat to provided bill unless seat has already been added, in which case nothing needs
                // to be done; if the provided seat is already assigned to another bill for the current order, then the
                // seat is first removed from the other bill and if no seats are left for the bill, the bill is deleted
                setStatus(Status.Ordered);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    private void setStatus(Status aStatus) {
        status = aStatus;
    }

    public Seat getCurrentSeat(int index) {
        Seat aCurrentSeat = currentSeats.get(index);
        return aCurrentSeat;
    }

    /**
     * subsets seats
     */
    public List<Seat> getCurrentSeats() {
        List<Seat> newCurrentSeats = Collections.unmodifiableList(currentSeats);
        return newCurrentSeats;
    }

    public int numberOfCurrentSeats() {
        int number = currentSeats.size();
        return number;
    }

    public boolean hasCurrentSeats() {
        boolean has = currentSeats.size() > 0;
        return has;
    }

    public int indexOfCurrentSeat(Seat aCurrentSeat) {
        int index = currentSeats.indexOf(aCurrentSeat);
        return index;
    }

    public Reservation getReservation(int index) {
        Reservation aReservation = reservations.get(index);
        return aReservation;
    }

    public List<Reservation> getReservations() {
        List<Reservation> newReservations = Collections.unmodifiableList(reservations);
        return newReservations;
    }

    public int numberOfReservations() {
        int number = reservations.size();
        return number;
    }

    public boolean hasReservations() {
        boolean has = reservations.size() > 0;
        return has;
    }

    public int indexOfReservation(Reservation aReservation) {
        int index = reservations.indexOf(aReservation);
        return index;
    }

    public Order getOrder(int index) {
        Order aOrder = orders.get(index);
        return aOrder;
    }

    public List<Order> getOrders() {
        List<Order> newOrders = Collections.unmodifiableList(orders);
        return newOrders;
    }

    public int numberOfOrders() {
        int number = orders.size();
        return number;
    }

    public boolean hasOrders() {
        boolean has = orders.size() > 0;
        return has;
    }

    public int indexOfOrder(Order aOrder) {
        int index = orders.indexOf(aOrder);
        return index;
    }

    public static int minimumNumberOfCurrentSeats() {
        return 0;
    }

    public boolean addCurrentSeat(Seat aCurrentSeat) {
        boolean wasAdded = false;
        if (currentSeats.contains(aCurrentSeat)) {
            return false;
        }
        currentSeats.add(aCurrentSeat);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeCurrentSeat(Seat aCurrentSeat) {
        boolean wasRemoved = false;
        if (currentSeats.contains(aCurrentSeat)) {
            currentSeats.remove(aCurrentSeat);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addCurrentSeatAt(Seat aCurrentSeat, int index) {
        boolean wasAdded = false;
        if (addCurrentSeat(aCurrentSeat)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCurrentSeats()) {
                index = numberOfCurrentSeats() - 1;
            }
            currentSeats.remove(aCurrentSeat);
            currentSeats.add(index, aCurrentSeat);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveCurrentSeatAt(Seat aCurrentSeat, int index) {
        boolean wasAdded = false;
        if (currentSeats.contains(aCurrentSeat)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCurrentSeats()) {
                index = numberOfCurrentSeats() - 1;
            }
            currentSeats.remove(aCurrentSeat);
            currentSeats.add(index, aCurrentSeat);
            wasAdded = true;
        } else {
            wasAdded = addCurrentSeatAt(aCurrentSeat, index);
        }
        return wasAdded;
    }

    public static int minimumNumberOfReservations() {
        return 0;
    }

    public boolean addReservation(Reservation aReservation) {
        boolean wasAdded = false;
        if (reservations.contains(aReservation)) {
            return false;
        }
        reservations.add(aReservation);
        if (aReservation.indexOfTable(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aReservation.addTable(this);
            if (!wasAdded) {
                reservations.remove(aReservation);
            }
        }
        return wasAdded;
    }

    public boolean removeReservation(Reservation aReservation) {
        boolean wasRemoved = false;
        if (!reservations.contains(aReservation)) {
            return wasRemoved;
        }

        int oldIndex = reservations.indexOf(aReservation);
        reservations.remove(oldIndex);
        if (aReservation.indexOfTable(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aReservation.removeTable(this);
            if (!wasRemoved) {
                reservations.add(oldIndex, aReservation);
            }
        }
        return wasRemoved;
    }

    public boolean addReservationAt(Reservation aReservation, int index) {
        boolean wasAdded = false;
        if (addReservation(aReservation)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfReservations()) {
                index = numberOfReservations() - 1;
            }
            reservations.remove(aReservation);
            reservations.add(index, aReservation);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveReservationAt(Reservation aReservation, int index) {
        boolean wasAdded = false;
        if (reservations.contains(aReservation)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfReservations()) {
                index = numberOfReservations() - 1;
            }
            reservations.remove(aReservation);
            reservations.add(index, aReservation);
            wasAdded = true;
        } else {
            wasAdded = addReservationAt(aReservation, index);
        }
        return wasAdded;
    }

    public static int minimumNumberOfOrders() {
        return 0;
    }

    public boolean addOrder(Order aOrder) {
        boolean wasAdded = false;
        if (orders.contains(aOrder)) {
            return false;
        }
        orders.add(aOrder);
        if (aOrder.indexOfTable(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aOrder.addTable(this);
            if (!wasAdded) {
                orders.remove(aOrder);
            }
        }
        return wasAdded;
    }

    public boolean removeOrder(Order aOrder) {
        boolean wasRemoved = false;
        if (!orders.contains(aOrder)) {
            return wasRemoved;
        }

        int oldIndex = orders.indexOf(aOrder);
        orders.remove(oldIndex);
        if (aOrder.indexOfTable(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aOrder.removeTable(this);
            if (!wasRemoved) {
                orders.add(oldIndex, aOrder);
            }
        }
        return wasRemoved;
    }

    public boolean addOrderAt(Order aOrder, int index) {
        boolean wasAdded = false;
        if (addOrder(aOrder)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfOrders()) {
                index = numberOfOrders() - 1;
            }
            orders.remove(aOrder);
            orders.add(index, aOrder);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveOrderAt(Order aOrder, int index) {
        boolean wasAdded = false;
        if (orders.contains(aOrder)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfOrders()) {
                index = numberOfOrders() - 1;
            }
            orders.remove(aOrder);
            orders.add(index, aOrder);
            wasAdded = true;
        } else {
            wasAdded = addOrderAt(aOrder, index);
        }
        return wasAdded;
    }

    public void delete() {
        tablesByNumber.remove(getNumber());
        currentSeats.clear();
        ArrayList<Reservation> copyOfReservations = new ArrayList<Reservation>(reservations);
        reservations.clear();
        for (Reservation aReservation : copyOfReservations) {
            if (aReservation.numberOfTables() <= Reservation.minimumNumberOfTables()) {
                aReservation.delete();
            } else {
                aReservation.removeTable(this);
            }
        }
        ArrayList<Order> copyOfOrders = new ArrayList<Order>(orders);
        orders.clear();
        for (Order aOrder : copyOfOrders) {
            if (aOrder.numberOfTables() <= Order.minimumNumberOfTables()) {
                aOrder.delete();
            } else {
                aOrder.removeTable(this);
            }
        }
    }


    /**
     * check that the provided quantity is an integer greater than 0
     */
    private boolean quantityIsPositive(int quantity) {
        // TODO
        return false;
    }


    /**
     * check that the provided order item is the last item of the current order of the table
     */
    private boolean iIsLastItem(OrderItem i) {
        // TODO
        return false;
    }


    /**
     * check that all seats of the table have a bill that belongs to the current order of the table
     */
    private boolean allSeatsBilled() {
        // TODO
        return false;
    }


    public String toString() {
        String outputString = "";
        return super.toString() + "[" +
                "number" + ":" + getNumber() + "," +
                "x" + ":" + getX() + "," +
                "y" + ":" + getY() + "," +
                "width" + ":" + getWidth() + "," +
                "length" + ":" + getLength() + "]"
                + outputString;
    }
    //------------------------
    // DEVELOPER CODE - PROVIDED AS-IS
    //------------------------

    // line 59 ../../../../../RestoAppPersistence.ump
    private static final long serialVersionUID = 8896099581655989380L;
// line 34 ../../../../../RestoApp v2.ump
//  1 <@>- 1..* Seat seats ;


}