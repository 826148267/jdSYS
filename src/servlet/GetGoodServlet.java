package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import Dao.GoodDao;
import entity.Good;
import entity.User;

/**
 * Servlet implementation class GetGoodServlet
 */
@WebServlet("/GetGoodServlet")
public class GetGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GetGoodServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		response.setContentType("text/json; charset=utf-8");
		if("getWaitReceive".equals(action)){
			getWaitReceive(request,response);	//��ȡ�ȴ����յĶ���
		}else{
			
		}
	}

	/**
	 * ��־ò���ȡ���ݲ���json����ʽ���ظ�ǰ��
	 * @param request
	 * @param response
	 */
	private void getWaitReceive(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		PrintWriter out = null;
		String uid = user.getManagerId();	//��ȡ��Ӧ����Ա�����Ļ��ﶩ��
		try {
			out = response.getWriter();
			ArrayList<Good> goods = GoodDao.getGood(uid);
			JSONArray ja = JSONArray.fromObject(goods);
			out.print(ja);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
