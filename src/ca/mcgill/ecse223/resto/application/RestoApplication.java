package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestoApplication {

	private static String filename = "menu.resto";

	private static RestoApp ra;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				RestoAppPage restoAppPage = new RestoAppPage();
				restoAppPage.setVisible(true);
				
			}
		});

	}

	public static RestoApp getRestoApp() {
		if (ra == null) {

			ra = load();
		}

		return ra;
	}

	public static void save() {
		PersistenceObjectStream.serialize(ra);
	}

	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		ra = (RestoApp) PersistenceObjectStream.deserialize();
		List<Reservation> reservations = new ArrayList<Reservation>(ra.getReservations());

		for(Reservation reservation: reservations){
			if(reservation.getDate().getTime() < Calendar.getInstance().getTime().getTime()){
				reservation.delete();
			}
		}

		if (ra == null) {
			ra = new RestoApp();
		}

		return ra;
	}

	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}
