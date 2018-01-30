/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 43 "restoAppModel.ump"
public class Menu
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Menu Associations
  private List<MenuItem> menuItems;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Menu(RestoApp aRestoApp)
  {
    menuItems = new ArrayList<MenuItem>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create menue due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public static int minimumNumberOfMenuItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MenuItem addMenuItem(int aSharedBy, String aItemCategory, double aPrice, String aName, RestoApp aRestoApp)
  {
    return new MenuItem(aSharedBy, aItemCategory, aPrice, aName, aRestoApp, this);
  }

  public boolean addMenuItem(MenuItem aMenuItem)
  {
    boolean wasAdded = false;
    if (menuItems.contains(aMenuItem)) { return false; }
    Menu existingMenu = aMenuItem.getMenu();
    boolean isNewMenu = existingMenu != null && !this.equals(existingMenu);
    if (isNewMenu)
    {
      aMenuItem.setMenu(this);
    }
    else
    {
      menuItems.add(aMenuItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMenuItem(MenuItem aMenuItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aMenuItem, as it must always have a menu
    if (!this.equals(aMenuItem.getMenu()))
    {
      menuItems.remove(aMenuItem);
      wasRemoved = true;
    }
    return wasRemoved;
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
      existingRestoApp.removeMenue(this);
    }
    restoApp.addMenue(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=menuItems.size(); i > 0; i--)
    {
      MenuItem aMenuItem = menuItems.get(i - 1);
      aMenuItem.delete();
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeMenue(this);
    }
  }

}