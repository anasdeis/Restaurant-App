/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 11 "restoAppModel.ump"
public class RestoApp
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RestoApp Associations
  private List<Table> tables;
  private List<Menu> menues;
  private List<MenuItem> menuitems;
  private List<Reservation> reservations;
  private List<Bill> bills;
  private List<Order> orders;
  private List<Seat> seats;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RestoApp()
  {
    tables = new ArrayList<Table>();
    menues = new ArrayList<Menu>();
    menuitems = new ArrayList<MenuItem>();
    reservations = new ArrayList<Reservation>();
    bills = new ArrayList<Bill>();
    orders = new ArrayList<Order>();
    seats = new ArrayList<Seat>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Table getTable(int index)
  {
    Table aTable = tables.get(index);
    return aTable;
  }

  public List<Table> getTables()
  {
    List<Table> newTables = Collections.unmodifiableList(tables);
    return newTables;
  }

  public int numberOfTables()
  {
    int number = tables.size();
    return number;
  }

  public boolean hasTables()
  {
    boolean has = tables.size() > 0;
    return has;
  }

  public int indexOfTable(Table aTable)
  {
    int index = tables.indexOf(aTable);
    return index;
  }

  public Menu getMenue(int index)
  {
    Menu aMenue = menues.get(index);
    return aMenue;
  }

  public List<Menu> getMenues()
  {
    List<Menu> newMenues = Collections.unmodifiableList(menues);
    return newMenues;
  }

  public int numberOfMenues()
  {
    int number = menues.size();
    return number;
  }

  public boolean hasMenues()
  {
    boolean has = menues.size() > 0;
    return has;
  }

  public int indexOfMenue(Menu aMenue)
  {
    int index = menues.indexOf(aMenue);
    return index;
  }

  public MenuItem getMenuitem(int index)
  {
    MenuItem aMenuitem = menuitems.get(index);
    return aMenuitem;
  }

  public List<MenuItem> getMenuitems()
  {
    List<MenuItem> newMenuitems = Collections.unmodifiableList(menuitems);
    return newMenuitems;
  }

  public int numberOfMenuitems()
  {
    int number = menuitems.size();
    return number;
  }

  public boolean hasMenuitems()
  {
    boolean has = menuitems.size() > 0;
    return has;
  }

  public int indexOfMenuitem(MenuItem aMenuitem)
  {
    int index = menuitems.indexOf(aMenuitem);
    return index;
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

  public Bill getBill(int index)
  {
    Bill aBill = bills.get(index);
    return aBill;
  }

  public List<Bill> getBills()
  {
    List<Bill> newBills = Collections.unmodifiableList(bills);
    return newBills;
  }

  public int numberOfBills()
  {
    int number = bills.size();
    return number;
  }

  public boolean hasBills()
  {
    boolean has = bills.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bills.indexOf(aBill);
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

  public static int minimumNumberOfTables()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Table addTable(int aNumberOfSeats, int aNumberOfEmptySeats, int aId, boolean aIsReserved, boolean aIsInUse, String aLocation)
  {
    return new Table(aNumberOfSeats, aNumberOfEmptySeats, aId, aIsReserved, aIsInUse, aLocation, this);
  }

  public boolean addTable(Table aTable)
  {
    boolean wasAdded = false;
    if (tables.contains(aTable)) { return false; }
    RestoApp existingRestoApp = aTable.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aTable.setRestoApp(this);
    }
    else
    {
      tables.add(aTable);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTable(Table aTable)
  {
    boolean wasRemoved = false;
    //Unable to remove aTable, as it must always have a restoApp
    if (!this.equals(aTable.getRestoApp()))
    {
      tables.remove(aTable);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTableAt(Table aTable, int index)
  {  
    boolean wasAdded = false;
    if(addTable(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTableAt(Table aTable, int index)
  {
    boolean wasAdded = false;
    if(tables.contains(aTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTables()) { index = numberOfTables() - 1; }
      tables.remove(aTable);
      tables.add(index, aTable);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTableAt(aTable, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfMenues()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Menu addMenue()
  {
    return new Menu(this);
  }

  public boolean addMenue(Menu aMenue)
  {
    boolean wasAdded = false;
    if (menues.contains(aMenue)) { return false; }
    RestoApp existingRestoApp = aMenue.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aMenue.setRestoApp(this);
    }
    else
    {
      menues.add(aMenue);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMenue(Menu aMenue)
  {
    boolean wasRemoved = false;
    //Unable to remove aMenue, as it must always have a restoApp
    if (!this.equals(aMenue.getRestoApp()))
    {
      menues.remove(aMenue);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addMenueAt(Menu aMenue, int index)
  {  
    boolean wasAdded = false;
    if(addMenue(aMenue))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenues()) { index = numberOfMenues() - 1; }
      menues.remove(aMenue);
      menues.add(index, aMenue);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMenueAt(Menu aMenue, int index)
  {
    boolean wasAdded = false;
    if(menues.contains(aMenue))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenues()) { index = numberOfMenues() - 1; }
      menues.remove(aMenue);
      menues.add(index, aMenue);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMenueAt(aMenue, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfMenuitems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MenuItem addMenuitem(int aSharedBy, String aItemCategory, double aPrice, String aName, Menu aMenu)
  {
    return new MenuItem(aSharedBy, aItemCategory, aPrice, aName, this, aMenu);
  }

  public boolean addMenuitem(MenuItem aMenuitem)
  {
    boolean wasAdded = false;
    if (menuitems.contains(aMenuitem)) { return false; }
    RestoApp existingRestoApp = aMenuitem.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aMenuitem.setRestoApp(this);
    }
    else
    {
      menuitems.add(aMenuitem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMenuitem(MenuItem aMenuitem)
  {
    boolean wasRemoved = false;
    //Unable to remove aMenuitem, as it must always have a restoApp
    if (!this.equals(aMenuitem.getRestoApp()))
    {
      menuitems.remove(aMenuitem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addMenuitemAt(MenuItem aMenuitem, int index)
  {  
    boolean wasAdded = false;
    if(addMenuitem(aMenuitem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuitems()) { index = numberOfMenuitems() - 1; }
      menuitems.remove(aMenuitem);
      menuitems.add(index, aMenuitem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMenuitemAt(MenuItem aMenuitem, int index)
  {
    boolean wasAdded = false;
    if(menuitems.contains(aMenuitem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuitems()) { index = numberOfMenuitems() - 1; }
      menuitems.remove(aMenuitem);
      menuitems.add(index, aMenuitem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMenuitemAt(aMenuitem, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfReservations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reservation addReservation(String aCustomerName, Date aDate, Time aTime, int aNumberOfCustomers, String aPhoneNumber, String aEmailAddress, int aReservationNumber, Table... allTables)
  {
    return new Reservation(aCustomerName, aDate, aTime, aNumberOfCustomers, aPhoneNumber, aEmailAddress, aReservationNumber, this, allTables);
  }

  public boolean addReservation(Reservation aReservation)
  {
    boolean wasAdded = false;
    if (reservations.contains(aReservation)) { return false; }
    RestoApp existingRestoApp = aReservation.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aReservation.setRestoApp(this);
    }
    else
    {
      reservations.add(aReservation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReservation(Reservation aReservation)
  {
    boolean wasRemoved = false;
    //Unable to remove aReservation, as it must always have a restoApp
    if (!this.equals(aReservation.getRestoApp()))
    {
      reservations.remove(aReservation);
      wasRemoved = true;
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

  public static int minimumNumberOfBills()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bill addBill(boolean aIsPaid, double aTotalPrice, Seat aSeat)
  {
    return new Bill(aIsPaid, aTotalPrice, aSeat, this);
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bills.contains(aBill)) { return false; }
    RestoApp existingRestoApp = aBill.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aBill.setRestoApp(this);
    }
    else
    {
      bills.add(aBill);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    //Unable to remove aBill, as it must always have a restoApp
    if (!this.equals(aBill.getRestoApp()))
    {
      bills.remove(aBill);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addBillAt(Bill aBill, int index)
  {  
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bills.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillAt(aBill, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(boolean aIsCancelled, Seat aSeat, MenuItem... allMenuItems)
  {
    return new Order(aIsCancelled, aSeat, this, allMenuItems);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    RestoApp existingRestoApp = aOrder.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aOrder.setRestoApp(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a restoApp
    if (!this.equals(aOrder.getRestoApp()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
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

  public static int minimumNumberOfSeats()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Seat addSeat(int aSeatNumber, Table aTable)
  {
    return new Seat(aSeatNumber, aTable, this);
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    RestoApp existingRestoApp = aSeat.getRestoApp();
    boolean isNewRestoApp = existingRestoApp != null && !this.equals(existingRestoApp);
    if (isNewRestoApp)
    {
      aSeat.setRestoApp(this);
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
    //Unable to remove aSeat, as it must always have a restoApp
    if (!this.equals(aSeat.getRestoApp()))
    {
      seats.remove(aSeat);
      wasRemoved = true;
    }
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

  public void delete()
  {
    while (tables.size() > 0)
    {
      Table aTable = tables.get(tables.size() - 1);
      aTable.delete();
      tables.remove(aTable);
    }
    
    while (menues.size() > 0)
    {
      Menu aMenue = menues.get(menues.size() - 1);
      aMenue.delete();
      menues.remove(aMenue);
    }
    
    while (menuitems.size() > 0)
    {
      MenuItem aMenuitem = menuitems.get(menuitems.size() - 1);
      aMenuitem.delete();
      menuitems.remove(aMenuitem);
    }
    
    while (reservations.size() > 0)
    {
      Reservation aReservation = reservations.get(reservations.size() - 1);
      aReservation.delete();
      reservations.remove(aReservation);
    }
    
    while (bills.size() > 0)
    {
      Bill aBill = bills.get(bills.size() - 1);
      aBill.delete();
      bills.remove(aBill);
    }
    
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
    }
    
    while (seats.size() > 0)
    {
      Seat aSeat = seats.get(seats.size() - 1);
      aSeat.delete();
      seats.remove(aSeat);
    }
    
  }

}