package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Manager;
import Dao.UserDao;

/**
 * Servlet implementation class UserInformServlet
 */
@WebServlet("/UserInformServlet")
public class UserInformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInformServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 此方法通过管理ID查询出名下用户ID的集合
	 * 并通过printWriter输出到前端
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Manager user = (Manager) request.getSession().getAttribute("user");
		String username = user.getUsername();
		String result = UserDao.getUserInform(username);
		PrintWriter out = response.getWriter();
		out.print(result);	//将结果异步输出到前端js处理函数进行处理
		out.flush();
		out.close();
	}
}
















