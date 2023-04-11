package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import DataClass.Insert_joinData;
import DataClass.loginData;
import DataClass.menuData;
import DataClass.rtdCntData;
import DataClass.storeData;
import DataClass.tagData;

public class DB_Conn {
	String _Sql;
	final int Max_FoodCode = 10001;

	Connection conn = null;

	HashMap<Integer, storeData> store_map = new HashMap<>();
	HashMap<Integer, menuData> menu_map = new HashMap<>();
	HashMap<Integer, rtdCntData> rtdCnt_map = new HashMap<>();

	public DB_Conn() {
		Connection();
	}

	public DB_Conn(String _Sql) {
		Connection();
		this._Sql = _Sql;
	}

	void Connection() {
		try {
			// mysql jdbc driver 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// db연결 문자열 but 이방법은 보안에 취약하다. ..
			String url = "jdbc:mysql://localhost/mmn?characterEncoding=UTF-8&serverTimezone=UTC";
			String id = "root"; // mysql 접속아이디
			String pwd = "1234"; // mysql 접속 비번

			// db 접속

			conn = DriverManager.getConnection(url, id, pwd);
			System.out.println("db접속 성공");
		} catch (Exception e) {
			// db관련작업은 반드시 익셉션 처리
			System.out.println("db접속 실패");
			e.printStackTrace();
		}
	}

	public void Insert_UserData(Insert_joinData _Data) {
		PreparedStatement pstmt = null; // SQL실행객체

		try {
			String sql = "INSERT INTO userTbl(userID, userPW, userName, userEmail, isMaster)" + " VALUES(?,?,?,?,?)";

			// sql 실행객체 생성
			pstmt = conn.prepareStatement(sql);

			// ? 에 입력될 값 매핑
			pstmt.setString(1, _Data.userID);
			pstmt.setString(2, _Data.userPW);
			pstmt.setString(3, _Data.userName);
			pstmt.setString(4, _Data.userEmail);
			pstmt.setString(5, _Data.isMaster);

			// executeQuery() select 명령어
			// executeUpdate select 이외 명령어
			pstmt.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			// 리소스 정리작업
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try {
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}

	// 0 : 마스터계정 로그인 성공 1 : 일반계정 로그인 성공 2: 비밀번호 다름 3: 없는 아이디
	public int loginMathcing(loginData _data) {
		try {
			Statement stmt = null;
			ResultSet res = null;
			stmt = conn.createStatement();
			String sql = "SELECT * FROM userTbl where userID = " + "'" + _data.userID + "'";
			res = stmt.executeQuery(sql);

			String userPW = null;
			String isMaster = null;

			while (res.next()) {
				userPW = res.getString("userPW");
				isMaster = res.getString("isMaster");
			}

			// userPW에 입력된 값이 있다면
			if (userPW != null) {
				// 입력된 패스워드와 데이터베이스의 패스워드가 일치한다면
				if (userPW.equals(_data.userPW)) {
					// 만약 마스터 계정이면 0을 리턴한다.
					if (isMaster.equals("Y")) {
						return 0;
						// 마스터 계정이 아니면 1을 리턴한다.
					} else {
						return 1;
					}
					// 입력된 패스워드와 데이터베이스의 패스워드가 일치하지 않다면 2를 리턴한다.
				} else {
					return 2;
				}
			}

			// 입력된 패스워드가 없다면 3을 리턴한다.
			return 3;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 12;
	}

	// HashMap인 store_map을 만들어간다.
	public void constructStoreMap() {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM storeTbl";
			res = stmt.executeQuery(sql);

			while (res.next()) {
				int storeCode = res.getInt("storeCode");
				String storeName = res.getString("storeName");
				int cateCode = res.getInt("cateCode");
				String openAt = res.getString("openAt");
				String closeAt = res.getString("closeAt");
				String offDays = res.getString("offDays");
				String lastOrder = res.getString("lastOrder");
				String phone = res.getString("phone");
				String addr = res.getString("addr");
				String parking = res.getString("parking");
				String storeImgPath = res.getString("storeImagePath");
				String web = res.getString("web");
				String breakStart = res.getString("breakStart");
				String breakEnd = res.getString("breakEnd");

				// storeDate 클래스의 객체를 생성한다.
				storeData sd = new storeData(storeCode, storeName, cateCode, openAt, closeAt, offDays, lastOrder, phone,
						addr, parking, storeImgPath, web, breakStart, breakEnd);
				// store_map의 키값인 storeCode와 value인 storeData의 객체를 집어넣는다.
				store_map.put(storeCode, sd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// HashMap 인 menu_map을 만들어간다.
	public void constructMenuMap() {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM menuTbl";
			res = stmt.executeQuery(sql);

			while (res.next()) {
				int storeCode = res.getInt("storeCode");
				int foodCode = res.getInt("foodCode");
				String foodName = res.getString("foodName");
				int price = res.getInt("price");

				// 클래스 menuData의 객체를 생성한다.
				menuData md = new menuData(storeCode, foodCode, foodName, price);
				// menu_map에 value인 위 객체와 키값인 foodCode를 집어넣는다.
				menu_map.put(foodCode, md);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// HashMap인 rtdCnt_map을 만든다.
	public void constructRtdCnt_map() {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM reviewTarget";
			res = stmt.executeQuery(sql);

			// tmp는 카운트 변수이다. 예를 들어, foodCode = 1 일때, tmp[1]은 foodCode가 1인 리뷰타겟의 개수이다.
			// Max_FoodCode는 무수히 큰 적당한 수(10001)로 대입된 변수다. 모든 foodCode를 표현하기 위함이다.
			int[] tmp = new int[Max_FoodCode];

			while (res.next()) {
				int foodCode = res.getInt("foodCode");

				// foodCode일때 배열 값을 1씩 증가시킨다. 리뷰 타겟의 개수를 증가시키는 것과 같다.
				tmp[foodCode]++;
			}

			// 모든 foodCode를 순회한다.
			for (int i = 0; i < Max_FoodCode; i++) {
				if (tmp[i] == 0)
					continue;
				// rtdCntData는 reviewTargetData의 개수를 세는 클래스다.
				// i는 foodCode이고 tmp[i]는 카운트 변수다.
				rtdCntData rcd = new rtdCntData(i, tmp[i]);
				// rtdCnt_map 을 construct 해 간다.
				rtdCnt_map.put(i, rcd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// foodCode가 주어졌을 때 음식 이름을 리턴한다.
	public String getFoodName(int foodCode) {
		Statement stmt = null;
		ResultSet res = null;
		String foodName = "";
		try {
			stmt = conn.createStatement();
			// 메뉴테이블에서 foodCode 를 입력해 메뉴들을 가져온다.
			String sql = "SELECT * FROM menuTbl Where foodCode = " + foodCode;
			res = stmt.executeQuery(sql);
			while (res.next()) {
				// 음식 이름을 foodName에 입력시킨다.
				foodName = res.getString("foodName");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return foodName;
	}

	// 평균평점을 리턴하는 함수
	public double getAverageRating(int storeCode) {
		// 평점의 합
		int ratingSum = 0;
		// 리뷰의 개수
		int cnt = 0;

		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM reviewTbl Where storeCode = " + storeCode;
			res = stmt.executeQuery(sql);

			// 가게코드가 storeCode인 리뷰들을 순회한다.
			while (res.next()) {
				// 평점에 해당하는 값을 변수 rating에 입력시킨다.
				int rating = res.getInt("rating");
				// 평점을 모두 더해준다.
				ratingSum += rating;
				// 리뷰의 개수
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 가게코드가 storeCode인 리뷰가 하나도 없다면 음수를 리턴한다.
		if (cnt == 0)
			return -1;

		return ((double) ratingSum) / ((double) cnt);
	}

	// HashMap인 store_map을 ArrayList로 바꿔준다.
	public ArrayList<storeData> storefindAll() {
		return new ArrayList<>(store_map.values());
	}

	// HashMap인 menu_map을 ArrayList로 바꿔준다.
	public ArrayList<menuData> menufindAll() {
		return new ArrayList<>(menu_map.values());
	}

	// HashMap인 rtdCnt_map을 ArrayList로 바꿔준다.
	public ArrayList<rtdCntData> rtdCntfindAll() {
		return new ArrayList<>(rtdCnt_map.values());
	}

	public storeData getStoreData(int storeCode) {
		storeData sd = new storeData();

		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM storeTbl where storeCode = " + storeCode;
			res = stmt.executeQuery(sql);

			while (res.next()) {
				String storeName = res.getString("storeName");
				int cateCode = res.getInt("cateCode");
				String openAt = res.getString("openAt");
				String closeAt = res.getString("closeAt");
				String offDays = res.getString("offDays");
				String lastOrder = res.getString("lastOrder");
				String phone = res.getString("phone");
				String addr = res.getString("addr");
				String parking = res.getString("parking");
				String storeImgPath = res.getString("storeImagePath");
				String web = res.getString("web");
				String breakStart = res.getString("breakStart");
				String breakEnd = res.getString("breakEnd");

				// storeData 클래스의 객체를 생성한다.
				sd = new storeData(storeCode, storeName, cateCode, openAt, closeAt, offDays, lastOrder, phone, addr,
						parking, storeImgPath, web, breakStart, breakEnd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sd;
	}

	// 메뉴 검색을 통해 storeData ArrayList를 가져온다.
	public ArrayList<storeData> getMenuInfo(String query) {
		ArrayList<storeData> list = new ArrayList<>();
		String[] tmp = query.split(" ");

		Statement stmt = null;
		ResultSet res = null;
		try {
			String sql = "SELECT * FROM menuTbl WHERE foodName LIKE \"%";
			stmt = conn.createStatement();
			for (int i = 0; i < tmp.length; i++) {
				sql += tmp[i] + "%";
			}
			sql += "\"";
			res = stmt.executeQuery(sql);
			while (res.next()) {
				list.add(getStoreData(res.getInt("storeCode")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 가게검색을 통해 storeData ArrayList를 가져온다.
	public ArrayList<storeData> getStoreInfo(String query) {
		ArrayList<storeData> list = new ArrayList<>();
		String[] tmp = query.split(" ");

		Statement stmt = null;
		ResultSet res = null;
		try {
			String sql = "SELECT * FROM storeTbl WHERE storeName LIKE \"%";
			stmt = conn.createStatement();
			for (int i = 0; i < tmp.length; i++) {
				sql += tmp[i] + "%";
			}
			sql += "\"";
			res = stmt.executeQuery(sql);
			while (res.next()) {
				int storeCode = res.getInt("storeCode");
				String storeName = res.getString("storeName");
				int cateCode = res.getInt("cateCode");
				String openAt = res.getString("openAt");
				String closeAt = res.getString("closeAt");
				String offDays = res.getString("offDays");
				String lastOrder = res.getString("lastOrder");
				String phone = res.getString("phone");
				String addr = res.getString("addr");
				String parking = res.getString("parking");
				String storeImgPath = res.getString("storeImagePath");
				String web = res.getString("web");
				String breakStart = res.getString("breakStart");
				String breakEnd = res.getString("breakEnd");

				// storeData 클래스의 객체를 생성한다.
				storeData sd = new storeData(storeCode, storeName, cateCode, openAt, closeAt, offDays, lastOrder, phone,
						addr, parking, storeImgPath, web, breakStart, breakEnd);

				list.add(sd);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 태그검색을 통해 tagData ArrayList를 가져온다.
	public ArrayList<tagData> getTagInfo(String query) {
		ArrayList<tagData> list = new ArrayList<>();
		String[] tmp = query.split(" ");

		Statement stmt = null;
		ResultSet res = null;
		try {
			String sql = "SELECT * FROM tagTbl WHERE tagName LIKE \"%";
			stmt = conn.createStatement();
			for (int i = 0; i < tmp.length; i++) {
				sql += tmp[i] + "%";
			}
			sql += "\"";
			res = stmt.executeQuery(sql);
			while (res.next()) {
				tagData td = new tagData();
				td.setTagId(res.getInt("tagID"));
				td.setTagName(res.getString("tagName"));
				td.setTagViews(res.getInt("tagViews"));

				list.add(td);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)
					res.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
