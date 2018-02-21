package ca.mcgill.ecse223.resto.application;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.view.RestoAppPage;

public class RestoApplication {

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
			// load model
			// TODO
			// for now, we are just creating an empty BTMS
			ra = new RestoApp();
		}
 		return ra;
	}
	
}
