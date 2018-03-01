/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.io.Serializable;

// line 27 "RestoAppPersistence.ump"
public class MenuItem implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MenuItem()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 33 "RestoAppPersistence.ump"
   public static  void reinitializeUniqueName(Menu menu){
    List <MenuItem> menuItems = menu.getMenuItems();
    menuitemsByName = new HashMap<String, MenuItem>();
    for (MenuItem menuItem : menuItems) {
      menuitemsByName.put(menuItem.getName(), menuItem);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 30 "RestoAppPersistence.ump"
  private static final long serialVersionUID = -1776230320092632776L ;

  
}