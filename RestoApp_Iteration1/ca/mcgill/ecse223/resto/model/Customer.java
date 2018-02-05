/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 24 "main.ump"
public class Customer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String name;

  //Customer Associations
  private List<Reservation> reservations;
  private Seat seat;
  private List<Bill> bills;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aName, Seat aSeat)
  {
    name = aName;
    reservations = new ArrayList<Reservation>();
    boolean didAddSeat = setSeat(aSeat);
    if (!didAddSeat)
    {
      throw new RuntimeException("Unable to create customer due to seat");
    }
    bills = new ArrayList<Bill>();
    orders = new ArrayList<Order>();
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

  public String getName()
  {
    return name;
  }

  public Reservation getReservation(int index)
  {
    Reservation aReservation = reservations.get(index);
    return aReservation;
  }

  public List<Reservation> getReservations()
  {
    List<Reservation> newReservations = Collections.unmodifiableList(reservations);
    return newReservations;
  }

  public int numberOfReservations()
  {
    int number = reservations.size();
    return number;
  }

  public boolean hasReservations()
  {
    boolean has = reservations.size() > 0;
    return has;
  }

  public int indexOfReservation(Reservation aReservation)
  {
    int index = reservations.indexOf(aReservation);
    return index;
  }

  public Seat getSeat()
  {
    return seat;
  }

  public Bill getBill(int index)
  {
    Bill aBill = bills.get(index);
    return aBill;
  }

  public List<Bill> getBills()
  {
    List<Bill> newBills = Collections.unmodifiableList(bills);
    return newBills;
  }

  public int numberOfBills()
  {
    int number = bills.size();
    return number;
  }

  public boolean hasBills()
  {
    boolean has = bills.size() > 0;
    return has;
  }

  public int indexOfBill(Bill aBill)
  {
    int index = bills.indexOf(aBill);
    return index;
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

  public static int minimumNumberOfReservations()
  {
    return 0;
  }

  public boolean addReservation(Reservation aReservation)
  {
    boolean wasAdded = false;
    if (reservations.contains(aReservation)) { return false; }
    reservations.add(aReservation);
    if (aReservation.indexOfCustomer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReservation.addCustomer(this);
      if (!wasAdded)
      {
        reservations.remove(aReservation);
      }
    }
    return wasAdded;
  }

  public boolean removeReservation(Reservation aReservation)
  {
    boolean wasRemoved = false;
    if (!reservations.contains(aReservation))
    {
      return wasRemoved;
    }

    int oldIndex = reservations.indexOf(aReservation);
    reservations.remove(oldIndex);
    if (aReservation.indexOfCustomer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReservation.removeCustomer(this);
      if (!wasRemoved)
      {
        reservations.add(oldIndex,aReservation);
      }
    }
    return wasRemoved;
  }

  public boolean addReservationAt(Reservation aReservation, int index)
  {  
    boolean wasAdded = false;
    if(addReservation(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReservationAt(Reservation aReservation, int index)
  {
    boolean wasAdded = false;
    if(reservations.contains(aReservation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReservations()) { index = numberOfReservations() - 1; }
      reservations.remove(aReservation);
      reservations.add(index, aReservation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReservationAt(aReservation, index);
    }
    return wasAdded;
  }

  public boolean setSeat(Seat aNewSeat)
  {
    boolean wasSet = false;
    if (aNewSeat == null)
    {
      //Unable to setSeat to null, as customer must always be associated to a seat
      return wasSet;
    }
    
    Customer existingCustomer = aNewSeat.getCustomer();
    if (existingCustomer != null && !equals(existingCustomer))
    {
      //Unable to setSeat, the current seat already has a customer, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Seat anOldSeat = seat;
    seat = aNewSeat;
    seat.setCustomer(this);

    if (anOldSeat != null)
    {
      anOldSeat.setCustomer(null);
    }
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfBills()
  {
    return 0;
  }

  public boolean addBill(Bill aBill)
  {
    boolean wasAdded = false;
    if (bills.contains(aBill)) { return false; }
    bills.add(aBill);
    if (aBill.indexOfCustomer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBill.addCustomer(this);
      if (!wasAdded)
      {
        bills.remove(aBill);
      }
    }
    return wasAdded;
  }

  public boolean removeBill(Bill aBill)
  {
    boolean wasRemoved = false;
    if (!bills.contains(aBill))
    {
      return wasRemoved;
    }

    int oldIndex = bills.indexOf(aBill);
    bills.remove(oldIndex);
    if (aBill.indexOfCustomer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBill.removeCustomer(this);
      if (!wasRemoved)
      {
        bills.add(oldIndex,aBill);
      }
    }
    return wasRemoved;
  }

  public boolean addBillAt(Bill aBill, int index)
  {  
    boolean wasAdded = false;
    if(addBill(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBillAt(Bill aBill, int index)
  {
    boolean wasAdded = false;
    if(bills.contains(aBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBills()) { index = numberOfBills() - 1; }
      bills.remove(aBill);
      bills.add(index, aBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBillAt(aBill, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(Menu aMenu)
  {
    return new Order(this, aMenu);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Customer existingCustomer = aOrder.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aOrder.setCustomer(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a customer
    if (!this.equals(aOrder.getCustomer()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
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

  public void delete()
  {
    ArrayList<Reservation> copyOfReservations = new ArrayList<Reservation>(reservations);
    reservations.clear();
    for(Reservation aReservation : copyOfReservations)
    {
      if (aReservation.numberOfCustomers() <= Reservation.minimumNumberOfCustomers())
      {
        aReservation.delete();
      }
      else
      {
        aReservation.removeCustomer(this);
      }
    }
    Seat existingSeat = seat;
    seat = null;
    if (existingSeat != null)
    {
      existingSeat.setCustomer(null);
    }
    ArrayList<Bill> copyOfBills = new ArrayList<Bill>(bills);
    bills.clear();
    for(Bill aBill : copyOfBills)
    {
      aBill.removeCustomer(this);
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "seat = "+(getSeat()!=null?Integer.toHexString(System.identityHashCode(getSeat())):"null");
  }
}