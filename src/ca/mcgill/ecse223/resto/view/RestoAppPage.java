/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.Waiter;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;

public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -2702005067769134471L;
	private static final int MAX_SEATS = 8;
	
    /************DECLARATIONS******************/
	private Color mainPopUpColor = new Color(246,245,202);
    private JPanel Image_panel;
    private JPanel app_panel;
    private JPanel buttons_panel;
    private JLabel jLabel2;
    private JPanel scroll_panel;
    private Integer selectedWaiter = -1;
    private JScrollPane scroll_layout;
    private RestoVisualizer restoVisualizer;
    // UI elements
  	private JLabel errorMessage;
  	// table
	private Table selectedTable = null;
  	// data elements
  	private String error = null;
  	// table assignment
  	//...
  	 private JTextField addWaiterField = new JTextField();
  	 private JComboBox <String> waiterList;
	
 	public RestoAppPage() {
 		initComponents();
 		refreshData();
 	}
 	
 	/** This method is called from within the constructor to initialize the form.
	 */
    private void initComponents() {
    	
    	errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
        app_panel = new JPanel();
        scroll_panel = new JPanel();
        restoVisualizer = new RestoVisualizer();
        restoVisualizer.setMinimumSize(new Dimension(10000,10000));
        scroll_layout = new JScrollPane();
        Image_panel = new JPanel();
        jLabel2 = new JLabel();
        buttons_panel = new JPanel();
        new JButton();
        new JButton();
        
        RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        
        app_panel.setBackground(java.awt.Color.white);

        Image_panel.setBackground(java.awt.Color.white);

        jLabel2.setIcon(new ImageIcon(getClass().getResource("../resources/Screen Shot 2018-02-20 at 1.08.07 AM.png"))); // NOI18N
        
        //elements for addTableButton, reserveTableButton, billTableButton, displayMenuButton
        RoundButton addTableButton = new RoundButton();
        addTableButton.setBackground(new Color(255,255,255));
        addTableButton.setToolTipText("Add a Table");
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/add.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			addTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		addTableButtonActionPerformed(evt);
            }
        });
		
		RoundButton waiterButton = new RoundButton();
		waiterButton.setBackground(new Color(255,255,255));
		waiterButton.setToolTipText("Add a Waiter");

		try {
			Image img = ImageIO.read(getClass().getResource("../resources/waiter.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			waiterButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		waiterButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		waiter_managementButtonActionPerformed(evt);
            }
        });
		
		RoundButton reserveTableButton = new RoundButton();
        reserveTableButton.setBackground(new Color(255,255,255));
        reserveTableButton.setToolTipText("Reserve a Table");
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/reserve.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			reserveTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		reserveTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		reserveTableButtonActionPerformed(evt);
            }
        });
		 
		RoundButton billTableButton = new RoundButton();
		billTableButton.setBackground(new Color(255,255,255));
		billTableButton.setToolTipText("Bill a Table or Seat(s)");

		try {
			Image img = ImageIO.read(getClass().getResource("../resources/bill.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			billTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		billTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		billTableButtonActionPerformed(evt);
            }
        });
		
		RoundButton displayMenuButton = new RoundButton();
        displayMenuButton.setBackground(new Color(255,255,255));
        displayMenuButton.setToolTipText("Display the Menu");
		
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/displayMenu.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			displayMenuButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		displayMenuButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		displayMenuButtonActionPerformed(evt);
            }
        });
		
		RoundButton orderButton = new RoundButton();
        orderButton.setBackground(new Color(255,255,255));
        orderButton.setToolTipText("Set as In Use");

        try {
			Image img = ImageIO.read(getClass().getResource("../resources/inUse.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			orderButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        orderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		orderButtonActionPerformed(evt);
            }
        });
        
		/********************LAYOUT FOR INITIAL STATE*******************/
        GroupLayout scroll_panel_Layout = new GroupLayout(scroll_panel);
        scroll_panel.setLayout(scroll_panel_Layout);
        scroll_panel_Layout.setHorizontalGroup(
        		scroll_panel_Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scroll_panel_Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(restoVisualizer)
                .addContainerGap(69, Short.MAX_VALUE))
            );
        scroll_panel_Layout.setVerticalGroup(
        		scroll_panel_Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(scroll_panel_Layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(restoVisualizer)
                    .addContainerGap(179, Short.MAX_VALUE))
            );
        
        scroll_layout.setViewportView(scroll_panel);

        
        
        GroupLayout Image_panelLayout = new GroupLayout(Image_panel);
        Image_panel.setLayout(Image_panelLayout);
        Image_panelLayout.setHorizontalGroup(
            Image_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, Image_panelLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );
        Image_panelLayout.setVerticalGroup(
            Image_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(Image_panelLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(139, 139, 139))
        );

        buttons_panel.setBackground(java.awt.Color.white);


        GroupLayout buttons_panelLayout = new GroupLayout(buttons_panel);
        buttons_panel.setLayout(buttons_panelLayout);
        buttons_panelLayout.setHorizontalGroup(
            buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(billTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reserveTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            	.addComponent(waiterButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            	.addComponent(orderButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            	.addComponent(displayMenuButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        

//        buttons_panelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addTableButton, reserveTableButton, displayMenuButton});

        buttons_panelLayout.setVerticalGroup(
            buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                	.addComponent(addTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                	.addComponent(billTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reserveTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(waiterButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orderButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayMenuButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        GroupLayout app_panelLayout = new GroupLayout(app_panel);
        app_panel.setLayout(app_panelLayout);
        app_panelLayout.setHorizontalGroup(
            app_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(app_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(app_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_layout, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttons_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(Image_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        
        app_panelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {Image_panel, scroll_layout});

        app_panelLayout.setVerticalGroup(
            app_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(app_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(app_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Image_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(app_panelLayout.createSequentialGroup()
                        .addComponent(scroll_layout)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttons_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(app_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(app_panel, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
        );

        pack();
    }

    private void refreshData() {
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
			
		}
		// this is needed because the size of the window changes depending on whether an error message is shown or not
		pack();
	}
    
    
    /**************ACTIONS*****************/
    private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = null;
		try {
			
			RestoAppController.createTable();
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
    }
    
    private void addWaiterButtonActionPerformed(java.awt.event.ActionEvent evt, String waiterName) {
		// clear error message
		error = null;
		
		// call the controller
		try {
			RestoAppController.createWaiter(addWaiterField.getText());
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// update visuals
		refreshData();
    }
    
    private void addOrderItemActionPerformed(java.awt.event.ActionEvent evt, String seatNumber, Table table){
    	String[] seatNumbers = {seatNumber}; 
		menuPopUp2(0,0,RestoAppController.getItemCategories(), null, seatNumbers, table);
		
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
    
    private void prepBillButtonActionPerformed(java.awt.event.ActionEvent evt, String tables, String seats) {
    	
    		//billPopup("hi", "bye", "100.45");
    	try {
    		List<Seat> seat_list= RestoAppController.getSeats(tables, seats);
    		List<OrderItem> orderItems = RestoAppController.issueBill(seat_list);
    		String[] orderItemsArray = new String[(orderItems.size())];
    		int k = 0;
    		Double price = 0.0;
    		String waiter= "";
    		for(OrderItem oItem: orderItems) {
    			price += oItem.getPricedMenuItem().getPrice()*oItem.getQuantity();
    			orderItemsArray[k] = oItem.toString();
    			//waiter = oItem.getOrder().getWaiter().getName();
    			k++;
    			
    		}
    		
    		billPopup(orderItemsArray, waiter, Double.toString(price));
    		
    		
    	} catch (InvalidInputException e) {
    		errorPopUp(e.getMessage());
    	}
    
    	
    }
    private void reserveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	reservePopUp(2,2);
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
    }
    
    private void waiter_managementButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	waiterPopUp(2,2);
    	RestoApp restoapp = RestoAppApplication.getRestoapp();
    	restoVisualizer.setResto(restoapp);
    }
    private void billTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    	issueBillPopUp();
    }
    
    private void displayMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {
		menuPopUp(2,2,RestoAppController.getItemCategories(), null);
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
    
    private void orderButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	orderPopup();
    	RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
    }
    
    private void tableCurrentSeatsChangeActionPerformed(ChangeEvent evt, int numSeats) {
    	try {
			RestoAppController.updateTable(selectedTable, selectedTable.getNumber(), numSeats);
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
    }
    
    private void tableNumberChangeActionPerformed(ActionEvent evt, String newTableNumber) {
		try {
			RestoAppController.updateTable(selectedTable, Integer.valueOf(newTableNumber), selectedTable.getCurrentSeats().size());
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
    }
    
    private void viewOrderActionPerformed(ActionEvent evt, Table table) {
		try {
	    	Map<String, List<OrderItem>> orderMap;
			try {
				orderMap = RestoAppController.getOrderItems(table);
				viewOrderPopUp(table, orderMap);
			} catch (InvalidInputException e) {
				errorPopUp(e.getMessage());
			}
	    	
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
		} finally {
			
		}
    }
    
    private void moveTableButtonActionPerformed(ActionEvent evt) {
    	error = null;
		restoVisualizer.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				int x_coordinate = e.getX();
				int y_coordinate= e.getY();
				try {
					RestoAppController.moveTable(selectedTable, x_coordinate,y_coordinate);
					RestoApp restoapp = RestoAppApplication.getRestoapp();
					restoVisualizer.setResto(restoapp);
					restoVisualizer.removeMouseListener(this);
				} catch (InvalidInputException e1) {
					e1.printStackTrace();
					errorPopUp(e1.getMessage()); //test
				} 	
			}
		});
	}
    
    /**
     * If selectedTable (global variable) is in the CurrentTables, calls the Controller method to remove
     */
	private void removeTableButtonActionPerformed(ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (!(selectedTable.getNumber() > 0))
			errorPopUp("Table needs to be selected for deletion!");
		
		if (error.length() == 0) {
			// call the controller
			try {
				Table toDelete = null;
				List<Table> currentTables = RestoAppApplication.getRestoapp().getCurrentTables();
				for(Table table : currentTables) {
					if(table.getNumber() == selectedTable.getNumber()) {
						toDelete = table;
					}
				}
				RestoAppController.removeTable(toDelete);
				RestoApp restoapp = RestoAppApplication.getRestoapp();
				restoVisualizer.setResto(restoapp);
			} catch (InvalidInputException e) {
				errorPopUp(e.getMessage());
			}
		}
		
		// update visuals
		refreshData();
	};
	
	private void rotateTableButtonActionPerformed(ActionEvent evt) {
		error = "";
		if (!(selectedTable.getNumber() > 0))
			errorPopUp("Table needs to be selected for rotation");
		
		if (error.length() == 0) {
			Table toRotate = null;
			List<Table> currentTables = RestoAppApplication.getRestoapp().getCurrentTables();
			for(Table table : currentTables) {
				if(table.getNumber() == selectedTable.getNumber()) {
					toRotate = table;
				}
			}
			if(toRotate.getFlipped()==0) {
				toRotate.setFlipped(1);
			}
			else {
				toRotate.setFlipped(0);
			}
			
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
		}
	}
	
	/**
	 * Displays the proper popUp for the category of menu selected
	 */
	private void CategoryButtonActionPerformed(ActionEvent evt, ItemCategory itemCategory, List<ItemCategory> i) {
		try {
			menuPopUp(2, 2, i, RestoAppController.getMenuItems(itemCategory));
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	
	private void CategoryButtonActionPerformed2(ActionEvent evt, ItemCategory itemCategory, List<ItemCategory> i, String[] seatNumbers, Table table) {
		try {
			menuPopUp2(0, 0, i, RestoAppController.getMenuItems(itemCategory), seatNumbers, table);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	
	private void MenuItemButtonActionPerformed2(ActionEvent evt, MenuItem menuItem, String[] seatNumbers, int quantity, Table table) {
		List<Seat> currentSeats = table.getCurrentSeats();
		List<Seat> seats = new ArrayList<Seat>();;
		for(Seat seat: currentSeats) {
			for(int i = 0; i < seatNumbers.length; i++) {
				if(seat.getNumber() == Integer.parseInt(seatNumbers[i])) {
					seats.add(seat);
				}
			}
		}
		for(Seat seat: seats) {
			System.out.println(seat.getNumber());
		}
		try {
			RestoAppController.orderMenuItem(menuItem, quantity, seats);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}

	private void removeSelectedOrderItemActionPerformed(ActionEvent evt, String seatNumber, JList<OrderItem> list) {
		
		OrderItem selectedOrderItem;
		if((selectedOrderItem = list.getSelectedValue()) == null){
			errorPopUp("No OrderItem selected");
		}
		
		try {
			RestoAppController.cancelOrderItem(selectedOrderItem, seatNumber);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	
	private void removeWaiterActionPerformed(ActionEvent evt) {
	/*
	 	TODO	
		try {
			
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
		
		*/
	}
	
	private void cancelTableOrderItemActionPerformed(ActionEvent evt, Table table) {
		try {
			RestoAppController.cancelOrder(table);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	
	private void endOrderActionPerformed(ActionEvent evt, Order order) {
		try {
			RestoAppController.endOrder(order);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	
	private void startOrderActionPerformed(ActionEvent evt, String[] tableNumbers) {
		List<Table> tables = new ArrayList<Table>();
		
		
		for (int k = 0; k < tableNumbers.length; k++) {
			int tableNumber = Integer.parseInt(tableNumbers[k]);
			tables.add(Table.getWithNumber(tableNumber));
		}
		try {
			RestoAppController.startOrder(tables);
			orderPopup();
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
		
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
	}
	/**
	 * Is called by the mouseListener in RestoVisualiser whenever a table is clicked. Makes the popup for that table appear
	 */
	public void tablePopUp(int x, int y, Table aTable) {

		selectedTable = aTable;
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(3,3));
        popupMenu.setBackground(mainPopUpColor);
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();

        //Table Label
        JLabel tableName = new JLabel();
        tableName.setBackground(mainPopUpColor);
        tableName.setOpaque(true);
        
        JTextField tableNumber = new JTextField();
        tableNumber.setBackground(mainPopUpColor);
        tableNumber.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		String newTableNumber = tableNumber.getText();
        		tableNumberChangeActionPerformed(evt, newTableNumber);
            }
        });   
        
        JSlider tableSlider = new JSlider();
        tableSlider.setBackground(mainPopUpColor);
        tableSlider.setMaximum(MAX_SEATS);
		tableSlider.setMinimum(1);
		tableSlider.setMajorTickSpacing(1);
		tableSlider.setPaintTicks(true);
		tableSlider.setPaintLabels(true);
		tableSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent evt) {
        		if(aTable.getStatus()!=Status.Available) {
        			errorPopUp("Cannot change size of InUse Table");
        		}
        		else {
            		int numSeats = tableSlider.getValue();
            		tableCurrentSeatsChangeActionPerformed(evt, numSeats);
        		}
        		
            }
        });
        
		//Delete Button
        RoundButton removeTableButton = new RoundButton();
        removeTableButton.setBackground(mainPopUpColor);
        removeTableButton.setToolTipText("Remove selected Table");
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/remove_yellow.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			removeTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		removeTableButtonActionPerformed(evt);
        		popupMenu.setVisible(false);
            }
        });
		
		//Move Button
		RoundButton moveTableButton = new RoundButton();
        moveTableButton.setBackground(mainPopUpColor);
        moveTableButton.setToolTipText("Move selected Table");

        try {
			Image img = ImageIO.read(getClass().getResource("../resources/move_green.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			moveTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        moveTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		moveTableButtonActionPerformed(evt);
        		popupMenu.setVisible(false);
        	}
        });

        //Rotate button
        RoundButton rotateTableButton = new RoundButton();
        rotateTableButton.setBackground(mainPopUpColor);
        rotateTableButton.setToolTipText("Rotate selected Table");

        try {
			Image img = ImageIO.read(getClass().getResource("../resources/rotate_green.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			rotateTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        rotateTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		rotateTableButtonActionPerformed(evt);
        	}
        });

        //viewOrder Button
        RoundButton viewOrderButton = new RoundButton();
        viewOrderButton.setBackground(mainPopUpColor);
        viewOrderButton.setToolTipText("View and manage Orders");

        try {
			Image img = ImageIO.read(getClass().getResource("../resources/order_yellow.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			viewOrderButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        viewOrderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		viewOrderActionPerformed(evt, selectedTable);
            }
        });
        
        
        String selectedTableNumber = "-1";
		if(selectedTable != null) {
			selectedTableNumber = Integer.toString(selectedTable.getNumber());
		}
		
		tableName.setText("Table ");
		tableNumber.setText(selectedTableNumber);
		tableSlider.setValue(selectedTable.getCurrentSeats().size());
		
		popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	        
	    popupMenuItem1.add(tableName);
	    popupMenuItem1.add(tableNumber);
	    popupMenuItem2.add(removeTableButton);
	    popupMenuItem2.add(moveTableButton);
	    popupMenuItem3.add(rotateTableButton);
	    popupMenuItem3.add(viewOrderButton);
	    popupMenuItem4.add(tableSlider);
	        
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
	        
		popupMenu.show(Image_panel, x, y);
	}

	/**
	 * Is called whenever a menu is selected. Makes the list of items for that menu appear.
	 */
	public void menuPopUp(int x, int y, List<ItemCategory> items, List<MenuItem> menuItems) {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(50,50));
        popupMenu.setBackground(mainPopUpColor);
        
        String c1 = items.get(0).toString();
        String c2 = items.get(1).toString();
        String c3 = items.get(2).toString();
        String c4 = items.get(3).toString();
        String c5 = items.get(4).toString();
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();
        JPanel popupMenuItem5 = new JPanel();
        JPanel popupMenuItem6 = new JPanel();
        JPanel popupMenuItem7 = new JPanel();

        JLabel menuName = new JLabel();
        menuName.setBackground(mainPopUpColor);
        menuName.setOpaque(true);
        menuName.setText("Menu");
		menuName.setFont(new Font(menuName.getFont().getName(), Font.PLAIN, 20));
        
        //Category1
        JButton Category1Button = new JButton();
        Category1Button.setBackground(mainPopUpColor);
		Category1Button.setText(c1);
		Category1Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(0), items);
            }
        });
        
		//Category2
        JButton Category2Button = new JButton();
        Category2Button.setBackground(mainPopUpColor);
		Category2Button.setText(c2);
		Category2Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(1), items);
            }
        });

		//Category3
        JButton Category3Button = new JButton();
        Category3Button.setBackground(mainPopUpColor);
		Category3Button.setText(c3);
		Category3Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(2), items);
            }
        });

		//Category4
        JButton Category4Button = new JButton();
        Category4Button.setBackground(mainPopUpColor);
		Category4Button.setText(c4);
		Category4Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(3), items);
            }
        });
        
		//Category5
        JButton Category5Button = new JButton();
        Category5Button.setBackground(mainPopUpColor);
		Category5Button.setText(c5);
		Category5Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(4), items);
            }
        });
		
		if (menuItems != null) {
			int j = 0;
			for (MenuItem menuItem : menuItems) {
				j++;
				JButton MenuItemButton = new JButton();
				MenuItemButton.setBackground(mainPopUpColor);
				MenuItemButton.setText(menuItem.getName());
				if (j < 5) {
					popupMenuItem3.add(MenuItemButton);
				}
				else if (j < 9) {
					popupMenuItem4.add(MenuItemButton);
				}
				else if (j < 13) {
					popupMenuItem5.add(MenuItemButton);
				}
				else if (j < 17) {
					popupMenuItem6.add(MenuItemButton);
				}
				else {
					popupMenuItem7.add(MenuItemButton);
				}
			}
		}
		
		popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem7.setLayout(new BoxLayout(popupMenuItem7, BoxLayout.LINE_AXIS));
	        
	    popupMenuItem1.add(menuName);
	    popupMenuItem2.add(Category1Button);
	    popupMenuItem2.add(Category2Button);
	    popupMenuItem2.add(Category3Button);
	    popupMenuItem2.add(Category4Button);
	    popupMenuItem2.add(Category5Button);
	        
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
	    popupMenu.add(popupMenuItem5);
	    popupMenu.add(popupMenuItem6);
	    popupMenu.add(popupMenuItem7);
	    
		popupMenu.show(Image_panel, x, y);
	}
	
	public void reservePopUp(int x, int y) {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(3,3));
        popupMenu.setBackground(mainPopUpColor);
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();
        JPanel popupMenuItem5 = new JPanel();
        JPanel popupMenuItem6 = new JPanel();
        JPanel popupMenuItem7 = new JPanel();
        JPanel popupMenuItem8 = new JPanel();
        JPanel popupMenuItem10 = new JPanel();
        JPanel popupMenuItem11 = new JPanel();

        //Reservation Labels
        
        JLabel makeReservation = new JLabel();
        makeReservation.setBackground(mainPopUpColor);
        makeReservation.setOpaque(true);
        makeReservation.setText("                              Make a Reservation                              ");
        
        JLabel reservationTable = new JLabel();
        reservationTable.setBackground(mainPopUpColor);
        reservationTable.setOpaque(true);
        reservationTable.setText("Tables: ");
        
        JLabel reservationDate = new JLabel();
        reservationDate.setBackground(mainPopUpColor);
        reservationDate.setOpaque(true);
        reservationDate.setText("Date (MM/DD/YYYY): ");
        
        JLabel reservationTime = new JLabel();
        reservationTime.setBackground(mainPopUpColor);
        reservationTime.setOpaque(true);
        reservationTime.setText("Time (HH:MM): ");
        
        JLabel reservationSize = new JLabel();
        reservationSize.setBackground(mainPopUpColor);
        reservationSize.setOpaque(true);
        reservationSize.setText("Size: ");
        
        JLabel reservationName = new JLabel();
        reservationName.setBackground(mainPopUpColor);
        reservationName.setOpaque(true);
        reservationName.setText("Name: ");
        
        JLabel reservationMail = new JLabel();
        reservationMail.setBackground(mainPopUpColor);
        reservationMail.setOpaque(true);
        reservationMail.setText("Email Address: ");
        
        JLabel reservationPhone = new JLabel();
        reservationPhone.setBackground(mainPopUpColor);
        reservationPhone.setOpaque(true);
        reservationPhone.setText("Phone Number: ");
        
        JLabel reservationNumber = new JLabel();
        reservationNumber.setBackground(mainPopUpColor);
        reservationNumber.setOpaque(true);
        reservationNumber.setText("Reservation Number: ");
        
        JLabel reservationField = new JLabel();
        reservationField.setBackground(mainPopUpColor);
        reservationField.setOpaque(true);
        
        PlaceholderTextField tableField = new PlaceholderTextField("Enter Table Numbers seperated by commas");
        tableField.setBackground(new Color(255, 230, 253));
        
        PlaceholderTextField dateField = new PlaceholderTextField("Enter Date (MM/DD/YYYY)");
        dateField.setBackground(new Color(255, 230, 253));
        
        PlaceholderTextField timeField = new PlaceholderTextField("Enter Time (HH:MM)/(H:MM)");
        timeField.setBackground(new Color(255, 230, 253));

        PlaceholderTextField sizeField = new PlaceholderTextField("Enter Party Size");
        sizeField.setBackground(new Color(255, 230, 253));
        
        PlaceholderTextField nameField = new PlaceholderTextField("Enter Reservation Name");
        nameField.setBackground(new Color(255, 230, 253));
        
        PlaceholderTextField mailField = new PlaceholderTextField("Enter Reservation Email");
        mailField.setBackground(new Color(255, 230, 253));
        
        PlaceholderTextField phoneField = new PlaceholderTextField("Enter Reservation Phone Number");
        phoneField.setBackground(new Color(255, 230, 253));
        
		//Delete Button
        JButton makeReservationButton = new JButton();
        makeReservationButton.setBackground(mainPopUpColor);
        makeReservationButton.setText("Make Reservation");
        makeReservationButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		List<Table> tables = new ArrayList<Table>();
        		
        		String[] tableNumbers = tableField.getText().split(",");
        		
        		for (int k = 0; k < tableNumbers.length; k++) {
        			int tableNumber = Integer.parseInt(tableNumbers[k]);
        			tables.add(Table.getWithNumber(tableNumber));
        		}
        		
        		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        		java.util.Date utilDate = null;
				try {
					utilDate = sdf1.parse(dateField.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		java.sql.Date date = new java.sql.Date(utilDate.getTime()); 
        		
        		Time time = null;
        		
        		if(timeField.getText().length() == 5) {
            		time = new Time(Integer.parseInt(timeField.getText().substring(0,2)), Integer.parseInt(timeField.getText().substring(3,5)), 0);
        		} else if(timeField.getText().length() == 4){
            		time = new Time(Integer.parseInt(timeField.getText().substring(0,1)), Integer.parseInt(timeField.getText().substring(2,4)), 0);
        		}
        		
        		try {
					int reservationNumber = RestoAppController.reserveTable(date, time, Integer.parseInt(sizeField.getText()), nameField.getText(), mailField.getText(), phoneField.getText(), tables);
					reservationField.setText(String.valueOf(reservationNumber));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidInputException e) {
					errorPopUp(e.getMessage());
					e.printStackTrace();
				}
        		
        		tableField.setText("");
        		dateField.setText("");
        		timeField.setText("");
        		sizeField.setText("");
        		nameField.setText("");
        		mailField.setText("");
        		phoneField.setText("");
        		
            }
        });
		
		popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem7.setLayout(new BoxLayout(popupMenuItem7, BoxLayout.LINE_AXIS));
	    popupMenuItem8.setLayout(new BoxLayout(popupMenuItem8, BoxLayout.LINE_AXIS));
	    popupMenuItem10.setLayout(new BoxLayout(popupMenuItem10, BoxLayout.LINE_AXIS));
	    popupMenuItem11.setLayout(new BoxLayout(popupMenuItem11, BoxLayout.LINE_AXIS));
	        
	    popupMenuItem1.add(makeReservation);
	    popupMenuItem2.add(reservationTable);
	    popupMenuItem2.add(tableField);
	    popupMenuItem3.add(reservationDate);
	    popupMenuItem3.add(dateField);
	    popupMenuItem4.add(reservationTime);
	    popupMenuItem4.add(timeField);
	    popupMenuItem5.add(reservationSize);
	    popupMenuItem5.add(sizeField);
	    popupMenuItem6.add(reservationName);
	    popupMenuItem6.add(nameField);
	    popupMenuItem7.add(reservationMail);
	    popupMenuItem7.add(mailField);
	    popupMenuItem8.add(reservationPhone);
	    popupMenuItem8.add(phoneField);
	    popupMenuItem10.add(makeReservationButton);
	    popupMenuItem11.add(reservationNumber);
	    popupMenuItem11.add(reservationField);
	        
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
	    popupMenu.add(popupMenuItem5);
	    popupMenu.add(popupMenuItem6);
	    popupMenu.add(popupMenuItem7);
	    popupMenu.add(popupMenuItem8);
	    popupMenu.add(popupMenuItem10);
	    popupMenu.add(popupMenuItem11);
    
		popupMenu.show(Image_panel, x, y);
	}
	
	public void waiterPopUp(int x, int y) {
		final JPopupMenu popUpWaiter = new JPopupMenu();
        popUpWaiter.setPopupSize(300,150);
        popUpWaiter.setBackground(mainPopUpColor);
        
        JPanel popUpItem1 = new JPanel();

        
        JLabel waiterManagementLabel = new JLabel();
        waiterManagementLabel.setBackground(mainPopUpColor);
        waiterManagementLabel.setOpaque(true);
        waiterManagementLabel.setText("                              Waiter Management                              ");
        

        
        popUpWaiter.setLayout(new BoxLayout(popUpWaiter, BoxLayout.PAGE_AXIS));
	    popUpItem1.setLayout(new BoxLayout(popUpItem1, BoxLayout.LINE_AXIS));

        
	    popUpItem1.add(waiterManagementLabel);
	    
	    popUpWaiter.add(popUpItem1);

	    
//--------------------------------------------------------------//
	    //Panels
		
	    JPanel popUpItem2 = new JPanel();
	    popUpItem2.setBackground(new Color(255, 230, 153));
	    
	    JPanel popUpItem3 = new JPanel();
	    popUpItem3.setBackground(new Color(255, 230, 153));
	    
	    JPanel popUpItem4 = new JPanel();
	    popUpItem4.setBackground(new Color(255, 230, 153));
	    
	    JPanel popUpItem5 = new JPanel();
	    popUpItem5.setBackground(new Color(255, 230, 153));
	    
	    JPanel popUpItem6 = new JPanel();
	    popUpItem6.setBackground(new Color(255, 230, 153));

	    
	    //Labels
	   
	    
	    JLabel nameLabel = new JLabel();
	    nameLabel.setBackground(mainPopUpColor);
	    nameLabel.setOpaque(true);
	    nameLabel.setText("Name: ");
	        
	    JLabel removeWaiterLabel = new JLabel();
	    removeWaiterLabel.setBackground(mainPopUpColor);
	    removeWaiterLabel.setOpaque(true);
	    removeWaiterLabel.setText("Remove Waiter: ");
	    
	    
	    //Text Fields
	    
	  //  private JTextField addWaiterField = new JTextField();
	    addWaiterField.setBackground(new Color(255, 255, 255));
	    
	    
	    PlaceholderTextField removeWaiterField = new PlaceholderTextField("Enter table number");
	    removeWaiterField.setBackground(new Color(255, 230, 253));
	    
	    waiterList = new JComboBox<String>(new String[0]);
	    waiterList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedWaiter = cb.getSelectedIndex();
		        System.out.println(selectedWaiter);
			}
		});
	    waiterList.setBackground(new Color(255, 255, 255));
	    waiterList.addItem("Test");
	    waiterList.addItem("Test2");
	    waiterList.addItem("Test3");
	    
	    //Buttons
	    
	    JButton deleteWaiterButton = new JButton();
	    deleteWaiterButton.setBackground(new Color(255, 255, 255));
	    deleteWaiterButton.setText("Delete Waiter");
	    deleteWaiterButton.addActionListener(new java.awt.event.ActionListener() {
	    		public void actionPerformed(java.awt.event.ActionEvent evt) {
	    			removeWaiterActionPerformed(evt);
	    		}
	    });
	    
	    JButton addWaiterButton = new JButton();
	    addWaiterButton.setBackground(new Color(255, 255,255));
	    addWaiterButton.setText("Add Waiter");
	    addWaiterButton.addActionListener(new java.awt.event.ActionListener() {
	    	public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		addWaiterButtonActionPerformed(evt, addWaiterField.getText());
	    	}
	    });
	    
	    
	    popUpWaiter.setLayout(new BoxLayout(popUpWaiter, BoxLayout.PAGE_AXIS));
	    popUpItem1.setLayout(new BoxLayout(popUpItem1, BoxLayout.LINE_AXIS));
	    popUpItem3.setLayout(new BoxLayout(popUpItem3, BoxLayout.LINE_AXIS));
	    popUpItem4.setLayout(new BoxLayout(popUpItem4, BoxLayout.LINE_AXIS));
	    popUpItem5.setLayout(new BoxLayout(popUpItem5, BoxLayout.LINE_AXIS));
	    popUpItem6.setLayout(new BoxLayout(popUpItem6, BoxLayout.LINE_AXIS));
  
	   
	    popUpItem3.add(nameLabel);
	    popUpItem3.add(addWaiterField);
	    popUpItem4.add(addWaiterButton);
	    popUpItem5.add(removeWaiterLabel);
	    popUpItem5.add(waiterList);
	    popUpItem6.add(deleteWaiterButton);    
	    
	    popUpWaiter.add(popUpItem1);
	    popUpWaiter.add(popUpItem3);
	    popUpWaiter.add(popUpItem4);
	    popUpWaiter.add(popUpItem5);
	    popUpWaiter.add(popUpItem6);
   
    	
	    popUpWaiter.show(Image_panel, 2, 2);
	    
	  
	}
	
	public void errorPopUp(String errorMessage) {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(3,3));
        popupMenu.setBackground(mainPopUpColor);
        
        JPanel popupMenuItem6 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        
        JLabel errorLabel = new JLabel();
        errorLabel.setBackground(mainPopUpColor);
        errorLabel.setOpaque(true);
        errorLabel.setText("                              Error:                              ");
        
        JLabel errorText = new JLabel();
        errorText.setBackground(mainPopUpColor);
        errorText.setOpaque(true);
        errorText.setText(errorMessage);
        
        popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
        
	    popupMenuItem6.add(errorLabel);
	    popupMenuItem2.add(errorText);
	    
	    popupMenu.add(popupMenuItem6);
	    popupMenu.add(popupMenuItem2);
	    
	    popupMenu.show(Image_panel, 0, 0);
	}
	
	public void orderPopup() {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(50,50));
        popupMenu.setBackground(mainPopUpColor);
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();
        
        JLabel orderLabel = new JLabel();
        orderLabel.setBackground(mainPopUpColor);
        orderLabel.setOpaque(true);
        
        orderLabel.setText("Create New Order");
        orderLabel.setFont(new Font(orderLabel.getFont().getName(), Font.PLAIN, 20));

        
        JLabel instructions = new JLabel();
        instructions.setBackground(mainPopUpColor);
        instructions.setOpaque(true);
        instructions.setText("Add other tables to order? (Seperated by commas)");
        
        JTextField tableField = new JTextField();
        tableField.setBackground(mainPopUpColor);
        
        JButton startOrderButton = new JButton();
        startOrderButton.setBackground(mainPopUpColor);
        startOrderButton.setText("Start Order");
        startOrderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		String[] tableNumbers = tableField.getText().split(",");
        		startOrderActionPerformed(evt, tableNumbers);
        		orderPopup();
        	}
        });
        
        popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	   
	    
	    popupMenuItem1.add(orderLabel);
	    popupMenuItem2.add(instructions);
	    popupMenuItem3.add(tableField);
	    popupMenuItem4.add(startOrderButton);
	    
	   
	    
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
        
	    JPanel popupMenuItem5 = new JPanel();
        popupMenuItem5.setBackground(mainPopUpColor);
        popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
 	    JLabel orderString = new JLabel();
 	    orderString.setText("Current Orders");
        orderString.setFont(new Font(orderString.getFont().getName(), Font.PLAIN, 20));
        popupMenuItem5.add(orderString);
        popupMenu.add(popupMenuItem5);
	    
        List<Order> orders = RestoAppApplication.getRestoapp().getCurrentOrders();
        
        for(Order order : orders) {
        	JLabel orderDescriptionLabel = new JLabel();
        	orderDescriptionLabel.setBackground(mainPopUpColor);
        	orderDescriptionLabel.setOpaque(true);
        	
        	String tablesList = "";
        	for(Table table : order.getTables()) {
        		tablesList = tablesList+table.getNumber()+", ";
        	}
        	
        	orderDescriptionLabel.setText("Number: "+order.getNumber()+" for Tables: "+tablesList);
        	
        	JButton endOrderButton = new JButton();
            endOrderButton.setBackground(mainPopUpColor);
            endOrderButton.setText("End order");
             
            endOrderButton.addActionListener(new java.awt.event.ActionListener() {
            	public void actionPerformed(java.awt.event.ActionEvent evt) {
             		endOrderActionPerformed(evt, order);
             		orderPopup();
             	}
            });
             
            JPanel popupMenuItem6 = new JPanel();
            popupMenuItem6.setBackground(mainPopUpColor);
            popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
     	    popupMenuItem6.add(orderDescriptionLabel);
     	    popupMenuItem6.add(endOrderButton);
     	    popupMenu.add(popupMenuItem6);
        }
	    
	    popupMenu.show(Image_panel, 2, 2);
	}
	
	public JPanel seatPopUp(JPopupMenu viewOrderPopUp, Table table, String seatNumber, List<OrderItem> orders) {

	    final JPanel seatPopupPanel = new JPanel();
	    seatPopupPanel.setLayout(new BoxLayout(seatPopupPanel, BoxLayout.Y_AXIS));
	    seatPopupPanel.setSize(300, 300);
	    seatPopupPanel.setBackground(mainPopUpColor);
	    
	    //Display the Seat Number at the top of panel
	    JPanel seatNumberPanel = new JPanel();
	    seatNumberPanel.setBackground(mainPopUpColor);
	    
	    JLabel seatLable = new JLabel();
	    seatLable.setBackground(mainPopUpColor);
	    seatLable.setOpaque(true);
	    seatLable.setText("Seat: " + seatNumber);
	    seatLable.setFont(new Font(seatLable.getFont().getName(), Font.PLAIN, 20));

	    
	    seatNumberPanel.add(seatLable);
	    seatPopupPanel.add(seatNumberPanel);
	    
	    //Display all the orderItems from the list
	    
	    int k = 0;
    	OrderItem[] arrayOrderItem = new OrderItem[orders.size()];
	    
	    for(OrderItem orderItem : orders) {
	    	arrayOrderItem[k] = orderItem;
	    	k++;
	    }
	
	    JList<OrderItem> list = new JList<OrderItem>(arrayOrderItem);
	    list.setBackground(mainPopUpColor);
	    list.setOpaque(true);
	    seatPopupPanel.add(list);
	    
	    //Add two buttons at the bottom of the panel (Add Order Item) and (Delete Order Item)
	    JButton addOrderItem = new JButton();
	    addOrderItem.setText("Add Order Item");
	    addOrderItem.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		addOrderItemActionPerformed(evt, seatNumber, table);
        		viewOrderPopUp.setVisible(false);
            }
        });
	    
	    JButton removeSelectedOrderItem = new JButton();
	    removeSelectedOrderItem.setText("Cancel Order Item");
	    removeSelectedOrderItem.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		removeSelectedOrderItemActionPerformed(evt, seatNumber, list);
        		refreshViewOrderPopUp(table);
            }
        });
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(addOrderItem);
	    buttonPanel.add(removeSelectedOrderItem);
	    buttonPanel.setBackground(mainPopUpColor);
	    
	    seatPopupPanel.add(buttonPanel);
	    
	    return seatPopupPanel;
	}
	
	public void viewOrderPopUp(Table table, Map<String,List<OrderItem>> orderMap) {

	    final JPopupMenu viewOrderPopUp = new JPopupMenu();
	    viewOrderPopUp.setSize(300, 300);
	    viewOrderPopUp.setBackground(mainPopUpColor);
	    viewOrderPopUp.setOpaque(true);
	    
	    //Display the table number at the top of popup
	    JPanel tableNumberPanel = new JPanel();
	    tableNumberPanel.setBackground(mainPopUpColor);
	    
	    JLabel tableLable = new JLabel();
	    tableLable.setBackground(mainPopUpColor);
	    tableLable.setOpaque(true);
	    tableLable.setText("Table: " + table.getNumber());
	    tableLable.setFont(new Font(tableLable.getFont().getName(), Font.PLAIN, 30));
	    
	    JButton cancelTableOrderButton = new JButton();
	    cancelTableOrderButton.setText("Cancel Order");
	    cancelTableOrderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		cancelTableOrderItemActionPerformed(evt, table);
        		refreshViewOrderPopUp(table);
            }
        });
	    
	    tableNumberPanel.add(tableLable);
	    tableNumberPanel.add(cancelTableOrderButton);
	    viewOrderPopUp.add(tableNumberPanel);
	    viewOrderPopUp.add(new JSeparator());
    	
	    //Display all of the seatPopUp from the input map
    	Set<String> seatNumbers = orderMap.keySet();
    	
    	for(String seatNumber : seatNumbers) {
    		viewOrderPopUp.add(seatPopUp(viewOrderPopUp, table, seatNumber, orderMap.get(seatNumber)));
    	    viewOrderPopUp.add(new JSeparator());
    	}	
    	
    	viewOrderPopUp.show(Image_panel, 0, 0);
	    
	}
	
	public void refreshViewOrderPopUp(Table table) {

		Map<String, List<OrderItem>> newOrderMap;
		try {
			newOrderMap = RestoAppController.getOrderItems(table);
    		viewOrderPopUp(table, newOrderMap);
		} catch (InvalidInputException e) {
			errorPopUp(e.getMessage());
		}
	}
	
	
	public void issueBillPopUp() {
		final JPopupMenu issue_bill_popup = new JPopupMenu();
		issue_bill_popup.setSize(300, 300);
		issue_bill_popup.setBackground(mainPopUpColor);
		
	    //Panels
		
	    JPanel popupMenuItem1 = new JPanel();
	    popupMenuItem1.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem2 = new JPanel();
	    popupMenuItem2.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem3 = new JPanel();
	    popupMenuItem3.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem4 = new JPanel();
	    popupMenuItem4.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem5 = new JPanel();
	    popupMenuItem5.setBackground(new Color(255, 230, 153));

	    

	    
	    //Labels
	    
	    JLabel issue_bill_title = new JLabel();
	    issue_bill_title.setBackground(mainPopUpColor);
	    issue_bill_title.setOpaque(true);
	    issue_bill_title.setText("              Prepare Bill              ");
	    issue_bill_title.setFont(new Font(issue_bill_title.getFont().getName(), Font.PLAIN, 30));
	    
	    JLabel seat_nums = new JLabel();
	    seat_nums.setBackground(mainPopUpColor);
	    seat_nums.setOpaque(true);
	    seat_nums.setText("Seat numbers: ");
	    
	    JLabel or_table = new JLabel();
	    or_table.setBackground(mainPopUpColor);
	    or_table.setOpaque(true);
	    or_table.setText("---------- OR ENTER TABLE NUMBER ---------- ");
	    
	    JLabel table_num = new JLabel();
	    table_num.setBackground(mainPopUpColor);
	    table_num.setOpaque(true);
	    table_num.setText("Table Number: ");
	    
	    
	    //Text Fields
	    
	    PlaceholderTextField seat_field = new PlaceholderTextField("Enter comma seperated list of seats");
	    seat_field.setBackground(new Color(255, 230, 253));
	    
	    
	    
	    PlaceholderTextField table_field = new PlaceholderTextField("Enter table number");
	    table_field.setBackground(new Color(255, 230, 253));
	    
	    //Buttons
	    
	    JButton prepBillButton = new JButton();
	    prepBillButton.setBackground(new Color(255, 230, 253));
	    prepBillButton.setText("Prepare Bill");
	    prepBillButton.addActionListener(new java.awt.event.ActionListener() {
	    		public void actionPerformed(java.awt.event.ActionEvent evt) {
	    			prepBillButtonActionPerformed(evt, table_field.getText(), seat_field.getText());
	    		}
	    });
	    
	    
	    issue_bill_popup.setLayout(new BoxLayout(issue_bill_popup, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));


	    
	    
	    
	    popupMenuItem1.add(issue_bill_title);
	    popupMenuItem2.add(seat_nums);
	    popupMenuItem2.add(seat_field);
	    popupMenuItem3.add(or_table);
	    popupMenuItem4.add(table_num);
	    popupMenuItem4.add(table_field);
	    popupMenuItem5.add(prepBillButton);


	    
	    
	    
	    
	    issue_bill_popup.add(popupMenuItem1);
	    issue_bill_popup.add(popupMenuItem2);
	    issue_bill_popup.add(popupMenuItem3);
	    issue_bill_popup.add(popupMenuItem4);
	    issue_bill_popup.add(popupMenuItem5);
   
    	
	    issue_bill_popup.show(Image_panel, 2, 2);
		
	}
	
	
	public void billPopup(String[] orderItems, String waiter, String price) {
		final JPopupMenu bill_popup = new JPopupMenu();
		bill_popup.setSize(300, 300);
		bill_popup.setBackground(mainPopUpColor);
		
		
		//Panels
		
	    JPanel popupMenuItem1 = new JPanel();
	    popupMenuItem1.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem2 = new JPanel();
	    popupMenuItem2.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem3 = new JPanel();
	    popupMenuItem3.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem4 = new JPanel();
	    popupMenuItem4.setBackground(new Color(255, 230, 153));

	    //use for bill content
	    JPanel popupMenuItem5 = new JPanel();
	    popupMenuItem5.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem6 = new JPanel();
	    popupMenuItem6.setBackground(new Color(255, 230, 153));
	    
	    JPanel popupMenuItem7 = new JPanel();
	    popupMenuItem7.setBackground(new Color(255, 230, 153));
	    
	    //JLists
	    
	    JList<String> list = new JList<String>(orderItems);
	    list.setBackground(mainPopUpColor);
	    list.setOpaque(true);
	    
	    
	    //Labels
	    
	    JLabel bill_title = new JLabel();
	    bill_title.setBackground(mainPopUpColor);
	    bill_title.setOpaque(true);
	    bill_title.setText("                 Bill                 ");
	    bill_title.setFont(new Font(bill_title.getFont().getName(), Font.PLAIN, 30));
	    
	    JLabel waiter_title = new JLabel();
	    waiter_title.setBackground(mainPopUpColor);
	    waiter_title.setOpaque(true);
	    waiter_title.setText("Waiter: ");
	    waiter_title.setFont(new Font(waiter_title.getFont().getName(), Font.PLAIN, 17));
	    
	    JLabel waiter_name_title = new JLabel();
	    waiter_name_title.setBackground(mainPopUpColor);
	    waiter_name_title.setOpaque(true);
	    waiter_name_title.setText(waiter);
	    
	    
	    JLabel header_lable = new JLabel();
	    header_lable.setBackground(mainPopUpColor);
	    header_lable.setOpaque(true);
	    header_lable.setText("Quantity          Food                 Price        ");
	    header_lable.setFont(new Font(header_lable.getFont().getName(), Font.PLAIN, 17));
	    
	    JLabel seperator_lable = new JLabel();
	    seperator_lable.setBackground(mainPopUpColor);
	    seperator_lable.setOpaque(true);
	    seperator_lable.setText("------------------------------------------");
	   
	    JLabel lower_seperator_lable = new JLabel();
	    lower_seperator_lable.setBackground(mainPopUpColor);
	    lower_seperator_lable.setOpaque(true);
	    lower_seperator_lable.setText("=======================================");
	    
	    JLabel total_lable = new JLabel();
	    total_lable.setBackground(mainPopUpColor);
	    total_lable.setOpaque(true);
	    total_lable.setText("Total                    $ ");
	    total_lable.setFont(new Font(total_lable.getFont().getName(), Font.PLAIN, 20));
	    
	    JLabel total_price = new JLabel();
	    total_price.setBackground(mainPopUpColor);
	    total_price.setOpaque(true);
	    total_price.setText(price);
	    total_price.setFont(new Font(total_price.getFont().getName(), Font.PLAIN, 20));
	    
	    
	    
	    bill_popup.setLayout(new BoxLayout(bill_popup, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem7.setLayout(new BoxLayout(popupMenuItem7, BoxLayout.LINE_AXIS));
	    
	    
	    
	    popupMenuItem1.add(bill_title);
	    popupMenuItem2.add(waiter_title);
	    popupMenuItem2.add(waiter_name_title);
	    popupMenuItem3.add(header_lable);
	    popupMenuItem4.add(seperator_lable);
	    popupMenuItem5.add(list);
	    popupMenuItem6.add(lower_seperator_lable);
	    popupMenuItem7.add(total_lable);
	    popupMenuItem7.add(total_price);
	    
	    
	    bill_popup.add(popupMenuItem1);
	    bill_popup.add(popupMenuItem2);
	    bill_popup.add(popupMenuItem3);
	    bill_popup.add(popupMenuItem4);
	    bill_popup.add(popupMenuItem5);
	    bill_popup.add(popupMenuItem6);
	    bill_popup.add(popupMenuItem7);
	    
	    
	    
	    bill_popup.show(Image_panel, 130, 130);

	}
	
	public void menuPopUp2(int x, int y, List<ItemCategory> items, List<MenuItem> menuItems, String[] seatNumbers, Table table) {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(50,50));
        popupMenu.setBackground(mainPopUpColor);
        
        String c1 = items.get(0).toString();
        String c2 = items.get(1).toString();
        String c3 = items.get(2).toString();
        String c4 = items.get(3).toString();
        String c5 = items.get(4).toString();
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();
        JPanel popupMenuItem5 = new JPanel();
        JPanel popupMenuItem6 = new JPanel();
        JPanel popupMenuItem7 = new JPanel();
        JPanel popupMenuItem8 = new JPanel();
        JPanel popupMenuItem9 = new JPanel();
        JPanel popupMenuItem10 = new JPanel();

        JLabel menuName = new JLabel();
        menuName.setBackground(mainPopUpColor);
        menuName.setOpaque(true);
        menuName.setText("Menu");
		menuName.setFont(new Font(menuName.getFont().getName(), Font.PLAIN, 20));
		
		JLabel seatsLabel = new JLabel();
		seatsLabel.setBackground(mainPopUpColor);
        seatsLabel.setOpaque(true);
        seatsLabel.setText("Order for Seats: ");
        
        JLabel quantityLabel = new JLabel();
        quantityLabel.setBackground(mainPopUpColor);
        quantityLabel.setOpaque(true);
        quantityLabel.setText("Quantity: ");
        
        JTextField seatsField = new JTextField();
        seatsField.setBackground(mainPopUpColor);
        StringBuilder seatsText = new StringBuilder();
        for(int i = 0; i < seatNumbers.length; i++) {
    		seatsText.append(seatNumbers[i] + ",");
    		System.out.println(i + seatNumbers[i]);
        }
        seatsField.setText(seatsText.toString());
        
        JTextField quantityField = new JTextField();
        quantityField.setBackground(mainPopUpColor);
        quantityField.setText("1");
        
        int quantity = Integer.parseInt(quantityField.getText());
        
        //Category1
        JButton Category1Button = new JButton();
        Category1Button.setBackground(mainPopUpColor);
		Category1Button.setText(c1);
		Category1Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed2(evt, items.get(0), items, seatsField.getText().split(","), table);
            }
        });
        
		//Category2
        JButton Category2Button = new JButton();
        Category2Button.setBackground(mainPopUpColor);
		Category2Button.setText(c2);
		Category2Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed2(evt, items.get(1), items, seatsField.getText().split(","), table);
            }
        });

		//Category3
        JButton Category3Button = new JButton();
        Category3Button.setBackground(mainPopUpColor);
		Category3Button.setText(c3);
		Category3Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed2(evt, items.get(2), items, seatsField.getText().split(","), table);
            }
        });

		//Category4
        JButton Category4Button = new JButton();
        Category4Button.setBackground(mainPopUpColor);
		Category4Button.setText(c4);
		Category4Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed2(evt, items.get(3), items, seatsField.getText().split(","), table);
            }
        });
        
		//Category5
        JButton Category5Button = new JButton();
        Category5Button.setBackground(mainPopUpColor);
		Category5Button.setText(c5);
		Category5Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed2(evt, items.get(4), items, seatsField.getText().split(","), table);
            }
        });
		
		if (menuItems != null) {
			int j = 0;
			for (MenuItem menuItem : menuItems) {
				j++;
				JButton MenuItemButton = new JButton();
				MenuItemButton.setBackground(mainPopUpColor);
				MenuItemButton.setText(menuItem.getName());
				MenuItemButton.addActionListener(new java.awt.event.ActionListener() {
		        	public void actionPerformed(java.awt.event.ActionEvent evt) {
		        		MenuItemButtonActionPerformed2(evt, menuItem, seatsField.getText().split(","), quantity, table);
		            }
		        });
				if (j < 5) {
					popupMenuItem3.add(MenuItemButton);
				}
				else if (j < 9) {
					popupMenuItem4.add(MenuItemButton);
				}
				else if (j < 13) {
					popupMenuItem5.add(MenuItemButton);
				}
				else if (j < 17) {
					popupMenuItem6.add(MenuItemButton);
				}
				else {
					popupMenuItem7.add(MenuItemButton);
				}
			}
		}
		
		popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem7.setLayout(new BoxLayout(popupMenuItem7, BoxLayout.LINE_AXIS));
	    popupMenuItem8.setLayout(new BoxLayout(popupMenuItem8, BoxLayout.LINE_AXIS));
	    popupMenuItem9.setLayout(new BoxLayout(popupMenuItem9, BoxLayout.LINE_AXIS));
	    popupMenuItem10.setLayout(new BoxLayout(popupMenuItem10, BoxLayout.LINE_AXIS));
	        
	    popupMenuItem1.add(menuName);
	    popupMenuItem2.add(Category1Button);
	    popupMenuItem2.add(Category2Button);
	    popupMenuItem2.add(Category3Button);
	    popupMenuItem2.add(Category4Button);
	    popupMenuItem2.add(Category5Button);
	    popupMenuItem9.add(seatsLabel);
	    popupMenuItem9.add(seatsField);
	    popupMenuItem10.add(quantityLabel);
	    popupMenuItem10.add(quantityField);
	        
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
	    popupMenu.add(popupMenuItem5);
	    popupMenu.add(popupMenuItem6);
	    popupMenu.add(popupMenuItem7);
	    popupMenu.add(popupMenuItem8);
	    popupMenu.add(popupMenuItem9);
	    popupMenu.add(popupMenuItem10);
	    
		popupMenu.show(Image_panel, x, y);
	}
}

