package DataClass;

public class loginData {
	public String userID;
	public String userPW;
	private static int loginStatus;

	public static int getLoginStatus() {
		return loginStatus;
	}

	public static void setLoginStatus(int loginStatus) {
		loginData.loginStatus = loginStatus;
	}

	public static void isLoginStatus() {
		if (loginStatus == 0 || loginStatus == 1) {
			System.out.println("로그인 중입니다.");
		} else {
			System.out.println("로그인을 하지 않았습니다.");
		}
	}
}
