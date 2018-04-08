/**
 * 
 */
package ca.mcgill.ecse223.resto.application;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;


public class RestoAppApplication {
	
	private static RestoApp  restoapp;
	private static RestoAppPage restoAppPage;
	private static String filename = "menu.resto";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                restoAppPage = new RestoAppPage();
                restoAppPage.setVisible(true);
            }
        });
	}

	public static RestoApp getRestoapp() {
		if (restoapp == null) {
			// load model
			
			restoapp = load();
		}
 		return restoapp;
	}
	
	public static RestoAppPage getRestoAppPage() {
		return restoAppPage;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(restoapp);
		}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoapp = (RestoApp) PersistenceObjectStream.deserialize();
		if (restoapp == null) {
			restoapp = new RestoApp();
		}else {
			restoapp.reinitialize();
		}
		return restoapp;
	}

}
