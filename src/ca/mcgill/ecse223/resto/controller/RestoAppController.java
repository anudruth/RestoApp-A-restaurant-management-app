package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;


public class RestoAppController {

	public RestoAppController() {
	}
	
	public static void removeTable(int number) throws InvalidInputException {
		RestoApp restoApp = RestoAppApplication.getRestoapp();
		Table table = Table.getWithNumber(number);
		if(table == null) throw new InvalidInputException("Invalid Table");
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
	
	//code for create/delete operation should look something like this
//	public static void createTable(int number, int x, int y, int width, int length, int numberOfSeats) throws InvalidInputException {
//		RestoApp restoApp = RestoAppApplication.getRestoApp();
//		try {
//			restoApp.addTable(number, x, y, width, length);
//			//num seats?
//			RestoAppApplication.save();
//		}
//		catch (RuntimeException e) {
//			throw new InvalidInputException(e.getMessage());
//		}
//	}
	
	
//	//code for list should look something like this
//	//JUST AN EXAMPLE
//	public static List<ItemCategory> getItemCategories() {
//		
//		RestoApp restoApp = RestoAppApplication.getRestoApp();
//		ArrayList<ItemCategory> result = new ArrayList<ItemCategory>();
//		for (ItemCategory itemCategory : restoApp.getItemCategories()) {
//			if (true) 
//				result.add(itemCategory);
//		}
//		return result;
}
