package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.WebSocketTool;
import entity.Good;
import entity.Manager;
import Dao.GoodDao;


@WebServlet("/SendGoodServlet")
public class SendGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public SendGoodServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.print("竟来了");
		sendGood(request,response);
	}

	/**
	 * 将数据写入数据库
	 * 需要传入request
	 * @param request
	 * @param response
	 */
	private void sendGood(HttpServletRequest request,
			HttpServletResponse response) {
		Good good = getGood(request,response);
		Manager manager = (Manager) request.getSession().getAttribute("user");
		good.setUid(manager.getUsername());
		PrintWriter out = null;
		boolean flag = false;
		try {
			flag = GoodDao.addGood(good);
			callClient(good.getUid());
			out = response.getWriter();
			if(flag){
				out.print("发布成功");
			}else{
				out.print("发布失败");
			}	
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通知客户，发布了新货物
	 * @param uid
	 */
	private void callClient(String uid) {
		String message = "有新货物";
		WebSocketTool.callAllClient(uid,message);
	}

	/**
	 * 封装货物订单对象
	 * @param request
	 * @param response
	 * @return
	 */
	private Good getGood(HttpServletRequest request,
			HttpServletResponse response) {
		Good good = new Good();
		String company = request.getParameter("company");
		int num = Integer.parseInt(request.getParameter("num"));
		float price = Float.parseFloat(request.getParameter("price"));
		String description = request.getParameter("description");
		Float ownerQQ = Float.parseFloat(request.getParameter("ownerQQ"));
		Manager manager = new Manager();
		good.setCorp(company);
		good.setStatus("待接单");
		good.setDescription(description);
		good.setSurplus(num);
		good.setOwnerQQ(ownerQQ);
		good.setPrice(price);
		good.setUid(manager.getUsername());
		return good ;
	}

}
