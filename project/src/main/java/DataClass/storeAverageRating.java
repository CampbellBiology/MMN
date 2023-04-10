package DataClass;

import DB.DB_Conn;

public class storeAverageRating implements Comparable<storeAverageRating>{
	int storeCode;
	double avgRating;
	
	public storeAverageRating(int _storeCode, double _avgRating) {
		// TODO Auto-generated constructor stub
		
		storeCode = _storeCode;
		avgRating = _avgRating;
	}
	
	public int getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	@Override
	public int compareTo(storeAverageRating sar) {
		if(sar.avgRating < avgRating) {
			return -1;
		}
		else if(sar.avgRating > avgRating) {
			return 1;
		}
		
		return 0;
	}
	
}
