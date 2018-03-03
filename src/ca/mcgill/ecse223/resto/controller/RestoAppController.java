package ca.mcgill.ecse223.resto.controller;

import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppController {

	public static List<Table> getTables(){
		return RestoApplication.getRestoApp().getCurrentTables();
	}
	public static void createTable(Table aTable) throws InvalidInputException {	
		RestoApp ra = RestoApplication.getRestoApp();
		try {
			ra.addCurrentTable(aTable);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
}
