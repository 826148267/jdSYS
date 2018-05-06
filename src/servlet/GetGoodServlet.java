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
			getWaitReceive(request,response);	//获取等待接收的订单
		}else{
			
		}
	}

	/**
	 * 向持久层拉取数据并以json的形式返回给前端
	 * @param request
	 * @param response
	 */
	private void getWaitReceive(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute("user");
		PrintWriter out = null;
		String uid = user.getManagerId();	//获取对应管理员发布的货物订单
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
