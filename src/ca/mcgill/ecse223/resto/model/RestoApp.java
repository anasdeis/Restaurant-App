/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.20.1.4071 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 3 "../../../../../../RestoAppPersistence.ump"
// line 5 "../../../../../../restoAppModel.ump"
public class RestoApp implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RestoApp Associations
  private List<Table> currentTables;
  private List<Order> currentOrders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RestoApp()
  {
    currentTables = new ArrayList<Table>();
    currentOrders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Table getCurrentTable(int index)
  {
    Table aCurrentTable = currentTables.get(index);
    return aCurrentTable;
  }

  /**
   * subsets tables
   */
  public List<Table> getCurrentTables()
  {
    List<Table> newCurrentTables = Collections.unmodifiableList(currentTables);
    return newCurrentTables;
  }

  public int numberOfCurrentTables()
  {
    int number = currentTables.size();
    return number;
  }

  public boolean hasCurrentTables()
  {
    boolean has = currentTables.size() > 0;
    return has;
  }

  public int indexOfCurrentTable(Table aCurrentTable)
  {
    int index = currentTables.indexOf(aCurrentTable);
    return index;
  }

  public Order getCurrentOrder(int index)
  {
    Order aCurrentOrder = currentOrders.get(index);
    return aCurrentOrder;
  }

  /**
   * subsets orders
   */
  public List<Order> getCurrentOrders()
  {
    List<Order> newCurrentOrders = Collections.unmodifiableList(currentOrders);
    return newCurrentOrders;
  }

  public int numberOfCurrentOrders()
  {
    int number = currentOrders.size();
    return number;
  }

  public boolean hasCurrentOrders()
  {
    boolean has = currentOrders.size() > 0;
    return has;
  }

  public int indexOfCurrentOrder(Order aCurrentOrder)
  {
    int index = currentOrders.indexOf(aCurrentOrder);
    return index;
  }

  public static int minimumNumberOfCurrentTables()
  {
    return 0;
  }

  public boolean addCurrentTable(Table aCurrentTable)
  {
    boolean wasAdded = false;
    if (currentTables.contains(aCurrentTable)) { return false; }
    currentTables.add(aCurrentTable);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCurrentTable(Table aCurrentTable)
  {
    boolean wasRemoved = false;
    if (currentTables.contains(aCurrentTable))
    {
      currentTables.remove(aCurrentTable);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addCurrentTableAt(Table aCurrentTable, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentTable(aCurrentTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentTables()) { index = numberOfCurrentTables() - 1; }
      currentTables.remove(aCurrentTable);
      currentTables.add(index, aCurrentTable);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentTableAt(Table aCurrentTable, int index)
  {
    boolean wasAdded = false;
    if(currentTables.contains(aCurrentTable))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentTables()) { index = numberOfCurrentTables() - 1; }
      currentTables.remove(aCurrentTable);
      currentTables.add(index, aCurrentTable);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentTableAt(aCurrentTable, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfCurrentOrders()
  {
    return 0;
  }

  public boolean addCurrentOrder(Order aCurrentOrder)
  {
    boolean wasAdded = false;
    if (currentOrders.contains(aCurrentOrder)) { return false; }
    currentOrders.add(aCurrentOrder);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCurrentOrder(Order aCurrentOrder)
  {
    boolean wasRemoved = false;
    if (currentOrders.contains(aCurrentOrder))
    {
      currentOrders.remove(aCurrentOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addCurrentOrderAt(Order aCurrentOrder, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentOrder(aCurrentOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentOrders()) { index = numberOfCurrentOrders() - 1; }
      currentOrders.remove(aCurrentOrder);
      currentOrders.add(index, aCurrentOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentOrderAt(Order aCurrentOrder, int index)
  {
    boolean wasAdded = false;
    if(currentOrders.contains(aCurrentOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentOrders()) { index = numberOfCurrentOrders() - 1; }
      currentOrders.remove(aCurrentOrder);
      currentOrders.add(index, aCurrentOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentOrderAt(aCurrentOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    currentTables.clear();
    currentOrders.clear();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 ../../../../../../RestoAppPersistence.ump
  private static final long serialVersionUID = -2683593616927798071L ;
//// line 6 ../../../../../../restoAppModel.ump
//  1 <@>- * Reservation reservations ;
//// line 7 ../../../../../../restoAppModel.ump
//  1 <@>- * Table tables ;
//// line 9 ../../../../../../restoAppModel.ump
//  1 <@>- * Order orders ;
//// line 11 ../../../../../../restoAppModel.ump
//  1 <@>- 1 Menu menu ;
//// line 12 ../../../../../../restoAppModel.ump
//  1 <@>- * PricedMenuItem pricedMenuItems ;
//// line 13 ../../../../../../restoAppModel.ump
//  1 <@>- * Bill bills ;

  
}