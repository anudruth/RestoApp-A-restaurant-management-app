package ca.mcgill.ecse223.resto.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;


public class RestoAppController {
	
	public static final int SEAT_DIAMETER = 20;
	
	public static final int TABLE_SPACING = 5 * SEAT_DIAMETER;
	
	private Table preExistTable;

	public RestoAppController() {
	}

	public static void createTable() throws InvalidInputException
	{	
		try
		{
			System.out.println("1");
			int newTableNumber =0;
			int aX;
			int aY;
			Table lastTable;
			Table secondLastTable;
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			List<Table> currentTables = restoapp.getCurrentTables();
			if(currentTables.size() != 0) {
				Table highestNumberedTable = currentTables.stream().max(Comparator.comparing(Table::getNumber)).get();
				newTableNumber = highestNumberedTable.getNumber() + 1;
			}
			else {
				newTableNumber =1 ;
			}
			System.out.println("2");
			int numberOfSeats = 4;
			int tableWidth = 3*SEAT_DIAMETER;
			int tableLength;
			if (numberOfSeats%2 ==0) {
				tableLength = (numberOfSeats-1)*SEAT_DIAMETER;
			}
			else
			{
				tableLength = (numberOfSeats) * SEAT_DIAMETER;
			}
			System.out.println("3");
			if(restoapp.getCurrentTables().size() == 0) {
				aX = 30;
				aY = 30;
				System.out.println("3.1");
			}
			else {
				System.out.println("3.2");
				lastTable = restoapp.getCurrentTables().get(restoapp.getCurrentTables().size()-1);
				//secondLastTable = restoapp.getCurrentTables().get(restoapp.getCurrentTables().size()-2);
				if(newTableNumber%2==0) {
					aX= lastTable.getX()+lastTable.getWidth()+TABLE_SPACING;
					aY = lastTable.getY();
					System.out.println("3.1.1");
				}
				else {
					System.out.println("3.1.2");
					aX =lastTable.getX();
					aY =lastTable.getY()+lastTable.getLength()+TABLE_SPACING;;
				}

			}


			System.out.println("4");
			Table newTable;
			
			Table existingTable = tableExists(newTableNumber, restoapp);
			
			
			if(existingTable != null)
			{
				newTable = existingTable;
			}
			else {
				newTable = restoapp.addTable(newTableNumber, aX,aY, tableWidth, tableLength);
			}
			
			restoapp.addCurrentTable(newTable);
			System.out.println("5");
			for (int seatCount = 1; seatCount <= numberOfSeats; seatCount++ )
			{
				newTable.addCurrentSeat(newTable.addSeat());
			}
			RestoAppApplication.save();
		}
		catch (RuntimeException e)
		{
			System.out.println(e.getMessage());
			throw new InvalidInputException(e.getMessage());
		}

	}
	
	private static Table tableExists(int tableNumber, RestoApp restoapp)
	{
		for (Table table : restoapp.getTables()) {
			if(table.getNumber() == tableNumber) {
				return table;
			}
		}
		return null;
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
			System.out.println("Current");
			for(Table atable : RestoAppApplication.getRestoapp().getCurrentTables()) {
				System.out.println("Table "+ atable.getNumber());
			}
			System.out.println("Real");
			for(Table atable : RestoAppApplication.getRestoapp().getTables()) {
				System.out.println("Table "+ atable.getNumber());
			}
			
		}catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	
public static void updateTable(Table table, int newNumber, int numberOfSeats) throws InvalidInputException {
		
		if(table == null) {
			throw new InvalidInputException("The table doesn't exist.");
		} else if (newNumber < 0 || numberOfSeats < 0) {
			throw new InvalidInputException("Numbers are not positive.");
		} else if (table.hasReservations()) {
			throw new InvalidInputException("The table is reserved.");
		}
		
		RestoApp r = RestoAppApplication.getRestoapp();
		
		List<Order> currentOrders = r.getCurrentOrders();
		
		for(int k = 0; k < currentOrders.size(); k++) {
			Order order = currentOrders.get(k);
			List<Table> tables = order.getTables();
			boolean inUse = tables.contains(table);
			if (inUse) {
				throw new InvalidInputException("Table in use.");
			}
		}
		
		try {
			table.setNumber(newNumber);
		} catch (RuntimeException e) {
			throw new InvalidInputException("The new number is a duplicate.");
		}
		
		int n = table.numberOfCurrentSeats();
		
		for (int k = 1; k <= (numberOfSeats - n); k++) {
			Seat seat = table.addSeat();
			table.addCurrentSeat(seat);
		}
		
		for (int k = 1; k <= (n - numberOfSeats); k++) {
			Seat seat = table.getCurrentSeat(0);
			table.removeCurrentSeat(seat);
		}
		
		RestoAppApplication.save();
		
	}
	
	public static void updateTable(int number, int newNumber, int numberOfSeats) throws InvalidInputException {
		
		Table table = RestoAppApplication.getRestoapp().getCurrentTable(number);
	
		if(table == null) {
			throw new InvalidInputException("The table doesn't exist.");
		} else if (newNumber < 0 || numberOfSeats < 0) {
			throw new InvalidInputException("Numbers are not positive.");
		} else if (table.hasReservations()) {
			throw new InvalidInputException("The table is reserved.");
		}
		
		RestoApp r = RestoAppApplication.getRestoapp();
		
		List<Order> currentOrders = r.getCurrentOrders();
		
		for(int k = 0; k < currentOrders.size(); k++) {
			Order order = currentOrders.get(k);
			List<Table> tables = order.getTables();
			boolean inUse = tables.contains(table);
			if (inUse) {
				throw new InvalidInputException("Table in use.");
			}
		}
		
		try {
			table.setNumber(newNumber);
		} catch (RuntimeException e) {
			throw new InvalidInputException("The new number is a duplicate.");
		}
		
		int n = table.numberOfCurrentSeats();
		
		System.out.println("The number of seats is: " + numberOfSeats);
		System.out.println("The current number of seats is: " + n);
		System.out.println("The difference is: " + (numberOfSeats - n));
		
		for (int k = 0; k < (numberOfSeats - n); k++) {
			Seat seat = table.addSeat();
			table.addCurrentSeat(seat);
		}
		
		for (int k = 0; k < (n - numberOfSeats); k++) {
			Seat seat = table.getCurrentSeat(0);
			table.removeCurrentSeat(seat);
		}
		
		RestoAppApplication.save();
		
	}
}