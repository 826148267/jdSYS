package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.UserDao;

/**
 * Servlet implementation class RemoveUserServlet
 */
@WebServlet("/removeUserServlet")
public class RemoveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clientId = request.getParameter("clientId");
		PrintWriter out = response.getWriter();
		boolean flag = false;
		try {
			flag = UserDao.removeClient(clientId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag){	//���Ϊ�棬��ɾ���ɹ�
			out.write("ɾ���ɹ�");
		}else{
			out.write("ɾ��ʧ��");
		}
		out.flush();
		out.close();
	}

}
