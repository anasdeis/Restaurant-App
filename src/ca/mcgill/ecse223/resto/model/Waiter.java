/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.0-b05b57321 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 62 "../../../../../RestoAppPersistence.ump"
// line 113 "../../../../../RestoApp v3.ump"
public class Waiter implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Waiter Attributes
  private String waiterName;
  private String waiterEmailAddress;
  private String waiterPhoneNumber;

  //Waiter Associations
  private List<Order> orders;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Waiter(String aWaiterName, String aWaiterEmailAddress, String aWaiterPhoneNumber, RestoApp aRestoApp)
  {
    waiterName = aWaiterName;
    waiterEmailAddress = aWaiterEmailAddress;
    waiterPhoneNumber = aWaiterPhoneNumber;
    orders = new ArrayList<Order>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create waiter due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWaiterName(String aWaiterName)
  {
    boolean wasSet = false;
    waiterName = aWaiterName;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaiterEmailAddress(String aWaiterEmailAddress)
  {
    boolean wasSet = false;
    waiterEmailAddress = aWaiterEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaiterPhoneNumber(String aWaiterPhoneNumber)
  {
    boolean wasSet = false;
    waiterPhoneNumber = aWaiterPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getWaiterName()
  {
    return waiterName;
  }

  public String getWaiterEmailAddress()
  {
    return waiterEmailAddress;
  }

  public String getWaiterPhoneNumber()
  {
    return waiterPhoneNumber;
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

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Waiter existingWaiter = aOrder.getWaiter();
    if (existingWaiter == null)
    {
      aOrder.setWaiter(this);
    }
    else if (!this.equals(existingWaiter))
    {
      existingWaiter.removeOrder(aOrder);
      addOrder(aOrder);
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
    if (orders.contains(aOrder))
    {
      orders.remove(aOrder);
      aOrder.setWaiter(null);
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
      existingRestoApp.removeWaiter(this);
    }
    restoApp.addWaiter(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !orders.isEmpty() )
    {
      orders.get(0).setWaiter(null);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    placeholderRestoApp.removeWaiter(this);
  }


  public String toString()
  {
    return super.toString() + "["+
            "waiterName" + ":" + getWaiterName()+ "," +
            "waiterEmailAddress" + ":" + getWaiterEmailAddress()+ "," +
            "waiterPhoneNumber" + ":" + getWaiterPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 65 ../../../../../RestoAppPersistence.ump
  private static final long serialVersionUID = 2897099481655989380L ;

  
}