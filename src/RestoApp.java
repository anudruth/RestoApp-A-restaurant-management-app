/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.io.Serializable;

// line 3 "RestoAppPersistence.ump"
public class RestoApp implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RestoApp()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 9 "RestoAppPersistence.ump"
   public void reinitialize(){
    Table.reinitializeUniqueNumber(this.getTables());
    Order.reinitializeAutouniqueNumber(this.getOrders());
    MenuItem.reinitializeUniqueName(this.getMenuItems()); //Menu Items through Menu
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 6 "RestoAppPersistence.ump"
  private static final long serialVersionUID = -2683593616927798071L ;

  
}