/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/



// line 35 "restoAppModel.ump"
public class Bill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bill Attributes
  private boolean isPaid;
  private double totalPrice;

  //Bill Associations
  private Seat seat;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bill(boolean aIsPaid, double aTotalPrice, Seat aSeat, RestoApp aRestoApp)
  {
    isPaid = aIsPaid;
    totalPrice = aTotalPrice;
    boolean didAddSeat = setSeat(aSeat);
    if (!didAddSeat)
    {
      throw new RuntimeException("Unable to create bill due to seat");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create bill due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsPaid(boolean aIsPaid)
  {
    boolean wasSet = false;
    isPaid = aIsPaid;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalPrice(double aTotalPrice)
  {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsPaid()
  {
    return isPaid;
  }

  public double getTotalPrice()
  {
    return totalPrice;
  }

  public Seat getSeat()
  {
    return seat;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public boolean setSeat(Seat aNewSeat)
  {
    boolean wasSet = false;
    if (aNewSeat == null)
    {
      //Unable to setSeat to null, as bill must always be associated to a seat
      return wasSet;
    }
    
    Bill existingBill = aNewSeat.getBill();
    if (existingBill != null && !equals(existingBill))
    {
      //Unable to setSeat, the current seat already has a bill, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Seat anOldSeat = seat;
    seat = aNewSeat;
    seat.setBill(this);

    if (anOldSeat != null)
    {
      anOldSeat.setBill(null);
    }
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
      existingRestoApp.removeBill(this);
    }
    restoApp.addBill(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Seat existingSeat = seat;
    seat = null;
    if (existingSeat != null)
    {
      existingSeat.setBill(null);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeBill(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isPaid" + ":" + getIsPaid()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seat = "+(getSeat()!=null?Integer.toHexString(System.identityHashCode(getSeat())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }
}