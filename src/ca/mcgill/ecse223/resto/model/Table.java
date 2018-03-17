/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-b05b57321 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 56 "../../../../../RestoAppPersistence.ump"
// line 1 "../../../../../RestoAppStates.ump"
// line 27 "../../../../../RestoApp v2.ump"
public class Table implements Serializable
{

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
  public enum Status { Available, InUse }
  public enum StatusInUse { Null, NotBilled, Billed }
  public enum StatusInUseNotBilled { Null, NoItemOrdered, ItemOrdered }
  private Status status;
  private StatusInUse statusInUse;
  private StatusInUseNotBilled statusInUseNotBilled;

  //Table Associations
  private List<Seat> seats;
  private List<Seat> currentSeats;
  private RestoApp restoApp;
  private List<Reservation> reservations;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table(int aNumber, int aX, int aY, int aWidth, int aLength, RestoApp aRestoApp)
  {
    x = aX;
    y = aY;
    width = aWidth;
    length = aLength;
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number");
    }
    seats = new ArrayList<Seat>();
    currentSeats = new ArrayList<Seat>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create table due to restoApp");
    }
    reservations = new ArrayList<Reservation>();
    orders = new ArrayList<Order>();
    setStatusInUse(StatusInUse.Null);
    setStatusInUseNotBilled(StatusInUseNotBilled.Null);
    setStatus(Status.Available);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      tablesByNumber.remove(anOldNumber);
    }
    tablesByNumber.put(aNumber, this);
    return wasSet;
  }

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setWidth(int aWidth)
  {
    boolean wasSet = false;
    width = aWidth;
    wasSet = true;
    return wasSet;
  }

  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }

  public static Table getWithNumber(int aNumber)
  {
    return tablesByNumber.get(aNumber);
  }

  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public int getWidth()
  {
    return width;
  }

  public int getLength()
  {
    return length;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    if (statusInUse != StatusInUse.Null) { answer += "." + statusInUse.toString(); }
    if (statusInUseNotBilled != StatusInUseNotBilled.Null) { answer += "." + statusInUseNotBilled.toString(); }
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public StatusInUse getStatusInUse()
  {
    return statusInUse;
  }

  public StatusInUseNotBilled getStatusInUseNotBilled()
  {
    return statusInUseNotBilled;
  }

  public boolean createOrder(Order order)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Available:
        if (isCurrentOrder(order))
        {
          setStatus(Status.InUse);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addToOrder(Order order)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Available:
        // line 12 "../../../../../RestoAppStates.ump"
        addToOrder(Order order)
        setStatus(Status.InUse);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean shareItem(OrderItem item,Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case NotBilled:
        exitStatusInUse();
        // line 23 "../../../../../RestoAppStates.ump"
        shareItem(OrderItem item, Seat seat)
        setStatusInUse(StatusInUse.NotBilled);
        wasEventProcessed = true;
        break;
      case Billed:
        exitStatusInUse();
        // line 32 "../../../../../RestoAppStates.ump"
        shareItem(OrderItem item, Seat seat)
        setStatusInUse(StatusInUse.Billed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean orderItem(MenuItem item,Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case NotBilled:
        exitStatusInUse();
        // line 24 "../../../../../RestoAppStates.ump"
        orderItem(MenuItem item, Seat seat)
        setStatusInUse(StatusInUse.NotBilled);
        wasEventProcessed = true;
        break;
      case Billed:
        exitStatusInUse();
        // line 34 "../../../../../RestoAppStates.ump"
        orderItem(MenuItem item, Seat seat)
        setStatusInUse(StatusInUse.Billed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelItem(OrderItem item,Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case NotBilled:
        exitStatusInUse();
        // line 25 "../../../../../RestoAppStates.ump"
        cancelItem(OrderItem item, Seat seat)
        setStatusInUse(StatusInUse.NotBilled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition8__()
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case NotBilled:
        if (findNumberOfItemOrdered()>0)
        {
          exitStatusInUse();
          setStatusInUse(StatusInUse.NotBilled);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeFromBill(Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case Billed:
        if (isLastBill())
        {
          exitStatusInUse();
        // line 29 "../../../../../RestoAppStates.ump"
          removeFromBill(Seat seat)
          setStatusInUseNotBilled(StatusInUseNotBilled.ItemOrdered);
          wasEventProcessed = true;
          break;
        }
        if (!(isLastBill()))
        {
          exitStatusInUse();
        // line 31 "../../../../../RestoAppStates.ump"
          removeFromBill(Seat seat)
          setStatusInUse(StatusInUse.Billed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean makeBill(Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    StatusInUseNotBilled aStatusInUseNotBilled = statusInUseNotBilled;
    switch (aStatusInUse)
    {
      case Billed:
        if (!(isBilled(seat)))
        {
          exitStatusInUse();
        // line 30 "../../../../../RestoAppStates.ump"
          makeBill(Seat seat)
          setStatusInUse(StatusInUse.Billed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aStatusInUseNotBilled)
    {
      case ItemOrdered:
        exitStatusInUse();
        // line 21 "../../../../../RestoAppStates.ump"
        makeBill(Seat seat)
        setStatusInUse(StatusInUse.Billed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addToBill(Bill bill,Seat seat)
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    switch (aStatusInUse)
    {
      case Billed:
        if (!(isBilled(seat)))
        {
          exitStatusInUse();
        // line 33 "../../../../../RestoAppStates.ump"
          addToBill(Bill bill, Seat seat)
          setStatusInUse(StatusInUse.Billed);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean makeAvailable()
  {
    boolean wasEventProcessed = false;
    
    StatusInUse aStatusInUse = statusInUse;
    StatusInUseNotBilled aStatusInUseNotBilled = statusInUseNotBilled;
    switch (aStatusInUse)
    {
      case Billed:
        if (isAllSeatsHaveBill())
        {
          exitStatus();
        // line 35 "../../../../../RestoAppStates.ump"
          removeOrder(Order order)
          setStatus(Status.Available);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aStatusInUseNotBilled)
    {
      case NoItemOrdered:
        exitStatus();
        // line 17 "../../../../../RestoAppStates.ump"
        removeOrder(Order order)
        setStatus(Status.Available);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition7__()
  {
    boolean wasEventProcessed = false;
    
    StatusInUseNotBilled aStatusInUseNotBilled = statusInUseNotBilled;
    switch (aStatusInUseNotBilled)
    {
      case NoItemOrdered:
        if (findNumberOfItemOrdered()>0)
        {
          exitStatusInUseNotBilled();
          setStatusInUseNotBilled(StatusInUseNotBilled.ItemOrdered);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitStatus()
  {
    switch(status)
    {
      case InUse:
        exitStatusInUse();
        break;
    }
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;

    // entry actions and do activities
    switch(status)
    {
      case InUse:
        if (statusInUse == StatusInUse.Null) { setStatusInUse(StatusInUse.NotBilled); }
        break;
    }
  }

  private void exitStatusInUse()
  {
    switch(statusInUse)
    {
      case NotBilled:
        exitStatusInUseNotBilled();
        setStatusInUse(StatusInUse.Null);
        break;
      case Billed:
        setStatusInUse(StatusInUse.Null);
        break;
    }
  }

  private void setStatusInUse(StatusInUse aStatusInUse)
  {
    statusInUse = aStatusInUse;
    if (status != Status.InUse && aStatusInUse != StatusInUse.Null) { setStatus(Status.InUse); }

    // entry actions and do activities
    switch(statusInUse)
    {
      case NotBilled:
        if (statusInUseNotBilled == StatusInUseNotBilled.Null) { setStatusInUseNotBilled(StatusInUseNotBilled.NoItemOrdered); }
        __autotransition8__();
        break;
    }
  }

  private void exitStatusInUseNotBilled()
  {
    switch(statusInUseNotBilled)
    {
      case NoItemOrdered:
        setStatusInUseNotBilled(StatusInUseNotBilled.Null);
        break;
      case ItemOrdered:
        setStatusInUseNotBilled(StatusInUseNotBilled.Null);
        break;
    }
  }

  private void setStatusInUseNotBilled(StatusInUseNotBilled aStatusInUseNotBilled)
  {
    statusInUseNotBilled = aStatusInUseNotBilled;
    if (statusInUse != StatusInUse.NotBilled && aStatusInUseNotBilled != StatusInUseNotBilled.Null) { setStatusInUse(StatusInUse.NotBilled); }

    // entry actions and do activities
    switch(statusInUseNotBilled)
    {
      case NoItemOrdered:
        __autotransition7__();
        break;
    }
  }

  public Seat getSeat(int index)
  {
    Seat aSeat = seats.get(index);
    return aSeat;
  }

  public List<Seat> getSeats()
  {
    List<Seat> newSeats = Collections.unmodifiableList(seats);
    return newSeats;
  }

  public int numberOfSeats()
  {
    int number = seats.size();
    return number;
  }

  public boolean hasSeats()
  {
    boolean has = seats.size() > 0;
    return has;
  }

  public int indexOfSeat(Seat aSeat)
  {
    int index = seats.indexOf(aSeat);
    return index;
  }

  public Seat getCurrentSeat(int index)
  {
    Seat aCurrentSeat = currentSeats.get(index);
    return aCurrentSeat;
  }

  /**
   * subsets seats
   */
  public List<Seat> getCurrentSeats()
  {
    List<Seat> newCurrentSeats = Collections.unmodifiableList(currentSeats);
    return newCurrentSeats;
  }

  public int numberOfCurrentSeats()
  {
    int number = currentSeats.size();
    return number;
  }

  public boolean hasCurrentSeats()
  {
    boolean has = currentSeats.size() > 0;
    return has;
  }

  public int indexOfCurrentSeat(Seat aCurrentSeat)
  {
    int index = currentSeats.indexOf(aCurrentSeat);
    return index;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Reservation getReservation(int index)
  {
    Reservation aReservation = reservations.get(index);
    return aReservation;
  }

  public List<Reservation> getReservations()
  {
    List<Reservation> newReservations = Collections.unmodifiableList(reservations);
    return newReservations;
  }

  public int numberOfReservations()
  {
    int number = reservations.size();
    return number;
  }

  public boolean hasReservations()
  {
    boolean has = reservations.size() > 0;
    return has;
  }

  public int indexOfReservation(Reservation aReservation)
  {
    int index = reservations.indexOf(aReservation);
    return index;
  }

  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }

  public boolean isNumberOfSeatsValid()
  {
    boolean isValid = numberOfSeats() >= minimumNumberOfSeats();
    return isValid;
  }

  public static int minimumNumberOfSeats()
  {
    return 1;
  }

  public Seat addSeat()
  {
    Seat aNewSeat = new Seat(this);
    return aNewSeat;
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    Table existingTable = aSeat.getTable();
    boolean isNewTable = existingTable != null && !this.equals(existingTable);

    if (isNewTable && existingTable.numberOfSeats() <= minimumNumberOfSeats())
    {
      return wasAdded;
    }
    if (isNewTable)
    {
      aSeat.setTable(this);
    }
    else
    {
      seats.add(aSeat);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSeat(Seat aSeat)
  {
    boolean wasRemoved = false;
    //Unable to remove aSeat, as it must always have a table
    if (this.equals(aSeat.getTable()))
    {
      return wasRemoved;
    }

    //table already at minimum (1)
    if (numberOfSeats() <= minimumNumberOfSeats())
    {
      return wasRemoved;
    }

    seats.remove(aSeat);
    wasRemoved = true;
    return wasRemoved;
  }

  public boolean addSeatAt(Seat aSeat, int index)
  {  
    boolean wasAdded = false;
    if(addSeat(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeatAt(Seat aSeat, int index)
  {
    boolean wasAdded = false;
    if(seats.contains(aSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeats()) { index = numberOfSeats() - 1; }
      seats.remove(aSeat);
      seats.add(index, aSeat);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeatAt(aSeat, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfCurrentSeats()
  {
    return 0;
  }

  public boolean addCurrentSeat(Seat aCurrentSeat)
  {
    boolean wasAdded = false;
    if (currentSeats.contains(aCurrentSeat)) { return false; }
    currentSeats.add(aCurrentSeat);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCurrentSeat(Seat aCurrentSeat)
  {
    boolean wasRemoved = false;
    if (currentSeats.contains(aCurrentSeat))
    {
      currentSeats.remove(aCurrentSeat);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addCurrentSeatAt(Seat aCurrentSeat, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentSeat(aCurrentSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentSeats()) { index = numberOfCurrentSeats() - 1; }
      currentSeats.remove(aCurrentSeat);
      currentSeats.add(index, aCurrentSeat);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentSeatAt(Seat aCurrentSeat, int index)
  {
    boolean wasAdded = false;
    if(currentSeats.contains(aCurrentSeat))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentSeats()) { index = numberOfCurrentSeats() - 1; }
      currentSeats.remove(aCurrentSeat);
      currentSeats.add(index, aCurrentSeat);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentSeatAt(aCurrentSeat, index);
    }
    return wasAdded;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeTable(this);
    }
    restoApp.addTable(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfReservations()
  {
    return 0;
  }

  public boolean addReservation(Reservation aReservation)
  {
    boolean wasAdded = false;
    if (reservations.contains(aReservation)) { return false; }
    reservations.add(aReservation);
    if (aReservation.indexOfTable(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReservation.addTable(this);
      if (!wasAdded)
      {
        reservations.remove(aReservation);
      }
    }
    return wasAdded;
  }

  public boolean removeReservation(Reservation aReservation)
  {
    boolean wasRemoved = false;
    if (!reservations.contains(aReservation))
    {
      return wasRemoved;
    }

    int oldIndex = reservations.indexOf(aReservation);
    reservations.remove(oldIndex);
    if (aReservation.indexOfTable(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReservation.removeTable(this);
      if (!wasRemoved)
      {
        reservations.add(oldIndex,aReservation);
      }
    }
    return wasRemoved;
  }

  public boolean addReservationAt(Reservation aReservation, int index)
  {  
    boolean wasAdded = false;
    if(addReservation(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReservationAt(Reservation aReservation, int index)
  {
    boolean wasAdded = false;
    if(reservations.contains(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReservationAt(aReservation, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    orders.add(aOrder);
    if (aOrder.indexOfTable(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrder.addTable(this);
      if (!wasAdded)
      {
        orders.remove(aOrder);
      }
    }
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (!orders.contains(aOrder))
    {
      return wasRemoved;
    }

    int oldIndex = orders.indexOf(aOrder);
    orders.remove(oldIndex);
    if (aOrder.indexOfTable(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrder.removeTable(this);
      if (!wasRemoved)
      {
        orders.add(oldIndex,aOrder);
      }
    }
    return wasRemoved;
  }

  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    tablesByNumber.remove(getNumber());
    while (seats.size() > 0)
    {
      Seat aSeat = seats.get(seats.size() - 1);
      aSeat.delete();
      seats.remove(aSeat);
    }
    
    currentSeats.clear();
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    placeholderRestoApp.removeTable(this);
    ArrayList<Reservation> copyOfReservations = new ArrayList<Reservation>(reservations);
    reservations.clear();
    for(Reservation aReservation : copyOfReservations)
    {
      if (aReservation.numberOfTables() <= Reservation.minimumNumberOfTables())
      {
        aReservation.delete();
      }
      else
      {
        aReservation.removeTable(this);
      }
    }
    ArrayList<Order> copyOfOrders = new ArrayList<Order>(orders);
    orders.clear();
    for(Order aOrder : copyOfOrders)
    {
      if (aOrder.numberOfTables() <= Order.minimumNumberOfTables())
      {
        aOrder.delete();
      }
      else
      {
        aOrder.removeTable(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "width" + ":" + getWidth()+ "," +
            "length" + ":" + getLength()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 59 ../../../../../RestoAppPersistence.ump
  private static final long serialVersionUID = 8896099581655989380L ;

  
}