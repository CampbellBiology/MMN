package Controller;

import java.util.ArrayList;

import DB.DB_Conn;
import DataClass.storeData;
import DataClass.tagData;

public class searchResult {
	DB_Conn db;
	ArrayList <storeData> storeInfo;
	ArrayList <tagData> tagInfo;
	
	public searchResult(String query) {
		db = new DB_Conn();
		calcStoreInfo(query);
		calcTagInfo(query);
	}
	
	public void calcStoreInfo(String query) {
		storeInfo = db.getStoreInfo(query);
		ArrayList <storeData> tmp = db.getMenuInfo(query);
		
		for(int i=0;i<tmp.size();i++) {
			storeInfo.add(tmp.get(i));
		}
	}
	
	public void calcTagInfo(String query) {
		tagInfo = db.getTagInfo(query);
	}
}
