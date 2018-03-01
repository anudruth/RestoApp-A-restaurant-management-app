/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.io.Serializable;

// line 41 "RestoAppPersistence.ump"
public class Order implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 47 "RestoAppPersistence.ump"
   public static  void reinitializeAutouniqueNumber(List<Order> orders){
    int nextId = 0; 
    for (Order order : orders) {
      if (order.getNumber() > nextId) {
        nextId = order.getNumber();
      }
    }
    nextId++;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 44 "RestoAppPersistence.ump"
  private static final long serialVersionUID = -3900912597282882073L ;

  
}