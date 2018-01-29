/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 7 "main.ump"
public class Table
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Table Attributes
  private int location_x;
  private int location_y;
  private boolean isReserved;
  private boolean isUsed;

  //Table Associations
  private List<Reservation> reservations;
  private List<Seat> seats;
  private Restaurant restaurant;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table(int aLocation_x, int aLocation_y, boolean aIsReserved, boolean aIsUsed, Restaurant aRestaurant)
  {
    location_x = aLocation_x;
    location_y = aLocation_y;
    isReserved = aIsReserved;
    isUsed = aIsUsed;
    reservations = new ArrayList<Reservation>();
    seats = new ArrayList<Seat>();
    boolean didAddRestaurant = setRestaurant(aRestaurant);
    if (!didAddRestaurant)
    {
      throw new RuntimeException("Unable to create table due to restaurant");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLocation_x(int aLocation_x)
  {
    boolean wasSet = false;
    location_x = aLocation_x;
    wasSet = true;
    return wasSet;
  }

  public boolean setLocation_y(int aLocation_y)
  {
    boolean wasSet = false;
    location_y = aLocation_y;
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

  public boolean setIsUsed(boolean aIsUsed)
  {
    boolean wasSet = false;
    isUsed = aIsUsed;
    wasSet = true;
    return wasSet;
  }

  public int getLocation_x()
  {
    return location_x;
  }

  public int getLocation_y()
  {
    return location_y;
  }

  public boolean getIsReserved()
  {
    return isReserved;
  }

  public boolean getIsUsed()
  {
    return isUsed;
  }

  public boolean isIsReserved()
  {
    return isReserved;
  }

  public boolean isIsUsed()
  {
    return isUsed;
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

  public Restaurant getRestaurant()
  {
    return restaurant;
  }

  public static int minimumNumberOfReservations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reservation addReservation(Date aDate, Time aTime, int aPartySize, Customer... allCustomers)
  {
    return new Reservation(aDate, aTime, aPartySize, this, allCustomers);
  }

  public boolean addReservation(Reservation aReservation)
  {
    boolean wasAdded = false;
    if (reservations.contains(aReservation)) { return false; }
    Table existingTable = aReservation.getTable();
    boolean isNewTable = existingTable != null && !this.equals(existingTable);
    if (isNewTable)
    {
      aReservation.setTable(this);
    }
    else
    {
      reservations.add(aReservation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReservation(Reservation aReservation)
  {
    boolean wasRemoved = false;
    //Unable to remove aReservation, as it must always have a table
    if (!this.equals(aReservation.getTable()))
    {
      reservations.remove(aReservation);
      wasRemoved = true;
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

  public static int minimumNumberOfSeats()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Seat addSeat(int aSeatNumber, boolean aIsOccupied)
  {
    return new Seat(aSeatNumber, aIsOccupied, this);
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

  public boolean setRestaurant(Restaurant aRestaurant)
  {
    boolean wasSet = false;
    if (aRestaurant == null)
    {
      return wasSet;
    }

    Restaurant existingRestaurant = restaurant;
    restaurant = aRestaurant;
    if (existingRestaurant != null && !existingRestaurant.equals(aRestaurant))
    {
      existingRestaurant.removeTable(this);
    }
    restaurant.addTable(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=reservations.size(); i > 0; i--)
    {
      Reservation aReservation = reservations.get(i - 1);
      aReservation.delete();
    }
    while (seats.size() > 0)
    {
      Seat aSeat = seats.get(seats.size() - 1);
      aSeat.delete();
      seats.remove(aSeat);
    }
    
    Restaurant existingRestaurant = restaurant;
    restaurant = null;
    if (existingRestaurant != null)
    {
      existingRestaurant.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "location_x" + ":" + getLocation_x()+ "," +
            "location_y" + ":" + getLocation_y()+ "," +
            "isReserved" + ":" + getIsReserved()+ "," +
            "isUsed" + ":" + getIsUsed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restaurant = "+(getRestaurant()!=null?Integer.toHexString(System.identityHashCode(getRestaurant())):"null");
  }
}