package DataClass;

public class storeAverageRatingData implements Comparable<storeAverageRatingData>{
	int storeCode;
	double avgRating;
	
	public storeAverageRatingData(int _storeCode, double _avgRating) {
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
	public int compareTo(storeAverageRatingData sar) {
		if(sar.avgRating < avgRating) {
			return -1;
		}
		else if(sar.avgRating > avgRating) {
			return 1;
		}
		
		return 0;
	}
	
}
