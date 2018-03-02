package ca.mcgill.ecse223.resto.controller;

import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppController {

	public static List<Table> getTables(){
		return RestoApplication.getRestoApp().getCurrentTables();
	}
	
}
