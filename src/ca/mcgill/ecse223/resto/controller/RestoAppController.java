package ca.mcgill.ecse223.resto.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class RestoAppController {

	public static List<Table> getTables(){
		return RestoApplication.getRestoApp().getCurrentTables();
	}
	public static void createTable(int numberOfSeats, int x, int y, int width, int length) throws InvalidInputException {	
		RestoApp ra = RestoApplication.getRestoApp();
		int generatedTableNumber = RestoAppController.generateTableNumber();
		
		String error = "";
		if (numberOfSeats <= 0) {
			error = "The number of seats must be greater than 0. ";
		}
		if (x < 0) {
			error = error + "The x location must be non-negative. ";
		}
		if (y < 0) {
			error = error + "The y location must be non-negative. ";
		}
		if (width <= 0) {
			error = error + "The width must be non-negative. ";
		}
		if (length <= 0) {
			error = error + "The location must be non-negative. ";
		}
		if (isDuplicateTableNumber(generatedTableNumber)){
			error = error + "Table with table number " + generatedTableNumber + " already exists. ";
		}
		if(isTableOverlapping(x,y,width,length)){
			error = error + "Table will overlap with another table. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		
		try {
			Table table = new Table(generatedTableNumber, x, y, width, length, ra);
			for(int i = 0; i < numberOfSeats; i++){
				table.addCurrentSeat(new Seat(table));
			}
			ra.addCurrentTable(table);
		}
		catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}
	}
	public static void removeTable(Integer selectedTableNumber) throws InvalidInputException {
		RestoApp ra = RestoApplication.getRestoApp();
		String error = "";

		try {
			
			for (Table table : ra.getCurrentTables()) {
				if(selectedTableNumber.equals(table.getNumber())) {
					ra.removeCurrentTable(table);
				}
			}
		}
		catch (Exception e) {
			error = e.getMessage();
			throw new InvalidInputException(e.getMessage());
		}

		
	}

	private static boolean isDuplicateTableNumber(int number) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		System.out.println(tables.size());
		for(int i = 0; i< tables.size(); i++){
			if(tables.get(i).getNumber() == number){
				return true;
			}
		}
		return false;
	}
	public static int generateTableNumber() {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		if(tables.size() == 0)return 1;
		else return tables.get(tables.size()-1).getNumber() + 1;

	}
	
	// Checks if the table that you are trying to input overlaps with another
	// It starts counting at index [0][0] for x and y.
	public static boolean isTableOverlapping(int x, int y, int width, int length) {
		RestoApp ra = RestoApplication.getRestoApp();
		List<Table> tables = ra.getCurrentTables();
		int[][] tableMap = new int[9999][9999];
		
		for(int k = 0; k < tables.size(); k++){
			for(int i = 0; i< tables.get(k).getWidth(); i++){
				for(int j = 0; j < tables.get(k).getLength(); j++){
					tableMap[tables.get(k).getX() + i][tables.get(k).getY() + j] = 1;
				}
			}
		}
		for(int i = 0; i< width; i++){
			for(int j = 0; j < length; j++){
				if(tableMap[i + x][j + y] == 1){
					return true;
				}
			}
		}
		return false;
	}
	
	public static ArrayList<MenuItem> getMenuItem(ItemCategory itemCategory) throws InvalidInputException {

		if (itemCategory.equals(null)) {
			throw new InvalidInputException("Item category is null"); 
		}
		
		RestoApp resto = RestoApplication.getRestoApp();
		ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();
		Menu menu = resto.getMenu();
		for (int j = 0; j < menu.numberOfMenuItems(); j++) {
			MenuItem currItem = menu.getMenuItem(j);
			if (currItem.getItemCategory().equals(itemCategory)) {
				itemList.add(currItem);
			}
		}
		return itemList; 
	}
	
}
