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
