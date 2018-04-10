package ca.mcgill.ecse223.resto.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

/**
 * Takes care of creating the tables using Java 2D
 */
public class RestoVisualizer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3046970097697621942L;
	
	//UI elements
	private Color higlighted = new Color(74,186,186);

	private List<RoundRectangle2D> rectangles = new ArrayList<RoundRectangle2D>();
	private List<Ellipse2D> circles = new ArrayList<Ellipse2D>();
	private int seatPlacementOffsetLength, seatPlacementOffsetWidth;
	private int seatCount;
	private Double sX,sY;
	private static final Double SEAT_DIAMETER = 20.0;
	
	
	// data elements
	private RestoApp restoapp;
	private Table selectedTable;
	private Seat selectedSeat;
	private HashMap<RoundRectangle2D, Table> tables;
	private HashMap<Ellipse2D, Seat> seats; 
	
	public RestoVisualizer() {
		super();
		init();
	}
	
	private void init() {
		restoapp = null;
		tables =new HashMap<RoundRectangle2D, Table>();
		seats = new HashMap<Ellipse2D, Seat>(); 
		selectedSeat = null;
		selectedTable = null;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) 
			{
				int x = e.getX();
				int y = e.getY();
				for(RoundRectangle2D rectangle: rectangles)
				{
					if(rectangle.contains(x, y))
					{	
						selectedTable = tables.get(rectangle);
						RestoAppApplication.getRestoAppPage().tablePopUp(x, y, selectedTable);
					}
				}
				
			}
		});
		
		
	}
	
	public void setResto(RestoApp restoapp)
	{
		this.restoapp = restoapp;
		tables = new HashMap<RoundRectangle2D, Table>();
		seats = new HashMap<Ellipse2D, Seat>(); 
		selectedSeat = null;
		selectedTable  = null;
		repaint();
		
	}
	private void doDrawing(Graphics g)
	{
		
		if(restoapp != null)
		{			
			Graphics2D g2d = (Graphics2D) g.create();	
			BasicStroke thinStroke = new BasicStroke(2);
			g2d.setStroke(thinStroke);
			
			rectangles.clear();
			tables.clear();
			
			for (Table table: restoapp.getCurrentTables()) {
				String status = "";
				if(table.getStatus() != Status.Available) {
					status = "In Use";
				}
				
				//draw table
				int k = 0;
				int sideCount = 0;
				int seatsPerSide;
				int totalSeats = table.getCurrentSeats().size();
				if (totalSeats <= 4) {
					seatsPerSide = 1;
				} else {
					seatsPerSide = (totalSeats - 1) / 2;
				}
				
				int x = table.getX();
				int y = table.getY();
				
				int tableWidth = table.getWidth();
				int tableLength = seatsPerSide*table.getLength();
				
				if (table.getFlipped()==1){
					tableWidth = seatsPerSide*table.getLength();
					tableLength = table.getWidth();
				}
				
				RoundRectangle2D rectangle = new RoundRectangle2D.Double(x,y, tableWidth, tableLength, 10, 10);
				
				rectangles.add(rectangle);
				
				tables.put(rectangle, table);
				if(table.hasOrders()) {
					g2d.setColor(higlighted);
				}else {
					g2d.setColor(Color.gray);
				}
				g2d.fill(rectangle);
				g2d.draw(rectangle);
				g2d.setColor(Color.BLACK);
				
				//draw Table number and Status
				g2d.drawString(new Integer(table.getNumber()).toString() ,(int) (x + (table.getWidth()/2.3)), (int)(y + (table.getLength()/1.8)) );
				g2d.drawString( status,(int) (x + (table.getWidth()/2.3))-12, (int)(y + (table.getLength()/1.8))+14 );

				//now draw seats
				seatCount = 0;
				seatPlacementOffsetLength = 0;
				seatPlacementOffsetWidth = 0;
				
				for(Seat seat: table.getCurrentSeats()) {
					if (totalSeats <= 2) {
						if (seatCount ==0) {
							sX = table.getX()+SEAT_DIAMETER;
							sY = table.getY()-1.5*(SEAT_DIAMETER);
						}
						else if(seatCount == 1) {
							sX = table.getX()+SEAT_DIAMETER;
							sY = table.getY()+3.5*(SEAT_DIAMETER);
						}
					} else {
						if (seatCount == 0) {
							if(table.getFlipped()==0) {
								sX = table.getX()+SEAT_DIAMETER;
								sY = table.getY()-1.5*(SEAT_DIAMETER);
							}else {
								sX = table.getX()-1.5*SEAT_DIAMETER;
								sY = table.getY()+SEAT_DIAMETER;
							}
						}
						else if(seatCount == 1 && totalSeats%2 == 0) {
							if(table.getFlipped()==0) {
								sX = table.getX()+SEAT_DIAMETER;
								sY = table.getY()+(3.5 + 3 * (seatsPerSide - 1))*(SEAT_DIAMETER);
							}else {
								sX = table.getX()+(3.5 + 3 * (seatsPerSide -1))*(SEAT_DIAMETER);
								sY = table.getY()+SEAT_DIAMETER;
							}
						}
						else if(seatCount % 2 == 0 && totalSeats % 2 == 0) {
							if(table.getFlipped()==0) {
								sX = table.getX()+3.5*SEAT_DIAMETER;
								sY = table.getY()+(1 + 3 * (sideCount - 2))*(SEAT_DIAMETER);
							}else {
								sX = table.getX()+(1 + 3 * (sideCount -2))*(SEAT_DIAMETER);
								sY = table.getY()+3.5*SEAT_DIAMETER;
							}
							k++;
						}
						else if(seatCount % 2 == 1 && totalSeats % 2 == 0) {
							if(table.getFlipped()==0) {
								sX = table.getX()-1.5*SEAT_DIAMETER;
								sY = table.getY()+(1 + 3 * (sideCount - 2))*(SEAT_DIAMETER);
							}else {
								sX = table.getX()+(1 + 3 * (sideCount - 2))*(SEAT_DIAMETER);
								sY = table.getY()-1.5*SEAT_DIAMETER;
							}
							k++;
						}
						else if(seatCount % 2 == 0 && totalSeats % 2 == 1) {
							if(table.getFlipped()==0) {
								sX = table.getX()+3.5*SEAT_DIAMETER;
								sY = table.getY()+(1 + 3 * (sideCount - 1))*(SEAT_DIAMETER);
							}else {
								sX = table.getX()+(1 + 3 * (sideCount - 1))*(SEAT_DIAMETER);
								sY = table.getY()+3.5*SEAT_DIAMETER;
							}
							k++;
						}
						else if(seatCount % 2 == 1 && totalSeats % 2 == 1) {
							if(table.getFlipped()==0) {
								sX = table.getX()-1.5*SEAT_DIAMETER;
								sY = table.getY()+(1 + 3 * (sideCount - 1))*(SEAT_DIAMETER);
							}else {
								sX = table.getX()+(1 + 3 * (sideCount - 1))*(SEAT_DIAMETER);
								sY = table.getY()-1.5*SEAT_DIAMETER;
							}
							k++;
						}
					}
				
					
					Ellipse2D circle = new Ellipse2D.Double(sX, sY, SEAT_DIAMETER, SEAT_DIAMETER);
					circles.add(circle);
					
					seats.put(circle, seat);
					
					if(seat.hasOrderItems()) {
						g2d.setColor(higlighted);
					}else {
						g2d.setColor(Color.GRAY);
					}
					g2d.fill(circle);
					g2d.draw(circle);
					g2d.setColor(Color.BLACK);
					g2d.drawString(new Integer(seat.getNumber()).toString(),  (sX.intValue())+4,  (sY.intValue())+15);
					
					if (k % 2 == 0) {
						sideCount++;
					}
					
					seatCount++;
					
					seatPlacementOffsetWidth+=(3*SEAT_DIAMETER);	
				}	
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		doDrawing(g);
	}

}
