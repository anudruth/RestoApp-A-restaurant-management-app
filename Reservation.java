/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 43 "main.ump"
public class Reservation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reservation Attributes
  private Date date;
  private Time time;
  private int partySize;

  //Reservation Associations
  private Table table;
  private List<Customer> customers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reservation(Date aDate, Time aTime, int aPartySize, Table aTable, Customer... allCustomers)
  {
    date = aDate;
    time = aTime;
    partySize = aPartySize;
    boolean didAddTable = setTable(aTable);
    if (!didAddTable)
    {
      throw new RuntimeException("Unable to create reservation due to table");
    }
    customers = new ArrayList<Customer>();
    boolean didAddCustomers = setCustomers(allCustomers);
    if (!didAddCustomers)
    {
      throw new RuntimeException("Unable to create Reservation, must have at least 1 customers");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setPartySize(int aPartySize)
  {
    boolean wasSet = false;
    partySize = aPartySize;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getPartySize()
  {
    return partySize;
  }

  public Table getTable()
  {
    return table;
  }

  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
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
      existingTable.removeReservation(this);
    }
    table.addReservation(this);
    wasSet = true;
    return wasSet;
  }

  public boolean isNumberOfCustomersValid()
  {
    boolean isValid = numberOfCustomers() >= minimumNumberOfCustomers();
    return isValid;
  }

  public static int minimumNumberOfCustomers()
  {
    return 1;
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    customers.add(aCustomer);
    if (aCustomer.indexOfReservation(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCustomer.addReservation(this);
      if (!wasAdded)
      {
        customers.remove(aCustomer);
      }
    }
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (!customers.contains(aCustomer))
    {
      return wasRemoved;
    }

    if (numberOfCustomers() <= minimumNumberOfCustomers())
    {
      return wasRemoved;
    }

    int oldIndex = customers.indexOf(aCustomer);
    customers.remove(oldIndex);
    if (aCustomer.indexOfReservation(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCustomer.removeReservation(this);
      if (!wasRemoved)
      {
        customers.add(oldIndex,aCustomer);
      }
    }
    return wasRemoved;
  }

  public boolean setCustomers(Customer... newCustomers)
  {
    boolean wasSet = false;
    ArrayList<Customer> verifiedCustomers = new ArrayList<Customer>();
    for (Customer aCustomer : newCustomers)
    {
      if (verifiedCustomers.contains(aCustomer))
      {
        continue;
      }
      verifiedCustomers.add(aCustomer);
    }

    if (verifiedCustomers.size() != newCustomers.length || verifiedCustomers.size() < minimumNumberOfCustomers())
    {
      return wasSet;
    }

    ArrayList<Customer> oldCustomers = new ArrayList<Customer>(customers);
    customers.clear();
    for (Customer aNewCustomer : verifiedCustomers)
    {
      customers.add(aNewCustomer);
      if (oldCustomers.contains(aNewCustomer))
      {
        oldCustomers.remove(aNewCustomer);
      }
      else
      {
        aNewCustomer.addReservation(this);
      }
    }

    for (Customer anOldCustomer : oldCustomers)
    {
      anOldCustomer.removeReservation(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Table placeholderTable = table;
    this.table = null;
    if(placeholderTable != null)
    {
      placeholderTable.removeReservation(this);
    }
    ArrayList<Customer> copyOfCustomers = new ArrayList<Customer>(customers);
    customers.clear();
    for(Customer aCustomer : copyOfCustomers)
    {
      aCustomer.removeReservation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "partySize" + ":" + getPartySize()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "table = "+(getTable()!=null?Integer.toHexString(System.identityHashCode(getTable())):"null");
  }
}