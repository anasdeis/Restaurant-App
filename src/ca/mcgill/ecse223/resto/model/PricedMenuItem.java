/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.20.1.4071 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.util.*;

// line 62 "../../../../../../restoAppModel.ump"
public class PricedMenuItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PricedMenuItem Attributes
  private double price;

  //PricedMenuItem Associations
  private List<OrderItem> orderItems;
  private MenuItem menuItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PricedMenuItem(double aPrice, MenuItem aMenuItem)
  {
    price = aPrice;
    orderItems = new ArrayList<OrderItem>();
    boolean didAddMenuItem = setMenuItem(aMenuItem);
    if (!didAddMenuItem)
    {
      throw new RuntimeException("Unable to create pricedMenuItem due to menuItem");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public double getPrice()
  {
    return price;
  }

  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }

  public MenuItem getMenuItem()
  {
    return menuItem;
  }

  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }

  public OrderItem addOrderItem(int aQuantity, Seat... allSeats)
  {
    return new OrderItem(aQuantity, this, allSeats);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    PricedMenuItem existingPricedMenuItem = aOrderItem.getPricedMenuItem();
    boolean isNewPricedMenuItem = existingPricedMenuItem != null && !this.equals(existingPricedMenuItem);
    if (isNewPricedMenuItem)
    {
      aOrderItem.setPricedMenuItem(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a pricedMenuItem
    if (!this.equals(aOrderItem.getPricedMenuItem()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public boolean setMenuItem(MenuItem aMenuItem)
  {
    boolean wasSet = false;
    //Must provide menuItem to pricedMenuItem
    if (aMenuItem == null)
    {
      return wasSet;
    }

    if (menuItem != null && menuItem.numberOfPricedMenuItems() <= MenuItem.minimumNumberOfPricedMenuItems())
    {
      return wasSet;
    }

    MenuItem existingMenuItem = menuItem;
    menuItem = aMenuItem;
    if (existingMenuItem != null && !existingMenuItem.equals(aMenuItem))
    {
      boolean didRemove = existingMenuItem.removePricedMenuItem(this);
      if (!didRemove)
      {
        menuItem = existingMenuItem;
        return wasSet;
      }
    }
    menuItem.addPricedMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
    MenuItem placeholderMenuItem = menuItem;
    this.menuItem = null;
    placeholderMenuItem.removePricedMenuItem(this);
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "menuItem = "+(getMenuItem()!=null?Integer.toHexString(System.identityHashCode(getMenuItem())):"null")
     + outputString;
  }
}