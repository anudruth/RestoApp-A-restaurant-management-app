/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 23 "../../../../../RestoAppPersistence.ump"
// line 144 "../../../../../RestoApp.ump"
public class Waiter implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  /**
	 * 
	 */
	private static final long serialVersionUID = -6819781202254769716L;
//Waiter Attributes
  private String name;
  private int id;

  //Waiter Associations
  private List<Bill> bill;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Waiter(String aName, int aId, RestoApp aRestoApp)
  {
    name = aName;
    id = aId;
    bill = new ArrayList<Bill>();
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create waiter due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
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

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }

  public Bill getBill(int index)
  {
    Bill aBill = bill.get(index);
    return aBill;
  }

  public List<Bill> getBill()
  {
    List<Bill> newBill = Collections.unmodifiableList(bill);
    return newBill;
  }

  public int numberOfBill()
  {
    int number = bill.size();
    return number;
  }

  public boolean hasBill()
  {
    boolean has = bill.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bill.indexOf(aBill);
    return index;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public static int minimumNumberOfBill()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bill addBill(Order aOrder, RestoApp aRestoApp, Seat... allIssuedForSeats)
  {
    return new Bill(aOrder, aRestoApp, this, allIssuedForSeats);
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bill.contains(aBill)) { return false; }
    Waiter existingWaiter = aBill.getWaiter();
    boolean isNewWaiter = existingWaiter != null && !this.equals(existingWaiter);
    if (isNewWaiter)
    {
      aBill.setWaiter(this);
    }
    else
    {
      bill.add(aBill);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    //Unable to remove aBill, as it must always have a waiter
    if (!this.equals(aBill.getWaiter()))
    {
      bill.remove(aBill);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addBillAt(Bill aBill, int index)
  {  
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBill()) { index = numberOfBill() - 1; }
      bill.remove(aBill);
      bill.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bill.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBill()) { index = numberOfBill() - 1; }
      bill.remove(aBill);
      bill.add(index, aBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillAt(aBill, index);
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
      existingRestoApp.removeWaiter(this);
    }
    restoApp.addWaiter(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=bill.size(); i > 0; i--)
    {
      Bill aBill = bill.get(i - 1);
      aBill.delete();
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeWaiter(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }
}