/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/



// line 17 "main.ump"
public class Seat
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Seat Attributes
  private int seatNumber;
  private boolean isOccupied;

  //Seat Associations
  private Customer customer;
  private Table table;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Seat(int aSeatNumber, boolean aIsOccupied, Table aTable)
  {
    seatNumber = aSeatNumber;
    isOccupied = aIsOccupied;
    boolean didAddTable = setTable(aTable);
    if (!didAddTable)
    {
      throw new RuntimeException("Unable to create seat due to table");
    }
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

  public boolean setIsOccupied(boolean aIsOccupied)
  {
    boolean wasSet = false;
    isOccupied = aIsOccupied;
    wasSet = true;
    return wasSet;
  }

  public int getSeatNumber()
  {
    return seatNumber;
  }

  public boolean getIsOccupied()
  {
    return isOccupied;
  }

  public boolean isIsOccupied()
  {
    return isOccupied;
  }

  public Customer getCustomer()
  {
    return customer;
  }

  public boolean hasCustomer()
  {
    boolean has = customer != null;
    return has;
  }

  public Table getTable()
  {
    return table;
  }

  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (customer != null && !customer.equals(aNewCustomer) && equals(customer.getSeat()))
    {
      //Unable to setCustomer, as existing customer would become an orphan
      return wasSet;
    }

    customer = aNewCustomer;
    Seat anOldSeat = aNewCustomer != null ? aNewCustomer.getSeat() : null;

    if (!this.equals(anOldSeat))
    {
      if (anOldSeat != null)
      {
        anOldSeat.customer = null;
      }
      if (customer != null)
      {
        customer.setSeat(this);
      }
    }
    wasSet = true;
    return wasSet;
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

  public void delete()
  {
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
    Table existingTable = table;
    table = null;
    if (existingTable != null)
    {
      existingTable.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "seatNumber" + ":" + getSeatNumber()+ "," +
            "isOccupied" + ":" + getIsOccupied()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "table = "+(getTable()!=null?Integer.toHexString(System.identityHashCode(getTable())):"null");
  }
}