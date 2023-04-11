package Controller;

import java.util.ArrayList;

import DB.DB_Conn;

public class searchResult {
	DB_Conn db;
	
	public searchResult(String query) {
		db = new DB_Conn();
		
		
	}
	
	public ArrayList<String> getStoreInfo(String query) {
		ArrayList <String> ret = new ArrayList<>();
		
		
		
		return ret;
	}
	
	public ArrayList<String> getTagInfo(String query) {
		ArrayList <String> ret = new ArrayList<>();
		
		
		return ret;
	}
}
