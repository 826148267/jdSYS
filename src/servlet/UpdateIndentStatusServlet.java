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
import entity.User;
import tools.WebSocketTool;
import Dao.GoodDao;
import Dao.IndentDao;

@WebServlet("/UpdateIndentStatusServlet")
public class UpdateIndentStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateIndentStatusServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String aciton = request.getParameter("action");
    	if ("againIndent".equals(aciton)) {
			dealAgainIndent(request,response);
		}else if("refuseIndent".equals(aciton)){
			dealRefuseIndent(request,response);
		}
	}

	private void dealRefuseIndent(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		String uid = user.getManagerId();
		String clientId = request.getParameter("clientId");
		commonUpdateAction(request,response,"refuse");
		WebSocketTool.callClient(uid,clientId,"havenewrefuse");
	}

	/**
	 * 将订单状态改为again
	 * @param request
	 * @param response
	 * @param string 
	 */
	private void dealAgainIndent(HttpServletRequest request,
			HttpServletResponse response) {
		int num = Integer.parseInt(request.getParameter("num"));
		int goodsId = Integer.parseInt(request.getParameter("goodsId"));
		String clientId = request.getParameter("clientId");
		Manager manager = (Manager) request.getSession().getAttribute("user");
		String uid = manager.getUsername();
		boolean flag = false;
		try {
			flag = GoodDao.updateGoodSurplus(goodsId,num);	//首先进行库存量更新，失败就直接跳过更新状态
			if (flag) {
				commonUpdateAction(request,response,"again");	//更新状态
				WebSocketTool.callClient(uid,clientId,"havenewagain");
			}else{
				PrintWriter out = response.getWriter();
				out.print("交易失败，库存已不足");
				out.flush();
				out.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实现同意和拒绝订单时更新状态的同一操作，抽象出一个函数
	 * 不论拒绝还是接受都必须对订单状态打标
	 * @param request
	 * @param response
	 * @param action	现在要进行的操作，是同意订单还是拒绝 ，参数可以是again或refuse
	 */
	private void commonUpdateAction(HttpServletRequest request,
			HttpServletResponse response,String stutas) {
		int indentId = Integer.parseInt(request.getParameter("indentId"));
		PrintWriter out = null;
		String result = "failto"+stutas;
		boolean flag = false;
		try {
			flag = IndentDao.updataIndentStatus(stutas,indentId);
			out = response.getWriter();
			if (flag) {
				result = "successto"+stutas;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(result);
		out.flush();
		out.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
