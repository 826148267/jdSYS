package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Manager;
import Dao.UserDao;

/**
 * Servlet implementation class addUserServlet
 */
@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("sdhisd");
		String clientId = request.getParameter("clientId");
		String remark = request.getParameter("remark");
		Manager user = (Manager) request.getSession().getAttribute("user");
		String uid = user.getUsername();
		PrintWriter out = response.getWriter();
		boolean flag = false;
		try {
			flag = UserDao.addClient(uid,clientId,remark);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(flag){
			out.print("添加成功");
		}else{
			out.print("添加失败");
		}
		out.flush();
		out.close();
	}

}
