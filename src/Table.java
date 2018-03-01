/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/


import java.io.Serializable;

// line 81 "RestoAppPersistence.ump"
public class Table implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Table()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

  // line 87 "RestoAppPersistence.ump"
   public static  void reinitializeUniqueNumber(List<Table> tables){
    tablesByNumber = new HashMap<Integer, Table>();
    for (Table table : tables) {
      tablesByNumber.put(table.getNumber(), table);
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 84 "RestoAppPersistence.ump"
  private static final long serialVersionUID = 8896099581655989380L ;

  
}