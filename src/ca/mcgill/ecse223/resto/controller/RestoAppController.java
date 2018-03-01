package ca.mcgill.ecse223.resto.controller;

import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;


public class RestoAppController {

	public RestoAppController() {
	}
	
	public static void removeTable(int number) throws InvalidInputException {
		RestoApp restoApp = RestoAppApplication.getRestoapp();
		Table table = Table.getWithNumber(number);
				
		boolean reserved = table.hasReservations();
		if(reserved == true) throw new InvalidInputException("Table is reserved");
		try {
			List<Order> currentOrders = restoApp.getCurrentOrders();
			for(Order order : currentOrders) { //if table in use throw exception
				List<Table> tables = order.getTables();
				boolean inUse = tables.contains(table);
				if(inUse == true) throw new InvalidInputException("Table is in use");
			}
			restoApp.removeCurrentTable(table);
			RestoAppApplication.save();
		}catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void removeTable(Table table) throws InvalidInputException {
		if(table == null) throw new InvalidInputException("Invalid Table");
		RestoApp restoApp = RestoAppApplication.getRestoapp();
		boolean reserved = table.hasReservations();
		if(reserved == true) throw new InvalidInputException("Table is reserved"); //if table reserved throw exception
		try {
			List<Order> currentOrders = restoApp.getCurrentOrders();
			for(Order order : currentOrders) { //if table in use throw exception
				List<Table> tables = order.getTables();
				boolean inUse = tables.contains(table);
				if(inUse == true) throw new InvalidInputException("Table is in use");
			}
			restoApp.removeCurrentTable(table);
			RestoAppApplication.save();
		}catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
}