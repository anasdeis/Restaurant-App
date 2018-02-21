package ca.mcgill.ecse223.resto.application;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;

public class RestoApplication {
	
	private static String filename = "data.restoapp";

	private static RestoApp ra;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestoAppPage().setVisible(true);
            }
        });
        
	}

	public static RestoApp getBtms() {
		if (ra == null) {
			
		ra = load();	
			
		}
 		return ra;
	}
	
	public static void save(){
		PersistenceObjectStream.serialize(filename);
		
	}
	
	public static RestoApp load(){
		PersistenceObjectStream.setFilename(filename);
		ra = (RestoApp) PersistenceObjectStream.deserialize();
		
		if(ra == null){
			ra = new RestoApp();
		}
		return ra;
	}
	
}
