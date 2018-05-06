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
import Dao.IndentDao;
import entity.Client;
import entity.Indent;
import entity.Manager;


@WebServlet("/GetIndentServlet")
public class GetIndentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String identity = request.getParameter("identity");
		if("manager".equals(identity)){
			if("getWaitExamine".equals(action)){
				getWaitExamine(request,response);
			}else if("getSuccess".equals(action)){
				getSuccessByManager(request,response);
			}else if("getHistory".equals(action)){
				getHistoryByManager(request,response);
			}
		}else if("client".equals(identity)){
			if("getSuccess".equals(action)){
				getSuccessByClient(request,response);
			}else if("getHistory".equals(action)){
				getHistoryByClient(request,response);
			}
		}
	}


	private void getHistoryByClient(HttpServletRequest request,
			HttpServletResponse response) {
		Client client = (Client) request.getSession().getAttribute("user");
		String clientId = client.getUsername();
		String uid = client.getManagerId();
		String status1 = "again";
		String status2 = "refuse";
		String status3 = "annul";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ArrayList<Indent> indents = IndentDao.getHistoryIndentByClient(uid,clientId,status1,status2,status3);
			for (int i = 0; i < indents.size(); i++) {
				Indent indent = indents.get(i);
				if ("again".equals(indent.getStatus())) {
					indent.setStatus("交易已成功");
				}else if("refuse".equals(indent.getStatus())) {
					indent.setStatus("已拒绝");
				}else {
					indents.remove(i);
				}
			}
			JSONArray ja = JSONArray.fromObject(indents);
			out.print(ja);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void getSuccessByClient(HttpServletRequest request,
			HttpServletResponse response) {
		Client client = (Client) request.getSession().getAttribute("user");
		String uid = client.getManagerId();
		String clientId = client.getUsername();
		String status = "again";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ArrayList<Indent> indents = IndentDao.getIndentByClient(uid,clientId,status);
			JSONArray ja = JSONArray.fromObject(indents);
			System.out.print(ja);
			out.print(ja);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取历史订单包括被拒绝的和同意的，撤销的已经被搜出来了，但暂时先过滤掉
	 * @param request
	 * @param response
	 */
	private void getHistoryByManager(HttpServletRequest request,
			HttpServletResponse response) {
		Manager manager = (Manager) request.getSession().getAttribute("user");
		String uid = manager.getUsername();
		String status1 = "again";
		String status2 = "refuse";
		String status3 = "annul";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ArrayList<Indent> indents = IndentDao.getHistoryIndentByManager(uid,status1,status2,status3);
			for (int i = 0; i < indents.size(); i++) {
				Indent indent = indents.get(i);
				if ("again".equals(indent.getStatus())) {
					indent.setStatus("交易已成功");
					System.out.print(indent.getStatus());
				}else if("refuse".equals(indent.getStatus())) {
					indent.setStatus("已拒绝");
				}else {
					indents.remove(i);
				}
			}
			JSONArray ja = JSONArray.fromObject(indents);
			out.print(ja);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 例行注释，获取已成功订单
	 * @param request
	 * @param response
	 */
	private void getSuccessByManager(HttpServletRequest request,
			HttpServletResponse response) {
		Manager manager = (Manager) request.getSession().getAttribute("user");
		String uid = manager.getUsername();
		String status = "again";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ArrayList<Indent> indents = IndentDao.getIndentByManager(uid,status);
			JSONArray ja = JSONArray.fromObject(indents);
			out.print(ja);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取待审批的订单
	 * @param request
	 * @param response
	 */
	private void getWaitExamine(HttpServletRequest request,
			HttpServletResponse response) {
		Manager manager = (Manager) request.getSession().getAttribute("user");
		String uid = manager.getUsername();
		String status = "WaitExamine";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			ArrayList<Indent> indents = IndentDao.getIndentByManager(uid,status);
			for (int i = 0; i < indents.size(); i++) {
				if(indents.get(i).getSurplus()<=0){
					indents.remove(i);
				}
			}
			JSONArray ja = JSONArray.fromObject(indents);
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












