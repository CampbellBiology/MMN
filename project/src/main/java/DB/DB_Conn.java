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
import DataClass.reviewTargetData;
import DataClass.rtdCntData;
import DataClass.storeData;

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

			if (userPW != null) {
				if (userPW.equals(_data.userPW)) {
					if (isMaster.equals("Y")) {
						return 0;
					} else {
						return 1;
					}
				} else {
					return 2;
				}
			}

			return 3;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 12;
	}

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
				String storeImgPath = res.getString("storeImgPath");
				String web = res.getString("web");
				String breakStart = res.getString("breakStart");
				String breakEnd = res.getString("breakEnd");

				storeData sd = new storeData(storeCode, storeName, cateCode, openAt, closeAt, offDays, lastOrder, phone,
						addr, parking, storeImgPath, web, breakStart, breakEnd);
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

				menuData md = new menuData(storeCode, foodCode, foodName, price);
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
	
	public void constructRtdCnt_map() {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM reviewTarget";
			res = stmt.executeQuery(sql);
			
			int [] tmp = new int[Max_FoodCode];

			while (res.next()) {
				int index = res.getInt("_index");
				int foodCode = res.getInt("foodCode");

				tmp[foodCode]++;
			}
			
			for(int i=0;i<Max_FoodCode;i++) {
				if(tmp[i] == 0)continue;
				rtdCntData rcd = new rtdCntData(i, tmp[i]);
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
	
	public String getFoodName(int foodCode) {
		Statement stmt = null;
		ResultSet res = null;
		String foodName="";
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM menuTbl Where foodCode = "+ foodCode;
			res = stmt.executeQuery(sql);
			while (res.next()) {
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

	public ArrayList<storeData> storefindAll() {
		return new ArrayList<>(store_map.values());
	}

	public ArrayList<menuData> menufindAll() {
		return new ArrayList<>(menu_map.values());
	}
	
	public ArrayList<rtdCntData> rtdCntfindAll() {
		return new ArrayList<>(rtdCnt_map.values());
	}
}
