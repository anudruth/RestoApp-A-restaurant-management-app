/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.util.*;

// line 30 "main.ump"
public class Bill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bill Attributes
  private int receiptNumber;
  private int total;

  //Bill Associations
  private List<Customer> customers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bill(int aReceiptNumber, int aTotal)
  {
    receiptNumber = aReceiptNumber;
    total = aTotal;
    customers = new ArrayList<Customer>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReceiptNumber(int aReceiptNumber)
  {
    boolean wasSet = false;
    receiptNumber = aReceiptNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotal(int aTotal)
  {
    boolean wasSet = false;
    total = aTotal;
    wasSet = true;
    return wasSet;
  }

  public int getReceiptNumber()
  {
    return receiptNumber;
  }

  public int getTotal()
  {
    return total;
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

  public static int minimumNumberOfCustomers()
  {
    return 0;
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    customers.add(aCustomer);
    if (aCustomer.indexOfBill(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCustomer.addBill(this);
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

    int oldIndex = customers.indexOf(aCustomer);
    customers.remove(oldIndex);
    if (aCustomer.indexOfBill(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCustomer.removeBill(this);
      if (!wasRemoved)
      {
        customers.add(oldIndex,aCustomer);
      }
    }
    return wasRemoved;
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
    ArrayList<Customer> copyOfCustomers = new ArrayList<Customer>(customers);
    customers.clear();
    for(Customer aCustomer : copyOfCustomers)
    {
      aCustomer.removeBill(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "receiptNumber" + ":" + getReceiptNumber()+ "," +
            "total" + ":" + getTotal()+ "]";
  }
}