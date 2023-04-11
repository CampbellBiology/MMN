package Controller;

import java.util.ArrayList;
import java.util.Collections;

import DB.DB_Conn;
import DataClass.storeAverageRating;
import DataClass.storeData;

public class getStoreAvgRating {
	
	ArrayList <storeAverageRating> list;
	
	public getStoreAvgRating() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<storeAverageRating>();
		
		DB_Conn _db = new DB_Conn();
		
		_db.constructStoreMap();
		
		ArrayList <storeData> sdList = _db.storefindAll();
		
		for(storeData tmp : sdList) {
			double res = _db.getAverageRating(tmp.storeCode);
			storeAverageRating sar = new storeAverageRating(tmp.storeCode, res);
			list.add(sar);
		}
	}
	
	public ArrayList<storeAverageRating> getRatingSorting() {
		ArrayList<storeAverageRating> ret = (ArrayList<storeAverageRating>)list.clone();
		Collections.sort(ret);
		
		return ret;
	}
}
