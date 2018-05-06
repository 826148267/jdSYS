package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.NoticeDao;
import entity.User;

/**
 * Servlet implementation class getNoticeServlet
 */
@WebServlet("/getNoticeServlet")
public class getNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dealGetNotice(request,response);	//处理获取公告信息的请求
	}

	/**
	 * 请求获取公告的处理函数
	 * @param request
	 * @param response
	 */
	private void dealGetNotice(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String uid = user.getManagerId();
		String result = "";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			result = NoticeDao.getNotice(uid);
			out.print(result);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
