package ca.mcgill.ecse223.resto.controller;

import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;


public class RestoAppController {
	
	public static final int SEAT_DIAMETER = 20;
	
	public static final int TABLE_SPACING = 5 * SEAT_DIAMETER;
	
	public RestoAppController() {
	}

	
	/**
	 * Creates a 4 seated table with a width of three times the diameter of a seat such that it doesn't overlap with other tables
	 * @throws InvalidInputException
	 */
	public static void createTable() throws InvalidInputException
	{	
		try
		{

			int newTableNumber =0;
			int aX;
			int aY;
			Table lastTable;
//			Table secondLastTable;
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			List<Table> currentTables = restoapp.getCurrentTables();
			if(currentTables.size() != 0) {
				Table highestNumberedTable = currentTables.stream().max(Comparator.comparing(Table::getNumber)).get();
				newTableNumber = highestNumberedTable.getNumber() + 1;
			}
			else {
				newTableNumber =1 ;
			}
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
			if(restoapp.getCurrentTables().size() == 0) {
				aX = 30;
				aY = 50;
			}
			else {
				lastTable = restoapp.getCurrentTables().get(restoapp.getCurrentTables().size()-1);
				//secondLastTable = restoapp.getCurrentTables().get(restoapp.getCurrentTables().size()-2);
				if(newTableNumber%3==0) 
				{
					aX= 30;
					aY =lastTable.getY()+lastTable.getLength()+TABLE_SPACING;
					
				}
				else {
					aX =lastTable.getX()+lastTable.getWidth()+TABLE_SPACING;
					aY =lastTable.getY();
				}


			}

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

			RestoAppController.updateTable(newTable, newTableNumber, 4);
			
			RestoAppApplication.save();
		}
		catch (RuntimeException e)
		{
			throw new InvalidInputException(e.getMessage());
		}

	}
	
	/**
	 * Used by createTable() to see if the table already exists
	 */
	private static Table tableExists(int tableNumber, RestoApp restoapp)
	{
		for (Table table : restoapp.getTables()) {
			if(table.getNumber() == tableNumber) {
				return table;
			}
		}
		return null;
	}
	
	/**
	 * Moves table to given x and y coordinates
	 */
	public static void moveTable(Table table, int x, int y) throws InvalidInputException {
		
		
		if (table == null) throw new InvalidInputException("The table doesn't exist.");
		if (x<0 || y<0) throw new InvalidInputException("x and y can not be negative");
		int width = table.getWidth();
		int length = table.getLength();
		RestoApp r = RestoAppApplication.getRestoapp();
		
		try {
		List <Table> currentTables = r.getCurrentTables();
		for (Table currentTable: currentTables) {
			if (currentTable.doesOverlap(x,y,width,length)) throw new InvalidInputException
			("Location is already taken by another table");
		}
		table.setX(x);
		table.setY(y);
	
		RestoAppApplication.save();
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * Moves table with number to coordinates x and y
	 */
	public static void moveTable(int number, int x, int y) throws InvalidInputException {
		
		Table table = RestoAppApplication.getRestoapp().getCurrentTable(number);
		if (table == null) throw new InvalidInputException("The table doesn't exist.");
		if (x<0 || y<0) throw new InvalidInputException("x and y can not be negative");
		int width = table.getWidth();
		int length = table.getLength();
		RestoApp r = RestoAppApplication.getRestoapp();
		
		try {
		List <Table> currentTables = r.getCurrentTables();
		for (Table currentTable: currentTables) {
			if (currentTable.doesOverlap(x,y,width,length)) throw new InvalidInputException
			("Location is already taken by another table");
		}
		table.setX(x);
		table.setY(y);
	
		RestoAppApplication.save();
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * removes table from currentTables list
	 */
	public static void removeTable(Table table) throws InvalidInputException {
		System.out.println("in removeTable()");
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
			System.out.println("Table "+String.valueOf(table.getNumber())+" removed");
			RestoAppApplication.save();
			
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	/**
	 * sets number of table to newNumber and adds/removes the correct number of seats to get numberOfSeats
	 */
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
	
	/**
	 * does same as updateTable(table,newNumber,numberOfSeats) but gets the table with its number
	 */
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
	
	public static List<ItemCategory> getItemCategories(){
		List<ItemCategory> i;
		i = Arrays.asList(ItemCategory.values());
		return i;
	}
	
	public static List<MenuItem> getMenuItems(ItemCategory itemCategory) throws InvalidInputException{
				
		if(itemCategory == null) {
			throw new InvalidInputException("Item Category unspecified");
		}
		
		List<MenuItem> I = new ArrayList<MenuItem>();
		RestoApp r = RestoAppApplication.getRestoapp();
		Menu menu = r.getMenu();
		List<MenuItem> menuItems = menu.getMenuItems();
		for(MenuItem menuItem : menuItems) {
			boolean current = menuItem.hasCurrentPricedMenuItem();
			ItemCategory category = menuItem.getItemCategory();
			if (current && category.equals(itemCategory)) {
				I.add(menuItem);
			}
		}
		return I;
	}
	
	public  static  int  reserveTable(Date  date,  Time  time,  int  numberInParty,  String  contactName,  String  contactEmailAddress, String contactPhoneNumber, List<Table> tables) throws InvalidInputException {
		
		long currentTime = System.currentTimeMillis();
		long dateMil = date.getTime();
		long timeMil = time.getTime();
		
		if(contactName == null || contactEmailAddress == null || contactPhoneNumber == null || date == null || time == null) {
			throw new InvalidInputException("Fields are NULL");
		} else if(contactName.isEmpty() || contactEmailAddress.isEmpty() || contactPhoneNumber.isEmpty()) {
			throw new InvalidInputException("Fields are Empty");
		} else if(numberInParty < 0) {
			throw new InvalidInputException("Party Size is Negative");
		} else if (dateMil + timeMil < currentTime) {
			throw new InvalidInputException("The reservation time is before current time.");
		}
		
		RestoApp r = RestoAppApplication.getRestoapp();
		
		List<Table> currentTables = r.getCurrentTables();
		
		int seatCapacity = 0;
		
		for(int k = 0; k < tables.size(); k++) {
			boolean current = currentTables.contains(tables.get(k));
			if (current) {
				seatCapacity += tables.get(k).numberOfCurrentSeats();
				List<Reservation> reservations = tables.get(k).getReservations();
				
				for(int i = 0; i < reservations.size(); i++) {
					boolean overlaps = reservations.get(i).doesOverlap(date, time);
					if(overlaps) {
						throw new InvalidInputException("The Date and Time overlap with some other reservation");
					}
				}
			} else {
				throw new InvalidInputException("The table is not in the resto");
			}
		}
		
		if (seatCapacity < numberInParty) {
			throw new InvalidInputException("Not enough seats");
		}
		
		Table[] resTables = new Table[tables.size()];
		for (int k = 0; k < resTables.length; k++) {
			resTables[k] = tables.get(k);
		}
		
		Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, resTables);
		
		RestoAppApplication.save();
		
		return res.getReservationNumber();
	}
	
	public static void startOrder(List<Table> tables) throws InvalidInputException{
		RestoApp r = RestoAppApplication.getRestoapp();
		List<Table> currentTables = r.getCurrentTables();
		for(Table table : tables) {
			boolean current = currentTables.contains(table);
			if(current == false) {
				throw new InvalidInputException("Table not found" + table);
			}
		}
		boolean orderCreated = false;
		Order newOrder = null;
		for(Table table : tables) {
			if (orderCreated) {
				table.addToOrder(newOrder);
			}
			else {
				Order lastOrder = null;
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				table.startOrder();
				if (table.numberOfOrders() > 0 && !table.getOrder(table.numberOfOrders() - 1).equals(lastOrder)){
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders() - 1);
				}
			}
		}
		if (orderCreated = false) {
			throw new InvalidInputException("Order not created");
		}
		r.addCurrentOrder(newOrder);
		RestoAppApplication.save();
	}
	
	public void endOrder(Order order) throws InvalidInputException{

		if(order == null){
			throw new InvalidInputException("no order selected");
		}

		RestoApp r = RestoAppApplication.getRestoapp();
		List<Order> currentOrders = r.getCurrentOrders();
		boolean current = currentOrders.contains(order);
		
		if(!current){
			throw new InvalidInputException("RestoApp does not contain order");
		}
		
		List<Table> tables = order.getTables();
		for(Table table : tables){
			if(table.numberOfOrders()>0 && table.getOrder(table.numberOfOrders()-1).equals(order)){
				table.endOrder(order);
			}
		}
		if(allTablesAvailableOrDifferentCurrentOrder(tables, order)){
			r.removeCurrentOrder(order);
		}
		RestoAppApplication.save();
	}
	
	public boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order){
		boolean flag = true;
		for(Table table : tables){
			if(!(table.getStatus() == Status.Available) || table.getOrder(table.numberOfOrders()-1).equals(order)) {
				flag = false;
			}
		}
		return flag;
	}
	
	public static Map<String,List<OrderItem>> getOrderItems(Table table) throws InvalidInputException {
		
		List<Seat> seats = table.getCurrentSeats();
		if (seats == null) {
			throw new InvalidInputException("This table has no seats");
		}
		
		RestoApp r = RestoAppApplication.getRestoapp();
		
		List<Table> currentTables = r.getCurrentTables();
		
		boolean current = currentTables.contains(table);
		if (!current) {
			throw new InvalidInputException("The table does not exist");
		}
		
		Status status = table.getStatus();
		if (status.equals(Status.Available)) {
			throw new InvalidInputException("This table is not in use");
		}
		
		Order lastOrder = null;
		if(table.numberOfOrders() > 0) {
			lastOrder = table.getOrder(table.numberOfOrders() - 1);
		} else {
			throw new InvalidInputException("Table has no Orders. IMPOSSIBLE");
		}
		
		List<Seat> currentSeats = table.getCurrentSeats();
		Map<String,List<OrderItem>> resultMap = new HashMap<String, List<OrderItem>>();
		List<OrderItem> resultTotal = new ArrayList<OrderItem>();
		
		for(Seat seat : currentSeats) {
			List<OrderItem> seatList = new ArrayList<OrderItem>();
			List<OrderItem> orderItems = seat.getOrderItems();
			for(OrderItem orderItem : orderItems) {
				Order order = orderItem.getOrder();
				if(lastOrder.equals(order) && !resultTotal.contains(orderItem)) {
					resultTotal.add(orderItem);
					seatList.add(orderItem);
				}
			}
			resultMap.put(String.valueOf(seat.getNumber()), seatList);
		}
		
		return resultMap;
	}
	
	public void issueBill() {
		
	}
	
	public static void cancelOrderItem(OrderItem orderItem) throws InvalidInputException{
		List<Seat> seats = orderItem.getSeats();
		Order order = orderItem.getOrder();
		List<Table> tables = new ArrayList<Table>();
		
		for(Seat seat : seats) {
			Table table = seat.getTable();
			Order lastOrder = null;
			if(table.numberOfOrders() > 0) {
				lastOrder = table.getOrder(table.numberOfOrders()-1);
			}
			else {
				throw new InvalidInputException("Table doesn't have an order");
			}
			
			if(lastOrder.equals(order) && !tables.contains(table)) {
				tables.add(table);
			}
		}
		
		for(Table table : tables) {
			table.cancelOrderItem(orderItem);
		}
		
		RestoAppApplication.save();
	}
	
	public static void cancelOrder(Table table) throws InvalidInputException {
		if(table.equals(null)) throw new InvalidInputException("Table is null. Cannot cancel order");
		
		RestoApp r = RestoAppApplication.getRestoapp();
		
		List<Table> currentTables = r.getCurrentTables();
		
		if(!currentTables.contains(table)) throw new InvalidInputException("Table is not in currentTables. Cannot cancel order");
		
		table.cancelOrder();
		
		RestoAppApplication.save();
	}
	
}
