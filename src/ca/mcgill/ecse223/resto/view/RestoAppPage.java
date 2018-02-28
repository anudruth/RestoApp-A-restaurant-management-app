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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;

/**
 *
 * @author anudruth, eliott
 */
public class RestoAppPage extends javax.swing.JFrame {

	private static final long serialVersionUID = -2702005067769134471L;
	
    /************DECLARATIONS******************/
    private JPanel Image_panel;
    private JButton add_table;
    private JPanel app_panel;
    private JPanel buttons_panel;
    private JButton jButton3;
    private JLabel jLabel2;
    private JPanel scroll_panel;
    private JScrollPane scroll_pane;
    // End of variables declaration//GEN-END:variables
    
    // UI elements
  	private JLabel errorMessage;
  	// table
  	private JLabel fakeTable;
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
		
        app_panel = new javax.swing.JPanel();
        scroll_panel = new JPanel();
        scroll_pane = new javax.swing.JScrollPane(scroll_panel);
        Image_panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        buttons_panel = new javax.swing.JPanel();
        add_table = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        
        app_panel.setBackground(java.awt.Color.white);

        Image_panel.setBackground(java.awt.Color.white);

        //jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resto_app/resources/Screen Shot 2018-02-20 at 1.08.07 AM.png"))); // NOI18N
        
        //elements for popup
        fakeTable = new JLabel();
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setMinimumSize(new Dimension(3,3));
        popupMenu.setBackground(new Color(255,230,153));
        
        JPanel popupMenuItem1 = new JPanel();
        JPanel popupMenuItem2 = new JPanel();
        JPanel popupMenuItem3 = new JPanel();

        //Table Label
        JLabel tableName = new JLabel();
        tableName.setBackground(new Color(255,230,153));
        
        //Delete Button
        RoundButton removeTableButton = new RoundButton();
        removeTableButton.setBackground(new Color(255,230,153));
		try {
			Image img = ImageIO.read(getClass().getResource("remove.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			removeTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeTableButton.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		System.out.println("remove button pressed");
        		removeTableButtonActionPerformed(evt);
            }
        });
		//Move Button
		RoundButton moveTableButton = new RoundButton();
        moveTableButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("move.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			moveTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}

        //Rotate button
        RoundButton rotateTableButton = new RoundButton();
        rotateTableButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("rotate.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			rotateTableButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}

        //inUse Button
        RoundButton inUseButton = new RoundButton();
        inUseButton.setBackground(new Color(255,230,153));
        try {
			Image img = ImageIO.read(getClass().getResource("inUse.bmp"));
			Image scaled = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			inUseButton.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}

        //Fake table for testing --- TO BE CHANGED
        fakeTable.setBackground(new java.awt.Color(0, 255, 0));
        fakeTable.setText("Table 1");
        fakeTable.setLocation(10, 10);
        scroll_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

        		int x_click = e.getX(); int y_click = e.getY();
        		System.out.println("Click recorded at x="+x_click+",y="+y_click);
        		System.out.println("2 Current Tables: ");
        		for(Table tabletmp : RestoAppApplication.getRestoapp().getCurrentTables()) {
        			System.out.println(tabletmp.getNumber());

        		}
        		for (Table table: RestoAppApplication.getRestoapp().getCurrentTables()) {
        			if(table.contains(x_click, y_click)) {
        				selectedTable = table;
        				System.out.println("Selected Table now table "+selectedTable.getNumber());
        				break;
        			}
        		}
        		int selectedTableNumber = -1;
        		if(selectedTable != null) {
        			selectedTableNumber = selectedTable.getNumber();
        		} else {
        			System.out.println("no table found for click");
        		}
                //Popup layout

        		tableName.setText("Table "+selectedTableNumber);
        		
                popupMenu.setLayout(new BoxLayout(popupMenu, BoxLayout.PAGE_AXIS));
                popupMenuItem1.setLayout(new BoxLayout(popupMenuItem1, BoxLayout.LINE_AXIS));
                popupMenuItem2.setLayout(new BoxLayout(popupMenuItem2, BoxLayout.LINE_AXIS));
                popupMenuItem3.setLayout(new BoxLayout(popupMenuItem3, BoxLayout.LINE_AXIS));

                popupMenuItem1.add(tableName);
                popupMenuItem2.add(removeTableButton);
                popupMenuItem2.add(moveTableButton);
                popupMenuItem3.add(rotateTableButton);
                popupMenuItem3.add(inUseButton);
                
                popupMenu.add(popupMenuItem1);
                popupMenu.add(popupMenuItem2);
                popupMenu.add(popupMenuItem3);
                
        		popupMenu.show(Image_panel, x_click, y_click);
			}
        });
        
        scroll_panel.add(fakeTable);

        //TABLE POPUP
        
        
        javax.swing.GroupLayout Image_panelLayout = new javax.swing.GroupLayout(Image_panel);
        Image_panel.setLayout(Image_panelLayout);
        Image_panelLayout.setHorizontalGroup(
            Image_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Image_panelLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );
        Image_panelLayout.setVerticalGroup(
            Image_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Image_panelLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(139, 139, 139))
        );

        buttons_panel.setBackground(java.awt.Color.white);

        add_table.setBackground(new java.awt.Color(0, 255, 0));
        add_table.setText("Add table");
        add_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	addTableButtonActionPerformed(evt);
            }
        });

        
        jButton3.setBackground(new java.awt.Color(255, 255, 0));
        jButton3.setText("Reserve table");

        javax.swing.GroupLayout buttons_panelLayout = new javax.swing.GroupLayout(buttons_panel);
        buttons_panel.setLayout(buttons_panelLayout);
        buttons_panelLayout.setHorizontalGroup(
            buttons_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add_table, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3))
        );

        buttons_panelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {add_table, jButton3});

        buttons_panelLayout.setVerticalGroup(
            buttons_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttons_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttons_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(add_table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        javax.swing.GroupLayout app_panelLayout = new javax.swing.GroupLayout(app_panel);
        app_panel.setLayout(app_panelLayout);
        app_panelLayout.setHorizontalGroup(
            app_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(app_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttons_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(Image_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        app_panelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Image_panel, scroll_pane});

        app_panelLayout.setVerticalGroup(
            app_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(app_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(app_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Image_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(app_panelLayout.createSequentialGroup()
                        .addComponent(scroll_pane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttons_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(app_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(app_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
        );
        //</AUTO-GENERATED>

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
    private void addTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_tableActionPerformed

	private void removeTableButtonActionPerformed(ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		if (!(selectedTable.getNumber() > 0))
			error = "Table needs to be selected for deletion!";
		
		if (error.length() == 0) {
			// call the controller
			try {
				System.out.println("passed error test");
				Table toDelete = null;
				List<Table> currentTables = RestoAppApplication.getRestoapp().getCurrentTables();
				System.out.println("3 Current Tables: ");
				for(Table table : currentTables) {
        			System.out.println(table.getNumber());
					if(table.getNumber() == selectedTable.getNumber()) {
						System.out.println("Found table "+table.getNumber()+"to delete");
						toDelete = table;
					}
				}
				RestoAppController.removeTable(toDelete);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}

		// update visuals
		refreshData();
	};

}
