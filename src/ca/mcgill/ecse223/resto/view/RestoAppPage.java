/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;

public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -2702005067769134471L;
	private static final int MAX_SEATS = 8;
	
    /************DECLARATIONS******************/
    private JPanel Image_panel;
    private JPanel app_panel;
    private JPanel buttons_panel;
    private JLabel jLabel2;
    private JPanel scroll_panel;
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
		
		RoundButton reserveTableButton = new RoundButton();
        reserveTableButton.setBackground(new Color(255,255,255));
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
                .addComponent(addTableButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                .addComponent(reserveTableButton)
                .addComponent(billTableButton)
            	.addComponent(displayMenuButton)
            	.addComponent(orderButton))
        );

        buttons_panelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addTableButton, reserveTableButton, displayMenuButton});

        buttons_panelLayout.setVerticalGroup(
            buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                	.addComponent(addTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                	.addComponent(reserveTableButton, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(billTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayMenuButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orderButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
			//TODO: Implement error messages on screen
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
    
    private void prepBillButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	
    }
    private void reserveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	reservePopUp(2,2);
		RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
    }
    private void billTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    	List<String> orderItems = new ArrayList<String>();
    	orderItems.add("Hello World");
    	orderItems.add("Order Item 1");
    	orderItems.add("Order Item 2");
    	orderItems.add("Change Seat");
    	orderItems.add("Change Seat");
    	orderItems.add("Order Item 3");
    	orderItems.add("Order Item 4");
    	orderItems.add("Change Seat");
    	
    	List<String> seatNumbers = new ArrayList<String>();
    	seatNumbers.add("1");
    	seatNumbers.add("2");
    	seatNumbers.add("5");
    	
    	
    issueBillPopUp();
    	
    //tablePopUp(1, orderItems, seatNumbers);
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
			error = e.getMessage();
		}
    }
    
    private void tableNumberChangeActionPerformed(ActionEvent evt, String newTableNumber) {
		try {
			RestoAppController.updateTable(selectedTable, Integer.valueOf(newTableNumber), selectedTable.getCurrentSeats().size());
			RestoApp restoapp = RestoAppApplication.getRestoapp();
			restoVisualizer.setResto(restoapp);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
    }
    
    int x_move;
    int y_move;
    private void moveTableButtonActionPerformed(ActionEvent evt) {
    	error = null;
		restoVisualizer.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				x_move = e.getX();
				y_move = e.getY();
				try {
					RestoAppController.moveTable(selectedTable, x_move,y_move);
					RestoApp restoapp = RestoAppApplication.getRestoapp();
					restoVisualizer.setResto(restoapp);
				} catch (InvalidInputException e1) {
					e1.printStackTrace();
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
			error = "Table needs to be selected for deletion!";
		
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
				error = e.getMessage();
			}
		}
		
		// update visuals
		refreshData();
	};
	
	/**
	 * Displays the proper popUp for the category of menu selected
	 */
	private void CategoryButtonActionPerformed(ActionEvent evt, ItemCategory itemCategory, List<ItemCategory> i) {
		try {
			menuPopUp(2, 2, i, RestoAppController.getMenuItems(itemCategory));
		} catch (InvalidInputException e) {
			error = e.getMessage();
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
        popupMenu.setBackground(new Color(255,230,153));
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();

        //Table Label
        JLabel tableName = new JLabel();
        tableName.setBackground(new Color(255,230,153));
        tableName.setOpaque(true);
        
        JTextField tableNumber = new JTextField();
        tableNumber.setBackground(new Color(255,230,153));
        tableNumber.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		String newTableNumber = tableNumber.getText();
        		tableNumberChangeActionPerformed(evt, newTableNumber);
            }
        });   
        
        JSlider tableSlider = new JSlider();
        tableSlider.setBackground(new Color(255,230,153));
        tableSlider.setMaximum(MAX_SEATS);
		tableSlider.setMinimum(1);
		tableSlider.setMajorTickSpacing(1);
		tableSlider.setPaintTicks(true);
		tableSlider.setPaintLabels(true);
		tableSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent evt) {
        		int numSeats = tableSlider.getValue();
        		tableCurrentSeatsChangeActionPerformed(evt, numSeats);
            }
        });
        
		//Delete Button
        RoundButton removeTableButton = new RoundButton();
        removeTableButton.setBackground(new Color(255,230,153));
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/remove.bmp"));
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
        moveTableButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("../resources/move.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			moveTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        moveTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		moveTableButtonActionPerformed(evt);
            }

			
        });

        //Rotate button
        RoundButton rotateTableButton = new RoundButton();
        rotateTableButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("../resources/rotate.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			rotateTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}

        //inUse Button
        RoundButton inUseButton = new RoundButton();
        inUseButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("../resources/inUse.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			inUseButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
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
	    popupMenuItem3.add(inUseButton);
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
        popupMenu.setBackground(new Color(255,230,153));
        
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
        menuName.setBackground(new Color(255,230,153));
        menuName.setOpaque(true);
        
        //Category1
        JButton Category1Button = new JButton();
        Category1Button.setBackground(new Color(255,230,153));
		Category1Button.setText(c1);
		Category1Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(0), items);
            }
        });
        
		//Category2
        JButton Category2Button = new JButton();
        Category2Button.setBackground(new Color(255,230,153));
		Category2Button.setText(c2);
		Category2Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(1), items);
            }
        });

		//Category3
        JButton Category3Button = new JButton();
        Category3Button.setBackground(new Color(255,230,153));
		Category3Button.setText(c3);
		Category3Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(2), items);
            }
        });

		//Category4
        JButton Category4Button = new JButton();
        Category4Button.setBackground(new Color(255,230,153));
		Category4Button.setText(c4);
		Category4Button.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		CategoryButtonActionPerformed(evt, items.get(3), items);
            }
        });
        
		//Category5
        JButton Category5Button = new JButton();
        Category5Button.setBackground(new Color(255,230,153));
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
				MenuItemButton.setBackground(new Color(255,230,153));
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
		
		menuName.setText("MENU ");
		
		
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
        popupMenu.setBackground(new Color(255,230,153));
        
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
        makeReservation.setBackground(new Color(255,230,153));
        makeReservation.setOpaque(true);
        makeReservation.setText("                              Make a Reservation                              ");
        
        JLabel reservationTable = new JLabel();
        reservationTable.setBackground(new Color(255,230,153));
        reservationTable.setOpaque(true);
        reservationTable.setText("Tables: ");
        
        JLabel reservationDate = new JLabel();
        reservationDate.setBackground(new Color(255,230,153));
        reservationDate.setOpaque(true);
        reservationDate.setText("Date (MM/DD/YYYY): ");
        
        JLabel reservationTime = new JLabel();
        reservationTime.setBackground(new Color(255,230,153));
        reservationTime.setOpaque(true);
        reservationTime.setText("Time: ");
        
        JLabel reservationSize = new JLabel();
        reservationSize.setBackground(new Color(255,230,153));
        reservationSize.setOpaque(true);
        reservationSize.setText("Size: ");
        
        JLabel reservationName = new JLabel();
        reservationName.setBackground(new Color(255,230,153));
        reservationName.setOpaque(true);
        reservationName.setText("Name: ");
        
        JLabel reservationMail = new JLabel();
        reservationMail.setBackground(new Color(255,230,153));
        reservationMail.setOpaque(true);
        reservationMail.setText("Email Address: ");
        
        JLabel reservationPhone = new JLabel();
        reservationPhone.setBackground(new Color(255,230,153));
        reservationPhone.setOpaque(true);
        reservationPhone.setText("Phone Number: ");
        
        JLabel reservationNumber = new JLabel();
        reservationNumber.setBackground(new Color(255,230,153));
        reservationNumber.setOpaque(true);
        reservationNumber.setText("Reservation: ");
        
        JLabel reservationField = new JLabel();
        reservationField.setBackground(new Color(255,230,153));
        reservationField.setOpaque(true);
        
        JTextField tableField = new JTextField();
        tableField.setBackground(new Color(255,230,153));
        
        JTextField dateField = new JTextField();
        dateField.setBackground(new Color(255,230,153));
        
        JTextField timeField = new JTextField();
        timeField.setBackground(new Color(255,230,153));

        JTextField sizeField = new JTextField();
        sizeField.setBackground(new Color(255,230,153));
        
        JTextField nameField = new JTextField();
        nameField.setBackground(new Color(255,230,153));
        
        JTextField mailField = new JTextField();
        mailField.setBackground(new Color(255,230,153));
        
        JTextField phoneField = new JTextField();
        phoneField.setBackground(new Color(255,230,153));
        
        
		//Delete Button
        JButton makeReservationButton = new JButton();
        makeReservationButton.setBackground(new Color(255,230,153));
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
	
	public void errorPopUp(String errorMessage) {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(3,3));
        popupMenu.setBackground(new Color(255,230,153));
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        
        JLabel errorLabel = new JLabel();
        errorLabel.setBackground(new Color(255,230,153));
        errorLabel.setOpaque(true);
        errorLabel.setText("                              Error:                              ");
        
        JLabel errorText = new JLabel();
        errorText.setBackground(new Color(255,230,153));
        errorText.setOpaque(true);
        errorText.setText(errorMessage);
        
        popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
        
	    popupMenuItem1.add(errorLabel);
	    popupMenuItem2.add(errorText);
	    
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    
	    popupMenu.show(Image_panel, 0, 0);
	}
	
	public void orderPopup() {

        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(50,50));
        popupMenu.setBackground(new Color(255,230,153));
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();
        JPanel popupMenuItem4 = new JPanel();
        JPanel popupMenuItem5 = new JPanel();
        JPanel popupMenuItem6 = new JPanel();
        JPanel popupMenuItem7 = new JPanel();
        
        JLabel orderLabel = new JLabel();
        orderLabel.setBackground(new Color(255,230,153));
        orderLabel.setOpaque(true);
        
        orderLabel.setText("Create New Order:");
        
        JLabel instructions = new JLabel();
        instructions.setBackground(new Color(255,230,153));
        instructions.setOpaque(true);
        instructions.setText("Add other tables to order? (Seperated by commas)");
        
        JTextField tableField = new JTextField();
        tableField.setBackground(new Color(255,230,153));
        
        JButton startOrderButton = new JButton();
        startOrderButton.setBackground(new Color(255,230,153));
        startOrderButton.setText("Start Order");
        startOrderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		List<Table> tables = new ArrayList<Table>();
        		String[] tableNumbers = tableField.getText().split(",");
        		
        		for (int k = 0; k < tableNumbers.length; k++) {
        			int tableNumber = Integer.parseInt(tableNumbers[k]);
        			tables.add(Table.getWithNumber(tableNumber));
        		}
        		try {
        			RestoAppController.startOrder(tables);
        		} catch (InvalidInputException e) {
        			error = e.getMessage();
        		}
        		
        		tableField.setText("");
        		RestoApp restoapp = RestoAppApplication.getRestoapp();
        		restoVisualizer.setResto(restoapp);
        	}
        });
        
        /*JLabel instructions2 = new JLabel();
        instructions.setBackground(new Color(255,230,153));
        instructions.setOpaque(true);
        instructions.setText("End order? (enter order number)");
        
        JTextField orderField = new JTextField();
        tableField.setBackground(new Color(255,230,153));
        
        JButton endOrderButton = new JButton();
        startOrderButton.setBackground(new Color(255,230,153));
        startOrderButton.setText("End order");
        startOrderButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		
        		try {
        			RestoAppController.endOrder();
        		} catch (InvalidInputException e) {
        			error = e.getMessage();
        		}
        		
        		tableField.setText("");
        		RestoApp restoapp = RestoAppApplication.getRestoapp();
        		restoVisualizer.setResto(restoapp);
        	}
        }); */
        
        popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
	    popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
	    popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
	    popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));
	    popupMenuItem4.setLayout(new BoxLayout(popupMenuItem4, BoxLayout.LINE_AXIS));
	    /*popupMenuItem5.setLayout(new BoxLayout(popupMenuItem5, BoxLayout.LINE_AXIS));
	    popupMenuItem6.setLayout(new BoxLayout(popupMenuItem6, BoxLayout.LINE_AXIS));
	    popupMenuItem7.setLayout(new BoxLayout(popupMenuItem7, BoxLayout.LINE_AXIS));*/
	    
	    popupMenuItem1.add(orderLabel);
	    popupMenuItem2.add(instructions);
	    popupMenuItem3.add(tableField);
	    popupMenuItem4.add(startOrderButton);
	    /*popupMenuItem5.add(instructions2);
	    popupMenuItem6.add(orderField);
	    popupMenuItem7.add(endOrderButton);*/
	    
	    popupMenu.add(popupMenuItem1);
	    popupMenu.add(popupMenuItem2);
	    popupMenu.add(popupMenuItem3);
	    popupMenu.add(popupMenuItem4);
	    /*popupMenu.add(popupMenuItem5);
	    popupMenu.add(popupMenuItem6);
	    popupMenu.add(popupMenuItem7);*/
	    
	    popupMenu.show(Image_panel, 2, 2);
	}
	
	public JPanel seatPopUp(String seatNumber, String[] orders) {

	    final JPanel popupMenu = new JPanel();
	    popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.Y_AXIS));
	    popupMenu.setSize(300, 300);
	    popupMenu.setBackground(new Color(255,230,153));
	    
	    JPanel popupMenuItem1 = new JPanel();
	    
	    JLabel seatLabel = new JLabel();
	    seatLabel.setBackground(new Color(255,230,153));
	    seatLabel.setOpaque(true);
	    seatLabel.setText("Seat: " + seatNumber);
	    
	    popupMenuItem1.add(seatLabel);
	    popupMenu.add(popupMenuItem1);
	    
	    JLabel orderItemLabel = new JLabel();
	    orderItemLabel.setBackground(new Color(255,230,153));
	    orderItemLabel.setOpaque(true);
	    
//	    int k = 0;
//	    
//	    int totalJLs = orders.size(); 
//	    
//    	String[] arrayJLs = new String[totalJLs];
//	    
//	    for(String orderItem : orders) {
//
//	    	arrayJLs[k] = orderItem;
//	    	k++;
//	    
//	    }
	    
	    int nullCounter = 0;
	    
	    for(int k = 0; k < orders.length; k++) {
			if (orders[k] == null) {
				nullCounter++;
			}
		}
	    
	    String[] finalOrder = new String[orders.length - nullCounter];
	    for(int k = 0; k < finalOrder.length; k++) {
			finalOrder[k] = orders[k];
			System.out.println(orders[k]);
		}
	    
	    JList list = new JList(finalOrder);
	    popupMenu.add(list);
	    
	    JButton addOrderItem = new JButton();
	    addOrderItem.setText("Add Order Item");
	    
	    JButton removeSelectedOrderItem = new JButton();
	    removeSelectedOrderItem.setText("Delete Order Item");
	    
	    JPanel popupMenuItem3 = new JPanel();
	    popupMenuItem3.add(addOrderItem);
	    popupMenuItem3.add(removeSelectedOrderItem);
	    
	    popupMenu.add(popupMenuItem3);
	    
	    return popupMenu;
	}
	
	public void tablePopUp(int tableNumber, List<String> orderItems, List<String> seatNumber) {

	    final JPopupMenu popupMenu = new JPopupMenu();
	    popupMenu.setSize(300, 300);
	    popupMenu.setBackground(new Color(255,230,153));
	    
	    JPanel popupMenuItem1 = new JPanel();
	    
	    JLabel seatLabel = new JLabel();
	    seatLabel.setBackground(new Color(255,230,153));
	    seatLabel.setOpaque(true);
	    seatLabel.setText("Table: " + tableNumber);
	    
	    popupMenuItem1.add(seatLabel);
	    popupMenu.add(popupMenuItem1);
    	
    	JPanel popupMenuItem2 = new JPanel();
    	popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.Y_AXIS));
    	
    	int seatCounter = 0;
    	int orderCounter = 0;
    	String[] currentSeatOrders = new String[orderItems.size()];
    	
    	for (String order : orderItems) {
    		if(order.equals("Change Seat")) {
    			for(int k = 0; k < currentSeatOrders.length; k++) {
    				System.out.println(currentSeatOrders[k]);
    			}
    			popupMenu.add(seatPopUp(seatNumber.get(seatCounter), currentSeatOrders));
    			seatCounter++;
    			for(int k = 0; k < currentSeatOrders.length; k++) {
    				currentSeatOrders[k] = null;
    			}
    			orderCounter = 0;
    		} else {
    			currentSeatOrders[orderCounter] = order;
    			orderCounter++;
    		}
    	}
    	
    	popupMenu.show(Image_panel, 0, 0);
	    
	}
	
	public void issueBillPopUp() {
		final JPopupMenu issue_bill_popup = new JPopupMenu();
		issue_bill_popup.setSize(300, 300);
		issue_bill_popup.setBackground(new Color(255,230,153));
		
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
	    issue_bill_title.setBackground(new Color(255,230,153));
	    issue_bill_title.setOpaque(true);
	    issue_bill_title.setText("              Prepare Bill              ");
	    
	    JLabel seat_nums = new JLabel();
	    seat_nums.setBackground(new Color(255,230,153));
	    seat_nums.setOpaque(true);
	    seat_nums.setText("Seat numbers: ");
	    
	    JLabel or_table = new JLabel();
	    or_table.setBackground(new Color(255,230,153));
	    or_table.setOpaque(true);
	    or_table.setText("---------- OR ENTER TABLE NUMBER ---------- ");
	    
	    JLabel table_num = new JLabel();
	    table_num.setBackground(new Color(255,230,153));
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
	    			prepBillButtonActionPerformed(evt);
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
	
}

