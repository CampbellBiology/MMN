package Controller;

import java.util.ArrayList;
import java.util.Collections;

import DB.DB_Conn;
import DataClass.storeAverageRatingData;
import DataClass.storeData;

// 가게평균평점데이터 클래스를 이용해 가게평균평점을 구축한다.
public class constructStoreAvgRating {
	
	ArrayList <storeAverageRatingData> list;
	
	public constructStoreAvgRating() {
		// TODO Auto-generated constructor stub
		// list의 객체를 생성한다.
		list = new ArrayList<storeAverageRatingData>();
		
		// DB_conn의 객체를 생성한다.
		DB_Conn _db = new DB_Conn();
		
		// 가게데이터 클래스를 이용한 HashMap을 구축한다.
		_db.constructStoreMap();
		
		// 구축된 HashMap을 ArrayList로 바꾸고 그것을 대입한다.
		ArrayList <storeData> sdList = _db.storefindAll();
		
		// 위 ArrayList를 순회한다.
		for(storeData tmp : sdList) {
			// 순회한 가게마다 평균평점을 구한다.
			double res = _db.getAverageRating(tmp.storeCode);
			// 평균평점데이터 클래스로 list를 구축한다.
			storeAverageRatingData sar = new storeAverageRatingData(tmp.storeCode, res);
			list.add(sar);
		}
	}
	
	// 가게평균평점 데이터를 내림차순으로 정렬한다.
	public void setRatingSortingDescending() {
		Collections.sort(list);
	}
}
