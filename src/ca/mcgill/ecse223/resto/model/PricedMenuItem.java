/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 62 "../../../../../RestoAppPersistence.ump"
// line 77 "../../../../../RestoApp.ump"
public class PricedMenuItem implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PricedMenuItem Attributes
  private double price;

  //PricedMenuItem Associations
  private RestoApp restoApp;
  private List<OrderItem> orderItems;
  private MenuItem menuItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PricedMenuItem(double aPrice, RestoApp aRestoApp, MenuItem aMenuItem)
  {
    price = aPrice;
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create pricedMenuItem due to restoApp");
    }
    orderItems = new ArrayList<OrderItem>();
    boolean didAddMenuItem = setMenuItem(aMenuItem);
    if (!didAddMenuItem)
    {
      throw new RuntimeException("Unable to create pricedMenuItem due to menuItem");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public double getPrice()
  {
    return price;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }

  public MenuItem getMenuItem()
  {
    return menuItem;
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
      existingRestoApp.removePricedMenuItem(this);
    }
    restoApp.addPricedMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, Order aOrder, Seat... allSeats)
  {
    return new OrderItem(aQuantity, this, aOrder, allSeats);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    PricedMenuItem existingPricedMenuItem = aOrderItem.getPricedMenuItem();
    boolean isNewPricedMenuItem = existingPricedMenuItem != null && !this.equals(existingPricedMenuItem);
    if (isNewPricedMenuItem)
    {
      aOrderItem.setPricedMenuItem(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a pricedMenuItem
    if (!this.equals(aOrderItem.getPricedMenuItem()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public boolean setMenuItem(MenuItem aMenuItem)
  {
    boolean wasSet = false;
    //Must provide menuItem to pricedMenuItem
    if (aMenuItem == null)
    {
      return wasSet;
    }

    if (menuItem != null && menuItem.numberOfPricedMenuItems() <= MenuItem.minimumNumberOfPricedMenuItems())
    {
      return wasSet;
    }

    MenuItem existingMenuItem = menuItem;
    menuItem = aMenuItem;
    if (existingMenuItem != null && !existingMenuItem.equals(aMenuItem))
    {
      boolean didRemove = existingMenuItem.removePricedMenuItem(this);
      if (!didRemove)
      {
        menuItem = existingMenuItem;
        return wasSet;
      }
    }
    menuItem.addPricedMenuItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removePricedMenuItem(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
    MenuItem placeholderMenuItem = menuItem;
    this.menuItem = null;
    if(placeholderMenuItem != null)
    {
      placeholderMenuItem.removePricedMenuItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "menuItem = "+(getMenuItem()!=null?Integer.toHexString(System.identityHashCode(getMenuItem())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 65 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = -4855219931984388104L ;

  
}