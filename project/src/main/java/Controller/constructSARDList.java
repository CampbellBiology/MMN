package Controller;

import java.util.ArrayList;
import java.util.Collections;

import DB.DB_Conn;
import DataClass.storeAverageRatingData;
import DataClass.storeData;

// 데이터클래스 storeAverageRating 의 리스트를 만든다. 
public class constructSARDList {
	
	// 데이터클래스 storeAverageRating의 리스트이다.
	ArrayList <storeAverageRatingData> list;
	
	public constructSARDList() {
		// TODO Auto-generated constructor stub
		// 리스트 객체 생성
		list = new ArrayList<storeAverageRatingData>();
		
		// DB_Conn 객체 생성
		DB_Conn _db = new DB_Conn();
		
		// 데이터클래스 storeData의 HashMap 자료구조를 만든다.
		_db.constructStoreMap();
		
		// 앞서 만든 HashMap 자료구조를 리스트로 바꿔서 sdList에 넣는다.
		ArrayList <storeData> sdList = _db.storefindAll();
		
		// sdList를 순회한다.
		for(storeData tmp : sdList) {
			// res는 평균 평점이다.
			double res = _db.getAverageRating(tmp.storeCode);
			// storeAverageRating의 객체를 생성한다.
			storeAverageRatingData sar = new storeAverageRatingData(tmp.storeCode, res);
			// 위 객체를 list에 추가해준다.
			list.add(sar);
		}
	}
	
	// 평점 내림차순 정렬
	public void ratingSortingDescending() {
		// 정렬
		Collections.sort(list);
	}
}
