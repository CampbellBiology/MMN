package DataClass;

public class storeData {
	public int storeCode;
	public String storeName;
	public int cateCode;
	public String openAt;
	public String closeAt;
	public String offDays;
	public String lastOrder;
	public String phone;
	public String addr;
	public String parking;
	public String storeImgPath;
	public String web;
	public String breakStart;
	public String breakEnd;
	
	public storeData() {
		
	}

	public storeData(int _storeCode, String _storeName, int _cateCode, String _openAt, String _closeAt, String _offDays,
			String _lastOrder, String _phone, String _addr, String _parking, String _storeImgPath, String _web,
			String _breakStart, String _breakEnd) {

		storeCode = _storeCode;
		storeName = _storeName;
		cateCode = _cateCode;
		openAt = _openAt;
		closeAt = _closeAt;
		offDays = _offDays;
		lastOrder = _lastOrder;
		phone = _phone;
		addr = _addr;
		parking = _parking;
		storeImgPath = _storeImgPath;
		web = _web;
		breakStart = _breakStart;
		breakEnd = _breakEnd;
	}
}
