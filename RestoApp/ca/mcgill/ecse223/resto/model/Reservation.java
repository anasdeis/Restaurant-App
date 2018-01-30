/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 22 "restoAppModel.ump"
public class Reservation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reservation Attributes
  private String customerName;
  private Date date;
  private Time time;
  private int numberOfCustomers;
  private String phoneNumber;
  private String emailAddress;
  private int reservationNumber;

  //Reservation Associations
  private List<Table> tables;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reservation(String aCustomerName, Date aDate, Time aTime, int aNumberOfCustomers, String aPhoneNumber, String aEmailAddress, int aReservationNumber, RestoApp aRestoApp, Table... allTables)
  {
    customerName = aCustomerName;
    date = aDate;
    time = aTime;
    numberOfCustomers = aNumberOfCustomers;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    reservationNumber = aReservationNumber;
    tables = new ArrayList<Table>();
    boolean didAddTables = setTables(allTables);
    if (!didAddTables)
    {
      throw new RuntimeException("Unable to create Reservation, must have at least 1 tables");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create reservation due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerName(String aCustomerName)
  {
    boolean wasSet = false;
    customerName = aCustomerName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfCustomers(int aNumberOfCustomers)
  {
    boolean wasSet = false;
    numberOfCustomers = aNumberOfCustomers;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setReservationNumber(int aReservationNumber)
  {
    boolean wasSet = false;
    reservationNumber = aReservationNumber;
    wasSet = true;
    return wasSet;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getNumberOfCustomers()
  {
    return numberOfCustomers;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public int getReservationNumber()
  {
    return reservationNumber;
  }

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

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public static int minimumNumberOfTables()
  {
    return 1;
  }

  public boolean addTable(Table aTable)
  {
    boolean wasAdded = false;
    if (tables.contains(aTable)) { return false; }
    Reservation existingReservation = aTable.getReservation();
    if (existingReservation != null && existingReservation.numberOfTables() <= minimumNumberOfTables())
    {
      return wasAdded;
    }
    else if (existingReservation != null)
    {
      existingReservation.tables.remove(aTable);
    }
    tables.add(aTable);
    setReservation(aTable,this);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTable(Table aTable)
  {
    boolean wasRemoved = false;
    if (tables.contains(aTable) && numberOfTables() > minimumNumberOfTables())
    {
      tables.remove(aTable);
      setReservation(aTable,null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean setTables(Table... newTables)
  {
    boolean wasSet = false;
    if (newTables.length < minimumNumberOfTables())
    {
      return wasSet;
    }

    ArrayList<Table> checkNewTables = new ArrayList<Table>();
    HashMap<Reservation,Integer> reservationToNewTables = new HashMap<Reservation,Integer>();
    for (Table aTable : newTables)
    {
      if (checkNewTables.contains(aTable))
      {
        return wasSet;
      }
      else if (aTable.getReservation() != null && !this.equals(aTable.getReservation()))
      {
        Reservation existingReservation = aTable.getReservation();
        if (!reservationToNewTables.containsKey(existingReservation))
        {
          reservationToNewTables.put(existingReservation, new Integer(existingReservation.numberOfTables()));
        }
        Integer currentCount = reservationToNewTables.get(existingReservation);
        int nextCount = currentCount - 1;
        if (nextCount < 1)
        {
          return wasSet;
        }
        reservationToNewTables.put(existingReservation, new Integer(nextCount));
      }
      checkNewTables.add(aTable);
    }

    tables.removeAll(checkNewTables);

    for (Table orphan : tables)
    {
      setReservation(orphan, null);
    }
    tables.clear();
    for (Table aTable : newTables)
    {
      if (aTable.getReservation() != null)
      {
        aTable.getReservation().tables.remove(aTable);
      }
      setReservation(aTable, this);
      tables.add(aTable);
    }
    wasSet = true;
    return wasSet;
  }

  private void setReservation(Table aTable, Reservation aReservation)
  {
    try
    {
      java.lang.reflect.Field mentorField = aTable.getClass().getDeclaredField("reservation");
      mentorField.setAccessible(true);
      mentorField.set(aTable, aReservation);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aReservation to aTable", e);
    }
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
      existingRestoApp.removeReservation(this);
    }
    restoApp.addReservation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(Table aTable : tables)
    {
      setReservation(aTable,null);
    }
    tables.clear();
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeReservation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "customerName" + ":" + getCustomerName()+ "," +
            "numberOfCustomers" + ":" + getNumberOfCustomers()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "reservationNumber" + ":" + getReservationNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }
}