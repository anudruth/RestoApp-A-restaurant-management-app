/**
 * 
 */
package ca.mcgill.ecse223.resto.application;


import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;


public class RestoAppApplication {
	
	private static RestoApp  restoapp;
	private static RestoAppPage restoAppPage;
	private static String filename = "data.restoapp";
	
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
