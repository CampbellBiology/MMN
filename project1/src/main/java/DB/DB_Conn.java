package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import DataClass.Insert_joinData;
import DataClass.loginData;

public class DB_Conn {
	String _Sql;

	Connection conn = null;

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
			System.out.println("db접속 성공!");
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
}
