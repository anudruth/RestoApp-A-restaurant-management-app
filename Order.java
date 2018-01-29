/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/



// line 37 "main.ump"
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Associations
  private Customer customer;
  private Menu menu;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(Customer aCustomer, Menu aMenu)
  {
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create order due to customer");
    }
    if (aMenu == null || aMenu.getOrder() != null)
    {
      throw new RuntimeException("Unable to create Order due to aMenu");
    }
    menu = aMenu;
  }

  public Order(Customer aCustomer)
  {
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create order due to customer");
    }
    menu = new Menu(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Customer getCustomer()
  {
    return customer;
  }

  public Menu getMenu()
  {
    return menu;
  }

  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeOrder(this);
    }
    customer.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeOrder(this);
    }
    Menu existingMenu = menu;
    menu = null;
    if (existingMenu != null)
    {
      existingMenu.delete();
    }
  }

}