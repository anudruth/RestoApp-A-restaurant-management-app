/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;

/**
 *
 * @author anudruth, eliott
 */
public class RestoAppPage extends JFrame {

	private static final long serialVersionUID = -2702005067769134471L;
	private static final int MAX_SEATS = 8;
	
    /************DECLARATIONS******************/
    private JPanel Image_panel;
    private JButton addTableButton;
    private JPanel app_panel;
    private JPanel buttons_panel;
    private JButton reserveTableButton;
    private JLabel jLabel2;
    private JPanel scroll_panel;
    private JScrollPane scroll_layout;
    private RestoVisualizer restoVisualizer;
    // End of variables declaration//GEN-END:variables
    
    // UI elements
  	private JLabel errorMessage;
  	// table
	private Table selectedTable = null;
  	// data elements
  	private String error = null;
  	// table assignment
  	//...
	
 	/** Creates new form BtmsPage */
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
        reserveTableButton = new JButton();
        
        RestoApp restoapp = RestoAppApplication.getRestoapp();
		restoVisualizer.setResto(restoapp);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        
        app_panel.setBackground(java.awt.Color.white);

        Image_panel.setBackground(java.awt.Color.white);

        jLabel2.setIcon(new ImageIcon(getClass().getResource("../resources/Screen Shot 2018-02-20 at 1.08.07 AM.png"))); // NOI18N
        
        //elements for addTableButton, reserveTableButton, billTableButton
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
                .addComponent(billTableButton))
        );

        buttons_panelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addTableButton, reserveTableButton});

        buttons_panelLayout.setVerticalGroup(
            buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttons_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                	.addComponent(addTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                	.addComponent(reserveTableButton, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(billTableButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
			// populate page with data
			
			// table
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
    private void reserveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void billTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
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

	public void popUp(int x, int y, Table aTable) {

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
    		popupMenu.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

        //Rotate button
        RoundButton rotateTableButton = new RoundButton();
        rotateTableButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("../resources/rotate.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			rotateTableButton.setIcon(new ImageIcon(scaled));
    		popupMenu.setVisible(false);
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
    		popupMenu.setVisible(false);

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

	
}
