/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 63 "restoAppModel.ump"
public class Seat
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Seat Attributes
  private int seatNumber;

  //Seat Associations
  private Table table;
  private RestoApp restoApp;
  private Bill bill;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Seat(int aSeatNumber, Table aTable, RestoApp aRestoApp)
  {
    seatNumber = aSeatNumber;
    boolean didAddTable = setTable(aTable);
    if (!didAddTable)
    {
      throw new RuntimeException("Unable to create seat due to table");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create seat due to restoApp");
    }
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSeatNumber(int aSeatNumber)
  {
    boolean wasSet = false;
    seatNumber = aSeatNumber;
    wasSet = true;
    return wasSet;
  }

  public int getSeatNumber()
  {
    return seatNumber;
  }

  public Table getTable()
  {
    return table;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Bill getBill()
  {
    return bill;
  }

  public boolean hasBill()
  {
    boolean has = bill != null;
    return has;
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

  public boolean setTable(Table aTable)
  {
    boolean wasSet = false;
    if (aTable == null)
    {
      return wasSet;
    }

    Table existingTable = table;
    table = aTable;
    if (existingTable != null && !existingTable.equals(aTable))
    {
      existingTable.removeSeat(this);
    }
    table.addSeat(this);
    wasSet = true;
    return wasSet;
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
      existingRestoApp.removeSeat(this);
    }
    restoApp.addSeat(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setBill(Bill aNewBill)
  {
    boolean wasSet = false;
    if (bill != null && !bill.equals(aNewBill) && equals(bill.getSeat()))
    {
      //Unable to setBill, as existing bill would become an orphan
      return wasSet;
    }

    bill = aNewBill;
    Seat anOldSeat = aNewBill != null ? aNewBill.getSeat() : null;

    if (!this.equals(anOldSeat))
    {
      if (anOldSeat != null)
      {
        anOldSeat.bill = null;
      }
      if (bill != null)
      {
        bill.setSeat(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(boolean aIsCancelled, RestoApp aRestoApp, MenuItem... allMenuItems)
  {
    return new Order(aIsCancelled, this, aRestoApp, allMenuItems);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Seat existingSeat = aOrder.getSeat();
    boolean isNewSeat = existingSeat != null && !this.equals(existingSeat);
    if (isNewSeat)
    {
      aOrder.setSeat(this);
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
    //Unable to remove aOrder, as it must always have a seat
    if (!this.equals(aOrder.getSeat()))
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

  public void delete()
  {
    Table placeholderTable = table;
    this.table = null;
    if(placeholderTable != null)
    {
      placeholderTable.removeSeat(this);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeSeat(this);
    }
    Bill existingBill = bill;
    bill = null;
    if (existingBill != null)
    {
      existingBill.delete();
      existingBill.setSeat(null);
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "seatNumber" + ":" + getSeatNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "table = "+(getTable()!=null?Integer.toHexString(System.identityHashCode(getTable())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bill = "+(getBill()!=null?Integer.toHexString(System.identityHashCode(getBill())):"null");
  }
}