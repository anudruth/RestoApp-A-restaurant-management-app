/**
 * 
 */
package ca.mcgill.ecse223.resto.application;


import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

/**
 * @author anudruth,eliott,romain
 *
 */

import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;


public class RestoAppApplication {
	
	private static RestoApp  restoapp;
	private static String filename = "data.restoapp";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
		restoapp = getRestoapp();
		//CODE TO TEST PERSISTENCE TODO: REMOVE
//		Table table1 = new Table(1,250,170,50,50,restoapp);
//		restoapp.addCurrentTable(table1);
//		Table table2 = new Table(2,400,370,50,50,restoapp);
//		restoapp.addCurrentTable(table2);
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestoAppPage().setVisible(true);
            }
        });
	}

	public static RestoApp getRestoapp() {
		if (restoapp == null) {
			// load model
			// TODO
			// for now, we are just creating an empty BTMS
			restoapp = load();
		}
 		return restoapp;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(restoapp);
		}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		restoapp = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded - create empty BTMS
		if (restoapp == null) {
			restoapp = new RestoApp();
		}else {
			restoapp.reinitialize();
		}
		return restoapp;
	}

}
