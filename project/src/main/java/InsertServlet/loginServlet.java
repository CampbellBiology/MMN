package InsertServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_Conn;
import DataClass.loginData;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public loginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");

		try {
			String user_id = request.getParameter("user_id");
			String user_pw = request.getParameter("user_pw");

			DB_Conn _DB = new DB_Conn();
			loginData _Data = new loginData();

			_Data.userID = user_id;
			_Data.userPW = user_pw;

			int res = _DB.loginMathcing(_Data);
			loginData.setLoginStatus(res);

			String context = request.getContextPath();

			if (res == 0) {
				System.out.println("마스터 계정 로그인 성공");
				response.sendRedirect(context + "/loginSuccess.jsp");
			} else if (res == 1) {
				System.out.println("일반 계정 로그인 성공");
				response.sendRedirect(context + "/loginSuccess2.jsp");
			} else if (res == 2) {
				System.out.println("비밀번호를 다시 확인 해주세요");
				response.sendRedirect(context + "/loginFailure.jsp");
			} else {
				System.out.println("아이디가 잘못 됐습니다.");
				response.sendRedirect(context + "/loginFailure.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
