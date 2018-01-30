/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 54 "restoAppModel.ump"
public class MenuItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MenuItem Attributes
  private int sharedBy;
  private String itemCategory;
  private double price;
  private String name;

  //MenuItem Associations
  private List<Order> orders;
  private RestoApp restoApp;
  private Menu menu;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MenuItem(int aSharedBy, String aItemCategory, double aPrice, String aName, RestoApp aRestoApp, Menu aMenu)
  {
    sharedBy = aSharedBy;
    itemCategory = aItemCategory;
    price = aPrice;
    name = aName;
    orders = new ArrayList<Order>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create menuitem due to restoApp");
    }
    boolean didAddMenu = setMenu(aMenu);
    if (!didAddMenu)
    {
      throw new RuntimeException("Unable to create menuItem due to menu");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSharedBy(int aSharedBy)
  {
    boolean wasSet = false;
    sharedBy = aSharedBy;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemCategory(String aItemCategory)
  {
    boolean wasSet = false;
    itemCategory = aItemCategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getSharedBy()
  {
    return sharedBy;
  }

  public String getItemCategory()
  {
    return itemCategory;
  }

  public double getPrice()
  {
    return price;
  }

  public String getName()
  {
    return name;
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

  public Menu getMenu()
  {
    return menu;
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
    if (aOrder.indexOfMenuItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrder.addMenuItem(this);
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
    if (aOrder.indexOfMenuItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrder.removeMenuItem(this);
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
      existingRestoApp.removeMenuitem(this);
    }
    restoApp.addMenuitem(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setMenu(Menu aMenu)
  {
    boolean wasSet = false;
    if (aMenu == null)
    {
      return wasSet;
    }

    Menu existingMenu = menu;
    menu = aMenu;
    if (existingMenu != null && !existingMenu.equals(aMenu))
    {
      existingMenu.removeMenuItem(this);
    }
    menu.addMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Order> copyOfOrders = new ArrayList<Order>(orders);
    orders.clear();
    for(Order aOrder : copyOfOrders)
    {
      if (aOrder.numberOfMenuItems() <= Order.minimumNumberOfMenuItems())
      {
        aOrder.delete();
      }
      else
      {
        aOrder.removeMenuItem(this);
      }
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeMenuitem(this);
    }
    Menu placeholderMenu = menu;
    this.menu = null;
    if(placeholderMenu != null)
    {
      placeholderMenu.removeMenuItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "sharedBy" + ":" + getSharedBy()+ "," +
            "itemCategory" + ":" + getItemCategory()+ "," +
            "price" + ":" + getPrice()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "menu = "+(getMenu()!=null?Integer.toHexString(System.identityHashCode(getMenu())):"null");
  }
}