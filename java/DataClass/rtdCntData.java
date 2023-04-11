package DataClass;

// reviewTargetData 의 개수를 세는 클래스다.
public class rtdCntData implements Comparable<rtdCntData> {
	int foodCode;
	int cnt;

	public rtdCntData(int _foodCode, int _cnt) {
		// TODO Auto-generated constructor stub
		foodCode = _foodCode;
		cnt = _cnt;
	}

	public int getFoodCode() {
		return foodCode;
	}

	public void setFoodCode(int foodCode) {
		this.foodCode = foodCode;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public int compareTo(rtdCntData rcd) {
		if (rcd.cnt < cnt) {
			return -1;
		} else if (rcd.cnt > cnt) {
			return 1;
		}

		return 0;
	}
}
