/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 1 "restoAppModel.ump"
public class Table
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Table Attributes
  private int numberOfSeats;
  private int numberOfEmptySeats;
  private int id;
  private boolean isReserved;
  private boolean isInUse;
  private String location;

  //Table Associations
  private RestoApp restoApp;
  private Reservation reservation;
  private List<Seat> seats;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table(int aNumberOfSeats, int aNumberOfEmptySeats, int aId, boolean aIsReserved, boolean aIsInUse, String aLocation, RestoApp aRestoApp)
  {
    numberOfSeats = aNumberOfSeats;
    numberOfEmptySeats = aNumberOfEmptySeats;
    id = aId;
    isReserved = aIsReserved;
    isInUse = aIsInUse;
    location = aLocation;
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create table due to restoApp");
    }
    seats = new ArrayList<Seat>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumberOfSeats(int aNumberOfSeats)
  {
    boolean wasSet = false;
    numberOfSeats = aNumberOfSeats;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfEmptySeats(int aNumberOfEmptySeats)
  {
    boolean wasSet = false;
    numberOfEmptySeats = aNumberOfEmptySeats;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsReserved(boolean aIsReserved)
  {
    boolean wasSet = false;
    isReserved = aIsReserved;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsInUse(boolean aIsInUse)
  {
    boolean wasSet = false;
    isInUse = aIsInUse;
    wasSet = true;
    return wasSet;
  }

  public boolean setLocation(String aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfSeats()
  {
    return numberOfSeats;
  }

  public int getNumberOfEmptySeats()
  {
    return numberOfEmptySeats;
  }

  public int getId()
  {
    return id;
  }

  public boolean getIsReserved()
  {
    return isReserved;
  }

  public boolean getIsInUse()
  {
    return isInUse;
  }

  public String getLocation()
  {
    return location;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public Reservation getReservation()
  {
    return reservation;
  }

  public boolean hasReservation()
  {
    boolean has = reservation != null;
    return has;
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

  public boolean setReservation(Reservation aReservation)
  {
    //
    // This source of this source generation is association_SetOptionalOneToMandatoryMany.jet
    // This set file assumes the generation of a maximumNumberOfXXX method does not exist because 
    // it's not required (No upper bound)
    //   
    boolean wasSet = false;
    Reservation existingReservation = reservation;

    if (existingReservation == null)
    {
      if (aReservation != null)
      {
        if (aReservation.addTable(this))
        {
          existingReservation = aReservation;
          wasSet = true;
        }
      }
    } 
    else if (existingReservation != null)
    {
      if (aReservation == null)
      {
        if (existingReservation.minimumNumberOfTables() < existingReservation.numberOfTables())
        {
          existingReservation.removeTable(this);
          existingReservation = aReservation;  // aReservation == null
          wasSet = true;
        }
      } 
      else
      {
        if (existingReservation.minimumNumberOfTables() < existingReservation.numberOfTables())
        {
          existingReservation.removeTable(this);
          aReservation.addTable(this);
          existingReservation = aReservation;
          wasSet = true;
        }
      }
    }
    if (wasSet)
    {
      reservation = existingReservation;
    }
    return wasSet;
  }
  
  public static int minimumNumberOfSeats()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Seat addSeat(int aSeatNumber, RestoApp aRestoApp)
  {
    return new Seat(aSeatNumber, this, aRestoApp);
  }

  public boolean addSeat(Seat aSeat)
  {
    boolean wasAdded = false;
    if (seats.contains(aSeat)) { return false; }
    Table existingTable = aSeat.getTable();
    boolean isNewTable = existingTable != null && !this.equals(existingTable);
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
    if (!this.equals(aSeat.getTable()))
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
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeTable(this);
    }
    if (reservation != null)
    {
      if (reservation.numberOfTables() <= 1)
      {
        reservation.delete();
      }
      else
      {
        Reservation placeholderReservation = reservation;
        this.reservation = null;
        placeholderReservation.removeTable(this);
      }
    }
    for(int i=seats.size(); i > 0; i--)
    {
      Seat aSeat = seats.get(i - 1);
      aSeat.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfSeats" + ":" + getNumberOfSeats()+ "," +
            "numberOfEmptySeats" + ":" + getNumberOfEmptySeats()+ "," +
            "id" + ":" + getId()+ "," +
            "isReserved" + ":" + getIsReserved()+ "," +
            "isInUse" + ":" + getIsInUse()+ "," +
            "location" + ":" + getLocation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reservation = "+(getReservation()!=null?Integer.toHexString(System.identityHashCode(getReservation())):"null");
  }
}