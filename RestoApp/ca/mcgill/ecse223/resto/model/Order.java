/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 48 "restoAppModel.ump"
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private boolean isCancelled;

  //Order Associations
  private Seat seat;
  private RestoApp restoApp;
  private List<MenuItem> menuItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(boolean aIsCancelled, Seat aSeat, RestoApp aRestoApp, MenuItem... allMenuItems)
  {
    isCancelled = aIsCancelled;
    boolean didAddSeat = setSeat(aSeat);
    if (!didAddSeat)
    {
      throw new RuntimeException("Unable to create order due to seat");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create order due to restoApp");
    }
    menuItems = new ArrayList<MenuItem>();
    boolean didAddMenuItems = setMenuItems(allMenuItems);
    if (!didAddMenuItems)
    {
      throw new RuntimeException("Unable to create Order, must have at least 1 menuItems");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsCancelled(boolean aIsCancelled)
  {
    boolean wasSet = false;
    isCancelled = aIsCancelled;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsCancelled()
  {
    return isCancelled;
  }

  public Seat getSeat()
  {
    return seat;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public MenuItem getMenuItem(int index)
  {
    MenuItem aMenuItem = menuItems.get(index);
    return aMenuItem;
  }

  public List<MenuItem> getMenuItems()
  {
    List<MenuItem> newMenuItems = Collections.unmodifiableList(menuItems);
    return newMenuItems;
  }

  public int numberOfMenuItems()
  {
    int number = menuItems.size();
    return number;
  }

  public boolean hasMenuItems()
  {
    boolean has = menuItems.size() > 0;
    return has;
  }

  public int indexOfMenuItem(MenuItem aMenuItem)
  {
    int index = menuItems.indexOf(aMenuItem);
    return index;
  }

  public boolean setSeat(Seat aSeat)
  {
    boolean wasSet = false;
    if (aSeat == null)
    {
      return wasSet;
    }

    Seat existingSeat = seat;
    seat = aSeat;
    if (existingSeat != null && !existingSeat.equals(aSeat))
    {
      existingSeat.removeOrder(this);
    }
    seat.addOrder(this);
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
      existingRestoApp.removeOrder(this);
    }
    restoApp.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public boolean isNumberOfMenuItemsValid()
  {
    boolean isValid = numberOfMenuItems() >= minimumNumberOfMenuItems();
    return isValid;
  }

  public static int minimumNumberOfMenuItems()
  {
    return 1;
  }

  public boolean addMenuItem(MenuItem aMenuItem)
  {
    boolean wasAdded = false;
    if (menuItems.contains(aMenuItem)) { return false; }
    menuItems.add(aMenuItem);
    if (aMenuItem.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMenuItem.addOrder(this);
      if (!wasAdded)
      {
        menuItems.remove(aMenuItem);
      }
    }
    return wasAdded;
  }

  public boolean removeMenuItem(MenuItem aMenuItem)
  {
    boolean wasRemoved = false;
    if (!menuItems.contains(aMenuItem))
    {
      return wasRemoved;
    }

    if (numberOfMenuItems() <= minimumNumberOfMenuItems())
    {
      return wasRemoved;
    }

    int oldIndex = menuItems.indexOf(aMenuItem);
    menuItems.remove(oldIndex);
    if (aMenuItem.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMenuItem.removeOrder(this);
      if (!wasRemoved)
      {
        menuItems.add(oldIndex,aMenuItem);
      }
    }
    return wasRemoved;
  }

  public boolean setMenuItems(MenuItem... newMenuItems)
  {
    boolean wasSet = false;
    ArrayList<MenuItem> verifiedMenuItems = new ArrayList<MenuItem>();
    for (MenuItem aMenuItem : newMenuItems)
    {
      if (verifiedMenuItems.contains(aMenuItem))
      {
        continue;
      }
      verifiedMenuItems.add(aMenuItem);
    }

    if (verifiedMenuItems.size() != newMenuItems.length || verifiedMenuItems.size() < minimumNumberOfMenuItems())
    {
      return wasSet;
    }

    ArrayList<MenuItem> oldMenuItems = new ArrayList<MenuItem>(menuItems);
    menuItems.clear();
    for (MenuItem aNewMenuItem : verifiedMenuItems)
    {
      menuItems.add(aNewMenuItem);
      if (oldMenuItems.contains(aNewMenuItem))
      {
        oldMenuItems.remove(aNewMenuItem);
      }
      else
      {
        aNewMenuItem.addOrder(this);
      }
    }

    for (MenuItem anOldMenuItem : oldMenuItems)
    {
      anOldMenuItem.removeOrder(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addMenuItemAt(MenuItem aMenuItem, int index)
  {  
    boolean wasAdded = false;
    if(addMenuItem(aMenuItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuItems()) { index = numberOfMenuItems() - 1; }
      menuItems.remove(aMenuItem);
      menuItems.add(index, aMenuItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMenuItemAt(MenuItem aMenuItem, int index)
  {
    boolean wasAdded = false;
    if(menuItems.contains(aMenuItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMenuItems()) { index = numberOfMenuItems() - 1; }
      menuItems.remove(aMenuItem);
      menuItems.add(index, aMenuItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMenuItemAt(aMenuItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Seat placeholderSeat = seat;
    this.seat = null;
    if(placeholderSeat != null)
    {
      placeholderSeat.removeOrder(this);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeOrder(this);
    }
    ArrayList<MenuItem> copyOfMenuItems = new ArrayList<MenuItem>(menuItems);
    menuItems.clear();
    for(MenuItem aMenuItem : copyOfMenuItems)
    {
      aMenuItem.removeOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isCancelled" + ":" + getIsCancelled()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seat = "+(getSeat()!=null?Integer.toHexString(System.identityHashCode(getSeat())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }
}